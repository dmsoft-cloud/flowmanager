package it.dmsoft.flowmanager.agent.engine.generic.utility.sftp;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

/**
 * Classe manager per la connessione sFTP.
 */
public class SftpConnection {
	private static String CHANNEL_TYPE = "sftp";

	private JSch secureChannel;
	private String host;
	private String user;
	private String password;
	private int port;
	private Session session;
	private ChannelSftp sFTPChannel;
	private String hostKeyAlias;
	private Logger logger = Logger.getLogger(SftpConnection.class);

	/**
	 * @param host
	 *            - username@hostname (oppure @localhost)
	 */
	public SftpConnection(String host, int port) {
		this.secureChannel = new JSch();
		this.port = port;
		this.host = host;
	}

	/**
	 * Inizia la sessione SSH e apri la connessione sFTP per iniziare il
	 * trasferimento.
	 * 
	 * @throws JSchException
	 */
	public void open() throws JSchException {
		// Ricava hostname e username.
		int at = this.host.indexOf('@');
		this.user = this.host.substring(0, at);
		this.host = this.host.substring(at + 1);
		
		// Connessione SSH.
		this.session = this.secureChannel.getSession(this.user, this.host, this.port);
		if (this.hostKeyAlias != null) {
			this.session.setHostKeyAlias(this.hostKeyAlias);
			logger.debug("Impostato HostKeyAlias: " + this.hostKeyAlias);
		}
		if (this.password != null) {
			this.session.setPassword(this.password);
		}
		
		// Imposta l'algoritmo corretto della chiave pubblica del server. 
		// Jsch preferisce ssh-rsa, ma nel nostro caso abbiamo ecdsa-sha2-nistp256, quindi dobbiamo esplicitarlo noi.
		HostKey[] hostKeys = this.secureChannel.getHostKeyRepository().getHostKey();
	    for (HostKey hk : hostKeys) { 
	    	String[] hosts = hk.getHost().split(",");
	    	for (String h : hosts) {
	    		if (h.equals(this.host) || h.equals(this.hostKeyAlias)) {
	    			String type = hk.getType();
	    			session.setConfig("server_host_key", type);	    			
	    		}
	    	}
	    }
	    
		this.session.connect();

		// Inizia sessione sFTP.
		Channel channel = this.session.openChannel(CHANNEL_TYPE);
		channel.connect();
		this.sFTPChannel = (ChannelSftp) channel;
	}

	/**
	 * Carica il file known_hosts da AS400.
	 * 
	 * @param as400path
	 *            - il path in formato AS400 che punta al file known_hosts
	 * @throws JSchException
	 * @throws FileNotFoundException
	 */
	public void setKnownHosts(String as400path) throws FileNotFoundException, JSchException {
		this.secureChannel.setKnownHosts(as400path);
	}

	/**
	 * Carica il file che contiene la chiave privata del client. 
	 * 
	 * @param as400path
	 *            - il path in formato AS400 che punta al file che contiene la
	 *            chiave privata
	 * @throws JSchException
	 * @throws IOException
	 */
	public void setIdentity(String as400path, String password) throws JSchException, IOException {
		if (password != null && !password.isEmpty()) {
			this.secureChannel.addIdentity(as400path, password);
		} else {
			this.secureChannel.addIdentity(as400path);			
		}
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Imposta l'alias per l'host key.
	 */
	public void setHostKeyAlias(String hostKeyAlias) {
		this.hostKeyAlias = hostKeyAlias;
	}

	public void close() {
		this.sFTPChannel.quit();
		this.session.disconnect();
	}

	/**
	 * Vai a directory su server.
	 * 
	 * @param path
	 * @throws SftpException
	 */
	public void cd(String path) throws SftpException {
		this.sFTPChannel.cd(path);
	}

	/**
	 * Vai a directory in locale.
	 * 
	 * @param path
	 * @throws SftpException
	 */
	public void lcd(String path) throws SftpException {
		this.sFTPChannel.lcd(path);
	}

	/**
	 * Carica il file puntato da src sulla cartella in remoto selezionata.
	 * 
	 * @throws SftpException
	 */
	public void put(String src) throws SftpException {
		this.sFTPChannel.put(src);
	}

	/**
	 * Carica lo stream src nel file con nome dest sulla cartella in remoto
	 * selezionata.
	 * 
	 * @throws SftpException
	 */
	public void put(InputStream src, String dest) throws SftpException {
		if (dest == null) {
			dest = "";
		}
		this.sFTPChannel.put(src, dest);
	}
	
	/**
	 * Carica il file o la cartella src in dest.
	 * 
	 * @throws SftpException
	 */
	public void put(String src, String dest) throws SftpException {
		this.sFTPChannel.put(src, dest);
	}

	/**
	 * Scarica il file src dalla cartella in remoto alla cartella in locale
	 * usando come nome dest.
	 * 
	 * @throws SftpException
	 */
	public void get(String src, String dest) throws SftpException {
		this.sFTPChannel.get(src, dest);
	}
	
	/**
	 * Scarica il file src dalla cartella in remoto come stream.
	 * 
	 * @throws SftpException
	 */
	public InputStream get(String src) throws SftpException {
		return this.sFTPChannel.get(src);
	}
	
	/**
	 * Lista i file presenti in path.
	 * 
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public Vector<LsEntry> ls(String path) throws SftpException {
		return this.sFTPChannel.ls(path);
	}
	
	/**
	 * Ritorna true se il path remoto passato punta ad una cartella;
	 * false altrimenti.
	 * @throws SftpException 
	 */
	public boolean isRemoteDir(String remotePath) throws SftpException {
		SftpATTRS attr = this.sFTPChannel.stat(remotePath);
		return attr.isDir();
	}
	
	/**
	 * Elimina il path puntato in remoto.
	 * 
	 * @throws SftpException
	 */
	public void rm(String path) throws SftpException {
		this.sFTPChannel.rm(path);
	}
	
	/**
	 * Imposta i parametri sftp in un colpo solo.
	 * @throws JSchException 
	 * @throws IOException se il file known_hosts o il file contenente la firma privata non esistono.
	 */
	public void setSftpParameters(SftpParameters sftpParams) throws JSchException, IOException {
		if (sftpParams == null) {
			return;
		}
		String kh = sftpParams.getKnownHostsPath();
		String idp = sftpParams.getIdentityPath();
		String idpwd = sftpParams.getIdentityPassword();
		String hka = sftpParams.getHostKeyAlias();
		String sftppwd = sftpParams.getSftpPassword();
		
		if (kh != null && !kh.isEmpty()) {
			this.setKnownHosts(kh);
		}
		if (idp != null && !idp.isEmpty()) {
			this.setIdentity(idp, idpwd);
		}
		if (hka != null && !hka.isEmpty()) {
			this.setHostKeyAlias(hka);
		}
		if (sftppwd != null && !sftppwd.isEmpty()) {
			this.setPassword(sftppwd);
		}
	}
}

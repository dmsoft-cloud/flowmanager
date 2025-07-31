package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.math.BigDecimal;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;


//	Host: indica il nome/ip del server remoto. Prevedere una stringa di almeno 100 in quanto alcuni server hanno nomi molto lunghi
//	Porta: indica la porta da utilizzare per la connessione SFTP/FTP. Se non indicata si utilizzerà la porta standard prevista per il protocollo in oggetto.
//	Folder remoto: indica il path all'interno del quale dovr avvenire la spedizione/ricezione sul server remoto
//	File name remoto: indica il nome che dovrà avere il file sul server remoto. Qualora non impostato il nome remoto sarà uguale a quello del file locale indicato nel campo 'nome stream file'
//	Utente FTP/SFTP: utente da utilizzare per la connessione FTP o SFTP sul server remoto.
//	Utente as400 sftp: utente da usare per la connessione con sftp per reperimento chiavi sftp
//	Password utente remoto FTP: password da usare nel caso di connessione con ftp. La password non dovrà essere in chiaro a video.
//	KnownHostsFile: host conosciuti 
//	KeyFile: il file contenenta la chiava per la connessione.

public class TrasmissionParams {
	
	

	private String host;
	
	private BigDecimal port;
	
	private String remote_Folder;
	
	private String remote_File_Name;
	
	private String local_Folder;
	
	private String local_File_Name;
	
	private String user;
	
	private String user_Sftp;
	
	private String password;
	
	private YesNo trustHost;
	
	private String known_Hosts_File;
	
	private String keyFile;
	
	private String hostKeyAlias;
	
	private YesNo passive_mode;
	
	private YesNo ftp_Secure;
	
	private BigDecimal retryCount;
	
	private BigDecimal retryIntervall;
	
	private boolean removeRemoteFile;
	
	private boolean launchErrorIfNoFileFound = false;
	
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public BigDecimal getPort() {
		return port;
	}

	public void setPort(BigDecimal port) {
		this.port = port;
	}

	public String getRemote_Folder() {
		return remote_Folder;
	}

	public void setRemote_Folder(String remote_Folder) {
		this.remote_Folder = remote_Folder;
	}

	public String getRemote_File_Name() {
		return remote_File_Name;
	}

	public void setRemote_File_Name(String remote_File_Name) {
		this.remote_File_Name = remote_File_Name;
	}

	public String getLocal_Folder() {
		return local_Folder;
	}

	public void setLocal_Folder(String local_Folder) {
		this.local_Folder = local_Folder;
	}

	public String getLocal_File_Name() {
		return local_File_Name;
	}

	public void setLocal_File_Name(String local_File_Name) {
		this.local_File_Name = local_File_Name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser_Sftp() {
		return user_Sftp;
	}

	public void setUser_Sftp(String user_Sftp) {
		this.user_Sftp = user_Sftp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKnown_Hosts_File() {
		return known_Hosts_File;
	}

	public void setKnown_Hosts_File(String known_Hosts_File) {
		this.known_Hosts_File = known_Hosts_File;
	}

	public String getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

	public YesNo getPassive_mode() {
		return passive_mode;
	}

	public void setPassive_mode(YesNo passive_mode) {
		this.passive_mode = passive_mode;
	}
	
	public BigDecimal getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(BigDecimal retryCount) {
		this.retryCount = retryCount;
	}

	public BigDecimal getRetryIntervall() {
		return retryIntervall;
	}

	public void setRetryIntervall(BigDecimal retryIntervall) {
		this.retryIntervall = retryIntervall;
	}
	
	public boolean isRemoveRemoteFile() {
		return removeRemoteFile;
	}

	public void setRemoveRemoteFile(boolean removeRemoteFile) {
		this.removeRemoteFile = removeRemoteFile;
	}
	
	public String getHostKeyAlias() {
		return hostKeyAlias;
	}

	public void setHostKeyAlias(String hostKeyAlias) {
		this.hostKeyAlias = hostKeyAlias;
	}

	public boolean isLaunchErrorIfNoFileFound() {
		return launchErrorIfNoFileFound;
	}

	public void setLaunchErrorIfNoFileFound(boolean launchErrorIfNoFileFound) {
		this.launchErrorIfNoFileFound = launchErrorIfNoFileFound;
	}
	
	public YesNo getFtp_secure() {
		return ftp_Secure;
	}

	public void setFtp_secure(YesNo ftp_Secure) {
		this.ftp_Secure = ftp_Secure;
	}
	
	public YesNo getTrustHost() {
		return trustHost;
	}

	public void setTrustHost(YesNo trustHost) {
		this.trustHost = trustHost;
	}

	public YesNo getFtp_Secure() {
		return ftp_Secure;
	}

	public void setFtp_Secure(YesNo ftp_Secure) {
		this.ftp_Secure = ftp_Secure;
	}

	@Override
	public String toString() {
		return "TrasmissionParams [host=" + host + ", port=" + port + ", remote_Folder=" + remote_Folder
				+ ", remote_File_Name=" + remote_File_Name + ", local_Folder=" + local_Folder + ", local_File_Name="
				+ local_File_Name + ", user=" + user + ", user_Sftp=" + user_Sftp + ", password=" + password
				+ ", trustHost=" + trustHost + ", known_Hosts_File=" + known_Hosts_File + ", keyFile=" + keyFile
				+ ", hostKeyAlias=" + hostKeyAlias + ", passive_mode=" + passive_mode + ", ftp_Secure=" + ftp_Secure
				+ ", retryCount=" + retryCount + ", retryIntervall=" + retryIntervall + ", removeRemoteFile="
				+ removeRemoteFile + ", launchErrorIfNoFileFound=" + launchErrorIfNoFileFound + "]";
	}

}

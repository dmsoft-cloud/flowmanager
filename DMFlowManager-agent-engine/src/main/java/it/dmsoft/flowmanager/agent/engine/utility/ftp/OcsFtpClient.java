package it.dmsoft.flowmanager.agent.engine.utility.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import it.dmsoft.flowmanager.agent.engine.ftp.manager.FtpSendManager;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.LoggerStream;

public class OcsFtpClient {

	private Logger logger = Logger.getLogger(OcsFtpClient.class);
	private String server;
	private int port;
	private String user;
	private String password;
	private boolean passiveMode;
	private FTPClient ftp;
	private FTPSClient ftps;
	private boolean secure;

	public OcsFtpClient(FtpParameters parameters, Logger logger) {
		this.server = parameters.getFtpHost();
		this.port = parameters.getFtpPort();
		this.user = parameters.getFtpUser();
		this.password = parameters.getFtpPassword();
		this.passiveMode = parameters.isPassiveMode();
		this.logger = logger;
		this.secure = parameters.isSecureConnection();
		logger.debug(new KeyValueLog("FtpParameters", parameters.toString()));
	}

	public void open() throws IOException {
		if (secure) {
			openSecure();
		} else {
			openNotSecure();
		}
	}

	private void openNotSecure() throws IOException {
		if (ftp != null) {
			this.close();
		}

		logger.debug("Ftp connessione");
		ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintStream(new LoggerStream(logger, logger.getLevel()))));
		ftp.connect(server, port);
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new IOException("Exception in connecting to FTP Server");
		}
		if (passiveMode) {
			logger.debug("Passive mode");
			ftp.enterLocalPassiveMode();
		}
		logger.debug("Login");
		if (!ftp.login(user, password)) {
			logger.error("FTP non connesso");
			throw new IOException("Connessione FTP non riuscita");
		}
		ftp.doCommand("site nam 1", "");
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
	}

	private void openSecure() throws IOException {
		if (ftps != null) {
			this.close();
		}

		logger.debug("Ftps connessione");
		ftps = new FTPSClient();
		ftps.addProtocolCommandListener(new PrintCommandListener(new PrintStream(new LoggerStream(logger, logger.getLevel()))));
		ftps.connect(server, port);
		int reply = ftps.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftps.disconnect();
			throw new IOException("Exception in connecting to FTP Server");
		}
		if (passiveMode) {
			logger.debug("Passive mode");
			ftps.enterLocalPassiveMode();
		}
		logger.debug("Login");
		if (!ftps.login(user, password)) {
			logger.error("FTPS non connesso");
			throw new IOException("Connessione FTPS non riuscita");
		}
		ftps.doCommand("site nam 1", "");
		ftps.setFileType(FTP.BINARY_FILE_TYPE);
		ftps.execPBSZ(0);
		ftps.execPROT("P");

	}

	public void close() throws IOException {
		if (secure) {
			ftps.disconnect();
		} else {
			ftp.disconnect();
		}
	}

	public void downloadFile(String source, String destination) throws IOException {
		FileOutputStream out = new FileOutputStream(destination);
		if (secure) {
			ftps.retrieveFile(source, out);
		} else {
			ftp.retrieveFile(source, out);
		}
	}

	public void put(File file, String path) throws IOException {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(new KeyValueLog("File", file.getAbsolutePath()), new KeyValueLog("path", path));
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error("Impossibile leggere il file di input", e);
			throw new IOException(e);
		}
		boolean copied;
		if (secure) {
			copied = ftps.storeFile(path, fis);
		} else {
			copied = ftp.storeFile(path, fis);
		}
		if (!copied) {
			throw new IOException("Impossibile inviare il file");
		}
	}

	public boolean isRemoteDir(String remotePath) throws IOException {
		if (remotePath == null || remotePath.trim().equals("")) {
			return true;
		}
		this.logger.debug("Analizzo il percorso " + remotePath);
		if (secure) {
			return this.ftps.changeWorkingDirectory(remotePath);
		} else {
			return this.ftp.changeWorkingDirectory(remotePath);
		}
	}

	public FTPFile[] ls(String pathname) throws IOException {
		if (secure) {
			return this.ftps.listFiles(pathname);
		} else {
			return this.ftp.listFiles(pathname);
		}
	}

	public void get(String remoteSrc, String dest) throws IOException {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(new KeyValueLog("remoteSrc", remoteSrc), new KeyValueLog("dest", dest));
		}
		OutputStream out = new FileOutputStream(new File(dest));
		boolean copied;
		if (secure) {
			copied = this.ftps.retrieveFile(remoteSrc, out);
		} else {
			copied = this.ftp.retrieveFile(remoteSrc, out);
		}
		if (!copied) {
			throw new IOException("Impossibile recuperare il file");
		}
	}

	public boolean rm(String src) throws IOException {
		if (secure) {
			return this.ftps.deleteFile(src);
		} else {
			return this.ftp.deleteFile(src);
		}
	}

	public void put(File localFile, String remotePath, String destFile) throws IOException {
		String currentWorkingDirectory = "";
		if (remotePath.isEmpty() || remotePath.equals(FtpSendManager.FORWARD_SLASH)) {
			if (secure) {
				currentWorkingDirectory = ftps.printWorkingDirectory();
			} else {
				currentWorkingDirectory = ftp.printWorkingDirectory();
			}
		}
		String path = Paths.get(currentWorkingDirectory, destFile).toString();
		if (logger.isDebugEnabled()) {
			logger.debug(new KeyValueLog("Remote path", path));
		}
		put(localFile, path);
	}
}

package it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.FileMailInfo;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.exception.PecDataException;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.InvioPecResponse;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce.OCSMimeMessage;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Allegato;

public class InvioPecUtility {
	public static FileMailInfo saveMessageIFS(PecDataMail request, OCSMimeMessage message, Logger logger) throws MessagingException, IOException, PecDataException {
		FileMailInfo dataMail = request.getPathSaveMail();
		logger.info("Salvataggio email su IFS");

		if (dataMail == null || dataMail.getPathMail() == null || "".equals(dataMail.getPathMail())) {
			logger.info("Non è richiesto il salvataggio della mail inviata");
			return dataMail;
		}
		String pathToSave = dataMail.getPathMail() + File.pathSeparator + message.getMessageID() + ".txt";
		logger.debug(new KeyValueLog("Salvataggio email nel percorso", pathToSave));
		if (!"".equals(dataMail.getFileMail())) {
			logger.debug("nome già impostato");
		} else {
			logger.debug("nome non impostato, impostazione automatica nome mail");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HHmmssSSSS");
			dataMail.setFileMail("Mail_" + sdf.format(timestamp) + ".eml");
		}
		String pathSeparator = dataMail.getPathMail().endsWith(File.separator) ? "" : File.separator;
		String path = dataMail.getPathMail() + pathSeparator + dataMail.getFileMail();
		logger.debug(new KeyValueLog("Path salvatagio mail", path));
		FileOutputStream fos = new FileOutputStream(new File(path));
		message.writeTo(fos);
		fos.close();
		return dataMail;
	}

	public static void saveMessageIMAP(PecDataMail request, OCSMimeMessage message, String tls, Logger logger) throws MessagingException, IOException, PecDataException {
		String protocol = null;
		String imapHost = request.getImapHost();
		int imapPort = request.getImapPort();
		String user = request.getImapUser();
		String pass = request.getImapPwd();
		if (request.isImapSecure()) {
			protocol = "imaps";
		} else {
			protocol = "imap";
		}
		if (request.getImapEmailInfo() == null || request.getImapEmailInfo().getPathMail() == null || request.getImapEmailInfo().getPathMail().equals("")) {
			logger.info("Dati per salvataggio IMAP non presenti");
			return;
		}
		if (imapHost == null || imapHost.equals("")) {
			logger.error("Imap Host non impostato, campo obbligatorio per salvataggio IMAP");
			throw new PecDataException("Imap Host non impostato, campo obbligatorio per salvataggio IMAP");
		}

		if (user == null || user.equals("") || pass == null || pass.equals("")) {
			logger.error("Credenziali Imap non impostate, campi obbligatori per salvataggio IMAP");
			throw new PecDataException("Credenziali Imap non impostate, campi obbligatori per salvataggio IMAP");
		}

		Properties props = System.getProperties();
		logger.debug(new KeyValueLog("Protocollo utilizzato", protocol));
		props.setProperty("mail.store.protocol", protocol);
		if (tls != null && !tls.equals("")) {
			logger.debug(new KeyValueLog("Versione TLS", tls));
			props.put("mail." + protocol + ".ssl.protocols", "TLSv" + tls);
		}
		logger.info("Apertura sessione");
		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore(protocol);
		store.connect(imapHost, imapPort, user, pass);

		logger.info("Avvio connessione a cartella");
		logger.debug("Cartella di destinazione: " + request.getImapEmailInfo().getPathMail());
		Folder inviata = store.getFolder(request.getImapEmailInfo().getPathMail());
		inviata.open(Folder.READ_WRITE);
		Message[] msgs = new Message[1];
		msgs[0] = message;

		logger.debug("Inserimento Messaggio nella cartella");
		inviata.appendMessages(msgs);
		inviata.close(true);
		store.close();
		logger.info("Salvataggio effettuato, connessione terminata.");
	}

	public static OCSMimeMessage sendEmail(PecDataMail request, String tls, int timeout, Logger logger) throws MessagingException, PecDataException, IOException {
		OCSMimeMessage ocsMessage = null;
		String protocolloInvio = null;
		boolean isSecureConnection = false;
		int numeroDestinatari = 0;

		if (request.isSecure()) {
			protocolloInvio = "smtps";
			if (request.getPort() == 0) {
				request.setPort(465);
			}
		} else {
			protocolloInvio = "smtp";
			if (request.getPort() == 0) {
				request.setPort(25);
			}
		}
		if (request.getHostname() == null || request.getHostname().equals("")) {
			logger.error("Campo Hostname obbligatorio per invio email");
			throw new PecDataException("Campo Hostname obbligatorio per invio email");
		}
		if (request.getFrom() == null || request.getFrom().equals("")) {
			logger.error("Campo From obbligatorio per invio email");
			throw new PecDataException("Campo From obbligatorio per invio email");
		}
		if (request.getSubject() == null || request.getSubject().equals("")) {
			logger.error("Campo Subject obbligatorio per invio email");
			throw new PecDataException("Campo Subject obbligatorio per invio email");
		}

		Properties props = new Properties();

		logger.debug(new KeyValueLog("Protocollo di invio utilizzato", protocolloInvio));
		props.put("mail.transport.protocol", protocolloInvio);

		logger.debug(new KeyValueLog("Hostname", request.getHostname()));
		logger.debug(new KeyValueLog("port", request.getPort()));
		props.put("mail.smtps.host", request.getHostname());

		if (tls != null && !tls.equals("")) {
			logger.debug("Impostazione protocollo a TLSv" + tls);
			props.put("mail.smtps.ssl.protocols", "TLSv" + tls);
		}

		if (timeout > 0) {
			logger.debug(new KeyValueLog("Impostazione timeout: ", timeout));
			props.put("mail.smtp.timeout", timeout);
			props.put("mail.smtp.connectiontimeout", timeout);
		}

		if (request.isSecure() && ((request.getSmtpUsername() != null && !request.getSmtpUsername().equals("")) && (request.getSmtpPassword() != null && !request.getSmtpPassword().equals("")))) {
			logger.debug("Connessione con autenticazione");
			isSecureConnection = true;
			props.put("mail.smtps.auth", "true");
		} else {
			logger.warn("Dati di autenticazione non valorizzati");
			logger.debug("Connessione senza autenticazione");
			props.put("mail.smtps.auth", "false");
		}

		logger.info("Caricamento sessione");
		Session mailSession = Session.getInstance(props);
		Transport transport = mailSession.getTransport();

		logger.info("Sessione creata, creazione messaggio");
		ocsMessage = new OCSMimeMessage(mailSession, request.getFrom());

		logger.debug("Inserimento soggetto in messaggio");
		ocsMessage.setSubject(request.getSubject());

		MimeMultipart multiPart = new MimeMultipart();
		logger.debug("Impostazione body messaggio");
		MimeBodyPart bodyPart = new MimeBodyPart();
		if (request.getBody() != null && !request.getBody().equals("")) {
			if (request.isTestoHtml()) {
				bodyPart.setContent(request.getBody(), "text/html");
			} else {
				bodyPart.setContent(request.getBody(), "text/plain");
			}
		}

		multiPart.addBodyPart(bodyPart);

		// Allegati
		if (request.esistonoAllegati()) {
			logger.debug("Sono presenti degli allegati, inserimento in corso");
			for (Allegato allegato : request.getAllegati()) {
				allegaDocumenti(allegato, multiPart, logger);
			}
		}
		logger.debug("Impostazione Content");
		ocsMessage.setContent(multiPart);

		logger.debug("Definizione to");
		if (request.getTos() != null) {
			for (String to : request.getTos()) {
				if (!"".equals(to)) {
					ocsMessage.addRecipients(Message.RecipientType.TO, to);
					logger.debug("Inserito in TO: " + to);
					numeroDestinatari++;
				}
			}
		}
		logger.debug("Definizione cc");
		if (request.getCcs() != null) {
			for (String cc : request.getCcs()) {
				if (!"".equals(cc)) {
					ocsMessage.addRecipients(Message.RecipientType.CC, cc);
					logger.debug("Inserito in CC: " + cc);
					numeroDestinatari++;
				}
			}
		}
		logger.debug("Definizione bcc");
		if (request.getBccs() != null) {
			for (String bcc : request.getBccs()) {
				if (!"".equals(bcc)) {
					ocsMessage.addRecipients(Message.RecipientType.BCC, bcc);
					logger.debug("Inserito in BCC: " + bcc);
					numeroDestinatari++;
				}
			}
		}

		Address addresses[] = new Address[1];
		addresses[0] = new InternetAddress(request.getFrom());
		ocsMessage.addFrom(addresses);

		if (isSecureConnection) {
			logger.debug("Apertura connessione con autenticazione");
			transport.connect(request.getHostname(), request.getPort(), request.getSmtpUsername(), request.getSmtpPassword());
		} else {
			logger.debug("Apertura connessione senza autenticazione");
			transport.connect(request.getHostname(), request.getPort(), null, null);
		}

		logger.debug("Numero destinatari impostati: " + numeroDestinatari);
		ocsMessage.setSentDate(new Date());
		logger.debug("Invio messaggio");
		transport.sendMessage(ocsMessage, ocsMessage.getAllRecipients());

		logger.debug("Chiusura connessione");
		transport.close();
		return ocsMessage;
	}

	private static MimeMultipart allegaDocumenti(Allegato allegato, MimeMultipart multipart, Logger logger) throws MessagingException {
		File file = new File(allegato.getPath());
		if (!file.exists()) {
			logger.warn("Path per gli allegati : '" + allegato.getPath() + "' non esiste.");
			return multipart;
		}

		if (file.isDirectory()) {
			logger.debug("Path per gli allegati : '" + allegato.getPath() + "' è una cartella.");
			File[] directoryListing = file.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					Allegato allegatoInDir = new Allegato();
					allegatoInDir.setPath(child.getAbsolutePath());
					logger.debug("Allega: '" + child.getPath());
					multipart = allegaDocumenti(allegatoInDir, multipart, logger);
				}
			}
		}

		if (file.isFile()) {
			MimeBodyPart allegatoBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(allegato.getPath());
			allegatoBodyPart.setDataHandler(new DataHandler(source));
			allegatoBodyPart.setFileName(file.getName());
			if ("".equals(allegato.getContentId())) {
				allegato.setContentId(file.getName());
			}
			if (!"".equals(allegato.getContentId())) {
				allegatoBodyPart.setContentID(allegato.getContentId());
			}
			logger.debug("Allego file: " + allegato);
			multipart.addBodyPart(allegatoBodyPart);
		}

		return multipart;
	}
	
	public static InvioPecResponse responsePec(PecDataMail request, String tls, int timeout, Logger logger) throws Exception {
		InvioPecResponse response = new InvioPecResponse();
		// Logica Invio Email
		logger.info("Inizio invio Email");
		OCSMimeMessage ocsMessage = sendEmail(request, tls, timeout, logger);
		logger.info("Invio terminato");

		saveMessageIMAP(request, ocsMessage, tls, logger);
		FileMailInfo fileMailInfo = saveMessageIFS(request, ocsMessage, logger);
		response.setPathSavedMail(fileMailInfo);

		logger.info(new KeyValueLog("MessageId inviato", ocsMessage.getMessageID()));
		if (ocsMessage.getMessageID() != null && !ocsMessage.getMessageID().equals("")) {
			response.setMsgId(ocsMessage.getMessageID());
		} else {
			throw new IOException("Errore nel recupero del messageId");
		}
		logger.info("Invio email completato con successo");

		return response;
	}
}

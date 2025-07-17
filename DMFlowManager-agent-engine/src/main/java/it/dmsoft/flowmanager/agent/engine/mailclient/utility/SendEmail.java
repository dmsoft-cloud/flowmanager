package it.dmsoft.flowmanager.agent.engine.mailclient.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.FileMailInfo;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.ResponseMail;

public abstract class SendEmail {
	private static Logger logger = Logger.getLogger(SendEmail.class);

	private SendEmail() {

	}

	public static ResponseMail sendMail(DataMail request, String tls) throws MessagingException, FileNotFoundException, IOException {
		logger.info("Invio mail: " + request);

		final String user = request.getSmtpUsername();
		final String password = request.getSmtpPassword();

		// impostazione proprietà
		Properties properties = System.getProperties();

		if (request.isSecure()) {
			if (tls == null) {
				tls = "1.2";
			}
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.ssl.protocols", "TLSv" + tls);
		}

		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", request.getHostname());
		properties.put("mail.smtp.port", request.getPort());

		properties.put("mail.smtp.timeout", request.getTimeout());
		properties.put("mail.smtp.connectiontimeout", request.getTimeout());

		Authenticator auth = null;

		if (request.getSmtpUsername() != null && !"".equals(request.getSmtpUsername().trim()) && request.getSmtpPassword() != null) {
			properties.put("mail.smtp.auth", "true");
			auth = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			};
		} else {
			properties.put("mail.smtp.auth", "false");
		}

		Session session = null;
		if (auth != null) {
			session = Session.getInstance(properties, auth);
		} else {
			session = Session.getInstance(properties);
		}

		MimeMessage message = new MimeMessage(session);

		// Aggiunta mittente
		message.setFrom(new InternetAddress(request.getFrom()));

		// Aggiunta destinatari
		for (String to : request.getTos()) {
			if (!"".equals(to)) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
		}
		for (String cc : request.getCcs()) {
			if (!"".equals(cc)) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			}
		}
		for (String bcc : request.getBccs()) {
			if (!"".equals(bcc)) {
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
			}
		}

		if (logger.isInfoEnabled()) {
			logger.info(new KeyValueLog("Numero di destinatari (to)", Integer.toString(request.getTos().size())));
			logger.info(new KeyValueLog("Numero di destinatari (cc)", Integer.toString(request.getCcs().size())));
			logger.info(new KeyValueLog("Numero di destinatari (bcc)", Integer.toString(request.getBccs().size())));
		}

		if (request.getTos().isEmpty() && request.getCcs().isEmpty() && request.getBccs().isEmpty()) {
			logger.error("Nessun destinatario impostato");
		}

		message.setSubject(request.getSubject(), "UTF-8");

		Multipart multipart = new MimeMultipart();
		message.setContent(multipart);

		// Allegati
		if (request.esistonoAllegati()) {
			for (Allegato allegato : request.getAllegati()) {
				allegaDocumenti(allegato, multipart);
			}
		}

		BodyPart htmlBody = new MimeBodyPart();

		// Messaggio
		String body = "";
		if (request.getBody() != null) {
			body = request.getBody();
		}

		if (request.isTestoHtml()) {
			htmlBody.setContent(body, "text/html");
		} else {
			htmlBody.setContent(body, "text/plain");
		}
		multipart.addBodyPart(htmlBody);
		Transport.send(message);

		logger.debug("Messaggio inviato: " + message.getMessageID());
		FileMailInfo fileMailInfo = saveMail(request, message);

		return new ResponseMail(message.getMessageID(), fileMailInfo);
	}

	private static FileMailInfo saveMail(DataMail dataMail, MimeMessage message) throws MessagingException, IOException {
		logger.debug(new KeyValueLog("Salvataggio mail", dataMail.getPathSaveMail().toString()));
		FileMailInfo fileMailInfo = new FileMailInfo();

		if (dataMail.getPathSaveMail() == null || dataMail.getPathSaveMail().getPathMail() == null || "".equals(dataMail.getPathSaveMail().getPathMail())) {
			logger.debug("Non è richiesto il salvataggio della mail inviata");
			return fileMailInfo;
		}
		fileMailInfo.setPathMail(dataMail.getPathSaveMail().getPathMail());
		if (!"".equals(dataMail.getPathSaveMail().getFileMail())) {
			logger.debug("Impostazione nome mail");
			fileMailInfo.setFileMail(dataMail.getPathSaveMail().getFileMail());
		} else {
			logger.debug("Impostazione automatica nome mail");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HHmmssSSSS");
			fileMailInfo.setFileMail("Mail_" + sdf.format(timestamp) + ".eml");
		}

		String pathSeparator = fileMailInfo.getPathMail().endsWith(File.separator) ? "" : File.separator;
		String path = fileMailInfo.getPathMail() + pathSeparator + fileMailInfo.getFileMail();
		logger.debug(new KeyValueLog("Path salvatagio mail", path));
		FileOutputStream fos = new FileOutputStream(new File(path));
		message.writeTo(fos);
		fos.close();
		return fileMailInfo;
	}

	private static Multipart allegaDocumenti(Allegato allegato, Multipart multipart) throws MessagingException {
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
					multipart = allegaDocumenti(allegatoInDir, multipart);
				}
			}
		}

		if (file.isFile()) {
			BodyPart allegatoBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(allegato.getPath());
			allegatoBodyPart.setDataHandler(new DataHandler(source));
			String fileName = allegato.getPath().substring(allegato.getPath().lastIndexOf("\\") + 1);
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
			allegatoBodyPart.setFileName(fileName);
			if ("".equals(allegato.getContentId())) {
				allegato.setContentId(fileName);
			}
			if (!"".equals(allegato.getContentId())) {
				allegatoBodyPart.setHeader("Content-ID", allegato.getContentId());
			}
			logger.debug("Allego file: " + allegato);
			multipart.addBodyPart(allegatoBodyPart);
		}

		return multipart;

	}

}
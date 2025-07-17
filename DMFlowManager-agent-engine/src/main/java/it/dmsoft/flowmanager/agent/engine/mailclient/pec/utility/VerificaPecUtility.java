package it.dmsoft.flowmanager.agent.engine.mailclient.pec.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.validator.SignedMailValidator;
import org.bouncycastle.pkix.jcajce.PKIXCertPathReviewer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.mail.imap.IMAPFolder;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.exception.PecDataException;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.CheckResult;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.MovePecRequest;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.MovePecResponse;
import it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce.PecConnection;

public class VerificaPecUtility {
	private static Store store;
	private static Session session;
	private static Folder inbox;

	public static boolean init(PecConnection request, int timeout, String tls, Logger logger) throws MessagingException, PecDataException {
		logger.info("Inizializzazione connessione per controllo email");
		Properties props = System.getProperties();
		String protocol;

		if (request.isSecure()) {
			protocol = "imaps";
		} else {
			protocol = "imap";
		}
		if (request.getHostname() == null) {
			logger.error("E' necessario impostare l'hostname da utilizzare per il collegamento imap");
			throw new PecDataException("E' necessario impostare l'hostname da utilizzare per il collegamento imap");
		}
		logger.info("Protocollo utilizzato: " + protocol);
		props.setProperty("mail.store.protocol", protocol);
		if (tls != null && !tls.equals("")) {
			logger.debug("Impostazione protocollo a TLSv" + tls);
			props.put("mail.imaps.ssl.protocols", "TLSv" + tls);
		}

		if (timeout > 0) {
			props.put("mail.smtp.timeout", timeout);
			props.put("mail.smtp.connectiontimeout", timeout);
			logger.debug("Impostazione timeout: " + timeout);
		}

		logger.info("Creazione sessione");
		session = Session.getDefaultInstance(props, null);
		logger.info("Reperimento store dalla sessione");
		store = session.getStore(protocol);

		logger.info("Avvio connessione");
		String username = request.getImapUser();
		String password = request.getImapPwd();
		if (logger.isDebugEnabled()) {
			logger.debug("Hostname: " + request.getHostname());
			logger.debug("Username: " + username);
		}
		if (request.getPort() == 0) {
			logger.debug("Avvio connessione senza impostazione porta");
			store.connect(request.getHostname(), username, password);
		} else {
			logger.debug("Avvio connessione con impostazione porta: " + request.getPort());
			store.connect(request.getHostname(), request.getPort(), username, password);
		}
		return true;
	}

	public static Iterator<Message> retrieveIterator(String InboxFolder, Logger logger) throws MessagingException {
		logger.info("Apertura inboxFolder per la lettura delle email");
		logger.debug("InboxFolder utilizzata: " + InboxFolder);
		inbox = store.getFolder(InboxFolder);

		logger.debug("Apertura inboxFolder in lettura/scrittura");
		inbox.open(Folder.READ_WRITE);

		logger.info("Recupero messaggi della inboxFolder");
		Message[] messages = inbox.getMessages();
		logger.debug("Array messaggi recuperati, conversione in iterator");
		return Arrays.asList(messages).iterator();
	}

	public static CheckResult checkResult(MimeMessage mimeMessage, String TruststorePath, String TruststorePwd, Logger logger) throws Exception {
		ArrayList<String> ar = new ArrayList<String>();
		logger.info("Avvio controllo email");
		if (!(mimeMessage.getContent() instanceof MimeMultipart)) {
			logger.debug("Il messaggio non e' un messaggio multipart");
			return null;
		}

		MimeMultipart multipart = (MimeMultipart) mimeMessage.getContent();
		/*
		 * Il messaggio e' composto da 2 parti: la prima parte e' un messaggio
		 * contente il messaggio inviato, il file daticert.xml e il contenuto
		 * dell'email la seconda parte e' la firma
		 */
		logger.info("Verifica numero di parti");
		if (multipart.getCount() != 2) {
			logger.info("Il messaggio non e' composto da 2 parti");
			return null;
		}

		logger.info("Ricerca del parte contente il file daticert.xml");
		MimeMultipart message = null;
		// Part firma = null;
		for (int i = 0; i < multipart.getCount(); i++) {
			Part part = multipart.getBodyPart(i);
			String disposition = part.getDisposition();
			if (disposition == null) {
				message = new MimeMultipart(part.getDataHandler().getDataSource());
			} else {
				// firma = part;
			}
		}

		if (message == null) {
			logger.info("Parte non trovata");
			return null;
		}

		logger.info("Verifica se la parte trovata contiene un attachment daticert.xml");
		Document datiCertDocument = null;

		int count = 0;

		try {
			count = message.getCount();
		} catch (MessagingException e) {
			logger.error("Si e' verificato un problema con il messaggio", e);
			logMailMessage(mimeMessage, logger);
			return null;
		}

		for (int i = 0; i < count; i++) {
			Part part = message.getBodyPart(i);

			String name = part.getFileName();

			if (name != null && name.equals("daticert.xml")) {
				logger.info("Daticert.xml trovata, apertura dell'allegato in formato xml");
				datiCertDocument = parseDOM(part.getInputStream(), logger);
				break;
			}
		}
		if (datiCertDocument == null) {
			logger.info("Daticert.xml non trovato");
			return null;
		}

		logger.info("Recupero dell'informazione msgid dal file daticert.xml");
		Node msgidNode = datiCertDocument.getElementsByTagName("msgid").item(0);
		if (msgidNode == null) {
			logger.warn("msgidNode null");
			return null;
		}
		String msgid = msgidNode.getTextContent();
		// era OCSPecClient
		if (msgid.indexOf("OcsMailClient") < 0 && msgid.indexOf(".OCSDCT.") < 0) {
			logger.info("Il msgid non corrisponde con quelli generati dal client di OCS, quindi viene scartato");
			logger.debug("Msgid recuperato: " + msgid);
			return null;
		}

		logger.info("Recupero delle informazioni sul file xml");
		Node postacertNode = datiCertDocument.getElementsByTagName("postacert").item(0);
		CheckResult result = new CheckResult();
		logger.debug("Email: " + msgid);
		result.setMessageId(msgid);

		logger.info("Validazione dell'email");
		boolean valid = checkEmail(session, mimeMessage, TruststorePath, TruststorePwd, logger);
		result.setValid(valid);
		logger.debug("Risultato validazione dell'email: " + valid);

		logger.info("Recupero del tipo");
		String tipo = postacertNode.getAttributes().getNamedItem("tipo").getNodeValue();
		logger.debug("Tipo recuperato: " + tipo);
		result.setTipo(tipo);

		logger.info("Recupero dell'errore");
		String errore = postacertNode.getAttributes().getNamedItem("errore").getNodeValue();
		logger.debug("Errore recuperato: " + errore);
		result.setErrore(errore);

		logger.info("Recupero del destinatario della consegna");
		String consegna = NodeUtility.nodeRetrieveSingleString("consegna", datiCertDocument, 0, logger);
		if (!consegna.isEmpty()) {
			result.setConsegna(consegna);
		}

		logger.info("Recupero del destinatario della ricezione");
		ar = NodeUtility.nodeRetrieveMultiString("ricezione", datiCertDocument, logger);
		for (int i = 0; i < ar.size(); i++) {
			result.addRicezione(ar.get(i));
		}
		ar.clear();

		logger.info("Recupero dell'errore esteso");
		String erroreEsteso = NodeUtility.nodeRetrieveSingleString("errore-esteso", datiCertDocument, 0, logger);
		if (!erroreEsteso.isEmpty()) {
			result.setErroreEsteso(erroreEsteso);
		}

		logger.info("Recupero del giorno");
		String giorno = NodeUtility.nodeRetrieveSingleString("giorno", datiCertDocument, 0, logger);
		if (!giorno.isEmpty()) {
			result.setGiorno(giorno);
		}

		logger.info("Recupero dell'ora");
		String ora = NodeUtility.nodeRetrieveSingleString("ora", datiCertDocument, 0, logger);
		if (!ora.isEmpty()) {
			result.setOra(ora);
		}

		logger.info("Recupero del gestore emittente");
		String gestoreEmittente = NodeUtility.nodeRetrieveSingleString("gestore-emittente", datiCertDocument, 0, logger);
		if (!gestoreEmittente.isEmpty()) {
			result.setGestoreEmittente(gestoreEmittente);
		}
		
		result.setRemoteMessageId(mimeMessage.getMessageID());

		return result;
	}

	public static Document parseDOM(InputStream inputStream, Logger logger) throws Exception {
		logger.info("Apertura gestore per lettura file xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		DocumentBuilder parser = factory.newDocumentBuilder();

		// Si e' verificato un caso in cui un daticert.xml faceva riferimento a
		// un DTD con un URL non raggiungibile
		// per evitare che questa situazione mandi in errore il programma e'
		// stato aggiunto un EntityResolver
		// in quanto l'applicazione comunque non prende in considerazione il DTD
		parser.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return new InputSource(new StringReader(""));
			}
		});

		parser.setErrorHandler(new DefaultHandler());
		logger.info("Parsing xml");
		return parser.parse(inputStream);
	}

	public static void logMailMessage(Message message, Logger logger) throws Exception {
		Enumeration<?> headers = message.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header hd = (Header) headers.nextElement();
			logger.error("Header name - : " + hd.getName() + " / Header value: " + hd.getValue());
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		message.writeTo(baos);
		logger.error(baos.toString());
	}

	public static boolean checkEmail(Session session, MimeMessage mimeMessage, String truststorePath, String truststorePwd, Logger logger) throws Exception {
		logger.info("Validazione email");
		Security.addProvider(new BouncyCastleProvider());

		logger.debug("Travaso email");
		mimeMessage = getMimeMessage(session, mimeMessage);

		logger.debug("Creazione KeyStore e CertStore");
		KeyStore caCerts = KeyStore.getInstance("JKS");
		String javaHome = System.getProperty("java.home");
		String path = javaHome + "/lib/security/cacerts";
		String pass = "changeit";
		
		if (truststorePath != null && truststorePwd != null) {
			if (!"".equals(truststorePath.trim()) && !"".equals(truststorePwd.trim())) {
				path = truststorePath;
				pass = truststorePwd;
			}
		}
		FileInputStream fis = new FileInputStream(path);
		caCerts.load(fis, pass.toCharArray());

		PKIXParameters param = new PKIXParameters(caCerts);

		CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(), "BC");
		param.addCertStore(certStore);
		param.setRevocationEnabled(false);

		logger.debug("Inizio validazione email");

		return verifySignedMail(mimeMessage, param, logger);
	}

	private static MimeMessage getMimeMessage(Session session, MimeMessage mimeMessage) throws Exception {
		StringBuffer buf = new StringBuffer();
		Enumeration<?> headers = mimeMessage.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header h = (Header) headers.nextElement();
			String header = h.getName() + ": " + h.getValue() + "\n";
			buf.append(header);
		}

		buf.append("\n");

		InputStream is = mimeMessage.getInputStream();
		byte b[] = new byte[1024];
		int n;
		while ((n = is.read(b)) > 0) {
			buf.append(new String(b, 0, n));
		}
		is.close();

		buf.append(new String(b));

		return new MimeMessage(session, new ByteArrayInputStream(buf.toString().getBytes()));
	}

	private static final String RESOURCE_NAME = "org.bouncycastle.mail.smime.validator.SignedMailValidatorMessages";

	@SuppressWarnings("rawtypes")
	public static boolean verifySignedMail(MimeMessage msg, PKIXParameters param, Logger logger) throws Exception {
		// set locale for the output
		Locale loc = Locale.ENGLISH;

		// validate signatures
		SignedMailValidator validator = new SignedMailValidator(msg, param);

		// iterate over all signatures and print results
		boolean valid = true;
		Iterator it = validator.getSignerInformationStore().getSigners().iterator();
		while (it.hasNext()) {
			SignerInformation signer = (SignerInformation) it.next();
			SignedMailValidator.ValidationResult result = validator.getValidationResult(signer);
			if (result.isValidSignature()) {
				ErrorBundle errMsg = new ErrorBundle(RESOURCE_NAME, "SignedMailValidator.sigValid");
				if (logger.isDebugEnabled()) {
					logger.debug(errMsg.getText(loc));
				}
			} else {
				valid = false;
				ErrorBundle errMsg = new ErrorBundle(RESOURCE_NAME, "SignedMailValidator.sigInvalid");
				if (logger.isDebugEnabled()) {
					logger.debug(errMsg.getText(loc));
				}
				// print errors
				if (logger.isDebugEnabled()) {
					logger.debug("Errors:");
					Iterator errorsIt = result.getErrors().iterator();
					while (errorsIt.hasNext()) {
						ErrorBundle errorMsg = (ErrorBundle) errorsIt.next();
						logger.debug("\t\t" + errorMsg.getDetail(loc));
					}
				}
			}

			if (!result.getNotifications().isEmpty()) {
				logger.debug("Notifications:");
				Iterator notIt = result.getNotifications().iterator();
				while (notIt.hasNext()) {
					ErrorBundle notMsg = (ErrorBundle) notIt.next();
					logger.debug("\t\t" + notMsg.getDetail(loc));
				}
			}
			PKIXCertPathReviewer review = result.getCertPathReview();
			if (review != null) {
				if (review.isValidCertPath()) {
					logger.debug("Certificate path valid");
				} else {
					valid = false;
					logger.debug("Certificate path invalid");
				}

				if (logger.isDebugEnabled()) {
					logger.debug("\nCertificate path validation results:");
					// global errors
					logger.debug("Errors:");
					Iterator errorsIt = review.getErrors(-1).iterator();
					while (errorsIt.hasNext()) {
						ErrorBundle errorMsg = (ErrorBundle) errorsIt.next();
						logger.debug("\t\t" + errorMsg.getDetail(loc));
					}
				}

				if (logger.isDebugEnabled()) {
					logger.debug("Notifications:");
					Iterator notificationsIt = review.getNotifications(-1).iterator();
					while (notificationsIt.hasNext()) {
						ErrorBundle noteMsg = (ErrorBundle) notificationsIt.next();
						logger.debug("\t" + noteMsg.getText(loc));
					}
				}

				// per certificate errors and notifications
				if (logger.isDebugEnabled()) {
					Iterator certIt = review.getCertPath().getCertificates().iterator();
					int i = 0;
					while (certIt.hasNext()) {
						X509Certificate cert = (X509Certificate) certIt.next();
						logger.debug("\nCertificate " + i + "\n========");
						logger.debug("Issuer: " + cert.getIssuerDN().getName());
						logger.debug("Subject: " + cert.getSubjectDN().getName());
						// exportCertificate(cert, "cert" + i);
						// errors
						logger.debug("\tErrors:");
						Iterator errorsIt = review.getErrors(i).iterator();
						while (errorsIt.hasNext()) {
							ErrorBundle errorMsg = (ErrorBundle) errorsIt.next();
							logger.debug("\t\t" + errorMsg.getDetail(loc));
						}

						// notifications
						logger.debug("\tNotifications:");
						Iterator notificationsIt = review.getNotifications(i).iterator();
						while (notificationsIt.hasNext()) {
							ErrorBundle noteMsg = (ErrorBundle) notificationsIt.next();
							logger.debug("\t\t" + noteMsg.getDetail(loc));
						}

						i++;
					}
				}
			}
		}
		return valid;
	}
	
	public static boolean moveMessage(List<Message> messages, String inboxFolder, String imapFolder, Logger logger) {
		try {
			logger.info("Richiesta di spostamento del messaggio");
			logger.debug("Apertura folder di destinazione: " + imapFolder);
			Folder inbox = store.getFolder(inboxFolder);
			Folder folderDiSalvataggio = store.getFolder(imapFolder);

			logger.info(folderDiSalvataggio.exists() + " - " + folderDiSalvataggio.getFullName());

			logger.info("Apertura della forlder in lettura/scrittura");
			inbox.open(Folder.READ_WRITE);
			folderDiSalvataggio.open(Folder.READ_WRITE);

			Message[] messagesArray = messages.toArray(new Message[0]);
			
			logger.debug("Copia messaggio");
			inbox.copyMessages(messagesArray, folderDiSalvataggio);
			
			logger.debug("Cancellazione messaggio precedente");
			inbox.setFlags(messagesArray, new Flags(Flags.Flag.DELETED), true);

			logger.debug("Chiusura folder di destinazione e commit");
			folderDiSalvataggio.close(true);

			logger.debug("Commit della inboxFolder");
			inbox.close(true);
			return true;
		} catch (Exception e) {
			logger.error("Ricevuta eccezione", e);
			return false;
		}
	}
	
	public static MovePecResponse moveMessages(MovePecRequest request, Logger logger) throws Exception {
		MovePecResponse response = new MovePecResponse();
		
		String inboxFolder = request.getInboxFolder();
		HashMap<String, String> moveInfo = request.getMoveInfoInternal();
		HashMap<String, List<Message>> messagesToMove = new HashMap<>();
		
		IMAPFolder imapFolder = (IMAPFolder) store.getFolder(inboxFolder);
		imapFolder.open(Folder.READ_WRITE);
		Message[] messages = imapFolder.getMessages();
		
		for (Message message : messages) {
			MimeMessage mimeMessage = (MimeMessage) message;
			if (moveInfo.containsKey(mimeMessage.getMessageID())) {
				String moveFolder = moveInfo.get(mimeMessage.getMessageID());
				if ("".equals(moveFolder)) {
					continue;
				}
				List<Message> messageList = messagesToMove.getOrDefault(moveFolder, new ArrayList<>());
				messageList.add(mimeMessage);
				messagesToMove.put(moveFolder, messageList);
			}
		}
		
		boolean result = true;
		
		for (String f : messagesToMove.keySet()) {
			boolean moveResult = moveMessage(messagesToMove.get(f), inboxFolder, f, logger);
			
			// Se result è true allora metto il valore presente in moveResult
			// Se result è false allora tengo il valore di result, per indicare a fine
			// esecuzione la presenza di un errore
			result = result ? moveResult : result;
		}
		
		response.setResult(result ? "OK" : "KO");
		
		return response;
	}

}

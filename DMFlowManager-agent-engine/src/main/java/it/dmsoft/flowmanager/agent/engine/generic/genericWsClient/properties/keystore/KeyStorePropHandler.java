package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Set;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.JvmProperties;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class KeyStorePropHandler {
	private static final String[] KNOWN_KEYSTORE_TYPES = { "jks", "pkcs12", "jceks", "dks", "pkcs11" };
	private static KeyManagerFactory kmf;
	private static TrustManagerFactory tmf;

	public static void init(KeyStore keyStoreInUse, String ksInUsePassword, KeyStore trustStoreInUse, Set<String> appNames) throws Exception {
		if (keyStoreInUse != null) {
			kmf = KeyManagerFactory.getInstance("IbmX509");
			kmf.init(keyStoreInUse, ksInUsePassword.toCharArray());
			JvmProperties.getLogger().debug(String.format("KeyStore inizializzato per %s", appNames.toString()));
		} else {
			JvmProperties.getLogger().debug(String.format("Nessun KeyStore in uso per %s", appNames.toString()));
		}
		if (trustStoreInUse != null) {
			tmf = TrustManagerFactory.getInstance("X509");
			tmf.init(trustStoreInUse);
			JvmProperties.getLogger().debug(String.format("TrustStore inizializzato per %s", appNames.toString()));
		} else {
			tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init((KeyStore) null);
			JvmProperties.getLogger().debug(String.format("Nessun TrustStore in uso per %s", appNames.toString()));
		}
	}

	public static void setKeyStore(String protocol, Logger logger) throws KeyManagementException, NoSuchAlgorithmException  {
		if (tmf == null) {
			logger.error("Nessuna TrustManagerFactory trovata!");
			return;
		}
		SSLContext sc = SSLContext.getInstance(protocol);
		if (kmf == null) {
			logger.info("Init SSL senza KeyManagerFactory");
			sc.init(null, tmf.getTrustManagers(), null);
		} else {
			logger.info("Init SSL con KeyManagerFactory");
			sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		}
		SSLContext.setDefault(sc);
	}

	public static void resetKeyStore(String protocol) throws NoSuchAlgorithmException, KeyManagementException  {
		SSLContext sc = SSLContext.getInstance(protocol);
		sc.init(null, null, null);
		SSLContext.setDefault(sc);
	}

	public static KeyStore loadKeyStore(String keyStorePath, String sPassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException {
		File keyStoreFile = new File(keyStorePath);
		if (!keyStoreFile.exists()) {
			return null;
		}
		for (String certType : KNOWN_KEYSTORE_TYPES) {
			KeyStore keystore = loadKeyStoreWithType(keyStorePath, sPassword, certType);
			if (keystore != null) {
				return keystore;
			}
		}
		throw new KeyStoreException(String.format("Impossibile caricare %s", keyStorePath));
	}

	private static KeyStore loadKeyStoreWithType(String keyStorePath, String sPassword, String certType) throws KeyStoreException, NoSuchAlgorithmException, CertificateException {
		KeyStore keystore = KeyStore.getInstance(certType);
		InputStream keyStoreData = null;
		try {
			keyStoreData = new FileInputStream(keyStorePath);
			keystore.load(keyStoreData, sPassword.toCharArray());
		} catch (IOException e) {
			JvmProperties.getLogger().warn(String.format("Lettura con tipo %s di %s fallita. Pwd: %s", certType, keyStorePath, sPassword), e);
			keystore = null;
		} finally {
			if (keyStoreData != null) {
				try {
					keyStoreData.close();
				} catch (IOException e) {
					JvmProperties.getLogger().error(e.getMessage(), e);
				}
			}
		}
		return keystore;
	}

	private KeyStorePropHandler() {
	}
}

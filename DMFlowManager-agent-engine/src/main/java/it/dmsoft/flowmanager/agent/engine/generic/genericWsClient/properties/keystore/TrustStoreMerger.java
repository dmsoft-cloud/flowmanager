package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.keystore;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.JvmProperties;

public class TrustStoreMerger {
	private static KeyStore keyStoreInUse = null;
	private static String ksInUsePassword = "";

	/**
	 * Setta il keystore in input come in uso la prima volta, fonde poi i
	 * successivi al keystore in uso
	 * 
	 * @param keyStorePath
	 * @param sPassword
	 * @param newAlias
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 */
	public static void addTrustStore(String keyStorePath, String sPassword, String newAlias) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		KeyStore keyStore = KeyStorePropHandler.loadKeyStore(keyStorePath, sPassword);
		if (keyStore == null) {
			JvmProperties.getLogger().error(String.format("Il TrustStore '%s' non esiste", keyStorePath));
			return;
		}
		if (keyStoreInUse == null) {
			JvmProperties.getLogger().debug(String.format("Set di '%s' come TrustStore in uso", keyStorePath));
			keyStoreInUse = keyStore;
			ksInUsePassword = sPassword;
		} else {
			JvmProperties.getLogger().debug(String.format("Fondo '%s' con TrustStore in uso", keyStorePath));
			mergeKeystores(keyStoreInUse, keyStore, ksInUsePassword, sPassword, newAlias);
		}
	}

	private static void mergeKeystores(KeyStore ksToFill, KeyStore ksToGetFrom, String sPasswordToFillK, String sPasswordToGetFrom, String newAlias)
			throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
		// Get all aliases in the old keystore
		Enumeration<String> enumeration = ksToGetFrom.aliases();
		int count = 0;
		while (enumeration.hasMoreElements()) {
			// Determine the current alias
			String alias = enumeration.nextElement();
			// Get Certificate
			Certificate cert = ksToGetFrom.getCertificate(alias);
			// Put them altogether in the new keystore
			alias = newAlias + "_" + count;
			ksToFill.setCertificateEntry(alias, cert);
			count++;
		}
		JvmProperties.getLogger().debug(String.format("Aggiunti %d certificati al TrustStore in uso", count));
	}

	private TrustStoreMerger() {
	}

	public static KeyStore getKeyStoreInUse() {
		return keyStoreInUse;
	}
}

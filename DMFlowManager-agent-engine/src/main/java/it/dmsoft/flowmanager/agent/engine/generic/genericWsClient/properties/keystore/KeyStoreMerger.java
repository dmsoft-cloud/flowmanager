package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.keystore;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.properties.JvmProperties;

public class KeyStoreMerger {
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
	public static void addKeyStore(String keyStorePath, String sPassword, String newAlias) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		KeyStore keyStore = KeyStorePropHandler.loadKeyStore(keyStorePath, sPassword);
		if (keyStore == null) {
			JvmProperties.getLogger().error(String.format("Il KeyStore '%s' non esiste", keyStorePath));
			return;
		}
		if (keyStoreInUse == null) {
			JvmProperties.getLogger().debug(String.format("Set di '%s' come KeyStore in uso", keyStorePath));
			keyStoreInUse = keyStore;
			ksInUsePassword = sPassword;
		} else {
			JvmProperties.getLogger().debug(String.format("Fondo '%s' con KeyStore in uso", keyStorePath));
			mergeKeystores(keyStoreInUse, keyStore, ksInUsePassword, sPassword, newAlias);
		}
	}

	private static void mergeKeystores(KeyStore newKeystore, KeyStore oldKeystore, String sPasswordNewK, String sPasswordOldK, String newAlias)
			throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
		// Get all aliases in the old keystore
		Enumeration<String> enumeration = oldKeystore.aliases();
		int count = 0;
		while (enumeration.hasMoreElements()) {
			// Determine the current alias
			String alias = enumeration.nextElement();
			// Get Key & Certificates
			Key key = null;
			try {				
				key = oldKeystore.getKey(alias, sPasswordOldK.toCharArray());
			} catch (UnrecoverableKeyException e) {
				key = oldKeystore.getKey(alias, "".toCharArray());
			}
			Certificate[] certs = oldKeystore.getCertificateChain(alias);
			if (certs == null) {
				continue;
			}
			// Put them altogether in the new keystore
			alias = newAlias + "_" + count;
			newKeystore.setKeyEntry(alias, key, sPasswordNewK.toCharArray(), certs);
			count++;
		}
		JvmProperties.getLogger().debug(String.format("Aggiunti %d certificati al KeyStore in uso", count));
	}

	private KeyStoreMerger() {
	}
	
	public static KeyStore getKeyStoreInUse() {
		return keyStoreInUse;
	}
	
	public static String getKsInUsePassword() {
		return ksInUsePassword;
	}

}

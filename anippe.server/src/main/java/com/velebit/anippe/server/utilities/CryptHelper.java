package com.velebit.anippe.server.utilities;

import com.velebit.anippe.server.config.ApplicationKeyConfigProperty;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.security.Crypter;

public class CryptHelper {
	final static String PASSWORD = CONFIG.getPropertyValue(ApplicationKeyConfigProperty.class);

	public static String encrypt(String textToEncrypt) {
		return BEANS.get(Crypter.class).init(PASSWORD.toCharArray(), 128).encrypt(textToEncrypt);
	}

	public static String decrypt(String textToDecrypt) {
		return BEANS.get(Crypter.class).init(PASSWORD.toCharArray(), 128).decrypt(textToDecrypt);
	}

}

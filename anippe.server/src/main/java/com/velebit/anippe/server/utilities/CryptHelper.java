package com.velebit.anippe.server.utilities;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.security.Crypter;

public class CryptHelper {
	final static String PASSWORD = "f9B8c39XF9B87MfeOQ7KCA6s6E72yF";

	public static String encrypt(String textToEncrypt) {
		return BEANS.get(Crypter.class).init(PASSWORD.toCharArray(), 128).encrypt(textToEncrypt);
	}

	public static String decrypt(String textToDecrypt) {
		return BEANS.get(Crypter.class).init(PASSWORD.toCharArray(), 128).decrypt(textToDecrypt);
	}

}

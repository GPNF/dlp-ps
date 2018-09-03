package app.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

class KeyManip {
	private String password = "123456789"; // DEFAULT PASSWORD;
											// will be using this for the time being
	private byte key[];

	public KeyManip(String password) {
		this.password = password;
	}

	public KeyManip() {
	}

	public SecretKeySpec setKey() {
		SecretKeySpec secretKey = null;
		MessageDigest sha = null;
		try {
			this.key = this.password.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			secretKey = new SecretKeySpec(key, "AES");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return secretKey;
	}
}

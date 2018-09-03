package app.util;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class AESEncryptorDecryptorUtil {
	private KeyManip keyManipulation;

	public AESEncryptorDecryptorUtil() {
		this.keyManipulation = new KeyManip();
	}

	public String encrypt(String stringToEncrypt) {
		String encryptedString = "";
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// setting key with a default password
			cipher.init(Cipher.ENCRYPT_MODE, this.keyManipulation.setKey());
			encryptedString = Base64.encodeBase64String(cipher.doFinal(stringToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return encryptedString.trim();

	}

	public String decrypt(String strToDecrypt) {
		String decryptedString = "";
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, this.keyManipulation.setKey());
			decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
		} catch (Exception e) {

			System.out.println("Error while decrypting: " + e.toString());
		}
		return decryptedString.trim();
	}
}

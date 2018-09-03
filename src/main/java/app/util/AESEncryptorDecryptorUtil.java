package app.util;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class AESEncryptorDecryptorUtil {
	private String encryptedString;
	private String decryptedString;
	private KeyManip keyManipulation;

	public AESEncryptorDecryptorUtil() {
		this.keyManipulation = new KeyManip();
	}

	public void encrypt(String stringToEncrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// setting key with a default password
			cipher.init(Cipher.ENCRYPT_MODE, this.keyManipulation.setKey());
			this.encryptedString = Base64.encodeBase64String(cipher.doFinal(stringToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
	}

	public void decrypt(String strToDecrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, this.keyManipulation.setKey());
			this.decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
		} catch (Exception e) {

			System.out.println("Error while decrypting: " + e.toString());
		}
	}

	public String getEncryptedString() {
		return this.encryptedString.trim();
	}

	public String getDecryptedString() {
		return this.decryptedString;
	}
}

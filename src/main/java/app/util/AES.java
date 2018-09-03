package app.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Aniruddha
 *
 */
public class AES {

	/**
	 * @param stringToEncrypt
	 * @return encryptedString
	 */
	public static String encrypt(String stringToEncrypt) {
		String encryptedString = "";
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
			encryptedString = Base64.encodeBase64String(cipher.doFinal(stringToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return encryptedString.trim();

	}
	
	public static void main(String[] args) {
		
		System.out.println(AES.encrypt("adarsh695@gmail.com"));
	}

	/**
	 * @param strToDecrypt
	 * @return decryptedString
	 */
	public static String decrypt(String strToDecrypt) {
		String decryptedString = "";
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
			decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
		} catch (Exception e) {

			System.out.println("Error while decrypting: " + e.toString());
		}
		return decryptedString.trim();
	}

	/**
	 * @return SecretKeySpec
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private static SecretKeySpec getSecretKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String privateKey = "123456789";
		byte[] key = privateKey.getBytes("UTF-8");
		MessageDigest shaMessageDigest = MessageDigest.getInstance("SHA-1");
		byte[] resultingHash = shaMessageDigest.digest(key);
		byte[] copiedKey = Arrays.copyOf(resultingHash, 16); // use only first
																// 128 bit
		SecretKeySpec secretKey = new SecretKeySpec(copiedKey, "AES");
		return secretKey;
	}

}

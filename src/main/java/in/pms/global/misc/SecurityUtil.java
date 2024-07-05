/**********************************************************
 Project:	   ClientTest	
 File:         SecurityUtil.java
 Created:      Jul 22, 2014
 Last Changed: Jul 22, 2014
 Author:       CDAC

This code is copyright (c) 2014 C-DAC Noida.

 ***********************************************************/
package in.pms.global.misc;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityUtil.
 */
public class SecurityUtil {

	/** The Constant KEY_STRING. */
	private static final String KEY_STRING = "197.255.248.97.234.56.100.241";

	/**
	 * Encrypt.
	 * 
	 * @param source
	 *            the source
	 * @return the string
	 */
	public static synchronized String encrypt(String source) {

		if (source == null || source.trim().length() == 0)
			return "";

		try {
			Key key = getKey();
			Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			desCipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cleartext = source.getBytes();
			byte[] ciphertext = desCipher.doFinal(cleartext);
			return getString(ciphertext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generate key.
	 * 
	 * @return the string
	 */
	public static synchronized String generateKey() {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("DES");
			SecretKey desKey = keygen.generateKey();
			byte[] bytes = desKey.getEncoded();
			return getString(bytes);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * Decrypt.
	 * 
	 * @param source
	 *            the source
	 * @return the string
	 */
	public static synchronized String decrypt(String source) {

		if (source == null || source.trim().length() == 0)
			return "";

		try {
			Key key = getKey();
			Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			byte[] ciphertext = getBytes(source);
			desCipher.init(Cipher.DECRYPT_MODE, key);
			byte[] cleartext = desCipher.doFinal(ciphertext);
			return new String(cleartext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the key.
	 * 
	 * @return the key
	 */
	private static synchronized Key getKey() {
		try {
			byte[] bytes = getBytes(KEY_STRING);
			DESKeySpec pass = new DESKeySpec(bytes);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey s = skf.generateSecret(pass);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the string.
	 * 
	 * @param bytes
	 *            the bytes
	 * @return the string
	 */
	private static synchronized String getString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			sb.append(0x00FF & b);
			if (i + 1 < bytes.length) {
				sb.append(".");
			}
		}
		return sb.toString();
	}

	/**
	 * Gets the bytes.
	 * 
	 * @param str
	 *            the str
	 * @return the bytes
	 */
	private static synchronized byte[] getBytes(String str) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		StringTokenizer st = new StringTokenizer(str, ".", false);
		while (st.hasMoreTokens()) {

			int i = Integer.parseInt(st.nextToken());
			bos.write((byte) i);

		}
		return bos.toByteArray();
	}

	/**
	 * Gets the time.
	 * 
	 * @return the time
	 */
	public static String getTime() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("ss.mm");

		String strTime = sdf.format(cal.getTime());

		return strTime;
	}

	/**
	 * Gets the token.
	 * 
	 * @return the token
	 */
	public static synchronized String getToken() {
		String token = "";

		String mmhh = getTime();

		String[] mmhhValues = mmhh.replace(".", "#").split("#");

		int ssValue = Integer.parseInt(mmhhValues[0]);
		int mmValue = Integer.parseInt(mmhhValues[1]);

		if (ssValue == 0) {
			ssValue = 1;
		}
		if (mmValue == 0) {
			mmValue = 1;
		}

		String code = "IC" + (ssValue % mmValue) + mmhh + "E";

		token = SecurityUtil.encrypt(code);

		return token + "#" + mmhh;
	}

	/**
	 * Gets the md5 hash.
	 * 
	 * @param strContent
	 *            the str content
	 * @return the md5 hash
	 */
	public static synchronized String getMd5Hash(String strContent) {

		StringBuffer sb = new StringBuffer("");

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strContent.getBytes());
			byte byteData[] = md.digest();
			// convert the byte to hex format method 1

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

	public static String sha1(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}

}
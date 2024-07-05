package in.pms.global.service;

import in.pms.global.misc.ResourceBundleFile;

import java.io.IOException;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

	/**
	 * Method Name - encrypt
	 * Purpose - It encrypts the given string using the Jascrypt and then performs a Base-64 encoding on it to remove special characters 
	 * or other characters that cannot be sent over a get request.
	 * Arguments - String to be encrypted
	 * Returns - The Encrypted and Encoded String
	 */
	@Override
	public String encrypt(String stString_p) {
		//Encrypting the received string
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		//Fetching the private key from the resource bundle
		String stPrivateKey = ResourceBundleFile.getValueFromKey("SECRETPRIVATEKEY");
	    encryptor.setPassword(stPrivateKey);        
	    String encryptedText = encryptor.encrypt(stString_p);
	    //Performing Base-64 encoding on the encrypted string
		//sun.misc.BASE64Encoder() deprecated for upgraded Java versions (jdk 11+)
	    //String encode=(new sun.misc.BASE64Encoder()).encode(encryptedText.getBytes());
	    String encode = Base64.getEncoder().encodeToString(encryptedText.getBytes());
		return encode;
	}
	
	/**
	 * Method Name - Dcrypt
	 * Purpose - It performs a Base-64 decoding on the encrypted String and then decrypts the decoded string using the Jascrypt.
	 * Arguments - String to be decrypted
	 * Returns - The Decrypted and Decoded String
	 */
	@Override
	public String dcrypt(String stEncString_p) {
		String decode="";
		//Performing Base-64 decoding on the string
		try {
			decode = new String(Base64.getDecoder().decode(stEncString_p));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		//Fetching the private key from the resource bundle
		String stPrivateKey = ResourceBundleFile.getValueFromKey("SECRETPRIVATEKEY");
	    decryptor.setPassword(stPrivateKey);  
	    
	    //Decrypting the encrypted String
	    String decryptedText = decryptor.decrypt(decode);
		return decryptedText;
	}
	
}

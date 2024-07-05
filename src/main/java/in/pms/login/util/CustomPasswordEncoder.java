
package in.pms.login.util;

import java.security.SecureRandom;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
//sun.misc.BASE64Decoder() deprecated for upgraded Java versions (jdk 11+)
//import sun.misc.BASE64Decoder;
import java.util.Base64;

public class CustomPasswordEncoder extends BCryptPasswordEncoder {
	

	private Pattern BCRYPT_PATTERN = Pattern
			.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
	private final Log logger = LogFactory.getLog(getClass());

	private final int strength;

	private final SecureRandom random;

	public CustomPasswordEncoder() {
		this(-1);
	}

	/**
	 * @param strength the log rounds to use, between 4 and 31
	 */
	public CustomPasswordEncoder(int strength) {
		this(strength, null);
	}

	/**
	 * @param strength the log rounds to use, between 4 and 31
	 * @param random the secure random instance to use
	 *
	 */
	public CustomPasswordEncoder(int strength, SecureRandom random) {
		/*if (strength != -1 && (strength < BCrypt.MIN_LOG_ROUNDS || strength > BCrypt.MAX_LOG_ROUNDS)) {
			throw new IllegalArgumentException("Bad strength");
		}*/
		this.strength = strength;
		this.random = random;
	}

	public String encode(CharSequence rawPassword) {
		String salt;
		if (strength > 0) {
			if (random != null) {
				salt = BCrypt.gensalt(strength, random);
			}
			else {
				salt = BCrypt.gensalt(strength);
			}
		}
		else {
			salt = BCrypt.gensalt();
		}
		return BCrypt.hashpw(rawPassword.toString(), salt);
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String inputPassword = null;
		
		try{
		 //byte[] decodedPass = new BASE64Decoder().decodeBuffer(rawPassword.toString());
		 byte[] decodedPass = Base64.getDecoder().decode(rawPassword.toString());
			String value =new String(decodedPass);
			String[] temp = value.split(" ### ");
			if(temp.length==3){
				String random1 = temp[0];
				String random2 = temp[2];
				
				RequestAttributes requestAttributes = RequestContextHolder
			            .currentRequestAttributes();
			    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
			    HttpServletRequest request = attributes.getRequest();
			    HttpSession httpSession = request.getSession(true);
			    String sessionRandom1 = (String) httpSession.getAttribute("prefixRandom");
			    String sessionRandom2 = (String) httpSession.getAttribute("suffixRandom");
				
			    if(!sessionRandom1.equals(random1) || !sessionRandom2.equals(random2)) {
			    	return false;
			    }
			    
				inputPassword = temp[1];
				
			}else{
				inputPassword = rawPassword.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (encodedPassword == null || encodedPassword.length() == 0) {
			logger.warn("Empty encoded password");
			return false;
		}

		if (!BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
			logger.warn("Encoded password does not look like BCrypt");
			return false;
		}

		return BCrypt.checkpw(inputPassword, encodedPassword);
	}


}

package in.pms.global.misc;

import java.util.ResourceBundle;

public class RegexValidationFile {
	public static String getValueFromKey(String strKey) {
		ResourceBundle bundle1 = ResourceBundle
				.getBundle("regexValidation");
		return bundle1.getString(strKey);
	}
}

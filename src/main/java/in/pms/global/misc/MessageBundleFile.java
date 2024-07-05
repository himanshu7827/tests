package in.pms.global.misc;

import java.util.ResourceBundle;

public class MessageBundleFile {
	public static String getValueFromKey(String p_strKey) {
        ResourceBundle bundle1 = ResourceBundle.getBundle("messages");    
        return bundle1.getString(p_strKey);
    }
}

package in.pms.global.misc;

import java.util.ResourceBundle;

public class ResourceBundleFile {
    public static String getValueFromKey(String p_strKey) {
        ResourceBundle bundle1 = ResourceBundle.getBundle("in.pms.global.misc.resourceBundlePath");    
        return bundle1.getString(p_strKey);
    }
    
    
}

package in.pms.global.util;

import java.text.Format;
import java.util.Locale;

import org.apache.commons.math3.util.Precision;

import com.ibm.icu.math.BigDecimal;

public interface CurrencyUtils {
	
	public static String convertToINR(Object obj){
		Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
		return format.format(new BigDecimal(""+obj));
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    return Precision.round(value, places);
	}

}

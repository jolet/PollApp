package hr.tvz.polling.controller.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Check if email contains '@' sign, restrict leading, trailing, or consecutive dots in emails, 
 * restrict no. of characters in top level domain<br>
 * 1) A-Z characters allowed<br>
 * 2) a-z characters allowed<br>
 * 3) 0-9 numbers allowed<br>
 * 4) Additionally email may contain only dot(.), dash(-) and underscore(_)<br>
 * 5) Rest all characters are not allowed<br>
 *
 */
public class EmailValidator {
	private static final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	private static Pattern pattern = Pattern.compile(regex);
	
	public static boolean checkValidEmail(String email){
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
}

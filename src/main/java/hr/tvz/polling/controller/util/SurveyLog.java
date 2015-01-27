package hr.tvz.polling.controller.util;

import hr.tvz.polling.bll.SurveySecurityRealm;

public class SurveyLog {
	
	private static SurveySecurityRealm secRealm = new SurveySecurityRealm();
	

	public static String userLog(String what) {
		return new StringBuilder().append("User [").append(secRealm.getCurentUsername()).append("] @ ")
				.append(secRealm.getHostAddress()).append(" ").append(what).toString();
	}
	
}

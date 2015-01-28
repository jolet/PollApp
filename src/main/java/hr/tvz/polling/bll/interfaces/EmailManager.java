package hr.tvz.polling.bll.interfaces;

public interface EmailManager {
	
	void sendEmail(String email, String header, String content);
}

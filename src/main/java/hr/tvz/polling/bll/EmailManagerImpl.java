package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.EmailManager;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailManagerImpl implements EmailManager {

	private JavaMailSenderImpl emailer;

	@Async
	@Override
	public void sendEmail(String emailAddress, String header, String content) {
		System.out.println("email: " + emailAddress);
		System.out.println("content: " + content);

		SimpleMailMessage emailToSend = new SimpleMailMessage();
		emailToSend.setFrom("surveyportaltest@gmail.com");
		emailToSend.setReplyTo("surveyportaltest@gmail.com");
		emailToSend.setSubject(header);
		emailToSend.setText(content);
		emailToSend.setTo(emailAddress);

		// emailer.send(emailToSend);
		System.out.println("done");
	}

	public JavaMailSenderImpl getEmailer() {
		return emailer;
	}

	public void setEmailer(JavaMailSenderImpl emailer) {
		this.emailer = emailer;
	}

}

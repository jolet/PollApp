package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.EmailManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailManagerImpl implements EmailManager {
	private static final Logger LOG = LoggerFactory.getLogger(EmailManagerImpl.class);
	
	private JavaMailSenderImpl emailer;

	@Async
	@Override
	public void sendEmail(String emailAddress, String header, String content) {
		LOG.info("email: " + emailAddress);
		LOG.info("content: " + content);

		SimpleMailMessage emailToSend = new SimpleMailMessage();
		emailToSend.setFrom("surveyportaltest@gmail.com");
		emailToSend.setReplyTo("surveyportaltest@gmail.com");
		emailToSend.setSubject(header);
		emailToSend.setText(content);
		emailToSend.setTo(emailAddress);

		try {
			emailer.send(emailToSend);
			LOG.info("Email to "+emailAddress+" sent successfully!");
		} catch (MailException e) {
			LOG.info("Emailer error: address "+emailAddress +" "+ e.getMessage(), e);
		}
	}

	public JavaMailSenderImpl getEmailer() {
		return emailer;
	}

	public void setEmailer(JavaMailSenderImpl emailer) {
		this.emailer = emailer;
	}

}

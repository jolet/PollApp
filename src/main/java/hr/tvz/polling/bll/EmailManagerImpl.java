package hr.tvz.polling.bll;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.polling.bll.interfaces.EmailManager;

@Service
@Transactional
public class EmailManagerImpl implements EmailManager {

	@Override
	public void sendEmail(String email, String header, String content) {
		System.out.println("email: "+ email);
		System.out.println("content: "+ content);
		
	}
	

}

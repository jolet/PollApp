package hr.tvz.polling.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncoderTest {

	public static void main(String [] args){
		
		int i = 0;
		while (i < 10) {
			String password = "te";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
	 
			System.out.println(i + ". "+hashedPassword);
			i++;
		}
	}
}

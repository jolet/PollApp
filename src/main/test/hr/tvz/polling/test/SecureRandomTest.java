package hr.tvz.polling.test;

import java.util.UUID;

public class SecureRandomTest {
	
	public static void main(String [] args) {
//		UUID uuid = UUID.randomUUID();
//		String randomUUIDString = uuid.toString();
		
		for(int i= 0; i<10; ++i){
			System.out.println(i+". "+UUID.randomUUID().toString());
		}
	}
}

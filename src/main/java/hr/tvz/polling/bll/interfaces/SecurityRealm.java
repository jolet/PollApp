package hr.tvz.polling.bll.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityRealm extends UserDetailsService{

	String getCurentUsername();
	boolean hasRole(String role);
	boolean isLoggedIn();
	String getHostAddress();
}

package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.SecurityRealm;
import hr.tvz.polling.bll.interfaces.UserManager;
//import hr.tvz.polling.model.User;
import hr.tvz.polling.model.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticates and authorizes users.
 * @author joso
 *
 */
@Service
@Transactional(readOnly = true)
public class SurveySecurityRealm implements SecurityRealm {
	private static final Logger LOG = LoggerFactory.getLogger(SurveySecurityRealm.class);

	@Autowired
	private UserManager userManager;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LOG.info("User " + username + " trying to log in...");
		hr.tvz.polling.model.User user = userManager.findByEmail(username);
		
		if(user != null){
			if(user.isActive()){
				
			List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
			return authenticateUser(user, authorities);
			}
			else {
				LOG.info("Login fail - inactive user: " + user.getEmail());
				throw new DisabledException("Inactive user.");
			}
		} else {
			LOG.info("Username not found. That's all we know. Login denied. User " + username);
			throw new UsernameNotFoundException("Username not found.");
		}
	}
	
	private List<GrantedAuthority> buildUserAuthority(Role role) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.getName()));
		List<GrantedAuthority> userAuthority = new ArrayList<>(authorities);
		return userAuthority;
	}

	public User authenticateUser(hr.tvz.polling.model.User user, List<GrantedAuthority> userAuthority) {
		return new User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, userAuthority);
	}
	
	private static User getCurentUserPrinicipal(){
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User){
			return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} else {
			return null;
		}
	}
	
	@Override
	public String getCurentUsername(){
		return getCurentUserPrinicipal() != null ? getCurentUserPrinicipal().getUsername() : "";
	}
	
	@Override
	public boolean hasRole(String role){
		if(getCurentUserPrinicipal() != null){
			for(GrantedAuthority authority : getCurentUserPrinicipal().getAuthorities()){
				if(role.equals(authority.getAuthority())){
					return true;
				}
			}
		}
		return false;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public boolean isLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}

	@Override
	public String getHostAddress() {
		return ((WebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
	}

	@Override
	public String encodePassword(String plainTextPass) {
		return encoder.encode(plainTextPass);
	}
	
	
	
	public BCryptPasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(BCryptPasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public String generateResetToken() {
		return UUID.randomUUID().toString();
	}

}

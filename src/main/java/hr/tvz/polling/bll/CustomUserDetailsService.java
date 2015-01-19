package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.UserManager;
//import hr.tvz.polling.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A custom service for retrieving users from a custom datasource, such as a
 * database.
 * <p>
 * This custom service must implement Spring's {@link UserDetailsService}
 */
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	UserManager userManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		
		try{
			hr.tvz.polling.model.User dbUser = userManager.findByEmail(username);
			
			if (dbUser != null) {
				user = new User(dbUser.getEmail(), 
						dbUser.getPassword(), 
						true,
						true,
						true,
						true,
						getAuthorities(1)
						);
			}
			
		} catch(Exception e){
			LOG.info("Error in retrieving username...");
			throw new UsernameNotFoundException("Error in retrieving username");
		}
		return user;
	}

	/**
	 * Retrieves the correct ROLE type depending on the access level, where
	 * access level is an Integer. Basically, this interprets the access value
	 * whether it's for a regular user or admin.
	 * 
	 * @param access
	 *            an integer value representing the access of the user
	 * @return collection of granted authorities
	 */
	public Collection<GrantedAuthority> getAuthorities(Integer access) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

		// All users are granted with ROLE_USER access
		// Therefore this user gets a ROLE_USER by default
		LOG.info("Grant ROLE_USER to this user");
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));

		// Check if this user has admin access
		// We interpret Integer(1) as an admin user
		if (access.compareTo(1) == 0) {
			// User has admin access
			LOG.info("Grant ROLE_ADMIN to this user");
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		// Return list of granted authorities
		return authList;
	}

}

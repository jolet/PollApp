package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.ClassGroupManager;
import hr.tvz.polling.bll.interfaces.EmailManager;
import hr.tvz.polling.bll.interfaces.RoleManager;
import hr.tvz.polling.bll.interfaces.SecurityRealm;
import hr.tvz.polling.bll.interfaces.UserManager;
import hr.tvz.polling.controller.util.EmailValidator;
import hr.tvz.polling.controller.util.HttpResponsePayloadWrapper;
import hr.tvz.polling.controller.util.SurveyLog;
import hr.tvz.polling.dal.ActivityRepository;
import hr.tvz.polling.dal.UserRepository;
import hr.tvz.polling.model.ClassGroup;
import hr.tvz.polling.model.User;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserManagerImpl implements UserManager {
	private static final Logger LOG = LoggerFactory.getLogger(UserManagerImpl.class);

	private static final String LOCALHOST_PASS_RESET_LINK = "http://localhost:8080/PollApp/auth/resetPassword?";
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	RoleManager roleManager;
	
	@Autowired
	ClassGroupManager classGroupManager;
	
	@Autowired
	SecurityRealm secRealm;
	
	@Autowired
	EmailManager emailManager;
	
	@Override
	public List<User> findAll() {
		return repository.findAll();
	}
	@Override
	public User findOne(Long id) {
		return repository.findOne(id);
	}
	@Override
	public void saveAndFlush(User user) {
		repository.saveAndFlush(user);
	}
	@Override
	public User findByEmail(String username) {
		return repository.findByEmail(username);
	}
	@Override
	public Long findIdByEmail(String email) {
		return repository.findIdByEmail(email);
	}
	@Override
	public List<HttpResponsePayloadWrapper> findAllByRole(String roleUser) {
		return protectSensitiveData(repository.findByRoleName(roleUser));
	}
	
	/**
	 * Strip sensitive and unneeded data, add user's points
	 * @param userList
	 * @return
	 */
	private List<HttpResponsePayloadWrapper> protectSensitiveData(List<User> userList){
		List<HttpResponsePayloadWrapper> usersWrapped = new ArrayList<HttpResponsePayloadWrapper>();
		for(User u : userList){
			u.setPassword(null);
//			u.setActivities(null);
			usersWrapped.add(new HttpResponsePayloadWrapper(activityRepository.getUserPoints(u.getId()), u));
		}
		return usersWrapped;
	}
	@Override
	public User findById(Long userId) {
		return repository.findById(userId);
	}
	@Override
	public void activateUser(String userId) {
		// XXX: string to long conversion, need util class
		Long id = Long.parseLong(userId);
		User userToSave = findById(id);
		
		boolean activated = userToSave.isActive();
		
		
		if(activated){ // user deactivation case.
			userToSave.setActive(false);
//			repository.saveAndFlush(userToSave);
		} else { // activation case - generate and send reset token
			String resetToken = secRealm.generateResetToken();
			userToSave.setResetToken(resetToken);
			
			//send reset token to user
			emailManager.sendEmail(userToSave.getEmail(), "Survey portal - activation", "Your account has been approved.\n"
					+ "Follow next link to update your login details.\n\n"
					+ LOCALHOST_PASS_RESET_LINK +resetToken);
			userToSave.setActive(true);
		} 
		repository.saveAndFlush(userToSave);
		
	}
	@Override
	public boolean registerUser(User user) {
		//check valid email and university
		//TODO:
		String emailToCheck = user.getEmail()+"@tvz.hr";
		if(!EmailValidator.checkValidEmail(emailToCheck)){
			return false;
		}
		user.setEmail(emailToCheck);
		
		//check already exists
		if (repository.findByEmail(user.getEmail()) != null){
			LOG.info(SurveyLog.userLog("email "+ user.getEmail()+" tried to register but already exists."));
			return false;
		}
		
		//admin activates users later
		user.setActive(false);
		
		ClassGroup cgr = classGroupManager.findByNameAndAcademicYear(user.getClassGroup().getName(), user.getClassGroup().getAcademicYear());
		if(cgr == null){
			LOG.info(SurveyLog.userLog("user [" + user.getEmail() + "] tried to register but class group was null"));
			return false;
		}
		user.setClassGroup(cgr);
		user.setRole(roleManager.getUserRole());
		
		repository.saveAndFlush(user);
		
		LOG.info(SurveyLog.userLog("user [" + user.getEmail() + "] registration: SUCCESS!"));
		
		//TODO: send email: user is registered and waiting admin approval.
		emailManager.sendEmail(user.getEmail(),	"Survey portal - registration",
						"Your registration is waiting for approval. You will be notified with "
						+ "further instructions once your registration details are checked and approved.");
		return true;
	}
	
	@Override
	public void sendResetToken(String email) {
		LOG.info(SurveyLog.userLog("email " + email +" requested password reset"));
		String resetToken = secRealm.generateResetToken();
		User user = repository.findByEmail(email);
		
		//inactive users should not have right to reset their passwords.
		if(user != null && user.isActive()){
			
			//send reset token to user
			emailManager.sendEmail(user.getEmail(), "Survey portal - password reset", "You (or someone pretending to be you) has requested password reset on Survey portal.\n"
					+ "If this was you, follow next link to update your login details. If this was not you, ignore this email.\n\n"
					+ LOCALHOST_PASS_RESET_LINK+resetToken);
			user.setResetToken(resetToken);
			repository.saveAndFlush(user);
			LOG.info(SurveyLog.userLog("resetToken set and emailed to " + user.getEmail()));
		} else {
			LOG.info(SurveyLog.userLog("non-existing user for email "+ email));
		}
		
		
	}
	@Override
	public void resetPassword(String resetToken, String plainTextPass) {
		User user = repository.findByResetToken(resetToken);
		
		if(user != null && user.isActive()){
			user.setResetToken(null);
			user.setPassword(secRealm.encodePassword(plainTextPass));
			
			repository.saveAndFlush(user);
			LOG.info(SurveyLog.userLog("user " + user.getEmail() + " password reset success."));
			
			emailManager.sendEmail(user.getEmail(), "Survey portal - password resetted", "Your password has been updated.");
		} else {
			LOG.info(SurveyLog.userLog("tried to reset password, but bad token was given: " + resetToken));
		}
	}
	@Override
	public String getUserFullName(String username) {
		User user = findByEmail(username);
		return user.getFirstName() + " " + user.getLastName();
	}
	
}

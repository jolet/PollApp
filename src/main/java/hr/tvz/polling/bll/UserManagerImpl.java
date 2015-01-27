package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.ClassGroupManager;
import hr.tvz.polling.bll.interfaces.RoleManager;
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
	@Autowired
	UserRepository repository;
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	RoleManager roleManager;
	
	@Autowired
	ClassGroupManager classGroupManager;
	
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
	public void flipUserState(String userId) {
		// XXX: string to long conversion, need util class
		Long id = Long.parseLong(userId);
		User userToSave = findById(id);
		userToSave.setActive(!userToSave.isActive());
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
		return true;
	}
}

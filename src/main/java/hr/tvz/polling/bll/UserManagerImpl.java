package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.UserManager;
import hr.tvz.polling.dal.UserRepository;
import hr.tvz.polling.model.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserManagerImpl implements UserManager {

	@Autowired
	UserRepository repository;
	
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
		 
	
}

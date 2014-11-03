package hr.tvz.polling.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.tvz.polling.bll.interfaces.RoleManager;
import hr.tvz.polling.dal.RoleRepository;
import hr.tvz.polling.model.Role;

@Service
@Transactional
public class RoleManagerImpl implements RoleManager{

	@Autowired
	RoleRepository repository;
	
	@Override
	public List<Role> findAll() {
		return repository.findAll();
	}

	@Override
	public Role findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void saveAndFlush(Role entity) {
		repository.saveAndFlush(entity);
	}

}

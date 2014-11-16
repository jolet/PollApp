package hr.tvz.polling.bll;

import java.util.List;

import hr.tvz.polling.bll.interfaces.OptionManager;
import hr.tvz.polling.dal.OptionRepository;
import hr.tvz.polling.model.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OptionManagerImpl implements OptionManager{

	@Autowired
	OptionRepository repository;
	
	@Override
	public List<Option> findAll() {
		return repository.findAll();
	}

	@Override
	public Option findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void saveAndFlush(Option entity) {
		repository.saveAndFlush(entity);
	}

//	@Override
//	public void delete(Long id) {
//		repository.delete(id);
//	}

}

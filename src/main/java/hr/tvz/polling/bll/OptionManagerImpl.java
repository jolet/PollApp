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
	@Transactional(readOnly = true)
	public List<Option> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Option findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional
	public void saveAndFlush(Option option) {
		repository.saveAndFlush(option);
	}
	
	@Override
	@Transactional
	public Option saveAndFlushAndReturn(Option option) {
		return repository.saveAndFlush(option);
	}
	

//	@Override
//	public void delete(Long id) {
//		repository.delete(id);
//	}

}

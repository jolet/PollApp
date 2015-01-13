package hr.tvz.polling.bll.interfaces;

import hr.tvz.polling.model.Option;

//	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
public interface OptionManager extends BaseManager<Option> {

	public Option saveAndFlushAndReturn(Option option);
	// void delete(Long id);
}

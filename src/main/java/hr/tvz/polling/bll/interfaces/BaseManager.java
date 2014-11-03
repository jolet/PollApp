package hr.tvz.polling.bll.interfaces;

import java.util.List;

public interface BaseManager<T> {

	List<T> findAll();
	T findOne(Long id);
	void saveAndFlush(T entity);
}

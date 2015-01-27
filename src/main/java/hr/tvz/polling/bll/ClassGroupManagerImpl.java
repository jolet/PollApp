package hr.tvz.polling.bll;

import hr.tvz.polling.bll.interfaces.ClassGroupManager;
import hr.tvz.polling.dal.ClassGroupRepository;
import hr.tvz.polling.model.ClassGroup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClassGroupManagerImpl implements ClassGroupManager {

	@Autowired
	ClassGroupRepository repository;

	@Override
	public List<ClassGroup> findAll() {
		return repository.findAll();
	}

	@Override
	public ClassGroup findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void saveAndFlush(ClassGroup cgr) {
//		if (checkExisting(cgr.getName(), cgr.getAcademicYear())) {
//			repository.saveAndFlush(cgr);
//			
//		}
	}

	private boolean checkExisting(String name, String academicYear) {
		return repository.findByNameAndAcademicYear(name, academicYear) == null ? true : false;
	}
	
	public ClassGroup saveAndFlushAndReturnCGR(ClassGroup cgr){
		if (checkExisting(cgr.getName(), cgr.getAcademicYear())) {
			return repository.saveAndFlush(cgr);
		} else {
			return repository.findByNameAndAcademicYear(cgr.getName(), cgr.getAcademicYear());
		}
	}
	
	@Override
	public ClassGroup findByNameAndAcademicYear(String name, String academicYear){
		return repository.findByNameAndAcademicYear(name, academicYear);
	}

}

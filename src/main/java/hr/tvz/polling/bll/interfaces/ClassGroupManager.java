package hr.tvz.polling.bll.interfaces;

import hr.tvz.polling.model.ClassGroup;

public interface ClassGroupManager extends BaseManager<ClassGroup>{

	ClassGroup saveAndFlushAndReturnCGR(ClassGroup cgr);

	ClassGroup findByNameAndAcademicYear(String name, String academicYear);
}

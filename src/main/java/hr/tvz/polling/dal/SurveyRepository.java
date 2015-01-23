package hr.tvz.polling.dal;

import hr.tvz.polling.model.Survey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>{

	List<Survey> findAllByActive(Boolean active);

//	Query("FROM Survey s where s.active is true AND s.classGroup.id = :classGroupId and s.id not in (select ))
//	List<Survey> findAllActiveForUser(@Param("userId")Long userId, @Param("classGroupId")Long classGroupId);

	@Query("FROM Survey s where s.active = :active AND s.classGroup.id = :classGroupId")
	List<Survey> findAllByActiveAndClassId(@Param("active")Boolean active, @Param("classGroupId")Long classGroupId);

}

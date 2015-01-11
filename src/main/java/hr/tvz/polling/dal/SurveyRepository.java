package hr.tvz.polling.dal;

import hr.tvz.polling.model.Survey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>{

	List<Survey> findAllByActive(Boolean active);

//	@Query("from ")
//	List<Survey> findAllOptionValuesStripped();

}

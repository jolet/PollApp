package hr.tvz.polling.dal;

import hr.tvz.polling.model.Activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

	@Query("SELECT COUNT(x) FROM Activity x WHERE x.option.survey.id = :surveyId AND x.user.id = :userId")
	Long checkAlreadyVoted(@Param("surveyId") Long surveyId, @Param("userId")Long userId);

	@Query("SELECT COUNT(x) FROM Activity x WHERE x.option.state IS TRUE AND x.user.id = :userId "
			+ "AND (x.option.survey.validTo < CURRENT_TIMESTAMP OR x.option.survey.validTo IS NULL)")
	Integer getUserPoints(@Param("userId")Long userId);

	@Query("from Activity a where a.user.id = :id")
	List<Activity> findAllByUserId(@Param("id")Long id);

	@Query("SELECT user.email from Activity x where x.option.id = :id")
	List<String> findWhoVoted(@Param("id")Long id);
	
}

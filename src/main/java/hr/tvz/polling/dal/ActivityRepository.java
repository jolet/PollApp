package hr.tvz.polling.dal;

import hr.tvz.polling.model.Activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

	@Query("SELECT COUNT(x) FROM Activity x WHERE x.option.survey.id = :surveyId AND x.user.id = :userId")
	Long checkAlreadyVoted(@Param("surveyId") Long surveyId, @Param("userId")Long userId);

}

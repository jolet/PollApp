package hr.tvz.polling.dal;

import hr.tvz.polling.model.Activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

//	@Query("SELECT COUNT(ACT_ID) FROM "
//			+ "(SELECT ACT_ID FROM POLLING.POL_ACTIVITY INNER JOIN POLLING.POL_OPTION on POL_ACTIVITY.OPT_ID = POL_OPTION.OPT_ID "
//			+ "where srv_id = 3 AND usr_id = 1337")
//	Integer checkAlreadyVoted(Long surveyId, Long userId);

}

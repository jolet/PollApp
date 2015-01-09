package hr.tvz.polling.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.tvz.polling.model.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>{

}

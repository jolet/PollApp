package hr.tvz.polling.dal;

import hr.tvz.polling.model.Activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

}

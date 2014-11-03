package hr.tvz.polling.dal;

import hr.tvz.polling.model.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long>{

}

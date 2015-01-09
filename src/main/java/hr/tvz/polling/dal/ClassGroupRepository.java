package hr.tvz.polling.dal;

import hr.tvz.polling.model.ClassGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long>{

}

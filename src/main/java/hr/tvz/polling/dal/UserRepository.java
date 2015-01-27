package hr.tvz.polling.dal;

import hr.tvz.polling.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	
	@Query("SELECT id FROM User u WHERE u.email = :email")
	Long findIdByEmail(@Param("email")String email);

	@Query("from User u where u.role.name = :roleUser")
	List<User> findByRoleName(@Param("roleUser")String roleUser);

	User findById(Long id);

}

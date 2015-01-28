package hr.tvz.polling.bll.interfaces;

import java.util.List;

import hr.tvz.polling.controller.util.HttpResponsePayloadWrapper;
import hr.tvz.polling.model.User;

public interface UserManager extends BaseManager<User>{

	User findByEmail(String username);
	
	Long findIdByEmail(String email);

	List<HttpResponsePayloadWrapper> findAllByRole(String roleUser);

	User findById(Long userId);

	void activateUser(String userId);

	boolean registerUser(User user);

	void sendResetToken(String email);

	void resetPassword(String resetToken, String password);

}

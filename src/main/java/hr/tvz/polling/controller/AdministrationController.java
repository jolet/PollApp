package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.UserManager;
import hr.tvz.polling.controller.util.Constants;
import hr.tvz.polling.controller.util.HttpResponsePayloadWrapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

	@Autowired
	UserManager userManager;
	
	@Autowired
	ActivityManager activityManager;
	
	@RequestMapping(value="/users")
	public @ResponseBody List<HttpResponsePayloadWrapper> getUserList(){
		return userManager.findAllByRole(Constants.ROLE_USER);
	}
	
	@RequestMapping(value="/updateUser/{userId}", method = RequestMethod.POST)
	public @ResponseBody void updateUser(@PathVariable String userId){
		userManager.activateUser(userId);
	}

	@RequestMapping(value="/userActivities/{userId}", method = RequestMethod.GET)
	public @ResponseBody List<HttpResponsePayloadWrapper> getUserActivities(@PathVariable String userId){
		return activityManager.findAllByUserId(userId);
	}
	
	@RequestMapping("/layout")
	public String getPartialPage(ModelMap modelMap) {
		return "administration/administrationLayout";
	}
}

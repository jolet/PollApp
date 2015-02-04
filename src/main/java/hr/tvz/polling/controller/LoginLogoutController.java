package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.ClassGroupManager;
import hr.tvz.polling.bll.interfaces.EmailManager;
import hr.tvz.polling.bll.interfaces.SecurityRealm;
import hr.tvz.polling.bll.interfaces.UserManager;
import hr.tvz.polling.controller.util.Constants;
import hr.tvz.polling.controller.util.HttpResponsePayloadWrapper;
import hr.tvz.polling.controller.util.SurveyLog;
import hr.tvz.polling.model.ClassGroup;
import hr.tvz.polling.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class LoginLogoutController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginLogoutController.class);

	@Autowired
	SecurityRealm secRealm;
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	ClassGroupManager classGroupManager;
	
	@Autowired
	EmailManager emailManager;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
		LOG.info(SurveyLog.userLog("accessed login page" + (error == true ? " with error = true: " : "")));
		return "publicPages/loginPage";
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String getDeniedPage() {
		LOG.info(SurveyLog.userLog("accessed denied page..."));
		return "publicPages/deniedPage";
	}
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String getResetPasswordPage() {
		LOG.info(SurveyLog.userLog("accessed reset password page..."));
		return "publicPages/resetPasswordPage";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String registerUser(@RequestBody User user) {
//		LOG.info(SurveyLog.userLog("user" + user.getEmail() + " trying to register..."));
		if(user.getEmail().contains("@")){
			return "\"error\"";
		} else if(userManager.registerUser(user)){
			return "\"success\"";
		} else {
			return "\"error\"";
		}
	}
	
//	@RequestMapping(value = "/forgottenPassword/{email}", method = RequestMethod.POST) // json doesn't like dot(.) in parameter
	@RequestMapping(value = "/forgottenPassword", method = RequestMethod.POST)
	public @ResponseBody void startPasswordResetProcess(@RequestBody String email) {
		LOG.info(email);
		userManager.sendResetToken(email);
	}
	
	@RequestMapping(value="/updatePassword/{resetToken}", method=RequestMethod.POST)
	public @ResponseBody void resetPassword(@PathVariable String resetToken, @RequestBody String password){
		LOG.info(SurveyLog.userLog("reset token: "+resetToken));
		userManager.resetPassword(resetToken, password);
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public @ResponseBody HttpResponsePayloadWrapper getMenuItems() {

		List<MenuItem> menu = new ArrayList<>();

		if (secRealm.isLoggedIn()) {
			if (secRealm.hasRole(Constants.ROLE_ADMIN)) {
				menu.add(new MenuItem("#/template", "TEMPLATE"));
				menu.add(new MenuItem("#/activation", "ACTIVATION"));
				menu.add(new MenuItem("#/vote", "VOTING"));
				menu.add(new MenuItem("#/results", "RESULTS"));
				menu.add(new MenuItem("#/administration", "ADMINISTRATION"));
			} else if (secRealm.hasRole(Constants.ROLE_USER)) {
				menu.add(new MenuItem("#/vote", "VOTE"));
				menu.add(new MenuItem("#/points", "SCORE"));
			}
//			menu.add(new MenuItem("#", "Welcome " + secRealm.getCurentUsername()));
//			menu.add(new MenuItem("/PollApp/auth/logout", "Logout"));
		}
		
		
		return new HttpResponsePayloadWrapper(getRoleHomeUrl(), menu, userManager.getUserFullName(secRealm.getCurentUsername()));
	}
	
	@RequestMapping("/classGroups")
	public @ResponseBody List<ClassGroup> getClassGroups() {
		// XXX: return only latest year
		return classGroupManager.findAll();
	}
	
	private String getRoleHomeUrl(){
		if(secRealm.hasRole(Constants.ROLE_ADMIN)){
			return "template"; 
		} else if (secRealm.hasRole(Constants.ROLE_USER)){
			return "vote";
		}
		return null;
	}

	private final class MenuItem implements Serializable {
		private static final long serialVersionUID = 1L;

		private String link;
		private String name;
		private String className;
		
		public MenuItem(String link, String name) {
			this.link = link;
			this.name = name;
		}
		
		@SuppressWarnings("unused")
		public MenuItem(String link, String name, String className) {
			this.link = link;
			this.name = name;
			this.className = className;
		}

		@SuppressWarnings("unused")
		public String getLink() {
			return link;
		}

		@SuppressWarnings("unused")
		public void setLink(String link) {
			this.link = link;
		}

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		public void setName(String name) {
			this.name = name;
		}

		@SuppressWarnings("unused")
		public String getClassName() {
			return className;
		}

		@SuppressWarnings("unused")
		public void setClassName(String className) {
			this.className = className;
		}
		
	}
}

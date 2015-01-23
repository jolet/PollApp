package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.SecurityRealm;
import hr.tvz.polling.controller.util.Constants;
import hr.tvz.polling.controller.util.HttpResponsePayloadWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class LoginLogoutController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginLogoutController.class);

	@Autowired
	SecurityRealm secRealm;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
		LOG.info("login hit " + error);
//		if (error == true) {
//			model.addAttribute("error", "Invalid username or password!");
//		}
//		return model;
		return "loginPage";
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String getDeniedPage() {
		return "deniedPage";
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
			} else if (secRealm.hasRole(Constants.ROLE_USER)) {
				menu.add(new MenuItem("#/vote", "VOTE"));
			}
			menu.add(new MenuItem("/PollApp/auth/logout", "Logout"));
		}
		
		
		return new HttpResponsePayloadWrapper(getRoleHomeUrl(), menu);
	}
	
	private String getRoleHomeUrl(){
		if(secRealm.hasRole(Constants.ROLE_ADMIN)){
			return "template"; 
		} else if (secRealm.hasRole(Constants.ROLE_USER)){
			return "vote";
		}
		return null;
	}

	private class MenuItem implements Serializable {
		private static final long serialVersionUID = 1L;

		public MenuItem(String link, String name) {
			this.link = link;
			this.name = name;
		}

		private String link;
		private String name;

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}

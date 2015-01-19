package hr.tvz.polling.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class LoginLogoutController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginLogoutController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value = "error", required = false) boolean error, ModelMap model) {
		LOG.info("login hit");
		return "loginPage";
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String getDeniedPage() {
		return "deniedPage";
	}
}

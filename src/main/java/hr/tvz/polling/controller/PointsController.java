package hr.tvz.polling.controller;

import hr.tvz.polling.bll.interfaces.ActivityManager;
import hr.tvz.polling.bll.interfaces.UserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/points")
public class PointsController {

	@Autowired
	ActivityManager activityManager;
	
	@RequestMapping(value="/total")
	public @ResponseBody Integer getPoints(){
		return activityManager.getUserPoints();
	}
	
	@RequestMapping("/layout")
	public String getPartialPage(ModelMap modelMap){
		return "points/pointsLayout";
	}
}

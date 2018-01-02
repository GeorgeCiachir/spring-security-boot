package george.projects.demos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/")
@Controller
public class ApplicationController {

	@ResponseBody
	@RequestMapping(value = "/description")
	public String description() {
		return "A small app that exemplifies the use of several spring security features";
	}

	@ResponseBody
	@RequestMapping(value = "/siteAdmin")
	public String siteAdmin(@RequestParam(value = "name", required = false, defaultValue = "user") String name) {
		return "Hello " + name + "! You are an authenticated site admin";
	}

	@ResponseBody
	@RequestMapping(value = "/opsAdmin")
	public ModelAndView opsAdmin(@RequestParam(value = "name", required = false, defaultValue = "user") String name) {
		return new ModelAndView("opsAdminPage");
//		return "Hello " + name + "! You are an authenticated ops admin";
	}

	@ResponseBody
	@RequestMapping(value = "/normalUser")
	public String normalUser(@RequestParam(value = "name", required = false, defaultValue = "user") String name) {
		return "Hello " + name + " ! You are an authenticated normal user";
	}

	@PreAuthorize("hasRole('PREMIUM_USER')")
	@ResponseBody
	@RequestMapping(value = "/premiumUser")
	public String premiumUser(@RequestParam(value = "name", required = false, defaultValue = "user") String name) {
		return "Hello " + name + " ! You are an authenticated premium user";
	}

	@ResponseBody
	@RequestMapping(value = "/home")
	public ModelAndView home() {
		return new ModelAndView("homePage");
	}
}
package george.projects.demos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/")
@Controller
public class ApplicationController {

	@ResponseBody
	@RequestMapping(value = "/description")
	public String description() {
		return "A small app that exemplifies the use of several spring security features";
	}

	@ResponseBody
	@RequestMapping(value = "/login/{name}")
	public String login(@PathVariable(value = "name") String name) {
		return "Hello " + name + " ! This is a clear route";
	}

	@ResponseBody
	@RequestMapping(value = "/siteAdmin/{name}")
	public String siteAdmin(@PathVariable(value = "name") String name) {
		return "Hello " + name + "! You are an authenticated site admin";
	}

	@ResponseBody
	@RequestMapping(value = "/opsAdmin/{name}")
	public String opsAdmin(@PathVariable(value = "name") String name) {
		return "Hello " + name + "! You are an authenticated ops admin";
	}

	@ResponseBody
	@RequestMapping(value = "/normalUser/{name}")
	public String normalUser(@PathVariable(value = "name") String name) {
		return "Hello " + name + " ! You are an authenticated normal user";
	}

	@PreAuthorize("hasRole('PREMIUM_USER')")
	@ResponseBody
	@RequestMapping(value = "/premiumUser/{name}")
	public String premiumUser(@PathVariable(value = "name") String name) {
		return "Hello " + name + " ! You are an authenticated premium user";
	}
}
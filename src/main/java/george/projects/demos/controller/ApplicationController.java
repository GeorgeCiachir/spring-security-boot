package george.projects.demos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/appController")
@Controller
public class ApplicationController {

	@ResponseBody
	@RequestMapping(value = "/login/{name}")
	public String login(@PathVariable(value = "name") String name) {
		return "Hello " + name + " ! This is a clear route";
	}

	@ResponseBody
	@RequestMapping(value = "/admin")
	public String admin() {
		return "Hello ! You must be an admin";
	}

	@ResponseBody
	@RequestMapping(value = "/testAuthentication")
	public String testAuthentication() {
		return "Hello ! You are authenticated";
	}
}
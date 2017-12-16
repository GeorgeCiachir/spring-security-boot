package george.projects.demos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/security")
@Controller
public class SecurityController {

	@RequestMapping(value = "/accessDenied")
	public ModelAndView accessDenied() {
		return new ModelAndView("accessDeniedPage");
	}

	@ResponseBody
	@RequestMapping(value = "/login")
	public ModelAndView login() {
		return new ModelAndView("loginPage");
	}
}

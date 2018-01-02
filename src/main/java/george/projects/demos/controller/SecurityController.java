package george.projects.demos.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/security")
@Controller
public class SecurityController {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityController.class);

	@RequestMapping(value = "/accessDenied")
	public ModelAndView accessDenied() {
		LOG.info("Inside the SecurityController");
		return new ModelAndView("accessDeniedPage");
	}

	@RequestMapping(value = "/manualLogin")
	public ModelAndView login() {
		LOG.info("Inside the SecurityController");
		return new ModelAndView("loginPageUsedByTheApplicationSecurityControllerToPerformManualLogin");
	}

	@RequestMapping(value = "/manualLogout")
	public ModelAndView logOut(HttpServletRequest request) throws ServletException {
		request.logout();
		return new ModelAndView("homePage");
	}
}

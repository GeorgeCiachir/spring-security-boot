package george.projects.demos.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import george.projects.demos.configuration.ApplicationSecurityProfile;
import george.projects.demos.security.config.CustomBasicAuthenticationEntryPointSecurityConfig;

@RequestMapping(value = "/security")
@Controller
public class SecurityController {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityController.class);

	@Autowired
	Environment env;

	@RequestMapping(value = "/accessDenied")
	public ModelAndView accessDenied() {
		LOG.info("Inside the SecurityController");
		return new ModelAndView("accessDeniedPage");
	}

	@RequestMapping(value = "/manualLogin")
	public ModelAndView login() {
		LOG.info("Inside the SecurityController");
		List<String> profiles = Arrays.stream(env.getActiveProfiles()).collect(Collectors.toList());
		if (profiles.contains(ApplicationSecurityProfile.CUSTOM_SECURITY_FILTER)) {
			return new ModelAndView("loginPageUsedByTheApplicationSecurityControllerToPerformManualLogin");
		} else {
			return new ModelAndView("loginPageUsedBySpringSecurityController");
		}
	}

	/**
	 * Performs manual logout and redirects to corresponding page
	 */
	@RequestMapping(value = "/manualLogout")
	public ModelAndView manualLogout(HttpServletRequest request) throws ServletException {
		request.logout();
		return new ModelAndView("homePage");
	}

	/**
	 * Performs custom logout - actually it just needs to be declared and no action is required
	 * This endpoint is used in the {@link CustomBasicAuthenticationEntryPointSecurityConfig} on the logout implementation
	 */
	@RequestMapping(value = "/customLogout")
	public void customLogout() {}
}

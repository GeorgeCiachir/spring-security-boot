package george.projects.demos.security.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		LOG.info("In CustomLogoutSuccessHandler");
		LOG.info("Logout : {}", authentication.getName());
		LOG.info("Logout : {}", authentication.getPrincipal().toString());

		// Need to manually set a redirect url if a particular one is needed. the default is "/"
		setDefaultTargetUrl("/description");
		super.onLogoutSuccess(request, response, authentication);
	}
}

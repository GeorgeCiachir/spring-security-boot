package george.projects.demos.configuration;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import george.projects.demos.security.config.CustomFilterChainSecurityConfig;

public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebAppInitializer() {
		super(CustomFilterChainSecurityConfig.class);
	}

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		super.insertFilters(servletContext, new CustomSpringSedcurityFilterChain());
	}
}

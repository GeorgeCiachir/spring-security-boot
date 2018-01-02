package george.projects.demos.configuration.webMvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import george.projects.demos.configuration.ApplicationSecurityProfile;

// class can be removed if the following properties are used in application.properties file
// spring.mvc.view.prefix=/WEB-INF/jsp/
// spring.mvc.view.suffix=.jsp
@Configuration
@EnableWebMvc
@Profile(ApplicationSecurityProfile.IN_MEMORY_AUTH_WITH_SPRING_SEC_CONTROLLER_LOGIN)
public class DefaultSpringLoginSecurityControllerWebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		registry.viewResolver(viewResolver);
	}

	/**
	 * Registers the default Spring security controller
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("loginPageUsedBySpringSecurityController");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
}

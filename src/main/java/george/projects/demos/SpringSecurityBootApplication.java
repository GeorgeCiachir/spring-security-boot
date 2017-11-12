package george.projects.demos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@SpringBootApplication
public class SpringSecurityBootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBootApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringSecurityBootApplication.class);
	}
}

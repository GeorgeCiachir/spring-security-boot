package george.projects.demos.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import george.projects.demos.configuration.EnvironmentSettings;
import george.projects.demos.configuration.SecurityProfile;
import george.projects.demos.security.filter.CustomFilter;

@Profile(SecurityProfile.CUSTOM_FILTER)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomFilterSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(CustomFilterSecurityConfig.class);

	private EnvironmentSettings environmentSettings;

	public CustomFilterSecurityConfig() {
		LOG.info("Global security configuration with the CUSTOM FILTER");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(environmentSettings.allowedUrlPatterns()).permitAll()
				.antMatchers("/siteAdmin/**").hasRole("ADMIN_SITE")
				.anyRequest().authenticated();
	}

	@Autowired
	public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
		this.environmentSettings = environmentSettings;
	}
}

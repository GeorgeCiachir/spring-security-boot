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
import george.projects.demos.configuration.ApplicationSecurityProfile;
import george.projects.demos.security.filter.CustomFilter;

@Profile(ApplicationSecurityProfile.CUSTOM_SECURITY_FILTER)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomFilterSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(CustomFilterSecurityConfig.class);

	private EnvironmentSettings environmentSettings;
	private CustomFilter customFilter;

	public CustomFilterSecurityConfig() {
		LOG.info("Global security configuration with the {}", ApplicationSecurityProfile.CUSTOM_SECURITY_FILTER);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.addFilterBefore(customFilter, BasicAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(environmentSettings.allowedUrlPatterns()).permitAll()
				.antMatchers("/siteAdmin/**").hasRole("ADMIN_SITE")
				.antMatchers("/opsAdmin/**").access("hasRole('ADMIN_OPS') and authentication.principal.equals('George@company.com')")
				.anyRequest().authenticated();
	}

	@Autowired
	public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
		this.environmentSettings = environmentSettings;
	}

	@Autowired
	public void setCustomFilter(CustomFilter customFilter) {
		this.customFilter = customFilter;
	}
}

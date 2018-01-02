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
import george.projects.demos.security.authentication.CustomAuthenticationProvider;
import george.projects.demos.security.filter.CustomSecurityFilter;

/**
 * No need to do set a custom authentication provider, because in the {@link this#configure(HttpSecurity)} method
 * a new security filter is provided, that uses the {@link CustomAuthenticationProvider}
 */
@Profile(ApplicationSecurityProfile.CUSTOM_SECURITY_FILTER)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomFilterSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(CustomFilterSecurityConfig.class);

	private EnvironmentSettings environmentSettings;
	private CustomSecurityFilter customSecurityFilter;

	public CustomFilterSecurityConfig() {
		LOG.info("Global security configuration with the {}", ApplicationSecurityProfile.CUSTOM_SECURITY_FILTER);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.addFilterBefore(customSecurityFilter, BasicAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(environmentSettings.allowedUrlPatterns()).permitAll()

				.antMatchers("/siteAdmin/**").hasRole("ADMIN_SITE")
				.antMatchers("/opsAdmin/**").access("hasRole('ADMIN_OPS') and authentication.principal.equals('George@company.com')")

				.anyRequest().authenticated()

				.and()
				// No need to explicitly set permitAll() on the login page, as it is already set above, in the allowedUrlPatterns()).permitAll()
				.formLogin()
				.loginPage("/security/manualLogin");

		httpSecurity.exceptionHandling().accessDeniedPage("/security/accessDenied");
	}

	@Autowired
	public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
		this.environmentSettings = environmentSettings;
	}

	@Autowired
	public void setCustomSecurityFilter(CustomSecurityFilter customSecurityFilter) {
		this.customSecurityFilter = customSecurityFilter;
	}
}

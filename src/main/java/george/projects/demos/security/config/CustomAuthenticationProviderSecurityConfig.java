package george.projects.demos.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import george.projects.demos.configuration.EnvironmentSettings;
import george.projects.demos.configuration.ApplicationSecurityProfile;
import george.projects.demos.security.authentication.CustomAuthenticationProvider;
import george.projects.demos.security.service.MySqlUserDetailsService;

@Profile(ApplicationSecurityProfile.CUSTOM_AUTH_PROVIDER)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomAuthenticationProviderSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProviderSecurityConfig.class);

	private CustomAuthenticationProvider authenticationProvider;
	private EnvironmentSettings environmentSettings;

	public CustomAuthenticationProviderSecurityConfig() {
		LOG.info("Global security configuration with the {}", ApplicationSecurityProfile.CUSTOM_AUTH_PROVIDER);
	}

	/**
	 * Set the {@link CustomAuthenticationProvider} as a global authentication provider that
	 * uses the custom {@link MySqlUserDetailsService} that maps to the provided SQL schema
	 *
	 * If not, it will default will be {@link DaoAuthenticationProvider} and auth will fail
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder
				.authenticationProvider(authenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeRequests()
				.antMatchers(environmentSettings.allowedUrlPatterns()).permitAll()
				.antMatchers("/siteAdmin/**").hasRole("ADMIN_SITE")
				.anyRequest().authenticated()
				.and()
				.httpBasic();
	}

	@Autowired
	public void setAuthenticationProvider(CustomAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Autowired
	public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
		this.environmentSettings = environmentSettings;
	}
}

package george.projects.demos.security.config;

import javax.sql.DataSource;

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

import george.projects.demos.configuration.SecurityProfile;
import george.projects.demos.security.authentication.CustomAuthenticationProvider;

@Profile(SecurityProfile.CUSTOM_AUTH_PROVIDER)
//@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomAuthenticationProviderSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProviderSecurityConfig.class);

	private CustomAuthenticationProvider authenticationProvider;
	private DataSource dataSource;

	public CustomAuthenticationProviderSecurityConfig() {
		LOG.info("Global security configuration with CUSTOM AUTHENTICATION PROVIDER");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder
				.authenticationProvider(authenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeRequests()
				.anyRequest().authenticated();
	}

	@Autowired
	public void setAuthenticationProvider(CustomAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}

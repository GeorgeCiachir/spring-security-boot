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

import george.projects.demos.configuration.EnvironmentSettings;
import george.projects.demos.configuration.SecurityProfile;

@Profile(SecurityProfile.DEFAULT_AUTH_PROVIDER)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DefaultAuthenticationProviderSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticationProviderSecurityConfig.class);

	private static final String USERS_QUERY = "select username, password, enabled from user where username = ?";
	private static final String ROLES_QUERY = "SELECT role_id, role_name FROM (SELECT * FROM user RIGHT JOIN user_has_role ON user.user_id = user_has_role.user_user_id where username = ?) as T INNER JOIN role on role_role_id = role_id";

	private EnvironmentSettings environmentSettings;
	private DataSource dataSource;

	public DefaultAuthenticationProviderSecurityConfig() {
		LOG.info("Global security configuration with the DEFAULT AUTHENTICATION PROVIDER");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder
				.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(USERS_QUERY)
				.authoritiesByUsernameQuery(ROLES_QUERY);
		//Note: could use a password encoder but this would mean to hash th password here
		//and let it visible when it comes from outside. Not a good idea
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers(environmentSettings.allowedUrlPatterns()).permitAll()
				.antMatchers("/siteAdmin/**").hasRole("ADMIN_SITE")
				.anyRequest().authenticated();
	}

	@Autowired
	public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
		this.environmentSettings = environmentSettings;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}

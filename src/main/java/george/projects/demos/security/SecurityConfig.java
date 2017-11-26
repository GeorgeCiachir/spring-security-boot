package george.projects.demos.security;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String USERS_QUERY = "select username, password, enabled from user where username = ?";
	private static final String ROLES_QUERY = "SELECT role_id, role_name FROM (SELECT * FROM user RIGHT JOIN user_has_role ON user.user_id = user_has_role.user_user_id where username = ?) as T INNER JOIN role on role_role_id = role_id";


	@Resource
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder
				.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(USERS_QUERY)
				.authoritiesByUsernameQuery(ROLES_QUERY);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.httpBasic()
				.and()
				.authorizeRequests()
				.anyRequest().authenticated();
	}
}

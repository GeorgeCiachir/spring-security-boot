package george.projects.demos.configuration.security;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 9)
public class UserRoutesSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ROLES_QUERY = "select u.email, r.role from user u inner join user_role ur on(u.user_id=ur.user_id) inner join role r on(ur.role_id=r.role_id) where u.email=?";
	private static final String USERS_QUERY = "select email, password, active from user where email=?";

	@Resource
	private DataSource dataSource;

	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder)
				.usersByUsernameQuery(USERS_QUERY)
				.authoritiesByUsernameQuery(ROLES_QUERY);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.antMatcher("/userPage/**")
				.authorizeRequests()
				.antMatchers("/premiumUsers").hasRole("ROLE_USER")
				.antMatchers("/normalUsers").hasRole("ROLE_USER")
				.anyRequest().authenticated();
	}

}

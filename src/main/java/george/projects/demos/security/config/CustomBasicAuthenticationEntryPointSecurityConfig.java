
package george.projects.demos.security.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import george.projects.demos.configuration.ApplicationSecurityProfile;
import george.projects.demos.configuration.EnvironmentSettings;
import george.projects.demos.configuration.webMvc.DefaultSpringLoginSecurityControllerWebConfig;
import george.projects.demos.security.logout.CustomLogoutSuccessHandler;

/**
 * Configuration using the default Spring security controller for login
 *
 * A JSP page must be provided for the controller to use, and the controller itself is
 * registered in the {@link DefaultSpringLoginSecurityControllerWebConfig}
 *
 * In this configuration, "in-memory" authentication is used, in two possible ways:
 * 		1. Override the {@link #userDetailsService()} of the {@link WebSecurityConfigurerAdapter} and configure the global Auth manager to used it
 * 		2. Directly configure the global Auth manager, by directly adding users and passwords (a bit messy)
 */
@Profile(ApplicationSecurityProfile.IN_MEMORY_AUTH_WITH_SPRING_SEC_CONTROLLER_LOGIN)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomBasicAuthenticationEntryPointSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private EnvironmentSettings environmentSettings;

	@Resource
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("George@company.com").password("$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q").authorities("ROLE_ADMIN_OPS").build());
		manager.createUser(User.withUsername("Petre@gmail.com").password("$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q").roles("SIMPLE_USER").build());
		return manager;
	}

	/**
	 * configure the global Auth manager to use the newly created userDetailsService
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder
				.userDetailsService(userDetailsService())
				.passwordEncoder(passwordEncoder);
	}

	/**
	 * Directly configure the global Auth manager, by directly adding users and passwords
	 * More messy than using the above InMemoryUserDetailsManager
	 */
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
//		authManagerBuilder
//				.inMemoryAuthentication()
//				.withUser("George@company.com")
//				.password("$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q") //the String value is "password"
//				.roles("ADMIN_OPS")
//				.and()
//				.withUser("Petre@gmail.com")
//				.password("$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q") //the String value is "password"
//				.roles("SIMPLE_USER")
//				.and()
//				.passwordEncoder(passwordEncoder);
//	}


	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeRequests()
				.regexMatchers(environmentSettings.allowedUrlPatterns()).permitAll()

				.regexMatchers("/premiumUser/.*").hasRole("SIMPLE_USER")
//				.regexMatchers("/opsAdmin").access("hasRole('ADMIN_OPS') and authentication.principal.equals('George@company.com')")
				.regexMatchers("/opsAdmin").access("hasRole('ROLE_ADMIN_OPS')")

				.anyRequest()
				.authenticated();

		httpSecurity
				.formLogin()
				.loginPage("/login")
				// If not specified, the initial requested url will be used for forwarding the request
//				.successForwardUrl("/description")
				.permitAll();

		// This goes through the default spring security controller logout url mapping
		httpSecurity.logout();

		// This goes through the application's SecurityController customLogout url mapping
		httpSecurity
				.logout()
				.logoutUrl("/security/customLogout")
				.logoutSuccessHandler(new CustomLogoutSuccessHandler())
				.invalidateHttpSession(true) //true by default
				.addLogoutHandler(new SecurityContextLogoutHandler())
				.deleteCookies("JSESSIONID");
	}
}

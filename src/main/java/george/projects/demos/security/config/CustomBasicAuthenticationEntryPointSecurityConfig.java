package george.projects.demos.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import george.projects.demos.configuration.ApplicationSecurityProfile;
import george.projects.demos.security.entrypoint.CustomBasicAuthenticationEntryPoint;

@Profile(ApplicationSecurityProfile.CUSTOM_BASIC_AUTHENTICATION_ENTRY_POINT)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomBasicAuthenticationEntryPointSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	private CustomBasicAuthenticationEntryPoint entryPoint;

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("George@company.com").password("$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q").roles("ADMIN_OPS").build());
		manager.createUser(User.withUsername("Petre@gmail.com").password("$2a$10$mxFO2xJN4xYTDR5D176Pd.huyp6nybiMdPQAd2XSxyDEf/8ennr4q").roles("SIMPLE_USER").build());
		return manager;
	}


	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeRequests()
				.regexMatchers("/premiumUser/.*").hasRole("SIMPLE_USER")
				.regexMatchers("/opsAdmin/.*").access("hasRole('ADMIN_OPS') and authentication.principal.equals('George@company.com')")
				.anyRequest()
				.authenticated()

				.and().httpBasic().authenticationEntryPoint(entryPoint)

				.and().requiresChannel().anyRequest().requiresSecure()

				.and().addFilter(basicAuthenticationFilter(super.authenticationManagerBean()));

		//FIXME: need to fix this so that it actually works
//		httpSecurity.formLogin().loginPage("/login").permitAll();
	}


	private BasicAuthenticationFilter basicAuthenticationFilter(AuthenticationManager authManager) {
		return new BasicAuthenticationFilter(authManager, entryPoint);
	}
}

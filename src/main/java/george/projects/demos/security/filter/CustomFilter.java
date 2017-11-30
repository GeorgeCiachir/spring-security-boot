package george.projects.demos.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import george.projects.demos.dao.RoleDao;
import george.projects.demos.dao.UserDao;
import george.projects.demos.security.authentication.CustomAuthenticationProvider;
import george.projects.demos.security.service.MySqlUserDetailsService;

public class CustomFilter extends OncePerRequestFilter {

	private static final String DRIVER_CLASS_NAME = "org.springframework.jdbc.datasource.DriverManagerDataSource";
	private static final String URL = "jdbc:mysql://localhost:3306/application_security_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private MySqlUserDetailsService retrievalService = new MySqlUserDetailsService();
	private CustomAuthenticationProvider authenticationProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		setup();

		boolean debug = this.logger.isDebugEnabled();
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("MyToken ")) {
			try {
				String[] credentials = this.extractAndDecodeHeader(header);

				assert credentials.length == 2;

				String username = credentials[0];
				if (debug) {
					this.logger.debug("MyToken header found for user \'" + username + "\'");
				}

				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, credentials[1]);
				authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
				Authentication authResult = authenticationProvider.authenticate(authRequest);
				this.logger.debug("Authentication success: " + authResult);
				SecurityContextHolder.getContext().setAuthentication(authResult);

			} catch (AuthenticationException e) {
				SecurityContextHolder.clearContext();
				if (debug) {
					this.logger.debug("Authentication request for failed: " + e);
				}

				return;
			}

			chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	private String[] extractAndDecodeHeader(String header) {
		String username = header.substring("MyToken ".length(), header.indexOf("/"));
		String password = header.substring(header.indexOf("/") + 1);
		return new String[] { username, password };
	}

	private void setup() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DRIVER_CLASS_NAME);
		dataSource.setUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);
		RoleDao roleDao = new RoleDao();
		UserDao userDao = new UserDao();
		roleDao.setJdbcTemplate(new JdbcTemplate(dataSource));
		userDao.setJdbcTemplate(new JdbcTemplate(dataSource));
		retrievalService.setRoleDao(roleDao);
		retrievalService.setUserDao(userDao);
	}

	public void setAuthenticationProvider(CustomAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}
}

package george.projects.demos.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import george.projects.demos.security.authentication.CustomAuthenticationProvider;

@Component
public class CustomFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(CustomFilter.class);

	private CustomAuthenticationProvider authenticationProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("MyToken ")) {
			try {
				String[] credentials = extractAndDecodeHeader(header);

				assert credentials.length == 2;

				String username = credentials[0];
				LOG.info("MyToken header found for user \'" + username + "\'");

				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, credentials[1]);
				Authentication authResult = authenticationProvider.authenticate(authRequest);
				LOG.info("Authentication success: " + authResult);
				SecurityContextHolder.getContext().setAuthentication(authResult);
			} catch (AuthenticationException e) {
				SecurityContextHolder.clearContext();
				LOG.info("Authentication request for failed: " + e);
				return;
			}

		}
		chain.doFilter(request, response);
	}

	private String[] extractAndDecodeHeader(String header) {
		String username = header.substring("MyToken ".length(), header.indexOf("/"));
		String password = header.substring(header.indexOf("/") + 1);
		return new String[] { username, password };
	}

	@Autowired
	public void setAuthenticationProvider(CustomAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}
}

package george.projects.demos.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import george.projects.demos.security.service.MySqlUserDetailsService;

@Service
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	private MySqlUserDetailsService retrievalService;

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();

		UserDetails user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);

		Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");

		try {
			additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
		}
		catch (AuthenticationException e) {
			LOG.info(e.getMessage(), e);
			throw e;
		}

		return createSuccessAuthentication(user.getUsername(), authentication, user);
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
		return retrievalService.loadUserByUsername(username);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() == null) {
			LOG.info("Authentication failed; no credentials provided");
			throw new BadCredentialsException("Authentication failed; no credentials provided");
		}

		if (!userDetails.getPassword().equals(authentication.getCredentials())) {
			LOG.info("Authentication failed; invalid password");
			throw new BadCredentialsException("Authentication failed; invalid password");
		}
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(principal,
														user.getPassword(),
														user.getAuthorities());
		authenticationToken.setDetails(authentication.getDetails());

		return authenticationToken;
	}

	@Autowired
	public void setRetrievalService(MySqlUserDetailsService retrievalService) {
		this.retrievalService = retrievalService;
	}
}
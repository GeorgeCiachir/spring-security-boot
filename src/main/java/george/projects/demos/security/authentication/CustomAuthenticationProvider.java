package george.projects.demos.security.authentication;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import george.projects.demos.dao.RoleDao;
import george.projects.demos.dao.UserDao;
import george.projects.demos.model.User;

@Service
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Resource
	private MySqlRetrievalService retrievalService;

	@Resource
	private UserDao userDao;

	@Resource
	private RoleDao roleDao;

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();

		UserDetails user;
		try {
			user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
		} catch (UsernameNotFoundException e) {
			LOG.info("User not found", e);
			throw e;
		}

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
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		User user = userDao.findByUsername(username);
		user.setRoles(roleDao.findUserRoles(username));
		return new UserInfo(user.getUserName(), user.getPassword(), user.getRoles());
	}

	@Override
	protected void additionalAuthenticationChecks(final UserDetails userDetails, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
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
}
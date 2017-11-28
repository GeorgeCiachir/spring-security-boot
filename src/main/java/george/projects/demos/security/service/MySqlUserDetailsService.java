package george.projects.demos.security.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import george.projects.demos.dao.RoleDao;
import george.projects.demos.dao.UserDao;
import george.projects.demos.model.Role;
import george.projects.demos.model.User;
import george.projects.demos.security.authentication.UserInfo;

@Service
public class MySqlUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MySqlUserDetailsService.class);

	private UserDao userDao;
	private RoleDao roleDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try {
			user = userDao.findByUsername(username);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.info("Authentication failed; User not found");
			throw new UsernameNotFoundException(username);
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		Set<Role> roles = roleDao.findUserRoles(username);
		user.setRoles(roles);
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new UserInfo(user.getUserName(), user.getPassword(), grantedAuthorities);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
}

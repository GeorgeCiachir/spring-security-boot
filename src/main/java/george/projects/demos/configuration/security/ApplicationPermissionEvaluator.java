package george.projects.demos.configuration.security;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class ApplicationPermissionEvaluator implements PermissionEvaluator {

	@Resource
	private DataSource dataSource;

	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

		JdbcTemplate template = new JdbcTemplate(dataSource);

		Object[] args = {((User) authentication.getPrincipal()).getUsername(), targetDomainObject.getClass().getName(), permission.toString()};

		int count = template.queryForObject("select count(*) from permissions p where p.username = ? and p.target = ? and p.permission = ?", args, Integer.class);
//		String = template.queryForObject("select count(*) from permissions p where p.username = ? and p.target = ? and p.permission = ?", args, Integer.class);
		return count == 1;
	}


	public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
		return false;
	}
}

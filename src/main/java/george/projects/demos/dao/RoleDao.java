package george.projects.demos.dao;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import george.projects.demos.model.Role;

@Service
public class RoleDao {

	private static final String ROLES_QUERY = "SELECT role_id, role_name FROM (SELECT * FROM user RIGHT JOIN user_has_role ON user.user_id = user_has_role.user_user_id where username = ?) as T INNER JOIN role on role_role_id = role_id";

	private JdbcTemplate jdbcTemplate;

	public Set<Role> findUserRoles(String username) {
		return jdbcTemplate.query(ROLES_QUERY, new String[]{username}, (resultSet, rowNum) -> {
			Role role = new Role();
			role.setId(resultSet.getLong("role_id"));
			role.setName(resultSet.getString("role_name"));
			return role;
		}).stream().collect(Collectors.toSet());
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

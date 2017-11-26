package george.projects.demos.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import george.projects.demos.model.Role;
import george.projects.demos.model.User;

@Service
public class UserDao {

	private static final String USERS_QUERY = "select * from user where username = ?";
	private static final String ROLES_QUERY = "SELECT role_id, role_name FROM (SELECT * FROM user RIGHT JOIN user_has_role ON user.user_id = user_has_role.user_user_id where username = ?) as T INNER JOIN role on role_role_id = role_id";

	@Resource
	private JdbcTemplate jdbcTemplate;

	public User findByUsername(String username) {
		return jdbcTemplate.queryForObject(USERS_QUERY, new Object[] { username }, (resultSet, rowNumber) -> {
			User user = new User();
			user.setId(resultSet.getLong("user_id"));
			user.setUserName(resultSet.getString("username"));
			user.setEmail(resultSet.getString("email"));
			user.setPassword(resultSet.getString("password"));
			user.setEnabled(resultSet.getBoolean("enabled"));
			user.setRoles(finUserRoles(username));
			return user;
		});
	}

	private List<Role> finUserRoles(String username) {
		return jdbcTemplate.query(ROLES_QUERY, new String[]{username}, (resultSet, rowNum) -> {
			Role role = new Role();
			role.setId(resultSet.getLong("role_id"));
			role.setName(resultSet.getString("role_name"));
			return role;
		});
	}
}

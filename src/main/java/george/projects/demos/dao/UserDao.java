package george.projects.demos.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import george.projects.demos.model.User;

@Service
public class UserDao {

	private static final String USERS_QUERY = "select * from user where username = ?";

	private JdbcTemplate jdbcTemplate;

	public User findByUsername(String username) {
		return jdbcTemplate.queryForObject(USERS_QUERY, new Object[] { username }, (resultSet, rowNumber) -> {
			User user = new User();
			user.setId(resultSet.getLong("user_id"));
			user.setUserName(resultSet.getString("username"));
			user.setEmail(resultSet.getString("email"));
			user.setPassword(resultSet.getString("password"));
			user.setEnabled(resultSet.getBoolean("enabled"));
			return user;
		});
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

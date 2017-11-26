package george.projects.demos.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ExternalServicesConfiguration {

//	private static final String USERS_QUERY = "select * from user where username = ?";
//	private static final String ROLES_QUERY = "SELECT role_id, role_name FROM (SELECT * FROM user RIGHT JOIN user_has_role ON user.user_id = user_has_role.user_user_id where username = ?) as T INNER JOIN role on role_role_id = role_id";



	private static final String DRIVER_CLASS_NAME = "org.springframework.jdbc.datasource.DriverManagerDataSource";
	private static final String URL = "jdbc:mysql://localhost:3306/application_security_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DRIVER_CLASS_NAME);
		dataSource.setUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);
		return dataSource;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

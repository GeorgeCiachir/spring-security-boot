package george.projects.demos.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ExternalServicesConfiguration {

	private static final String DRIVER_CLASS_NAME = "org.springframework.jdbc.datasource.DriverManagerDataSource";
	private static final String URL = "jdbc:mysql://localhost:3306/application_security_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	//FIXME: Find a better solution for the Tomcat container for the jdbc driver (at the moment it works when using one of the commented lines below)
	@Bean
	public DataSource dataSource() throws ClassNotFoundException {
//		Class.forName("com.mysql.jdbc.Driver");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
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

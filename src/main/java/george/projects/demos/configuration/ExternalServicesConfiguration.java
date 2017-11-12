package george.projects.demos.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ExternalServicesConfiguration {

	private static final String DRIVER_CLASS_NAME = "org.springframework.jdbc.datasource.DriverManagerDataSource";
	private static final String URL = "jdbc:mysql://localhost:3306/securityDb";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

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

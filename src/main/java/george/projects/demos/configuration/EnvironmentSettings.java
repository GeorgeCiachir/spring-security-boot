package george.projects.demos.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.security")
public class EnvironmentSettings {

	private String authentication;
	private List<String> unrestrictedUrls;
	private String loginUrl;
	private String logoutUrl;
	private String redirectToUrlAfterLogout;

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public List<String> getUnrestrictedUrls() {
		return unrestrictedUrls;
	}

	public void setUnrestrictedUrls(List<String> unrestrictedUrls) {
		this.unrestrictedUrls = unrestrictedUrls;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getRedirectToUrlAfterLogout() {
		return redirectToUrlAfterLogout;
	}

	public void setRedirectToUrlAfterLogout(String redirectToUrlAfterLogout) {
		this.redirectToUrlAfterLogout = redirectToUrlAfterLogout;
	}
}

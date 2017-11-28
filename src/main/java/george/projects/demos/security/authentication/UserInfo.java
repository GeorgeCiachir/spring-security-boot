package george.projects.demos.security.authentication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfo implements UserDetails {

	private String username;
	private String password;
	private Set<GrantedAuthority> authorities = new HashSet<>();
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	public UserInfo(String username, String password, Set<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities.addAll(authorities);
	}

	UserInfo(){}

	@Override
	public final String getUsername() {
		return username;
	}

	@Override
	public final String getPassword() {
		return password;
	}

	@Override
	public final Set<GrantedAuthority> getAuthorities() {
		return new HashSet<>(authorities);
	}

	//The bellow properties should be set, not defaulted to true
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public static UserInfoBuilder builder() {
		return UserInfoBuilder.getInstance();
	}

	public static class UserInfoBuilder {

		private static UserInfo userInfo;

		private UserInfoBuilder(){
			userInfo = new UserInfo();
		}

		static UserInfoBuilder getInstance() {
			return new UserInfoBuilder();
		}

		public UserInfoBuilder withUsername(String username) {
			userInfo.setUsername(username);
			return this;
		}

		public UserInfoBuilder withPassword(String password) {
			userInfo.setPassword(password);
			return this;
		}

		public UserInfoBuilder withAuthorities(Set<GrantedAuthority> authorities) {
			userInfo.setAuthorities(authorities);
			return this;
		}

		public UserInfo build() {
			return userInfo;
		}
	}
}
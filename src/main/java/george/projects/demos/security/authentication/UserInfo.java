package george.projects.demos.security.authentication;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import george.projects.demos.model.Role;

public class UserInfo implements UserDetails {

	private String username;
	private String password;
	private Set<GrantedAuthority> authorities = new HashSet<>();
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	UserInfo(String username, String password, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.authorities.addAll(roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()));
	}

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
}
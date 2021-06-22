package com.springtutorial.springsecurityjpa.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	
	private String username;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	public MyUserDetails(User user) {
		this.username = user.getUserName();
		this.password = user.getPassword();
		this.active = user.isActive();
		this.authorities = Arrays.stream(user.getRoles().split(","))
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList());
				
	}
	
	public MyUserDetails() {
		super();
	}

	/**
	 * Returns the authorities granted to the user. Cannot return <code>null</code>.
	 * @return the authorities, sorted by natural key (never <code>null</code>)
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * Returns the password used to authenticate the user.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the username used to authenticate the user. Cannot return
	 * <code>null</code>.
	 * @return the username (never <code>null</code>)
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Indicates whether the user's account has expired. An expired account cannot be
	 * authenticated.
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 * @return <code>true</code> if the user's credentials are valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	public boolean isEnabled() {
		return active;
	}

	@Override
	public String toString() {
		return "MyUserDetails [username=" + username + ", password=" + password + ", active=" + active
				+ ", authorities=" + authorities + "]";
	}
	
}

package com.springtutorial.springsecurityjpa;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springtutorial.springsecurityjpa.exceptions.JwtAuthenticationEntryPoint;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private Filter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("Hits configure authentication");
		 auth.userDetailsService(userDetailsService);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("Hits configure authorization");
		http.cors().and().csrf().disable().authorizeRequests()
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/addUser").hasRole("ADMIN")
			.antMatchers("/user").hasAnyRole("ADMIN","USER")
			.antMatchers("/").permitAll()
			.antMatchers("/authenticate").permitAll()
			.antMatchers("/logbackendout").permitAll()
			.anyRequest().authenticated()
			.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			//.formLogin();
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
}

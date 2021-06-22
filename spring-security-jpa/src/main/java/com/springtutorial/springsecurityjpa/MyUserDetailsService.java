package com.springtutorial.springsecurityjpa;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springtutorial.springsecurityjpa.models.MyUserDetails;
import com.springtutorial.springsecurityjpa.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserRepository userRepository;
	
	@Override
	public
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Hits loadUserByUsername " + username);
		Optional<User> user = userRepository.findByUserName(username);
		logger.info("Hits loadUserByUsername " + user.get());
		user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
		MyUserDetails mud = user.map(MyUserDetails::new).get();
		logger.info("mud " + mud);
		return mud;
	} 
}

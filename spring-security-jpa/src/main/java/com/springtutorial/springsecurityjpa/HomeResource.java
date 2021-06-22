package com.springtutorial.springsecurityjpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springtutorial.springsecurityjpa.daos.InvalidJwtRepository;
import com.springtutorial.springsecurityjpa.models.AuthenticationRequest;
import com.springtutorial.springsecurityjpa.models.InvalidJwt;
import com.springtutorial.springsecurityjpa.models.User;
import com.springtutorial.springsecurityjpa.util.JwtUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController	
public class HomeResource {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	InvalidJwtRepository invalidJwtRepository;
	
	@GetMapping("/")
	public String home() {
		return "Welcome";
	}
	
	@GetMapping("/user")
	public String user() {
		logger.info("Hits user API");
		return "Welcome User";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "Welcome Admin";
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello!";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		logger.info("Hits authenticate API");
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		logger.info("In authenticate API, before calling loadUserByUsername");
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(jwt);
	}
	
	@RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
		logger.info("Hits refreshToken API");
		
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(expectedMap.get("sub").toString());
		
		final String jwt = jwtTokenUtil.refreshToken(expectedMap, userDetails);
		
		//return ResponseEntity.ok(new AuthenticationResponse(jwt));
		return ResponseEntity.ok(jwt);
	}
	
	@RequestMapping(value = "/logbackendout", method = RequestMethod.GET)
	public ResponseEntity<?> logout(HttpServletRequest request) throws Exception {
		logger.info("Hits logout API");
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String jwt = null;
			

		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				jwt = authorizationHeader.substring(7);
				invalidJwtRepository.save(new InvalidJwt(jwt));
		}
		
		
		
		
		//return ResponseEntity.ok(new AuthenticationResponse(jwt));
		return ResponseEntity.ok("token invalidated");
	}
	
	@PostMapping("/addUser")
	public void addUser(@RequestBody User user) {
		String pwd = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(pwd);
		userRepository.save(user);
		
	}
	
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
}

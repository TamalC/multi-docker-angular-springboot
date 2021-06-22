package com.springtutorial.springsecurityjpa.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtutorial.springsecurityjpa.daos.InvalidJwtRepository;
import com.springtutorial.springsecurityjpa.exceptions.CustomJwtExpiredException;
import com.springtutorial.springsecurityjpa.exceptions.CustomJwtInvalidException;
import com.springtutorial.springsecurityjpa.models.InvalidJwt;
import com.springtutorial.springsecurityjpa.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
  	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	InvalidJwtRepository invalidJwtRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
				logger.info("Before doFilterInternal inside JwtRequestFilter");
				final String authorizationHeader = request.getHeader("Authorization");
				
				String username = null;
				String jwt = null;
				
				
				try {
					

					if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
						jwt = authorizationHeader.substring(7);
						username = jwtUtil.extractUsername(jwt);
					}
					
					if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
						if(jwtUtil.validateToken(jwt, userDetails)) {
														
							if(!isJwtInvalid(jwt)) {
								UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken = 
										new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
								UsernamePasswordAuthenticationToken
									.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
								SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken);
								logger.info("Is the user Authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
							}
							else {
								CustomJwtInvalidException customJwtInvalidException = new CustomJwtInvalidException("Inavlid token");
								request.setAttribute("exception", customJwtInvalidException);
							}
							
							
						}
					}
					logger.info("After doFilterInternal inside JwtRequestFilter");
					
				}
				catch(ExpiredJwtException e) {
					
					String isRefreshToken = request.getHeader("isRefreshToken");
					String requestUrl = request.getRequestURL().toString();
					
					if(isRefreshToken == null) {
						CustomJwtExpiredException customJwtExpiredException = new CustomJwtExpiredException("Token has expired");
						request.setAttribute("exception", customJwtExpiredException);
						
					}
						
					
					else if(isRefreshToken.equals("true") && requestUrl.contains("/refreshToken")) {
						UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken = 
								new UsernamePasswordAuthenticationToken(null, null, null);
						SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken);
						request.setAttribute("claims", e.getClaims());
						logger.info("Is the user Authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
					}
						
				}
				
				catch(BadCredentialsException ex)
				 {
					 request.setAttribute("exception", ex);
				 }
				
				chain.doFilter(request, response);
			}
	
	public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
	
	private boolean isJwtInvalid(String jwt) {
		List<InvalidJwt> inValidJwts = invalidJwtRepository.findAll();
		for(InvalidJwt i : inValidJwts) {
			String r = i.getJwt();
			System.out.println("ret: " + r );
			System.out.println("jwt: " + jwt );
			if(r.equals(jwt))
				return true;
		}
		return false;
	}
}

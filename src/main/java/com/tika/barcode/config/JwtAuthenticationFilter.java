package com.tika.barcode.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tika.barcode.service.JwtService;
import com.tika.barcode.service.UserService;

/**
 * Processes an {@link JwtAuthenticationFilter} request.
 * @author Raghu
 *
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		if(authHeader==null) {
			filterChain.doFilter(request, response);
			return;
		}else {
			if (!authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
		}
		
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUserName(jwt);
		if (!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
			if (jwtService.isTokenValid(jwt, userDetails)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				context.setAuthentication(authToken);
				SecurityContextHolder.setContext(context);
			}
		}
		filterChain.doFilter(request, response);
	}
	public String getCurrentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			return authentication.getName();
		}
		return null;
	}

}

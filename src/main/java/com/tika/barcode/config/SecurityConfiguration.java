package com.tika.barcode.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tika.barcode.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Processes an {@link SecurityConfiguration } request.
 * @author Raghu
 *
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UserService userService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						request -> request.requestMatchers(new AntPathRequestMatcher("/test"),new AntPathRequestMatcher("/api/v1/**"),new AntPathRequestMatcher("/api/v1/users/usersList"),
								new AntPathRequestMatcher("/api/v1/users/getUsersList"), new AntPathRequestMatcher("/getFaxPdf/**"), new AntPathRequestMatcher("/h2-console/**"),
								new AntPathRequestMatcher("api/v1/users/update/user/password/**"), new AntPathRequestMatcher("/api/v1/auth/signup"), new AntPathRequestMatcher("/api/v1/users/create/user"),
								new AntPathRequestMatcher("/api/v1/notification/emails/forgotpassword"), new AntPathRequestMatcher("/api/v1/auth/signin"),
								new AntPathRequestMatcher("v3/api-docs"), new AntPathRequestMatcher("/swagger-ui.html"),new AntPathRequestMatcher("/api-docs/**"), new AntPathRequestMatcher("/swagger-ui/**") , 
								new AntPathRequestMatcher("/api/v1/users/updateUser/**") , new AntPathRequestMatcher("/api/v1/scanSupport/**") , new AntPathRequestMatcher("/api/consignment/list/all**"),
								new AntPathRequestMatcher("/api/get/all/territoryList/**") , new AntPathRequestMatcher("api/v1/territory/get/all_territory/**") ,  new AntPathRequestMatcher("api/v1/terr_align/update/**") ,
								  new AntPathRequestMatcher("api/v1/inventory//update/inventory/details/**")).permitAll().anyRequest().authenticated())
				.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService.userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}

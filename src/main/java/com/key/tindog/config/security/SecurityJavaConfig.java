package com.key.tindog.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

	private RequestAwareAuthenticationSuccessHandler mySuccessHandler;
	private AuthenticationEntryPoint restAuthenticationEntryPoint;
	private PasswordEncoder passwordEncoder;

	public SecurityJavaConfig(RequestAwareAuthenticationSuccessHandler mySuccessHandler, AuthenticationEntryPoint restAuthenticationEntryPoint, PasswordEncoder passwordEncoder) {
		this.mySuccessHandler = mySuccessHandler;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin").password(passwordEncoder.encoder().encode("admin")).roles("ADMIN")
				.and()
				.withUser("user").password(passwordEncoder.encoder().encode("user")).roles("USER");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
				.and()
					.csrf().disable()
					.exceptionHandling()
					.authenticationEntryPoint(restAuthenticationEntryPoint)
				.and()
					.authorizeRequests()
					.antMatchers("/getProfileById/*").authenticated()
					.antMatchers("/admin**").hasRole("ADMIN")
					.anyRequest().permitAll()
				.and()
					.formLogin()
					.successHandler(mySuccessHandler)
				.and()
					.logout();
	}
}

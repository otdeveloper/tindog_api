package com.key.tindog.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

	private RequestAwareAuthenticationSuccessHandler mySuccessHandler;
	private AuthenticationEntryPoint restAuthenticationEntryPoint;

	public SecurityJavaConfig(RequestAwareAuthenticationSuccessHandler mySuccessHandler, AuthenticationEntryPoint restAuthenticationEntryPoint) {
		this.mySuccessHandler = mySuccessHandler;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin").password(encoder().encode("admin")).roles("ADMIN")
				.and()
				.withUser("user").password(encoder().encode("user")).roles("USER");
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
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
					.anyRequest().permitAll()
					//.antMatchers("/getProfilesByLocation/**").hasRole("ADMIN")
				.and()
					.formLogin()
					.successHandler(mySuccessHandler)
				.and()
					.logout();
	}
}

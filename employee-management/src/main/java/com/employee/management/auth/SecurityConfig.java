package com.employee.management.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
	        http.authorizeRequests().antMatchers(HttpMethod.GET,"*/employees/*").permitAll();
	        http.authorizeRequests().antMatchers(HttpMethod.POST).hasAnyRole("USER").anyRequest().fullyAuthenticated().and().httpBasic();
	        http.authorizeRequests().antMatchers(HttpMethod.PUT).hasAnyRole("USER").anyRequest().fullyAuthenticated().and().httpBasic();
	        http.authorizeRequests().antMatchers(HttpMethod.DELETE).hasAnyRole("USER").anyRequest().fullyAuthenticated().and().httpBasic();
	}
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
}

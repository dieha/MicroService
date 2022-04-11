package com.redhat.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.redhat.lab.util.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		// 禁用csrf
		// options全部放行
		// post 放行
		httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers("/login", "/h2/*").permitAll().antMatchers(HttpMethod.PUT)
				.authenticated().antMatchers(HttpMethod.DELETE).authenticated().antMatchers(HttpMethod.GET)
				.authenticated().antMatchers(HttpMethod.POST).authenticated();

		httpSecurity.headers().frameOptions().disable();
		httpSecurity.headers().cacheControl();
		httpSecurity.addFilterBefore(new JWTAuthenticationFilter(authenticationManager()),
				UsernamePasswordAuthenticationFilter.class);
	}
}

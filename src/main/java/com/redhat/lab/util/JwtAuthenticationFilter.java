package com.redhat.lab.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.redhat.lab.config.JwtConfig;
import com.redhat.lab.entity.JwtAccount;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	JwtConfig jwtConfig;
	JwtTokenUtil jwtTokenUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.jwtConfig = SpringUtil.getBean(JwtConfig.class);
		this.jwtTokenUtil = SpringUtil.getBean(JwtTokenUtil.class);

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// 取出Token
		String token = request.getHeader(jwtConfig.getTokenHeader());

		if (token != null && token.startsWith(jwtConfig.getTokenPrefix())) {

			JwtAccount jwtAccount = jwtTokenUtil.parseAccessToken(token);
			Set<GrantedAuthority> authorities = new HashSet<>();

			authorities.add(new SimpleGrantedAuthority("ROLE_" + "a"));

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAccount,
					jwtAccount.getAccount(), authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

}

package com.redhat.lab.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.redhat.lab.config.JwtConfig;
import com.redhat.lab.entity.JwtAccount;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Resource
	JwtConfig jwtConfig;
	@Resource
	JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// 取出Token
		String token = request.getHeader(jwtConfig.getTokenHeader());

		if (token != null && token.startsWith(jwtConfig.getTokenPrefix())) {
			try {

				JwtAccount jwtAccount = jwtTokenUtil.parseAccessToken(token);
				if (jwtAccount != null) {
					Set<GrantedAuthority> authorities = new HashSet<>();

					authorities.add(new SimpleGrantedAuthority("ROLE_" + "admin"));

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							jwtAccount, jwtAccount.getAccount(), authorities);

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (Exception e) {
				log.debug("Jwt fillter",e);
			}
		}

		filterChain.doFilter(request, response);
	}

}

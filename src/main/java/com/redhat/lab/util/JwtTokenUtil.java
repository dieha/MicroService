package com.redhat.lab.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.redhat.lab.config.JwtConfig;
import com.redhat.lab.entity.JwtAccount;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JwtTokenUtil {

	private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private JwtConfig jwtConfig;

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public String createAccessToken(JwtAccount account) {
		String token = Jwts.builder().setId(account.getAccount()).setIssuedAt(new Date())//
				.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration())) // 過期時間
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret()) // 加密
				.claim("accountId", account.getAccountId()).claim("ip", account.getIp()).compact();
		return jwtConfig.getTokenPrefix() + token;
	}

	
	public JwtAccount parseAccessToken(String token) {
		JwtAccount account = null;
		if (StringUtils.hasLength(token)) {
			try {
				// 去除JWT前綴
				token = token.substring(jwtConfig.getTokenPrefix().length());

				// 解析Token
				Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
				// 用户信息
				account = new JwtAccount(claims.getId(), claims.get("accountId").toString(),
						claims.get("ip").toString());
			} catch (Exception e) {
				log.info("Jwt error", e);
			}
		}
		return account;
	}

	public boolean isValid(String refreshTime) {
		LocalDateTime validTime = LocalDateTime.parse(refreshTime, df);
		LocalDateTime localDateTime = LocalDateTime.now();
		if (localDateTime.compareTo(validTime) > 0) {
			return false;
		}
		return true;
	}

}

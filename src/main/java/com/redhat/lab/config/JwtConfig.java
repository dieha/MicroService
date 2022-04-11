package com.redhat.lab.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "jwt")
@SuppressWarnings("static-access")
@Component
@Getter
@Setter
public class JwtConfig {
	public static final String REDIS_TOKEN_KEY_PREFIX = "TOKEN_";

	private long expiration;
	private long refreshTime; 
	private String secret; 
	private String tokenPrefix; 
	private String tokenHeader; 

}

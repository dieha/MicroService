package com.redhat.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtAccount {
	
	private final String account;
	private final String accountId;
	private final String ip;

}

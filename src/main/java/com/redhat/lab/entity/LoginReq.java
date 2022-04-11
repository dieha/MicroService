package com.redhat.lab.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginReq {

	@NotNull(message = "account is mandatory")
	@Min(1)
	@Max(20)
	private String account;
	@NotNull(message = "password is mandatory")
	@Min(1)
	@Max(20)
	private String password;

}

package com.redhat.lab.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.lab.entity.LoginReq;
import com.redhat.lab.entity.LoginRes;
import com.redhat.lab.usecase.LoginService;

@RestController
@RequestMapping("/")
public class LoginController {

	@Resource
	LoginService loginService;

	@GetMapping("/login")
	public LoginRes logins(@RequestBody LoginReq req) {

		if (loginService.login(req.getAccount(), req.getPassword())) {
			return new LoginRes("token");
		}
		return new LoginRes();
	}

	@PostMapping("/login")
	public LoginRes login(@RequestBody LoginReq req) {

		if (loginService.login(req.getAccount(), req.getPassword())) {
			return new LoginRes("token");
		}
		return new LoginRes();
	}

}

package com.redhat.lab.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.lab.entity.GeneralRes;
import com.redhat.lab.entity.JwtAccount;
import com.redhat.lab.entity.LoginReq;
import com.redhat.lab.entity.LoginRes;
import com.redhat.lab.usecase.LoginServiceImp;
import com.redhat.lab.util.AccessAddressUtils;
import com.redhat.lab.util.JwtTokenUtil;

@RestController
@RequestMapping("/")
@CrossOrigin
public class LoginController {

	@Resource
	LoginServiceImp loginService;
	@Resource
	JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public GeneralRes loginJwt(HttpServletRequest reauest,@Valid  @RequestBody LoginReq req) {

		try {
			if (loginService.login(req.getAccount(), req.getPassword())) {
				String ip = AccessAddressUtils.getIpAddress(reauest);
				final String token = jwtTokenUtil.createAccessToken(new JwtAccount(req.getAccount(), ip));
				return new GeneralRes("000", "ok", new LoginRes(token));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GeneralRes("500", "", null);
	}

	@GetMapping("/index")
	public String index(HttpServletRequest reauest) {

		return "1";
	}

	@PostMapping("/index")
	public String indexp(HttpServletRequest reauest) {

		return "10";
	}

	@GetMapping("/logins")
	public LoginRes logins(HttpServletRequest reauest) {

		return new LoginRes("test");
	}
}

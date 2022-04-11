package com.redhat.lab.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redhat.lab.config.JwtConfig;
import com.redhat.lab.util.JwtTokenUtil;

@Service
public class LoginServiceImp implements LoginService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtConfig jwtConfig;

	@Override
	public boolean login(String account, String password) {

		if ("admin".equals(account) && "password".equals(password)) {

			return true;
		} else {
			return false;
		}
	}

}

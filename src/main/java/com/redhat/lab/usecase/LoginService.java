package com.redhat.lab.usecase;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

	public boolean login(String account, String password) {

		if ("admin".equals(account) && "password".equals(password)) {
			return true;
		} else {
			return false;
		}
	}
}

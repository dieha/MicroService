package com.redhat.lab.usecase;

import com.redhat.lab.entity.Account;

public interface LoginService {

	public Account login(String identity, String account, String password);
}

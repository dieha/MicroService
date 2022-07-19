package com.redhat.lab.usecase;

import com.redhat.lab.entity.Account;
import com.redhat.lab.excepation.DataNotFountException;
import com.redhat.lab.excepation.PasswordException;

public interface LoginService {

	public Account login(String identity, String account, String password) throws DataNotFountException, PasswordException;
	public Account getAccount(String accountId);
}

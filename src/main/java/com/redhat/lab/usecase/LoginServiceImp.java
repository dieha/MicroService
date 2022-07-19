package com.redhat.lab.usecase;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.redhat.lab.dao.AccoountDao;
import com.redhat.lab.entity.Account;
import com.redhat.lab.excepation.DataNotFountException;
import com.redhat.lab.excepation.PasswordException;

@Service
public class LoginServiceImp implements LoginService {

	@Autowired
	private AccoountDao accoountDao;

	@Override
	public Account login(String identity, String account, String password)
			throws DataNotFountException, PasswordException {

		Optional<Account> optional = accoountDao.findOne(Example.of(Account.of(identity, account)));
		if (optional.isPresent()) {
			Account accountDB = optional.get();
			String pwd = new String(Base64.getUrlDecoder().decode(password));
			if (account.equals(accountDB.getAccount()) && pwd.endsWith(accountDB.getPassword())) {
				return accountDB;
			} else {
				throw new PasswordException(" password fail");
			}
		} else {
			throw new DataNotFountException(account + " not found");
		}
	}

	@Override
	public Account getAccount(String accountId) {

		Optional<Account> optional = accoountDao.findById(Long.parseLong(accountId));
		if (optional.isPresent()) {
			
			return optional.get();
		}
		return null;
	}

}

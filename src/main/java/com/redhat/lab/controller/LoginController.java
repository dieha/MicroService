package com.redhat.lab.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.lab.entity.Account;
import com.redhat.lab.entity.GeneralRes;
import com.redhat.lab.entity.JwtAccount;
import com.redhat.lab.entity.LoginRes;
import com.redhat.lab.excepation.DataNotFountException;
import com.redhat.lab.excepation.PasswordException;
import com.redhat.lab.gateways.AuditLogService;
import com.redhat.lab.gateways.MailService;
import com.redhat.lab.usecase.LoginServiceImp;
import com.redhat.lab.util.AccessAddressUtils;
import com.redhat.lab.util.JwtTokenUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/")
@CrossOrigin
public class LoginController {

	@Resource
	LoginServiceImp loginService;
	@Autowired
	MailService mailService;
	@Autowired
	AuditLogService auditLogService;
	@Resource
	JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public GeneralRes loginJwt(HttpServletRequest reauest, @RequestHeader(value = "uuid", required = false) String uuid,
			@Valid @RequestBody Account account) {

		try {
			auditLogService.logAudit(null, "login", account, uuid);

			account = loginService.login(account.getIdentity(), account.getAccount(), account.getPassword());
			if (account != null) {
				mailService.sendMail(account, uuid);
				String ip = AccessAddressUtils.getIpAddress(reauest);
				final String token = jwtTokenUtil
						.createAccessToken(new JwtAccount(account.getAccount(), account.getAccountId().toString(), ip));
				return new GeneralRes("000", "ok", new LoginRes(account.getAccountId().toString(), token));
			}
		} catch (DataNotFountException | PasswordException e) {

			return new GeneralRes("400", e.getMessage(), null);

		} catch (Exception e) {
			log.error("error", e);
		}
		return new GeneralRes("500", "internal error", null);
	}

	@GetMapping("/account/{id}")
	public GeneralRes account(HttpServletRequest reauest, @PathVariable("id") String accountId) {
		try {

			Account account = loginService.getAccount(accountId);
			return new GeneralRes("000", "ok", account);

		} catch (Exception e) {
			log.error("error", e);
		}
		return new GeneralRes("500", "internal error", null);
	}

}

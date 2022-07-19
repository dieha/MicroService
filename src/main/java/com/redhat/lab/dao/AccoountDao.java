package com.redhat.lab.dao;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redhat.lab.entity.Account;


@Repository
public interface AccoountDao extends JpaRepository<Account, Long> {
}

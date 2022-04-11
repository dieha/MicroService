package com.redhat.lab.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
public class Account {
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long account_id;

	@Column(name = "name")
	private String name;

	@Column(name = "password", nullable = true, length = 10)
	private String password;

	@Column(name = "create_date")
	private Date create_date;

}
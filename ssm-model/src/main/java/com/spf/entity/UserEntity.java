package com.spf.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntity implements Serializable{
	private Integer id;
	private String name;
	private String age;
	private String sex;
	private String phone;
	private String pwd;
}

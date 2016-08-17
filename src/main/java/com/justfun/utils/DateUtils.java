package com.justfun.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

public class DateUtils {
	public static void main(String[] args) {
		String salt = "admin" + RandomStringUtils.randomAlphanumeric(10);
		String pwd = new Md5Hash("123456",salt,2).toString();
		System.out.println(pwd);
	}
}

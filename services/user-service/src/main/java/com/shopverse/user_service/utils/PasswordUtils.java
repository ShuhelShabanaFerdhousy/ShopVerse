package com.shopverse.user_service.utils;

import java.util.regex.Pattern;

public class PasswordUtils {
	private static final Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$");
	
	public static boolean isPasswordValid(String password) {
		if(password == null) return false;
		return pattern.matcher(password).matches();
	}
}

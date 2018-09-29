package com.boylegu.springboot_vue;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class MD5Test {

	public static void main(String[] args) throws Exception {
		String pass = MD5("bangsun");
		// 1BAA263B0FB13A20768551FC6D123E68
		System.out.println(pass);
		System.out.println(Base64.encodeBase64String("bangsun".getBytes()));
	}

	private static String MD5(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			return toHex(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte[] bytes) {

		final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
}

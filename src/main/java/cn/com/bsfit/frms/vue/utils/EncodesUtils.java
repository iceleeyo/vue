package cn.com.bsfit.frms.vue.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class EncodesUtils {
	
	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	private static final String HMACSHA1 = "HmacSHA1";
	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;
	private static final int DEFAULT_AES_KEYSIZE = 128;
	private static final int DEFAULT_IVSIZE = 16;
	private static SecureRandom random = new SecureRandom();
	private static final Logger log = LoggerFactory.getLogger(EncodesUtils.class);

	private EncodesUtils() {
	}

	public static byte[] hmacSha1(byte[] input, byte[] key) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input);
		} catch (GeneralSecurityException e) {
			log.error("hmacSha1 error ", e);
		}
		return key;
	}

	public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) {
		byte[] actual = hmacSha1(input, key);
		return Arrays.equals(expected, actual);
	}

	public static byte[] generateHmacSha1Key() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
			keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			log.error("generateHmacSha1Key error ", e);
		}
		return null;
	}

	public static byte[] aesEncrypt(byte[] input, byte[] key) {
		return aes(input, key, 1);
	}

	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, 1);
	}

	public static String aesDecrypt(byte[] input, byte[] key) {
		byte[] decryptResult = aes(input, key, 2);
		return new String(decryptResult, Charset.forName(DEFAULT_URL_ENCODING));

	}

	public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
		byte[] decryptResult = aes(input, key, iv, 2);
		return new String(decryptResult, Charset.forName(DEFAULT_URL_ENCODING));

	}

	/**
	 * 如果解密失败，返回“new byte[0]”
	 * 
	 * @param input
	 * @param key
	 * @param mode
	 * @return
	 */
	private static byte[] aes(byte[] input, byte[] key, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			log.error("aes error ", e);
		}
		if (2 == mode)
			return new byte[0];
		else
			return input;
	}

	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_CBC);
			cipher.init(mode, secretKey, ivSpec);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			log.error("aes error ", e);
		}
		if (2 == mode)
			return new byte[0];
		else
			return input;
	}

	public static byte[] generateAesKey() {
		return generateAesKey(DEFAULT_AES_KEYSIZE);
	}

	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			log.error("generateAesKey error ", e);
		}
		return null;
	}

	public static byte[] generateIV() {
		byte[] bytes = new byte[DEFAULT_IVSIZE];
		random.nextBytes(bytes);
		return bytes;
	}

	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			log.error("decodeHex error ", e);
		}
		return null;
	}

	public static String encodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	public static String encodeUrlSafeBase64(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input);
	}

	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}

	/**
	 * 解密
	 * 
	 * @param pwd
	 *            密码
	 * @param secretKey
	 *            秘钥
	 * @return
	 */
	public static String decrypt(String pwd, String secretKey) {
		String ret = aesDecrypt(decodeBase64(pwd), decodeBase64(secretKey));
		if (StringUtils.isEmpty(ret)) {
			return pwd;
		}
		return ret;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String x = "白日依山尽adfsd==";
		String key = encodeBase64(generateAesKey());
		String y = encodeBase64(aes(x.getBytes("utf-8"), decodeBase64(key), 1));
		System.out.println(decrypt(x, key));
		System.out.println(decrypt(y, key));
	}
}

package org.dazzle.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.dazzle.common.exception.BaseException;

 

/**
 * <p>
 * 加密工具，可以进行MD5和SHA1加密计算.
 * </p>
 * @author hcqt@qq.com
 */
public class EncryptUtils {

	private static final String DIGEST_MD5 = "MD5";

	private static final String DIGEST_SHA1 = "SHA1";

	/**
	 * 16进制数值
	 */
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 生成MD5加密校验码
	 *
	 * @param string 待加密字符串
	 * @return MD5加密校验码
	 * @since 0.2
	 * @author hcqt@qq.com
	 */
	public static final String md5(String string) {
		return encrypt(DIGEST_MD5, string);
	}

	/**
	 * 生成MD5加密校验码
	 *
	 * @param file 待加密文件
	 * @return MD5加密校验码
	 * @since 0.2
	 * @author hcqt@qq.com
	 */
	public static final String md5(File file) {
		return encrypt(DIGEST_MD5, file);
	}
	
	/**
	 * 生成SHA1加密校验码
	 *
	 * @param string 待加密字符串
	 * @return SHA1加密校验码
	 * @since 0.2
	 * @author hcqt@qq.com
	 */
	public static final String sha1(String string) {
		return encrypt(DIGEST_SHA1, string);
	}

	/**
	 * 生成SHA1加密校验码
	 *
	 * @param file 待加密文件
	 * @return SHA1加密校验码
	 * @since 0.2
	 * @author hcqt@qq.com
	 */
	public static final String sha1(File file) {
		return encrypt(DIGEST_SHA1, file);
	}

	/**
	 * 生成指定算法的加密校验码
	 *
	 * @param algorithm 算法
	 * @param str 待加密字符串
	 * @return 加密校验码
	 * @since 0.21
	 * @author hcqt@qq.com
	 */
	public static final String encrypt(String algorithm, String str) {
		return encryptString(getEncrypt(algorithm), str);
	}

	/**
	 * 生成指定算法的加密校验码
	 *
	 * @param algorithm 算法
	 * @param file 待加密文件
	 * @return 加密校验码
	 * @since 0.21
	 * @author hcqt@qq.com
	 */
	public static final String encrypt(String algorithm, File file) {
		return encryptFile(getEncrypt(algorithm), file);
	}

	/**
	 * 计算结果转为16进制表示
	 *
	 * @param bytes 待转换Byte数组
	 * @return 转换结果
	 * @since 0.2
	 * @author hcqt@qq.com
	 */
	public static final String bytesToHex(byte[] bytes) {
		int length = bytes.length;
		StringBuilder sb = new StringBuilder(2 * length);
		for (int i = 0; i < length; i++) {
			sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >> 4]);
			sb.append(HEX_DIGITS[bytes[i] & 0xf]);
		}
		return sb.toString();
	}

	/**
	 * 获得指定的算法加密器
	 *
	 * @param algorithm 算法
	 * @throws CatGroupException 如果没有参数algorithm指定的加密算法则抛出此异常
	 * @return 加密器
	 * @since 0.2
	 * @author hcqt@qq.com
	 */
	private static final MessageDigest getEncrypt(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new BaseException("encrypt_file_j3fvU", "创建{0}算法加密器失败，详情——{1}", ex, algorithm, EU.out(ex));
		}
	}

	/**
	 * 使用加密器对目标字符串进行加密
	 *
	 * @param digest 加密器
	 * @param string 目标字符串
	 * @return 计算结果
	 * @since 0.2
	 * @author hcqt@qq.com
	 */
	private static final String encryptString(MessageDigest digest, String string) {
		return bytesToHex(digest.digest(string.getBytes()));
	}

	/**
	 * 使用加密器对目标文件进行加密
	 *
	 * @param digest 加密器
	 * @param file 目标文件
	 * @throws CatGroupException 当文件未找到或读取错误时抛出此异常
	 * @return 计算结果
	 * @since 0.2
	 * @author hcqt@qq.com
	 */
	private static final String encryptFile(MessageDigest digest, File file) {
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				digest.update(buffer, 0, numRead);
			}
			return bytesToHex(digest.digest());
		}
		catch (FileNotFoundException ex) {
			throw new BaseException("encrypt_file_3xSAc", "文件{0}未找到", ex, file.getName());
		}
		catch (IOException ex) {
			throw new BaseException("encrypt_file_4ghcf", "文件{0}发生I/O错误", ex, file.getName());
		}
		finally {
			try { fis.close(); } catch (IOException ex) { }
		}
	}

}

package top.lothar.sdims.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 对本地账号的密码进行加密
 * 因此在查询时候要按照机密的密码进行查询登录
 * 也就是存入本地密码时候是加密的，取出的时候也要用加密的形式来取
 * @author Lothar
 * 
 */
public class MD5 {
	
	public static final String getMd5(String str) {
		// 16进制数组
		char hexDigits[] = { '5', '0', '5', '6', '2', '9', '6', '2', '5', 'q', 'b', 'l', 'e', 's', 's', 'y' };

		char strchar[];
		// 将传入的字符串转换为byte数组
		byte strbyte[];
		strbyte = str.getBytes();
		// 获取MD5加密对象
		MessageDigest instance;
		try {
			// 获取加密对象
			instance = MessageDigest.getInstance("MD5");
			// 传入加密需要的byte数据
			instance.update(strbyte);
			// 获取加密后的数组
			byte[] digest = instance.digest();
			int j = digest.length;
			int k = 0;
			strchar = new char[j * 2];
			// 将数组做位移
			for (int i = 0; i < j; i++) {
				byte byte0 = digest[i];
				strchar[k++] = hexDigits[byte0 >>> 4 & 0xf];
				strchar[k++] = hexDigits[byte0 & 0xf];
			}
			//转换为String
			return new String(strchar);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}
	public static void main(String[] args) {
		System.out.println(MD5.getMd5("Lothar"));
	}
}

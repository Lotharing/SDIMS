package top.lothar.sdims.util;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class DESUtil {
	
	private static Key key;

	private static String KEY_ETR="mykey";
	private static String CHARSETNAME="utf-8";
	private static String ALGORITHM="DES";
	private static String OFWAY="SHA1PRNG";
	static{
		try {
			//生成DES算法
			KeyGenerator generator=KeyGenerator.getInstance(ALGORITHM);
			//运行SHA1安全策略
		    SecureRandom secureRandom=SecureRandom.getInstance(OFWAY);
		    //设置秘钥种子
		    secureRandom.setSeed(KEY_ETR.getBytes());
		    //初始化基于SHA1秘钥算法对象
		    generator.init(secureRandom);
		    //生成秘钥对象
		    key=generator.generateKey();
		    generator=null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 加密信息(在main函数中跑出来，放在jdbc连接字段，调用getDecryptString去解析加密字段)
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str){
		// 基于BASE64编码，接收byte[]并转换成String
		BASE64Encoder base64Encoder=new BASE64Encoder();
		try {
			//按照utf-8编码
			byte[] bytes = str.getBytes(CHARSETNAME);
			//获取加密后的对象
			Cipher cipher=Cipher.getInstance(ALGORITHM);
			//初始化密码信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//加密
			byte[] doFinal = cipher.doFinal(bytes);
			//将加密后的字节，encode成string并且返回
			return base64Encoder.encode(doFinal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获取解密后信息
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str){
		
		BASE64Decoder base64Decoder=new BASE64Decoder();
		try{
			//将字符串decode成byte[]
			byte[] decodeBuffer = base64Decoder.decodeBuffer(str);
			//获取解密对象
			Cipher cipher=Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			//解密
			byte[] doFinal = cipher.doFinal(decodeBuffer);
			//返回解密后的信息
			return new String(doFinal, CHARSETNAME);
		}catch(Exception e){
			throw new RuntimeException(e);
		} finally {
		}
	}
	
	public static void main(String args[]){
		String encryptString1 = getEncryptString("lutong");
		String encryptString2 = getEncryptString("Lutong~918");
		System.out.println(encryptString1);
		System.out.println(encryptString2);
	}

}

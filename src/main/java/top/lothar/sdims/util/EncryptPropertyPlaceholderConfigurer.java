package top.lothar.sdims.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{

	//设置加密的字段数组
	private String encryptPropertyNames[]={"jdbc.username","jdbc.password"};
	/*
	 * 获取解密后的数据(转换)
	 */
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if(isEncryptProp(propertyName)){
			propertyValue=DESUtil.getDecryptString(propertyValue);
			return propertyValue;
		}else{
			return propertyValue;
		}
	}
	/*
	 * 判断该字段是否已经加密
	 */
	private boolean isEncryptProp(String propertyName) {
		for (String encryptPropertyName : encryptPropertyNames) {
			if(encryptPropertyName.equals(propertyName)){
				return true;
			}
		}
		return false;
	}
}

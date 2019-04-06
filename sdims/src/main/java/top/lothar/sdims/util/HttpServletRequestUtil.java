package top.lothar.sdims.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理HttpServletRequest参数对象
 * @author Lothar
 *
 */
public class HttpServletRequestUtil { 
	/**
	 * 转整型
	 * @param request
	 * @param key
	 * @return
	 */
	public static int getInt(HttpServletRequest request,String key) {
		try {
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	/**
	 * 转Long型
	 * @param request
	 * @param key
	 * @return
	 */
	public static long getLong(HttpServletRequest request,String key) {
		try {
			return Long.decode(request.getParameter(key));
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	/**
	 * 转double型
	 * @param request
	 * @param key
	 * @return
	 */
	public static double getDouble(HttpServletRequest request,String key){
		try{
			return Double.valueOf(request.getParameter(key));
		}catch(Exception e){
			return -1d;
		}
	}
	/**
	 * 转boolean型
	 * @param request
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request,String key){
		try{
			return Boolean.valueOf(request.getParameter(key));
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 转String型
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getString(HttpServletRequest request,String key){
		try{
			String result=request.getParameter(key);
			if(result!=null){
				result=result.trim();
			}
			if("".equals(result)){
				result=null;
			}
			return result;
		}catch(Exception e){
			return null;
		}
	}

}

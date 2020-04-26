package top.lothar.sdims.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/**
 * 订单编号生成工具
 * @author Lothar
 *
 */
public class OrderNumberUtil {
	//随机数（5位数配合文件名）
	private static final Random r = new Random();
	//日期标准格式（用来生成文件名）yyyyMMddHHmmss
	private static final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMddHH");
	/**
	 * 生成订单编号随机名规则
	 * 当前时间年月日时分秒+5位随机数
	 * @return
	 */
	public String getRandomFileName() {
		//获取随机五位数
		int rannum = r.nextInt(89999)+10000;
		//按照固定格式返回时间
		String nowTimeStr = DateFormat.format(new Date());
		//字符串+整型自动转化为字符串
		return nowTimeStr+rannum;
	}
}

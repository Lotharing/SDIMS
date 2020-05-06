package top.lothar.sdims.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtils {

    //日期标准格式（用来生成文件名）
    private static final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    //随机数（5位数配合文件名）
    private static final Random r = new Random();

    public static String getRandomFileName() {
        //获取随机五位数
        int rannum = r.nextInt(89999)+10000;
        //按照固定格式返回时间
        String nowTimeStr = DateFormat.format(new Date());
        //字符串+整型自动转化为字符串
        return nowTimeStr+rannum;
    }

}

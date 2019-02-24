package top.littlefogcat.shuzihuarongdao.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by LittleFogCat on 2019/2/23.
 */
public class TimeUtil {
    public static String transformTimeFromMillisToFormat(long ms, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(ms);
    }

    public static String format(String str, Object... args) {
        return String.format(str, args);
    }
}

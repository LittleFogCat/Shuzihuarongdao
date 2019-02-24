package top.littlefogcat.shuzihuarongdao.util;

import com.google.gson.Gson;

/**
 * Created by LittleFogCat on 2019/2/23.
 */
public class GsonUtil {

    public static <T> T fromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}

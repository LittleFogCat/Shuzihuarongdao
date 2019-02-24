package top.littlefogcat.shuzihuarongdao.util;

import java.util.Random;

/**
 * Created by LittleFogCat on 2019/2/23.
 */
public class RandomUtil {
    private static Random sRandom;

    public static Random getRandom(){
        if (sRandom == null) {
            sRandom = new Random();
        }
        return sRandom;
    }

    public static int randomInt(int from, int to) {
        int r = getRandom().nextInt(to - from);
        return from + r;
    }
}

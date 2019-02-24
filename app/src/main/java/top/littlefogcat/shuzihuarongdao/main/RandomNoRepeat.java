package top.littlefogcat.shuzihuarongdao.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用于生成不重复的随机数
 * Created by LittleFogCat on 2019/2/21.
 *
 * @deprecated 可能会生成不可解的情况
 */
public class RandomNoRepeat {
    private List<Integer> mRandomArr;

    /**
     * 在一串连续整数中取随机值
     *
     * @param first 连续整数的第一个
     * @param size  连续整数的数量
     */
    RandomNoRepeat(int first, int size) {
        mRandomArr = new ArrayList<>();
        for (int i = first; i < size + first; i++) {
            mRandomArr.add(i);
        }
        Collections.shuffle(mRandomArr);
    }

    int nextInt() {
        if (mRandomArr == null || mRandomArr.isEmpty()) {
            return 0;
        }
        int i = mRandomArr.get(0);
        mRandomArr.remove(0);
        return i;
    }

}

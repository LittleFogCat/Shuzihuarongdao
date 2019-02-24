package top.littlefogcat.shuzihuarongdao.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by LittleFogCat on 2019/2/19.
 */
public class CubeView extends android.support.v7.widget.AppCompatTextView {
    private Position mPosition;
    private int mNumber;

    public CubeView(Context context) {
        super(context);
        init();
    }

    public CubeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CubeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    public void setNumber(int n) {
        mNumber = n;
        setText(String.valueOf(n));
    }

    public int getNumber() {
        return mNumber;
    }

    public Position getPosition() {
        return mPosition;
    }

    public void setPosition(Position position) {
        this.mPosition = position;
    }

    @Override
    public String toString() {
        return "CubeView{" +
                "mPosition=" + mPosition +
                ", mNumber=" + mNumber +
                '}';
    }
}

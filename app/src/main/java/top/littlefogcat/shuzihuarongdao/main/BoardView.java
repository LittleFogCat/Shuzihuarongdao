package top.littlefogcat.shuzihuarongdao.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import top.littlefogcat.shuzihuarongdao.R;

/**
 * Created by LittleFogCat on 2019/2/19.
 */
public class BoardView extends ViewGroup {
    private static final String TAG = "BoardView";
    /**
     * 每一行有多少个棋子
     */
    private int mSizeX = 4;
    /**
     * 有多少行棋子
     */
    private int mSizeY = 4;
    private int mChildSize;

    private int mChildWidth;
    private int mChildHeight;

    private Position mBlankPos;
    private CubeView[] mChildren;

    private OnFinishListener mFinishListener;

    public BoardView(Context context) {
        super(context);
        init();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoardView);
        mSizeX = a.getInt(R.styleable.BoardView_sizeH, 4);
        mSizeY = a.getInt(R.styleable.BoardView_sizeV, 4);
        a.recycle();
        init();
    }

    private void init() {
        mChildSize = mSizeX * mSizeY - 1;
        mChildren = new CubeView[mChildSize];
        Position p = new Position(mSizeX, mSizeY);
        for (int i = 0; i < mChildSize; i++) {
            final CubeView view = (CubeView) LayoutInflater.from(getContext()).inflate(R.layout.cube_view, this, false);
            view.setPosition(new Position(p));
            view.setOnClickListener(v -> moveChildToBlank(view));
            addView(view);
            p.moveToNextPosition();
            mChildren[i] = view;
        }
        mBlankPos = new Position(mSizeX, mSizeY, mSizeX - 1, mSizeY - 1);
    }

    public void setData(List<Integer> data) {
        for (int i = 0; i < mChildSize; i++) {
            CubeView child = (CubeView) getChildAt(i);
            child.setNumber(data.get(i));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        mChildWidth = w / mSizeX;
        mChildHeight = h / mSizeY;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            CubeView v = (CubeView) getChildAt(i);
            if (v == null) {
                continue;
            }
            LayoutParams lp = v.getLayoutParams();
            lp.width = mChildWidth;
            lp.height = mChildHeight;
            v.setLayoutParams(lp);
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, mChildWidth / 3);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            CubeView v = (CubeView) getChildAt(i);
            Position p = v.getPosition();
            int left = p.x * mChildWidth;
            int top = p.y * mChildHeight;
            int right = left + mChildWidth;
            int bottom = top + mChildHeight;
            v.layout(left, top, right, bottom);
        }
    }

    public void moveChildToBlank(CubeView child) {
        Position childPos = child.getPosition();
        Position dstPos = mBlankPos;
        if (childPos.x == dstPos.x && Math.abs(childPos.y - dstPos.y) == 1 ||
                childPos.y == dstPos.y && Math.abs(childPos.x - dstPos.x) == 1) {
            Log.d(TAG, "moveChildToBlank: 开始移动");
            child.setPosition(dstPos);

            child.setX(dstPos.x * mChildWidth);
            child.setY(dstPos.y * mChildHeight);

            mBlankPos = childPos;
            mStepCounter.add();
        }
        checkPosition();
    }

    /**
     * 检查所有格子位置是否正确
     */
    private void checkPosition() {
        if (mBlankPos.x != mSizeX - 1 || mBlankPos.y != mSizeY - 1) {
            Log.d(TAG, "checkPosition: 1");
            return;
        }

        for (CubeView child : mChildren) {
            int num = child.getNumber();
            int x = child.getPosition().x;
            int y = child.getPosition().y;
            if (y * mSizeX + x + 1 != num) {
                Log.d(TAG, "checkPosition: 2 " + x + ", " + y);
                return;
            }
        }

        Log.d(TAG, "checkPosition: 完成拼图");
        if (mFinishListener != null) {
            mFinishListener.onFinished(mStepCounter.step);
        }
        for (CubeView child : mChildren) {
            child.setClickable(false);
        }
    }

    void setOnFinishedListener(OnFinishListener l) {
        mFinishListener = l;
    }

    interface OnFinishListener {
        void onFinished(int step);
    }

    public int getSizeX() {
        return mSizeX;
    }

    public int getSizeY() {
        return mSizeY;
    }

    /**
     * 步数统计
     */
    class StepCounter {
        private int step = 0;

        void add() {
            step++;
        }

        void clear() {
            step = 0;
        }
    }

    private StepCounter mStepCounter = new StepCounter();

}

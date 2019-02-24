package top.littlefogcat.shuzihuarongdao.main;

import top.littlefogcat.shuzihuarongdao.util.RandomUtil;

/**
 * Created by LittleFogCat on 2019/2/23.
 */
public class BoardGenerator {
    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;

    private int[][] mBoard;
    private int mSizeX;
    private int mSizeY;

    private int mBlankX;
    private int mBlankY;

    /**
     * @param sizeX 列数
     * @param sizeY 行数
     */
    public BoardGenerator(int sizeX, int sizeY) {
        mSizeX = sizeX;
        mSizeY = sizeY;
        mBoard = new int[sizeY][sizeX];
        generate();
    }


    public void generate() {
        int totalCount = mSizeX * mSizeY - 1;
        int temp = 1;
        for (int i = 0; i < mSizeY; i++) {
            for (int j = 0; j < mSizeX; j++) {
                mBoard[i][j] = temp;
                temp++;
            }
        }
        mBlankX = mSizeX - 1;
        mBlankY = mSizeY - 1;
        for (int i = 0; i < 10000; i++) {
            moveRandomly();
        }
        while (mBlankX != mSizeX - 1) {
            moveToRight(mBlankY, mBlankX);
            mBlankX++;
        }
        while (mBlankY != mSizeY - 1) {
            moveToDown(mBlankY, mBlankX);
            mBlankY++;
        }

        if (mListener != null) {
            mListener.onGenerated(mBoard);
        }
    }


    private void moveRandomly() {
        int r = RandomUtil.randomInt(0, 4);
        switch (r) {
            case LEFT:
                if (moveToLeft(mBlankY, mBlankX)) {
                    mBlankX--;
                }
                break;
            case UP:
                if (moveToUp(mBlankY, mBlankX)) {
                    mBlankY--;
                }
                break;
            case RIGHT:
                if (moveToRight(mBlankY, mBlankX)) {
                    mBlankX++;
                }
                break;
            case DOWN:
                if (moveToDown(mBlankY, mBlankX)) {
                    mBlankY++;
                }
                break;
        }
    }

    private void exchange(int a1, int b1, int a2, int b2) {
        int temp = mBoard[a1][b1];
        mBoard[a1][b1] = mBoard[a2][b2];
        mBoard[a2][b2] = temp;
    }

    private boolean moveToLeft(int a, int b) {
        if (b > 0) {
            exchange(a, b, a, b - 1);
            return true;
        } else {
            return false;
        }
    }

    private boolean moveToRight(int a, int b) {
        if (b < mSizeX - 1) {
            exchange(a, b, a, b + 1);
            return true;
        } else {
            return false;
        }
    }

    private boolean moveToUp(int a, int b) {
        if (a > 0) {
            exchange(a, b, a - 1, b);
            return true;
        } else {
            return false;
        }
    }

    private boolean moveToDown(int a, int b) {
        if (a < mSizeY - 1) {
            exchange(a, b, a + 1, b);
            return true;
        } else {
            return false;
        }
    }

    private OnGeneratedListener mListener;

    public void setOnGeneratedListener(OnGeneratedListener l) {
        mListener = l;
    }

    public interface OnGeneratedListener {
        void onGenerated(int[][] board);
    }
}

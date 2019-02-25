package top.littlefogcat.shuzihuarongdao.main;

/**
 * 定义棋子的位置
 */
class Position {
    int sizeX; // 总列数
    int sizeY; // 总行数
    int x; // 横坐标
    int y; // 纵坐标

    public Position() {
    }

    Position(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Position(int sizeX, int sizeY, int x, int y) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x = x;
        this.y = y;
    }

    Position(Position orig) {
        this(orig.sizeX, orig.sizeY, orig.x, orig.y);
    }

    /**
     * 移动到下一个位置
     */
    boolean moveToNextPosition() {
        if (x < sizeX - 1) {
            x++;
        } else if (y < sizeY - 1) {
            x = 0;
            y++;
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

package homework.task_01_02;

import java.util.Scanner;

/**
 * 作业第二题：五子棋游戏
 */
public class Chessboard {
    /**
     * 棋盘的大小 行和列 16 x 16
     */
    private int length = 16;

    /**
     * 定义存储 棋子落子的二维数组
     */
    private int[][] boards = new int[length][length];

    /**
     * 黑子用1表示
     */
    private int blackMole = 1;

    /**
     * 白字用2表示
     */
    private int whiteMole = 2;

    /**
     * false 表示黑子执行
     * true 表示白子执行
     */
    private boolean blackOrWhite = true;

    /**
     * 对外暴露自定义构造方法 支持创建自定义的棋盘大小
     *
     * @param length 棋盘的行和列
     */
    public Chessboard(int length) {
        setLength(length);
    }

    public void setLength(int length) {
        if (length >= 5) {
            this.length = length;
        } else {
            System.out.println("棋盘最小是：5 x 5");
        }
    }

    public Chessboard() {
    }


    /**
     * 绘制棋盘
     */
    private void draw() {
        //绘制右侧 " " 0 。。。。
        for (int i = 0; i < length + 1; i++) {
            if (0 == i) {
                System.out.print("  ");
            } else {
                System.out.printf("%x ", i - 1);
            }
        }
        System.out.println();
        //绘制左侧
        for (int i = 0; i < length; i++) {
            System.out.printf("%x ", i);
            for (int j = 0; j < length; j++) {
                //0 0
                //落子
                if (boards[i][j] != 0) {//x == j && i == y
                    System.out.print(boards[i][j] + " ");//1 或者 2 表示黑子或者白子
                } else {
                    System.out.print("+ ");//待落子的位置
                }
            }
            System.out.println();
        }
    }

    /**
     * 开始游戏
     */
    private void play() {
        draw();//绘制棋盘
        //有一下个人开始落子 判断棋手
        blackOrWhite = !blackOrWhite;
        //棋手下棋落子
        playChess();
    }

    /**
     * 下棋落子
     */
    private void playChess() {
        System.out.println("请输入位置x从0开始，y从0开始(输入规则:1,1)：");
        System.out.println(blackOrWhite ? "白方下棋：" : "黑方下棋：");
        Scanner sc = new Scanner(System.in);
        String coord = sc.nextLine();
        String[] split = coord.split(",");//1,2
        //判断输入是否合法
        if (verify(split)) {
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            //1. 判断当前的位置是否有棋子
            if (boards[y][x] != 0) {
                System.out.println("该位置已有棋子,请重新下棋：");
                playChess();
            }
            //2. 当前的位置没有棋子 则落子
            //保存在二维数组中 记录
            boards[y][x] = blackOrWhite ? whiteMole : blackMole;
            //判断是否胜利，如果由一方胜利结束游戏
            boolean ifWin = ifWin();
            if (ifWin) {
                draw();//胜利之后重新绘制棋盘
                System.out.println(blackOrWhite ? "白子胜利" : "黑子胜利");
                System.out.println("游戏结束");
            } else {
                //如果没有任何人胜利就重新绘制棋盘
                play();
            }
        } else {
            playChess();//落子不合法 重新落子
        }
    }

    /**
     * 验证落子是否合法 : 规则 1,1
     *
     * @param split
     * @return
     */
    private boolean verify(String[] split) {
        if (split.length == 2) {
            //如果合法存入棋盘数组中
            try {
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                if (x < 0 || x >= length) {
                    System.out.print("超出棋盘范围，请重新下棋：");
                    return false;
                }
                if (y < 0 || y >= length) {
                    System.out.print("超出棋盘范围，请重新下棋：");
                    return false;
                }
            } catch (NumberFormatException e) {
                //如果不合法重新输入
                System.out.print("输入不符合规则，请输入数字类型：");
                return false;
            }
        } else {//1,1,2   s,x .....格式都是不合法的
            //如果不合法重新输入
            System.out.print("输入不符合规则，只能输入两个值，例如输入：1,2 请重新输入：");
            return false;
        }
        return true;
    }

    /**
     * 判断是否有一方胜利
     *
     * @return
     */
    public boolean ifWin() {
        if (blackOrWhite) {//白色棋手
            //判断白子是否获胜 行或列或对角线 2 * 5 则赢
            //获胜的依据 行 、列、左上角对角线、右上角对角线 是否连着有5个则获胜
            return row(whiteMole) || column(whiteMole) || rightAngle(whiteMole) || leftAngle(whiteMole);
        } else {//黑色棋手
            //判断黑子是否获胜 行或列或对角线 1 * 5 则赢
            return row(blackMole) || column(blackMole) || rightAngle(blackMole) || leftAngle(blackMole);
        }
    }

    /**
     * 计算每一行有没有成立的五子棋
     *
     * @param n
     * @return
     */
    public boolean row(int n) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                //n=2 白色棋手
                //判断每一行：例如： 2 1 2 2 2 2 2 1 2 2 0 0 0
                if (boards[i][j] == n) {//必须是一个连续的相同的数字
                    result++;
                } else {
                    result = 0;
                }
                if (result == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 计算每一列有没有成立的五子棋
     *
     * @param n
     * @return
     */
    public boolean column(int n) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            for (int[] arr : boards) {
                if (arr[i] == n) {
                    result++;
                } else {
                    result = 0;
                }
                if (result == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 计算右上角到左下角有没有成立的五子棋
     *
     * @return
     */
    public boolean rightAngle(int n) {
        int result = 0;
        for (int k = 0; k <= length - 1; k++) {
            for (int i = k, j = 0; i >= 0 && j <= k; i--, j++) {
                if (boards[i][j] == n) {
                    result++;
                } else {
                    result = 0;
                }
                if (result == 5) {
                    return true;
                }
            }
        }
        for (int k = length - 1; k >= 1; k--) {
            for (int i = length - 1, j = length - k; i >= 1 && j <= length - 1; i--, j++) {
                if (boards[i][j] == n) {
                    result++;
                } else {
                    result = 0;
                }
                if (result == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 计算左上角到右下角有没有成立的五子棋
     *
     * @return
     */
    public boolean leftAngle(int n) {
        int result = 0;
        for (int k = length - 1; k >= 0; k--) {
            for (int i = 0, j = k; i <= length - 1 - k && j <= length - 1; i++, j++) {
                if (boards[i][j] == n) {//必须是连续的
                    result++;
                } else {
                    result = 0;
                }
                if (result == 5) {
                    return true;
                }
            }
        }
        for (int k = 1; k <= length - 1; k++) {
            for (int i = k, j = 0; i <= length - 1 && j <= length - 1 - k; i++, j++) {
                if (boards[i][j] == n) {
                    result++;
                } else {
                    result = 0;
                }
                if (result == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getLength() {
        return length;
    }



    /**
     * 开始游戏
     */
    public void start() {
        play();
    }

    public static void main(String[] args) {
        Chessboard chessboard = new Chessboard(16);
        chessboard.start();
    }
}

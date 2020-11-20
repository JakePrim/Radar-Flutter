package homework.task_01_02;

/**
 * 作业的第一题
 * 定义一个长度为[16][16]的整型二维数组并输入或指定所有位置的元素值，
 * 分别实现二维数组中所有行和所有列中所有元素的累加和并打印。
 * <p>
 * 再分别实现二维数组中左上角到右下角和右上角到左下角所有元素的累加和并打印。
 */
public class PrintArray {
    private int[][] arrs = new int[16][16];

    /**
     * 1. 初始化数组中的所有位置的元素值
     */
    public void init() {
        int length = arrs.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                arrs[i][j] = j;//每一行：0-15 进行计算
            }
        }
    }

    /**
     * 2. 新建成员方法，计算数组中所有行元素的累加
     *
     * @param n 表示第几行
     * @return 计算的结果是：从 0 - n 行的总和
     */
    public int row(int n) {
        if (n < 0 || n > arrs.length) return 0;
        int result = 0;
        //双层循环嵌套 计算每一行的值
        //第一层循环的是行
        for (int i = 0; i < n; i++) {
            //例如：第0行
            for (int j = 0; j < arrs.length; j++) {
                //第二层循环的是列 进行相加就是每一行的总和
                result += arrs[i][j];
            }
        }
        //全部循环完之后就是所有行的总和
        return result;
    }

    /**
     * 3.计算数组中所有列元素的累加和
     *
     * @param n 表示第几列
     * @return 计算的结果是：从 0 - n 列的总和
     */
    public int column(int n) {
        if (n < 0 || n > arrs.length) return 0;
        int result = 0;
        //双层循环
        for (int i = 0; i < n; i++) {
            //第一层循环 循环有多少列

            //第二层循环 循环有多少行 根据行的某一列相加就是列的总和
            //int[] arr = arrs[i] { {1,2,3,4,5},{6,7,8,9,10} }
            for (int[] arr : arrs) {
                //int[] arr是数组中的行
                //例如：
                //arrs = { {1,2,3,4,5},{6,7,8,9,10} }
                //foreach arrs 得到的就是：
                //arr[0] = {1,2,3,4,5}
                //arr[1] = {6,7,8,9,10}
                //例如 i=0  就是遍历第1列 arr[0]
                //arr[i] 得到的是列的值
                result += arr[i];
            }
        }
        return result;
    }

    /**
     * 4.计算右上角到左下角的和
     *
     * @return
     */
    public int rightAngle(int n) {
        int result = 0;
        //首先分析如何进行循环遍历
        //假设 n = 4
        //分析：0 -> n-1
        // 1  2  3  4     1
        // 5  6  7  8     2  5
        // 9  9  9  9     3  6  9
        // 9  9  9  9     4  7  9  9
        for (int k = 0; k <= n - 1; k++) {
            //k==0 : i = 0 j = 0
            //k==1 : i = 1 j = 0 ; i = 0 j = 1
            //k==2 : i = 2 j = 0 ; i = 1 j = 1; i = 0 j = 2
            //k==3 : i = 3 j = 0 ; i = 2 j = 1 ; i = 1 j=2;i=0 j=3

            //
            //推导：i = k j = 0; i >= 0 && j <= k;i--,j++

            //j=3
            for (int i = k, j = 0; i >= 0 && j <= k; i--, j++) {
                result += arrs[i][j];
            }
        }
        //分析：n-1 -> 1
        // 1  2  3  4
        // 5  6  7  8[3][1]     8  9  9
        // 9  9  9  9[3][2]     9  9
        // 9  9  9  9[3][3]     9
        for (int k = n - 1; k >= 1; k--) {
            //推导：
            //k==3: i=3 j=1; i=2 j=2; i=1 j=3
            //k==2: i=3 j=2; i=2 j=3
            //k==1: i=3 j=3

            //循环条件：i=n-1 j=n-k; i>=1 && j<=n-1;i--;j++
            for (int i = n - 1, j = n - k; i >= 1 && j <= n - 1; i--, j++) {
                result += arrs[i][j];
            }
        }
        return result;
    }

    /**
     * 5.计算左上角到右下角的和
     *
     * @return
     */
    public int leftAngle(int n) {
        int result = 0;
        //分析：n-1 -> 0
        // 1  2  3  4     4
        // 5  6  7  8     3  8
        // 9  9  9  9     2  7  9
        // 9  9  9  9     1  6  9  9
        //假设 n = 4
        for (int k = n - 1; k >= 0; k--) {
            //i <= n - 1 - k
            //找到规律
            //k==3 : i = 0 j = 3;
            //k==2 : i = 0 j = 2;i=1 j=3
            //k==1 : i = 0 j = 1;i=1 j=2;i=2 j=3
            //k==0 : i = 0 j = 0;i=1 j=1;i=2 j=2;i=3 j=3
            //推导 循环条件：
            //i=0 j=k;i<=n-1-k&&j<=n-1;i++ j++
            for (int i = 0, j = k; i <= n - 1 - k && j <= n - 1; i++, j++) {
                result += arrs[i][j];
            }
        }
        //分析：1 -> n-1
        // 1  2  3  4  i=0
        // 5  6  7  8     5  9  9
        // 9  9  9  9     9  9
        // 9  9  9  9     9
        for (int k = 1; k <= n - 1; k++) {
            //k==1: i=1 j=0;i=2 j=1;i=3 j=2
            //k==2: i=2 j=0;i=2 j=1
            //k==3: i=3 j=0

            //i=k j=0;i<=n-1 && j<=n-1-k;i++ j++
            //每一列循环 下标i 会增加
            for (int i = k, j = 0; i <= n - 1 && j <= n - 1 - k; i++, j++) {
                result += arrs[i][j];
            }
        }
        return result;
    }


    /**
     * 打印验证数组
     */
    public void print() {
        int length = arrs.length;
        for (int i = 0; i < length; i++) {
            System.out.println();
            for (int j = 0; j < length; j++) {
                System.out.print("" + arrs[i][j] + " ");
            }
        }
    }

    public static void main(String[] args) {
        PrintArray printArray = new PrintArray();
        printArray.init();
        printArray.print();
        System.out.println();
        System.out.println("所有行的总和:" + printArray.row(16));//1920
        System.out.println("所有列的总和:" + printArray.column(16));//1920
        System.out.println("左上角到右" +
                "下角的和:" + printArray.leftAngle(16));//120
        System.out.println("右上角到左下角的和:" + printArray.rightAngle(16));//120
    }
}

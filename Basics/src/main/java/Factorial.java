public class Factorial {
    private int result = 1;

    /**
     * 循环方式实现
     *
     * @param i
     * @return
     */
    int show(int i) {
        for (int j = 1; j <= i; j++) {
            result *= j;
        }
        return result;
    }

    /**
     * 递归方式实现
     *
     * @param n
     * @return
     */
    int recursive(int n) {
        if (n == 1) {
            return n;
        }
        return n * recursive(n - 1);
        //5 * recursive(4) => return 4 * recursive(3) => return 3 * recursive(2) => return 2 * recursive(1)
        //return 2 * 1 => return 2 * 3 => return 4 * 6 => return 5 * 24 => 120
    }

    int show2(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return show2(n - 1) + show2(n - 2);
    }

    public static void main(String[] args) {
        Factorial factorial = new Factorial();
        System.out.println(factorial.show(5));
        System.out.println(factorial.recursive(5));
        System.out.println(factorial.show2(5));
    }
}

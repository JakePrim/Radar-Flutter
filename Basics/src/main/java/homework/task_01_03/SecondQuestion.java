package homework.task_01_03;

/**
 * 第二题
 * 编程获取两个指定字符串中的最大相同子串并打印出来。
 * <p>
 * 如： s1="asdafghjka", s2="aaasdfg" 他们的最大子串为"asd"
 * <p>
 * 提示： 将短的那个串进行长度依次递减的子串与较长的串比较。
 */
public class SecondQuestion {
    public static void main(String[] args) {
        String s1 = "asdafghjka";
        String s2 = "aaasdfg";
        //分析：
        //第一轮：
        //aaasdfg 判断是否是s1的子串 明显不是
        //第二轮 s2的长度减一
        //aasdfg
        //aaasdf
        //判断是否是s1的子串明显不是
        //第三轮 s2的长度减2 长度为5
        //aaasd
        //aasdf
        //asdfg
        //依次类推
        String maxSubString = findMaxSubString(s1, s2);
        System.out.println("最大子串:" + maxSubString);
    }

    private static String findMaxSubString(String s1, String s2) {
        for (int i = 0; i < s2.length(); i++) {
            //第一循环轮数
            for (int j = 0, n = s2.length() - i; n < s2.length(); j++, n++) {
                String temp = s2.substring(j, n);//获取子串
                if (s1.contains(temp)) {
                    return temp;
                }
            }
        }
        return "";
    }
}

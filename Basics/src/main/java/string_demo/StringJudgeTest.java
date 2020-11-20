package string_demo;

public class StringJudgeTest {
    public static void main(String[] args) {
        //1. 创建字符串对象
        String str1 = new String("上海自来水来自海上");
        System.out.println(str1);
        //2. 判断该字符串内容是否为回文
        for (int i = 0; i < str1.length() / 2; i++) {
            if (str1.charAt(i) != str1.charAt(str1.length() - i - 1)) {
                System.out.println("该字符串不是回文");
                return;
            }
        }
        System.out.println("该字符串是回文");
    }
}

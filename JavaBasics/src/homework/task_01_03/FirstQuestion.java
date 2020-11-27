package homework.task_01_03;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 编程统计字符串"ABCD123!@#$%ab"中大写字母、小写字母、数字、其它字符的个数并打 印出来。
 */
public class FirstQuestion {
    public static void main(String[] args) {
        String value = "ABCD123!@#$%ab";
        //方案1：通过ASCII码来判断 0-9：48-57，大写字母：A-Z：65-90，小写字母:97-122
        int capital = 0;
        int number = 0;
        int lowercase = 0;
        int other = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c >= 48 && c <= 57) {
                number++;
            } else if (c >= 65 && c <= 90) {
                capital++;
            } else if (c >= 97 && c <= 122) {
                lowercase++;
            } else {
                other++;
            }
        }
        System.out.println("大写字母:" + capital);//4
        System.out.println("小写字母:" + lowercase);//2
        System.out.println("数字:" + number);//3
        System.out.println("其他字符:" + other);//5
        //方案2：正则表达式
        capital = 0;
        number = 0;
        lowercase = 0;
        other = 0;
        String regx_lowercase = "[a-z]";
        String regx_capital = "[A-Z]";
        String regx_number = "[0-9]";
        for (int i = 0; i < value.length(); i++) {
            if (Pattern.matches(regx_capital, String.valueOf(value.charAt(i)))) {
                capital++;
            } else if (Pattern.matches(regx_lowercase, String.valueOf(value.charAt(i)))) {
                lowercase++;
            } else if (Pattern.matches(regx_number, String.valueOf(value.charAt(i)))) {
                number++;
            } else {
                other++;
            }
        }
        System.out.println("正则表达式方案：");
        System.out.println("大写字母:" + capital);//4
        System.out.println("小写字母:" + lowercase);//2
        System.out.println("数字:" + number);//3
        System.out.println("其他字符:" + other);//5
    }
}

package homework.task_01_03;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 准备一个 HashMap 集合，统计字符串"123,456,789,123,456"中每个数字字符串出现的次数并打印出来。
 */
public class ThirdQuestion {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        String value = "123,456,789,123,456";
        String[] split = value.split(",");
        //循环字符串数组 存入到map集合中
        for (int i = 0; i < split.length; i++) {
            if (map.containsKey(split[i])) {
                Integer integer = map.get(split[i]);
                map.put(split[i], integer + 1);
            } else {
                map.put(split[i], 1);
            }
        }
        //打印map集合
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + " 出现了 " + entry.getValue() + " 次");
        }
    }
}

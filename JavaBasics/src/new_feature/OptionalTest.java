package new_feature;

import java.util.Optional;
import java.util.function.Function;

public class OptionalTest {
    public static void main(String[] args) {
        String str1 = null;
        //将数据str1装到Optional对象代理的容器中
        Optional<String> optional = Optional.ofNullable(str1);
        //建立映射 使用字符串的长度与字符串建立映射关系
        Optional<Integer> integer = optional.map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        });
        //若字符串为空打印0 否则打印字符串的长度
        System.out.println(integer.orElse(0));// 0
    }
}

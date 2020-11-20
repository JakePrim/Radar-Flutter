public class CharacterTest {
    public static void main(String[] args) {
        //1. 在Java5之前实现装箱和拆箱机制
        Character ca1 = Character.valueOf('a');//char类型到Character类型的转换
        System.out.println("ca1=" + ca1);
        char c1 = ca1.charValue();//Character 类型到char类型的转换
        System.out.println("c1=" + c1);

        //2. Java5开始支持自动装箱和拆箱
        Character ca2 = 'b';
        char c2 = ca2;
        System.out.println("c2=" + c2);

        //3. 实现字符类型的判断以及转换
        System.out.println(Character.isUpperCase(c2));//是否为大写字母 false
        System.out.println(Character.isLowerCase(c2));//是否为小写字母 true
        System.out.println(Character.isDigit(c2));//判断是否为数字字符 false
        System.out.println("转换为大写字符："+Character.toUpperCase(c2));
        System.out.println("转换为小写字符："+Character.toLowerCase(c2));

    }
}

package annotation_demo;

import java.lang.annotation.*;

@Documented //表示下面的注解信息可以被javadoc工具提取到API文档中 一般很少使用
//@Retention(RetentionPolicy.SOURCE)//下面的注解在源代码中有效
//@Retention(RetentionPolicy.CLASS)//下面的注解在字节码文件中有效 默认方式
@Retention(RetentionPolicy.RUNTIME)//下面的注解在程序运行时有效
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.TYPE,ElementType.METHOD,ElementType.PARAMETER})
@Inherited //表示下面的注解所修饰类中的注解，可以被子类继承使用
public @interface MyAnnotation {
    //default 给value的默认值
    String value() default "1";//声明一个String类型的成员变量 名字为value
    String value2();
}

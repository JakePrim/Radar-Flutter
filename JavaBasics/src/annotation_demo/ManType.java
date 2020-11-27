package annotation_demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@Repeatable(value = ManTypes.class)
@Target({ElementType.TYPE_USE})
public @interface ManType {
    String value() default "";
}

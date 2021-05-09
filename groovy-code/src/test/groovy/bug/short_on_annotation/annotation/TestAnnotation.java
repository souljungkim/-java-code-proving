package bug.short_on_annotation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 1. Create some annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

    short shortProp1() default (short) -1;
    short shortProp2() default (short) -1;

    int intProp1() default -1;
    int intProp2() default -1;
    int intProp3() default -1;

    long longProp1() default -1L;
    long longProp2() default -1L;

    boolean booleanProp() default false;

}
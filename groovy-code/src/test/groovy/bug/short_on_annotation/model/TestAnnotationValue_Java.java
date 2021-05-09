package bug.short_on_annotation.model;


import bug.short_on_annotation.annotation.TestAnnotation;
import bug.short_on_annotation.code.TestCode;

/**
 * 3-1. It all works on Java class
 */
@TestAnnotation(
        intProp1 = 1,
        intProp2 = TestCode.INT_1,
        longProp1 = 1L,
        longProp2 = TestCode.LONG_1,
        shortProp1 = 1,
        shortProp2 = TestCode.SHORT_1,
        intProp3 = TestCode.SHORT_1
)
class TestAnnotationValue_Java {

    Integer objectId;

}

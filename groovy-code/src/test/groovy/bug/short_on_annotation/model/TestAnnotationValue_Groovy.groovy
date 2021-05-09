package bug.short_on_annotation.model

import bug.short_on_annotation.code.TestCode


/**
 * 3-2. 'int' and 'long' works on all version of groovy
 *      But 'short' does not works on some groovy version.
 */
@bug.short_on_annotation.annotation.TestAnnotation(
        intProp1 = 1,
        intProp2 = TestCode.INT_1,

        longProp1 = 1L,
        longProp2 = TestCode.LONG_1,

        /**
         * Defining directly
         *  - It always does not works on groovy class
         **/
//        shortProp1 = 1,

        /**
         * [SOLUTION 2.1.3 ~ 2.5.3]  Defining as an already defined short variable
         *  - It works on Groovy between 2.1.3 and 2.5.3 (tested by 2.1.3 / 2.4.13 / 2.5.3)
         *  - But, It does not works since Groovy 2.5.4. (tested by 2.5.4 / 2.5.14 / 3.0.8)
         **/
//        shortProp2 = TestCode.SHORT_1,

        /**
         * [SOLUTION 2.5.4 ~ ..]  Defining as an already defined short variable
         *  - It works since Groovy 2.5.4
         *  - But, It does not works before Groovy 2.5.4.
         **/
        intProp3 = TestCode.SHORT_1
)
class TestAnnotationValue_Groovy {

    Integer objectId

}

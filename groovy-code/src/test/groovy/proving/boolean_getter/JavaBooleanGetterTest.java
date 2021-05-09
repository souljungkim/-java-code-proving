package proving.boolean_getter;

import proving.boolean_getter.model.JavaClass;
import proving.boolean_getter.model.JavaClass_explicit_get;
import org.junit.Test;

public class JavaBooleanGetterTest {

    /**
     * [Java] Non-Auto-Generated Any Getters
     */
    @Test
    public void java_never_auto_generate_getters(){
        JavaClass j = new JavaClass();

        //boolean - Primitive Type
//        assertThrows(java.lang.Exception.class, () -> j.boolPrim );
//        assertThrows(java.lang.Exception.class, () -> j.getBoolPrim() );
//        assertThrows(java.lang.Exception.class, () -> j.isBoolPrim() );

        //Boolean - Reference Type
//        assertThrows(java.lang.Exception.class, () -> j.boolRef );
//        assertThrows(java.lang.Exception.class, () -> j.getBoolRef() );
//        assertThrows(java.lang.Exception.class, () -> j.isBoolRef() );
    }


    /**
     * [Java] User makes Getters
     */
    @Test
    public void java_getters(){
        JavaClass_explicit_get j = new JavaClass_explicit_get();

        //boolean - Primitive Type
        assert j.isBoolPrim();

        //Boolean - Reference Type
        assert j.getBoolRef();
    }

}

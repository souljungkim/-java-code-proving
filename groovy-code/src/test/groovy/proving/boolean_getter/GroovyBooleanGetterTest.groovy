package proving.boolean_getter

import jaemisseo.man.version.GroovyVersion
import org.junit.Test
import proviing.boolean_getter.model.GroovyClass
import proviing.boolean_getter.model.GroovyClass_explicit_get
import proviing.boolean_getter.model.GroovyClass_explicit_is
import proviing.boolean_getter.model.JavaClass

import static org.junit.Assert.assertThrows

/**
 * Groovy Class Test
 *  - tested by 2.2.0 ~ 3.0.8
 */
public class GroovyBooleanGetterTest {

    /**
     * [Java] Non-Auto-Generated Any Getters
     */
    @Test
    void java_never_auto_generate_getters(){
        JavaClass j = new JavaClass()

        //boolean - Primitive Type
        assert j.boolPrim
        assertThrows(groovy.lang.MissingMethodException, { j.getBoolPrim() })
        assertThrows(groovy.lang.MissingMethodException, { j.isBoolPrim()})

        //Boolean - Reference Type
        assert j.boolRef
        assertThrows(groovy.lang.MissingMethodException, { j.getBoolRef() })
        assertThrows(groovy.lang.MissingMethodException, { j.isBoolRef()})
    }


    /**
     * [Groovy] Auto-Generated Boolean Type Getters
     *
     *      - Before 'org.codehaus.groovy:groovy-all:3.0.6'
     *          - Primitive-Type: getName(), isName()
     *          - Reference-Type: getName()
     *
     *      - From 'org.codehaus.groovy:groovy-all:3.0.6'
     *          - Primitive-Type: getName(), isName()
     *          - Reference-Type: getName(), isName()
     */
    @Test
    void auto_generated_boolean_type_getters(){
        GroovyClass g = new GroovyClass()

        //boolean - Primitive Type
        assert g.boolPrim
        assert g.getBoolPrim()
        assert g.isBoolPrim()

        //Boolean - Reference Type
        assert g.boolRef
        assert g.getBoolRef()
        if (GroovyVersion.from("3.0.6")){
            //Gradle: compile 'org.codehaus.groovy:groovy-all:3.0.6'
            assert g.isBoolRef()
        }else{
            //Gradle: compile 'org.codehaus.groovy:groovy-all:3.0.5' //or lower version
            assertThrows(groovy.lang.MissingMethodException, { g.isBoolRef() })
        }
    }


    /**
     * [Groovy] Effect of explicit isName() getter
     *
     *      - Primitive-Type: isName()
     *      - Reference-Type: isName() getName()
     */
    @Test
    void effect_of_explicit_isName_getter(){
        GroovyClass_explicit_is o = new GroovyClass_explicit_is()

        //boolean - Primitive Type
        assert o.boolPrim
        assertThrows(groovy.lang.MissingMethodException, { o.getBoolPrim() })
        assert o.isBoolPrim()

        //Boolean - Reference Type
        assert o.boolRef
        assert o.getBoolRef()
        assert o.isBoolRef()
    }


    /**
     * [Groovy] Effect of explicit getName() getter
     *
     *      - Primitive-Type: getName()
     *      - Reference-Type: getName()
     */
    @Test
    void effect_of_explicit_getName_getter(){
        GroovyClass_explicit_get o = new GroovyClass_explicit_get()

        //boolean - Primitive Type
        assert o.boolPrim
        assert o.getBoolPrim()
        assertThrows(groovy.lang.MissingMethodException, { o.isBoolPrim() })

        //Boolean - Reference Type
        assert o.boolRef
        assert o.getBoolRef()
        assertThrows(groovy.lang.MissingMethodException, { o.isBoolRef() })
    }


}






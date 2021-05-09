package proving.boolean_getter.model;

public class JavaClass_explicit_get {

    private boolean boolPrim = true;
    private Boolean boolRef = true;

    public boolean isBoolPrim() {
        return boolPrim;
    }

    public void setBoolPrim(boolean boolPrim) {
        this.boolPrim = boolPrim;
    }

    public Boolean getBoolRef() {
        return boolRef;
    }

    public void setBoolRef(Boolean boolRef) {
        this.boolRef = boolRef;
    }
}

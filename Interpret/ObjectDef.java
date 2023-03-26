package lang.Interpret;

import java.util.HashMap;

public class ObjectDef {
    String className;
    Boolean isConstructorSet;
    public HashMap<String, Val> defVals = new HashMap<>();
    public HashMap<String,Val> defMethods = new HashMap<>();

    public Val constructor;
    public ObjectDef(String className) {
        this.className = className;
        this.isConstructorSet = false;
    }

    public Val lookUpMethod(String id){
        return defMethods.get(id);
    }
    public void setConstructor(Val val){
        this.constructor = val;
    }
    public void constructorEnable() {
        this.isConstructorSet = true;
    }
}

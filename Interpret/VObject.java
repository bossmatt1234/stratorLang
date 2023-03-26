package lang.Interpret;

public class VObject extends Val{
    Type type;
    ObjectVar val;

    public VObject(ObjectVar val) {
        this.val = val;
    }

    public VObject updateObj(ObjectVar var){
        this.type = Type.TObject;
        this.val = var;
        return this;
    }
}

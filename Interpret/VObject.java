package lang.Interpret;

public class VObject extends Val{
    Type type;
    ObjectVar val;

    public VObject(ObjectVar val) {
        this.val = val;
    }

}


package lang.Interpret;

import java.util.ArrayList;

public class VList extends Val{

    Type type;

    ArrayList<Val> listVal;

    public VList(ArrayList<Val> listVal) {
        this.listVal = listVal;
        this.type = Type.TList;
    }

    @Override
    public String toString() {
        return listVal.toString();
    }
}

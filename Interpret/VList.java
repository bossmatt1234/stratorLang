package lang.Interpret;

import java.util.ArrayList;

public class VList extends Val{

    Type type;
    Type itemType;

    ArrayList<Val> listVal;

    public VList(ArrayList<Val> listVal, Type itemType) {
        this.listVal = listVal;
        this.type = Type.TList;
        this.itemType = itemType;

    }

    @Override
    public String toString() {
        return listVal.toString();
    }

    public Type getItemType() {
        return itemType;
    }
}

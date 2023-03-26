package lang.Interpret;

import java.util.ArrayList;
import java.util.Scanner;

public class TypeChecker implements Checker{

    public boolean check(Type type, Val val){
        if(type != Type.TAuto){
            if (val instanceof VInteger)
                return isType(type, (VInteger) val);
            else if(val instanceof VDouble)
                return isType(type,(VDouble) val);
            else if(val instanceof VBool)
                return isType(type, (VBool) val);
            else if(val instanceof VString)
                return isType(type,(VString) val);
            else if(val instanceof VChar)
                return isType(type,(VChar) val);
            else if(val instanceof VFunc)
                return isType(type,(VFunc) val);
            else if(val instanceof VObject)
                return isType(type,(VObject) val);
            else if (val instanceof VList) {
                return isType(type, (VList) val);
            } else
                throw new RuntimeException("checking error");
        }else{
            return true;
        }
    }

    private boolean isType(Type type, VObject val) {
        return val.type == type;
    }

    private boolean isType(Type type, VFunc val) {
        return val.type == type;
    }

    @Override
    public boolean isType(Type type, VInteger val){
        return val.type == type;
    }

    @Override
    public boolean isType(Type type, VDouble val) {
        return val.type == type;
    }

    @Override
    public boolean isType(Type type, VBool val) {
        return val.type == type;
    }

    @Override
    public boolean isType(Type type, VString val) {
        return val.type == type;
    }

    @Override
    public boolean isType(Type type, VChar val) {
        return val.type == type;
    }

    @Override
    public boolean isType(Type type, VList val) {
        if(val.listVal.isEmpty()) return true;

        if(val.listVal.get(0).type == Type.TAuto ){
            return true;
        }
        for(Val item: val.listVal){
            if(item.type != type){
                return false;
            }
        }
        return true;
    }

    @Override
    public Val returnValOfType(Type t) {
        switch (t){
            case TInt -> {return new VInteger(0);}
            case TDouble -> {return new VDouble(0.0);}
            case TBoolean -> {return new VBool(false);}
            case TString -> {return new VString("");}
            case TChar -> {return new VChar('0');}
            case TAuto -> {
                VChar dynamicVal = new VChar('0');
                dynamicVal.setDynamic(true);
                return dynamicVal;
            }
            case TList -> {return new VList(new ArrayList<Val>());}
            default -> {throw new RuntimeException("Error in returning type");}
            }

        }
    @Override
    public Type returnType(Val val){
        if (val instanceof VInteger)
            return Type.TInt;
        else if(val instanceof VDouble)
            return Type.TDouble;
        else if(val instanceof VBool)
            return Type.TBoolean;
        else if(val instanceof VString)
            return Type.TString;
        else if(val instanceof VChar)
            return Type.TChar;
        else if(val instanceof VFunc)
            return Type.TFunc;
        else if(val instanceof VObject)
            return Type.TObject;
        else if (val instanceof VList) {
            return Type.TList;
        } else
            return Type.TAuto;
    }

    public Val inferInput(Scanner x){
        if (x.hasNextInt()){
            return new VInteger(x.nextInt());
        } else if (x.hasNextDouble()) {
            return new VDouble(x.nextDouble());
        } else if (x.hasNextLine()) {
            return new VString(x.nextLine());
        } else if (x.hasNextBoolean()) {
            return new VBool(x.nextBoolean());
        } else{
            throw new RuntimeException("Infer error");
        }

    }
}




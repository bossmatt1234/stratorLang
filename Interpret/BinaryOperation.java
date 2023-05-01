package lang.Interpret;

import com.google.common.math.IntMath;

import java.util.ArrayList;
import java.util.Objects;

import static lang.Interpret.Operator.ADD;
import static lang.Interpret.Operator.ASSIGN;


public class BinaryOperation implements Eval {
    @Override
    public Val checkInstance(Val leftVal, Val rightVal, Operator op, String types) {
        switch(types){
            case "VIntegerVInteger" ->{
                return eval((VInteger) leftVal, (VInteger) rightVal,op);
            }
            case "VDoubleVDouble" ->{
                return eval((VDouble) leftVal, (VDouble) rightVal,op);
            }
            case "VStringVString" ->{
                return eval((VString) leftVal, (VString) rightVal,op);
            }
            case "VCharVChar" ->{
                return eval((VChar) leftVal, (VChar) rightVal,op);
            }
            case "VBoolVBool" ->{
                return eval((VBool) leftVal, (VBool) rightVal,op);
            }

            case "VStringVChar" ->{
                return eval((VString) leftVal, (VChar) rightVal,op);
            }
            case "VCharVString" ->{
                return eval((VChar) leftVal, (VString) rightVal,op);
            }

            case "VStringVInteger" ->{
                return eval((VString) leftVal, (VInteger) rightVal,op);
            }
            case "VIntegerVString" ->{
                return eval((VInteger) leftVal, (VString) rightVal,op);
            }

            case "VStringVDouble" ->{
                return eval((VString) leftVal, (VDouble) rightVal,op);
            }
            case "VDoubleVString" ->{
                return eval((VDouble) leftVal, (VString) rightVal,op);
            }

            case "VStringVBool" ->{
                return eval((VString) leftVal, (VBool) rightVal,op);
            }
            case "VBoolVString" ->{
                return eval((VBool) leftVal, (VString) rightVal,op);
            }
            case "VListVList" ->{
                return eval((VList) leftVal, (VList) rightVal,op);
            }
            case "VFuncVFunc" ->{
                return eval((VFunc) leftVal, (VFunc) rightVal,op);
            }
            default ->{return null;}
        }
    }

    private Val eval(VFunc leftVal, VFunc rightVal, Operator op) {
        if(op == ASSIGN){
            return rightVal;
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VInteger x, VInteger y, Operator op) {
        switch (op) {
            case ADD -> {
                return new VInteger(x.val + y.val);
            }
            case SUBTRACT -> {
                return new VInteger(x.val - y.val);
            }
            case MULTIPLY -> {
                return new VInteger(x.val * y.val);
            }
            case DIVIDE -> {
                return new VInteger(x.val / y.val);
            }
            case EXPONENT -> {
                return new VInteger(IntMath.checkedPow(x.val,y.val));
            }
            case ASSIGN -> {
                return new VInteger(y.val);
            }
            case EQUALS -> {
                return new VBool(x.val == y.val);
            }
            case NOT_EQUALS -> {
                return new VBool(x.val != y.val);
            }
            case GREATER_THAN -> {
                return new VBool(x.val > y.val);
            }
            case LESS_THAN -> {
                return new VBool(x.val < y.val);
            }
            case GREATER_THAN_EQUALS -> {
                return new VBool(x.val >= y.val);
            }
            case LESS_THAN_EQUALS -> {
                return new VBool(x.val <= y.val);
            }
            case AND -> {
                return new VBool(x == y);
            }
            case MOD -> {
                return new VInteger(x.val % y.val);
            }
            default -> {
                return null;
            }
        }

    }

    @Override
    public Val eval(VDouble x, VDouble y, Operator op) {
        switch (op) {
            case ADD -> {
                return new VDouble(x.val + y.val);
            }
            case SUBTRACT -> {
                return new VDouble(x.val - y.val);
            }
            case MULTIPLY -> {
                return new VDouble(x.val * y.val);
            }
            case DIVIDE -> {
                return new VDouble(x.val / y.val);
            }
            case EXPONENT -> {
                return new VDouble(Math.pow(x.val,y.val));
            }
            case ASSIGN -> {
                return new VDouble(y.val);
            }
            case EQUALS -> {
                return new VBool(x.val == y.val);
            }
            case NOT_EQUALS -> {
                return new VBool(x.val != y.val);
            }
            case GREATER_THAN -> {
                return new VBool(x.val > y.val);
            }
            case LESS_THAN -> {
                return new VBool(x.val < y.val);
            }
            case GREATER_THAN_EQUALS -> {
                return new VBool(x.val >= y.val);
            }
            case LESS_THAN_EQUALS -> {
                return new VBool(x.val <= y.val);
            }
            case AND -> {
                return new VBool(x == y);
            }
            case MOD -> {
                return new VDouble(x.val % y.val);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Val eval(VString x, VString y, Operator op) {
        switch (op){
            case ADD -> {
                return new VString(x.val+y.val);
            }
            case ASSIGN -> {
                return new VString(y.val);
            }
            case EQUALS -> {
                return new VBool(Objects.equals(x.val, y.val));
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Val eval(VChar x, VChar y, Operator op) {
        switch (op){
            case ADD -> {return new VString(String.valueOf(x.val).concat(String.valueOf(y.val)));}
            case ASSIGN -> {
                return new VChar(y.val);
            }
            case EQUALS -> {
                return new VBool(x.val == y.val);
            }
            case NOT_EQUALS -> {
                return new VBool(x.val != y.val);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Val eval(VBool x, VBool y, Operator op) {
        switch (op){
            case AND -> {
                if(x.val && y.val){
                    return new VBool(true);
                }else{
                    return new VBool(false);
                }
            }
            case OR -> {
                if(x.val || y.val){
                    return new VBool(true);
                }else{
                    return new VBool(false);
                }
            }
            case EQUALS -> {
                return new VBool(x.val == y.val);
            }
            case ASSIGN -> {
                return new VBool(y.val);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Val eval(VString x, VChar y, Operator op) {
        if(op == ADD){
            return new VString(String.valueOf(x.val).concat(String.valueOf(y.val)));
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VChar x, VString y, Operator op) {
        if(op == ADD){
            return new VString(String.valueOf(x.val).concat(String.valueOf(y.val)));
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VString x, VInteger y, Operator op) {
        if(op == ADD){
            return new VString(String.valueOf(x.val).concat(String.valueOf(y.val)));
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VInteger x, VString y, Operator op) {
        if(op == ADD){
            return new VString(String.valueOf(x.val).concat(y.val));
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VString x, VDouble y, Operator op) {
        if(op == ADD){
            return new VString(String.valueOf(x.val).concat(String.valueOf(y.val)));
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VDouble x, VString y, Operator op) {
        if(op == ADD){
            return new VString(String.valueOf(x.val).concat(String.valueOf(y.val)));
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VString x, VBool y, Operator op) {
        if(op == ADD){
            return new VString(String.valueOf(x.val).concat(String.valueOf(y.val)));
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VBool x, VString y, Operator op) {
        if(op == ADD){
            return new VString(String.valueOf(x.val).concat(String.valueOf(y.val)));
        }else{
            return null;
        }
    }

    @Override
    public Val eval(VList x, VList y, Operator op) {
        if(op == ASSIGN){
            return new VList(new ArrayList<>(y.listVal),y.itemType);
        }else{
            return null;
        }
    }

}

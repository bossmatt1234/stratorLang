package lang.Interpret;

import lang.Interpret.Exceptions.ErrorThrow;

public class UnaryOperation {

    public Val checkInstance(Val leftVal, Operator op) {

        if(leftVal instanceof VInteger ){
            return eval((VInteger) leftVal, op);
        }
        else if(leftVal instanceof VDouble){
            return eval((VDouble) leftVal, op);
        }
        else{
            throw new ErrorThrow();
        }
    }

    public Val eval(VInteger x, Operator op){
        switch (op){
            case INCREMENT -> {
                return new VInteger(x.val+1);
            }
            case DECREMENT -> {
                return new VInteger(x.val-1);
            }
            default -> {
                throw new ErrorThrow();
            }
        }
    }

    public Val eval(VDouble x, Operator op){
        switch (op){
            case INCREMENT -> {
                return new VDouble(x.val+1.0);
            }
            case DECREMENT -> {
                return new VDouble(x.val-1.0);
            }
            default -> {
                throw new ErrorThrow();
            }
        }
    }





}

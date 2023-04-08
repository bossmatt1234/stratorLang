package lang.Interpret.Exceptions;


public class ThrowErrorException extends RuntimeException{

    String trace;


    public ThrowErrorException(int lineNum, int columnName){
        this.trace = "At line " + lineNum + ", column " + columnName + ": \n \t";

        getMessage();
    }


    public String getMessage(){
        return trace + "Throw error: Throw statement should only be used in the {try} block.";
    }


}

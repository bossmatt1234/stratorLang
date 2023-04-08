package lang.Interpret.Exceptions;


public class ReturnErrorException extends RuntimeException{

    String trace;


    public ReturnErrorException(int lineNum, int columnName){
        this.trace = "At line " + lineNum + ", column " + columnName + ": \n \t";

        getMessage();
    }


    public String getMessage(){
        return trace + "Return error: Return should only be used in functions.";
    }


}

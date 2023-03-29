package lang.Interpret.Exceptions;


public class CommonError extends RuntimeException{

    String trace;


    public CommonError(int lineNum, int columnName){
        this.trace = "At line " + lineNum + ", column " + columnName + ": \n \t";

        getMessage();
    }


    public String getMessage(){
        return trace;
    }


}

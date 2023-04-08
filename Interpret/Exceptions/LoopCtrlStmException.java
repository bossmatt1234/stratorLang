package lang.Interpret.Exceptions;


public class LoopCtrlStmException extends RuntimeException{

    String trace;
    String construct;

    public LoopCtrlStmException(int lineNum, int columnName, String construct){
        this.trace = "At line " + lineNum + ", column " + columnName + ": \n \t";
        this.construct = construct;
        getMessage();
    }


    public String getMessage(){
        return trace + construct +" error: " + construct + " should only be used in loop constructs.";
    }


}

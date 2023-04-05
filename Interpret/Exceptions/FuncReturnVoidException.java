package lang.Interpret.Exceptions;

public class FuncReturnVoidException extends CommonError{
    String ident;

    public FuncReturnVoidException(int lineNum, int columnName, String ident) {
        super(lineNum, columnName);
        this.ident = ident;

        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Function Return Type (Void) Error: Return is not allowed if function type is void";
    }
}

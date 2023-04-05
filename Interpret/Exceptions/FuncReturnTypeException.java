package lang.Interpret.Exceptions;

public class FuncReturnTypeException extends CommonError{
    String ident;

    public FuncReturnTypeException(int lineNum, int columnName, String ident) {
        super(lineNum, columnName);
        this.ident = ident;

        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Function Return Type Error: Return type is not compatible with function type {" + ident + "}.";
    }
}

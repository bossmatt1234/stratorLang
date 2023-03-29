package lang.Interpret.Exceptions;

public class LookUpVarException extends CommonError{

    String ident;
    public LookUpVarException(int lineNum, int columnName, String ident) {
        super(lineNum, columnName);
        this.ident = ident;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Variable lookup error: Variable {" + ident + "} has not been found";
    }
}

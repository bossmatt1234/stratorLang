package lang.Interpret.Exceptions;

public class ExtendEnvException extends CommonError{

    String ident;

    public ExtendEnvException(int lineNum, int columnName, String ident) {
        super(lineNum, columnName);
        this.ident = ident;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Variable initialisation error: Variable {" + ident + "} has already been defined in scope.";
    }
}

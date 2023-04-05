package lang.Interpret.Exceptions;

public class ClassConstructorStmException extends CommonError{

    String ident;

    public ClassConstructorStmException(int lineNum, int columnName, String ident) {
        super(lineNum, columnName);
        this.ident = ident;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Constructor statement error: " + ident + " statement not allowed in Constructor function.";
    }
}

package lang.Interpret.Exceptions;

public class LookUpDefException extends CommonError{
    String ident;
    public LookUpDefException(int lineNum, int columnName, String ident) {
        super(lineNum, columnName);
        this.ident = ident;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Class lookup error: Class {" + ident + "} has not been found";
    }
}

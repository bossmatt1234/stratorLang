package lang.Interpret.Exceptions;

public class NullMethodException extends CommonError{
    String ident;
    String ident2;
    public NullMethodException(int lineNum, int columnName, String ident, String ident2) {
        super(lineNum, columnName);
        this.ident = ident;
        this.ident2 = ident2;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Method Lookup Error: Method {" + ident + "} has not been found in class {" + ident2 + "}.";
    }
}

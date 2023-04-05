package lang.Interpret.Exceptions;

public class ClassConstructorNameError extends CommonError{
    String ident;
    String ident2;
    public ClassConstructorNameError(int lineNum, int columnName, String ident, String ident2) {
        super(lineNum, columnName);
        this.ident = ident;
        this.ident2 = ident2;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Constructor name error: Given constructor name {" + ident + "} does not match class name {" + ident2 + "}.";
    }
}

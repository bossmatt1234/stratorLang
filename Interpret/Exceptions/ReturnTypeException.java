package lang.Interpret.Exceptions;

public class ReturnTypeException extends CommonError{
    public ReturnTypeException(int lineNum, int columnName) {
        super(lineNum, columnName);
    }

    @Override
    public String getMessage() {
        return trace + "Infer type error: Value does not correspond to a type";
    }
}

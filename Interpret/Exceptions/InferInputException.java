package lang.Interpret.Exceptions;

public class InferInputException extends CommonError{

    public InferInputException(int lineNum, int columnName) {
        super(lineNum, columnName);
    }

    @Override
    public String getMessage() {
        return trace + "Infer input error: Input value does not match given type";
    }
}

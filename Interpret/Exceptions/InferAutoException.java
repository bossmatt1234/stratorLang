package lang.Interpret.Exceptions;

import lang.Interpret.Type;

public class InferAutoException extends CommonError{

    public InferAutoException(int lineNum, int columnName) {
        super(lineNum, columnName);
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Infer error: Can't infer type of empty variable";
    }
}

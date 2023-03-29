package lang.Interpret.Exceptions;

import lang.Interpret.ErrorType;

public class ArgNumError extends CommonError{
    int num1;
    int num2;
    public ArgNumError(int lineNum, int columnName, int num1, int num2) {
        super(lineNum, columnName);
        this.num1 = num1;
        this.num2 = num2;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Argument number error: Function or method requires " + num1 + "parameters, only " + num2 +" given." ;
    }
}

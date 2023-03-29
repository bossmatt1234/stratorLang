package lang.Interpret.Exceptions;

public class ClassInitErrorException extends CommonError{
    String stm;
    public ClassInitErrorException(int lineNum, int columnName, String stm) {
        super(lineNum, columnName);
        this.stm = stm;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Class initialisation error: " + stm + " statement not allowed in class definition block.";
    }
}

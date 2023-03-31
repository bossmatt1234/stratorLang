package lang.Interpret.Exceptions;

public class FuncModeObjectException extends CommonError {
    String val;
    public FuncModeObjectException(int lineNum, int columnName, String val) {
        super(lineNum, columnName);
        this.val = val;

    }

    @Override
    public String getMessage() {
        return trace + "Functional Mode (Object) Error: Classes are not allowed in Functional Mode" +
                "Instead, try creating a function named {" +val+ "} which has nested functions inside. The clojure can mimic Object Orientation.";
    }
}

package lang.Interpret.Exceptions;

public class FuncModeSetListException extends CommonError {
    String val;


    public FuncModeSetListException(int lineNum, int columnName, String val) {
        super(lineNum, columnName);
        this.val = val;

    }

    @Override
    public String getMessage() {
        return trace + "Functional Mode (Set List Item) Error: Replacing an item in a list mutates {" + val + "} variable. " +
                "Instead, append items from {" +val+ "} to a second list, and check if replacement item matches index of item or condition for replacement.";
    }
}

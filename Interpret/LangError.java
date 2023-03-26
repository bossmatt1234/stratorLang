package lang.Interpret;

public class LangError extends Exception{
    int lineNum;
    public LangError(String errorMessage, int lineNum){
        super(errorMessage);
        this.lineNum = lineNum;

    }

    public int getLineNum() {
        return lineNum;
    }
}

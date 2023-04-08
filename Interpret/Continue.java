package lang.Interpret;

public class Continue extends RuntimeException{
    public int lineNum;
    public int colNum;
    public Continue(int lineNum, int colNum){
        super(null,null,false,false);
        this.lineNum = lineNum;
        this.colNum = colNum;

    }
}

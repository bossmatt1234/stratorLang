package lang.Interpret;

public class Break extends RuntimeException{
    public int lineNum;
    public int colNum;
    public Break(int lineNum, int colNum){
        super(null,null,false,false);
        this.lineNum = lineNum;
        this.colNum = colNum;

    }
}

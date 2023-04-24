package lang.Interpret;

public class Throw extends RuntimeException{
    Val returnVal;

    public int lineNum;
    public int colNum;
    public Throw(Val val, int lineNum, int colNum){
        super(null,null,false,false);
        this.returnVal = val;
        this.lineNum = lineNum;
        this.colNum = colNum;

    }
}


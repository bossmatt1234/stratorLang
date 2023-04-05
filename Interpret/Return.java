package lang.Interpret;

public class Return extends RuntimeException{
    Val returnVal;

    int lineNum;
    int colNum;
    public Return(Val val, int lineNum, int colNum){
        super(null,null,false,false);
        this.returnVal = val;
        this.lineNum = lineNum;
        this.colNum = colNum;

    }


}

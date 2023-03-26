package lang.Interpret;

public class Return extends RuntimeException{
    Val returnVal;

    public Return(Val val){
        super(null,null,false,false);
        this.returnVal = val;

    }

}

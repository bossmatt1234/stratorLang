package lang.Interpret;

public class ExtendEnvThrow extends RuntimeException{
    String ident;
    public ExtendEnvThrow(String ident){
        super(null,null,false,false);
        this.ident = ident;
    }
}

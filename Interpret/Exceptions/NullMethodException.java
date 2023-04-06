package lang.Interpret.Exceptions;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import lang.Interpret.ObjectDef;
import lang.Interpret.Val;

import java.util.HashMap;

public class NullMethodException extends CommonError{
    String ident;
    String ident2;
    String similar;
    public NullMethodException(int lineNum, int columnName, String ident, String ident2, HashMap<String, Val> context) {
        super(lineNum, columnName);
        this.ident = ident;
        this.ident2 = ident2;

        NormalizedLevenshtein l = new NormalizedLevenshtein();
        String similar = "";
        double distance = 1.0;
        for(HashMap.Entry<String, Val> entry: context.entrySet()){
            if(l.distance(ident, entry.getKey()) < distance){
                similar = entry.getKey();
                distance = l.distance(ident,entry.getKey());
            }
        }
        if(context.size() ==0 || distance == 1.0){
            this.similar = "";
        }else{
            this.similar = "Did you mean {" + similar + "}?";
        }

        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Method Lookup Error: Method {" + ident + "} has not been found in class {" + ident2 + "}. " + similar;
    }
}

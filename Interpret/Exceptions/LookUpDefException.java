package lang.Interpret.Exceptions;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import lang.Interpret.ObjectDef;
import lang.Interpret.Val;

import java.util.HashMap;

public class LookUpDefException extends CommonError{
    String ident;
    String similar;
    public LookUpDefException(int lineNum, int columnName, String ident,HashMap<String, ObjectDef> context) {
        super(lineNum, columnName);
        this.ident = ident;

        NormalizedLevenshtein l = new NormalizedLevenshtein();
        String similar = "";
        double distance = 1.0;
        for(HashMap.Entry<String, ObjectDef> entry: context.entrySet()){
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
        return trace + "Class lookup error: Class {" + ident + "} has not been found. " + similar;
    }
}

package lang.Interpret;

import lang.Interpret.Exceptions.ExtendEnvException;
import lang.Interpret.Exceptions.LookUpDefException;
import lang.Interpret.Exceptions.LookUpVarException;

import java.util.HashMap;
import java.util.LinkedList;

public class Env {

    public LinkedList<HashMap<String, Val>> contexts;

    public LinkedList<HashMap<String, ObjectDef>> objectDefs;

    public Env() {
        this.contexts = new LinkedList<>();
        this.contexts.add(new HashMap<>());

        this.objectDefs = new LinkedList<>();
        this.objectDefs.add(new HashMap<>());
    }

    public Val lookupVar(String id, int lineNum, int columnNum) {
        Val returnVal;
        if (!contexts.getLast().containsKey(id)) {
            for (int i = contexts.size() - 1; i >= 0; i--) {
                if (contexts.get(i).containsKey(id)) {
                    returnVal = contexts.get(i).get(id);
                    if(returnVal == null){
                        throw new LookUpVarException(lineNum,columnNum,id);
                    }else{
                        return returnVal;
                    }
                }
            }
        }else {
            returnVal = contexts.getLast().get(id);
            if (returnVal == null) {
                throw new LookUpVarException(lineNum, columnNum, id);
            } else {
                return returnVal;
            }
        }
        throw new LookUpVarException(lineNum, columnNum, id);
    }

    public ObjectDef lookupDef(String id, int lineNum, int columnNum) {
        if(this.objectDefs.getLast().get(id) == null){
            throw new LookUpDefException(lineNum,columnNum,id);
        }
        return this.objectDefs.getLast().get(id);
    }

    public Env updateVar(String id, Val val) {
        if (!contexts.getLast().containsKey(id)) {
            for (int i = contexts.size() - 1; i >= 0; i--) {
                if (contexts.get(i).containsKey(id)) {
                    contexts.get(i).put(id, val);
                    return this;
                }
            }
        }
        this.contexts.getLast().put(id, val);
        return this;
    }

    public Env extendEnvVar(String id, Val val) {
        if (!contexts.getLast().containsKey(id)) {
            this.contexts.getLast().put(id, val);
            return this;
        } else {
            throw new ExtendEnvThrow(id);
        }
    }

    public void newBlock() {
        this.contexts.add(new HashMap<>());

    }

    public void emptyBlock() {
        this.contexts.removeLast();
    }
}

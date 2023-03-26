package lang.Interpret;

import java.util.HashMap;
import java.util.LinkedList;

public class TypeEnv {
    public LinkedList<HashMap<String, Type>> contexts;

    public LinkedList<HashMap<String, Type>> objectDefs;

    public TypeEnv() {
        this.contexts = new LinkedList<>();
        this.contexts.add(new HashMap<>());

        this.objectDefs = new LinkedList<>();
        this.objectDefs.add(new HashMap<>());
    }

    public Type lookupVar(String id) {
        if (!contexts.getLast().containsKey(id)) {
            for (int i = contexts.size() - 1; i >= 0; i--) {
                if (contexts.get(i).containsKey(id)) {
                    return contexts.get(i).get(id);
                }
            }
        }
        return contexts.getLast().get(id);
    }

    public Type lookupDef(String id) {
        return this.objectDefs.getLast().get(id);
    }

    public TypeEnv updateVar(String id, Type val) {
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

    public TypeEnv extendEnvVar(String id, Type val) {
        if (!contexts.getLast().containsKey(id)) {
            this.contexts.getLast().put(id, val);
            return this;
        } else {
            String msg = id +" already defined";
            throw new RuntimeException(msg);
        }
    }

    public void newBlock() {
        this.contexts.add(new HashMap<>());

    }

    public void emptyBlock() {
        this.contexts.removeLast();
    }
}

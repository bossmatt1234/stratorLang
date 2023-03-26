package lang.Interpret;

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

    public Val lookupVar(String id) {
        if (!contexts.getLast().containsKey(id)) {
            for (int i = contexts.size() - 1; i >= 0; i--) {
                if (contexts.get(i).containsKey(id)) {
                    return contexts.get(i).get(id);
                }
            }
        }
        return contexts.getLast().get(id);
    }

    public ObjectDef lookupDef(String id) {
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

package lang.Interpret;

import lang.Absyn.Exp;
import lang.Absyn.ListExp;
import lang.Absyn.ListStm;

import java.util.HashMap;
import java.util.LinkedList;

public class Function {
    public LinkedList<Arg> args = new LinkedList<>();
    public ListStm listStm;
    public Val returnVal;
    public HashMap<String, Val> closure = new HashMap<>();

}

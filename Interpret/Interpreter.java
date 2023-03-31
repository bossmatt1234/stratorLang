// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Interpret;


import com.google.common.collect.Iterables;
import lang.Absyn.*;
import lang.Interpret.Exceptions.*;

import java.util.*;

import static lang.Interpret.Operator.*;

public class Interpreter
{
    /* Program Start */
    public void interpret(Program p){

        p.accept(new ProgramVisitor(),new Env());
    }
  public static class ProgramVisitor implements Program.Visitor<Object,Env>
  {
    public Object visit(lang.Absyn.PStms p, Env env)
    { /* Code for PStms goes here */

        return new NormalModeVisitor(p,env);
    }
    public Object visit(lang.Absyn.PFunctionalModeStms p, Env env)
    { /* Code for PFunctionalModeStms goes here */

        return new FunctionalModeVisitor(p,env);
    }
  }




}

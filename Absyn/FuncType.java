// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public abstract class FuncType implements java.io.Serializable {
  public abstract <R,A> R accept(FuncType.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    R visit(lang.Absyn.FuncType_void p, A arg);
    R visit(lang.Absyn.FuncTypeVarType p, A arg);

  }

}

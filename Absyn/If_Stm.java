// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public abstract class If_Stm implements java.io.Serializable {
  public abstract <R,A> R accept(If_Stm.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    R visit(lang.Absyn.SIf p, A arg);
    R visit(lang.Absyn.SIfElse p, A arg);
    R visit(lang.Absyn.SIfElseIf p, A arg);

  }

}

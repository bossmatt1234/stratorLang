// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public abstract class Stm_Declare implements java.io.Serializable {
  public abstract <R,A> R accept(Stm_Declare.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    R visit(lang.Absyn.SDecl p, A arg);

  }

}

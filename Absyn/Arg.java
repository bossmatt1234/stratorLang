// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public abstract class Arg implements java.io.Serializable {
  public abstract <R,A> R accept(Arg.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(lang.Absyn.ArgDecl p, A arg);

  }

}

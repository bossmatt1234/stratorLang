// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public abstract class TypeCast implements java.io.Serializable {
  public abstract <R,A> R accept(TypeCast.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(lang.Absyn.TypeCast_toInt p, A arg);
    public R visit(lang.Absyn.TypeCast_toDouble p, A arg);
    public R visit(lang.Absyn.TypeCast_toBool p, A arg);
    public R visit(lang.Absyn.TypeCast_toChar p, A arg);
    public R visit(lang.Absyn.TypeCast_toString p, A arg);

  }

}

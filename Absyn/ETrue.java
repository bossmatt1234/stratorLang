// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class ETrue  extends Exp {
  public int line_num, col_num, offset;
  public ETrue() { }

  public <R,A> R accept(lang.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
      return o instanceof ETrue;
  }

  public int hashCode() {
    return 37;
  }


}

// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class SCall  extends Stm {
  public final Exp exp_;
  public int line_num, col_num, offset;
  public SCall(Exp p1) { exp_ = p1; }

  public <R,A> R accept(lang.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof SCall x) {
        return this.exp_.equals(x.exp_);
    }
    return false;
  }

  public int hashCode() {
    return this.exp_.hashCode();
  }


}

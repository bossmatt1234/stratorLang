// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class IfS  extends Stm {
  public final If_Stm if_stm_;
  public int line_num, col_num, offset;
  public IfS(If_Stm p1) { if_stm_ = p1; }

  public <R,A> R accept(lang.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof IfS x) {
        return this.if_stm_.equals(x.if_stm_);
    }
    return false;
  }

  public int hashCode() {
    return this.if_stm_.hashCode();
  }


}

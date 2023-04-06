// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class SPLoopRangeStart  extends Stm_Loop {
  public final String ident_;
  public final Exp exp_;
  public final ListStm liststm_;
  public int line_num, col_num, offset;
  public SPLoopRangeStart(String p1, Exp p2, ListStm p3) { ident_ = p1; exp_ = p2; liststm_ = p3; }

  public <R,A> R accept(lang.Absyn.Stm_Loop.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof SPLoopRangeStart x) {
        return this.ident_.equals(x.ident_) && this.exp_.equals(x.exp_) && this.liststm_.equals(x.liststm_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.ident_.hashCode())+this.exp_.hashCode())+this.liststm_.hashCode();
  }


}

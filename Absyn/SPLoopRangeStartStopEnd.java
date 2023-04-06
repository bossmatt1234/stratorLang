// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class SPLoopRangeStartStopEnd  extends Stm_Loop {
  public final String ident_;
  public final Exp exp_1, exp_2, exp_3;
  public final ListStm liststm_;
  public int line_num, col_num, offset;
  public SPLoopRangeStartStopEnd(String p1, Exp p2, Exp p3, Exp p4, ListStm p5) { ident_ = p1; exp_1 = p2; exp_2 = p3; exp_3 = p4; liststm_ = p5; }

  public <R,A> R accept(lang.Absyn.Stm_Loop.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof SPLoopRangeStartStopEnd x) {
        return this.ident_.equals(x.ident_) && this.exp_1.equals(x.exp_1) && this.exp_2.equals(x.exp_2) && this.exp_3.equals(x.exp_3) && this.liststm_.equals(x.liststm_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(37*(37*(this.ident_.hashCode())+this.exp_1.hashCode())+this.exp_2.hashCode())+this.exp_3.hashCode())+this.liststm_.hashCode();
  }


}

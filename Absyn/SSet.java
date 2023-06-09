// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class SSet  extends Stm {
  public final String ident_;
  public final Exp exp_1, exp_2;
  public int line_num, col_num, offset;
  public SSet(String p1, Exp p2, Exp p3) { ident_ = p1; exp_1 = p2; exp_2 = p3; }

  public <R,A> R accept(lang.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof SSet x) {
        return this.ident_.equals(x.ident_) && this.exp_1.equals(x.exp_1) && this.exp_2.equals(x.exp_2);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.ident_.hashCode())+this.exp_1.hashCode())+this.exp_2.hashCode();
  }


}

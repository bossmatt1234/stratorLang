// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class EAssign  extends Exp {
  public final Exp exp_1, exp_2;
  public final Assign_Op assign_op_;
  public int line_num, col_num, offset;
  public EAssign(Exp p1, Assign_Op p2, Exp p3) { exp_1 = p1; assign_op_ = p2; exp_2 = p3; }

  public <R,A> R accept(lang.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof lang.Absyn.EAssign) {
      lang.Absyn.EAssign x = (lang.Absyn.EAssign)o;
      return this.exp_1.equals(x.exp_1) && this.assign_op_.equals(x.assign_op_) && this.exp_2.equals(x.exp_2);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.exp_1.hashCode())+this.assign_op_.hashCode())+this.exp_2.hashCode();
  }


}

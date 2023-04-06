// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class SConstInit  extends Stm {
  public final VarType vartype_;
  public final String ident_;
  public final Exp exp_;
  public int line_num, col_num, offset;
  public SConstInit(VarType p1, String p2, Exp p3) { vartype_ = p1; ident_ = p2; exp_ = p3; }

  public <R,A> R accept(lang.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof SConstInit x) {
        return this.vartype_.equals(x.vartype_) && this.ident_.equals(x.ident_) && this.exp_.equals(x.exp_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.vartype_.hashCode())+this.ident_.hashCode())+this.exp_.hashCode();
  }


}

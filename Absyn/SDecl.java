// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class SDecl  extends Stm_Declare {
  public final VarType vartype_;
  public final String ident_;
  public int line_num, col_num, offset;
  public SDecl(VarType p1, String p2) { vartype_ = p1; ident_ = p2; }

  public <R,A> R accept(lang.Absyn.Stm_Declare.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof SDecl x) {
        return this.vartype_.equals(x.vartype_) && this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.vartype_.hashCode())+this.ident_.hashCode();
  }


}

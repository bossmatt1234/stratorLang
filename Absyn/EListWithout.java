// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class EListWithout  extends Exp {
  public final String ident_;
  public final Exp exp_;
  public int line_num, col_num, offset;
  public EListWithout(String p1, Exp p2) { ident_ = p1; exp_ = p2; }

  public <R,A> R accept(lang.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof lang.Absyn.EListWithout) {
      lang.Absyn.EListWithout x = (lang.Absyn.EListWithout)o;
      return this.ident_.equals(x.ident_) && this.exp_.equals(x.exp_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.ident_.hashCode())+this.exp_.hashCode();
  }


}

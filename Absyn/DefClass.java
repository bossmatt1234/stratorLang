// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class DefClass  extends Stm {
  public final String ident_;
  public final ListStm liststm_;
  public int line_num, col_num, offset;
  public DefClass(String p1, ListStm p2) { ident_ = p1; liststm_ = p2; }

  public <R,A> R accept(lang.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof lang.Absyn.DefClass) {
      lang.Absyn.DefClass x = (lang.Absyn.DefClass)o;
      return this.ident_.equals(x.ident_) && this.liststm_.equals(x.liststm_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.ident_.hashCode())+this.liststm_.hashCode();
  }


}

// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class EId  extends Exp {
  public final String ident_;
  public int line_num, col_num, offset;
  public EId(String p1) { ident_ = p1; }

  public <R,A> R accept(lang.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof EId x) {
        return this.ident_.equals(x.ident_);
    }
    return false;
  }

  public int hashCode() {
    return this.ident_.hashCode();
  }


}

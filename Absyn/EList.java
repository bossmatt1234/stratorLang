// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class EList  extends Exp {
  public final ListItem listitem_;
  public int line_num, col_num, offset;
  public EList(ListItem p1) { listitem_ = p1; }

  public <R,A> R accept(lang.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof EList x) {
        return this.listitem_.equals(x.listitem_);
    }
    return false;
  }

  public int hashCode() {
    return this.listitem_.hashCode();
  }


}

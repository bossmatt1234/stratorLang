// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class ESelect  extends Exp {
  public final String ident_1, ident_2;
  public int line_num, col_num, offset;
  public ESelect(String p1, String p2) { ident_1 = p1; ident_2 = p2; }

  public <R,A> R accept(lang.Absyn.Exp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof ESelect x) {
        return this.ident_1.equals(x.ident_1) && this.ident_2.equals(x.ident_2);
    }
    return false;
  }

  public int hashCode() {
    return 37*(this.ident_1.hashCode())+this.ident_2.hashCode();
  }


}

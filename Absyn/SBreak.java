// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class SBreak  extends Stm {
  public int line_num, col_num, offset;
  public SBreak() { }

  public <R,A> R accept(lang.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
      return o instanceof SBreak;
  }

  public int hashCode() {
    return 37;
  }


}

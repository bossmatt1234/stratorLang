// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class SContinue  extends Stm {
  public int line_num, col_num, offset;
  public SContinue() { }

  public <R,A> R accept(lang.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
      return o instanceof SContinue;
  }

  public int hashCode() {
    return 37;
  }


}

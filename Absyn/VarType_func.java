// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class VarType_func  extends VarType {
  public int line_num, col_num, offset;
  public VarType_func() { }

  public <R,A> R accept(lang.Absyn.VarType.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
      return o instanceof VarType_func;
  }

  public int hashCode() {
    return 37;
  }


}

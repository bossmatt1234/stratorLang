// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class AssignMul  extends Assign_Op {
  public int line_num, col_num, offset;
  public AssignMul() { }

  public <R,A> R accept(lang.Absyn.Assign_Op.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof lang.Absyn.AssignMul) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return 37;
  }


}

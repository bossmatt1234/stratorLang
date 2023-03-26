// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public class Block  extends Stm {
  public final ListStm liststm_;
  public int line_num, col_num, offset;
  public Block(ListStm p1) { liststm_ = p1; }

  public <R,A> R accept(lang.Absyn.Stm.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(java.lang.Object o) {
    if (this == o) return true;
    if (o instanceof lang.Absyn.Block) {
      lang.Absyn.Block x = (lang.Absyn.Block)o;
      return this.liststm_.equals(x.liststm_);
    }
    return false;
  }

  public int hashCode() {
    return this.liststm_.hashCode();
  }


}

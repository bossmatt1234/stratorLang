// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public abstract class Stm implements java.io.Serializable {
  public abstract <R,A> R accept(Stm.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(lang.Absyn.DefFun p, A arg);
    public R visit(lang.Absyn.DefConstructor p, A arg);
    public R visit(lang.Absyn.DefClass p, A arg);
    public R visit(lang.Absyn.DefClassInherits p, A arg);
    public R visit(lang.Absyn.SPrint p, A arg);
    public R visit(lang.Absyn.SBreak p, A arg);
    public R visit(lang.Absyn.SContinue p, A arg);
    public R visit(lang.Absyn.InitialiseStm p, A arg);
    public R visit(lang.Absyn.DeclareStm p, A arg);
    public R visit(lang.Absyn.AssignStm p, A arg);
    public R visit(lang.Absyn.LoopStm p, A arg);
    public R visit(lang.Absyn.IncrnDecrmStm p, A arg);
    public R visit(lang.Absyn.SCall p, A arg);
    public R visit(lang.Absyn.SAppend p, A arg);
    public R visit(lang.Absyn.SRemove p, A arg);
    public R visit(lang.Absyn.SReturn p, A arg);
    public R visit(lang.Absyn.SObjInit p, A arg);
    public R visit(lang.Absyn.SConstInit p, A arg);
    public R visit(lang.Absyn.IfS p, A arg);
    public R visit(lang.Absyn.Block p, A arg);

  }

}

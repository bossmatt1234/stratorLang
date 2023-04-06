// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang.Absyn;

public abstract class Stm_Loop implements java.io.Serializable {
  public abstract <R,A> R accept(Stm_Loop.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    R visit(lang.Absyn.SWhile p, A arg);
    R visit(lang.Absyn.SPLoopIdent p, A arg);
    R visit(lang.Absyn.SPLoopList p, A arg);
    R visit(lang.Absyn.SPLoopRangeStart p, A arg);
    R visit(lang.Absyn.SPLoopRangeStartStop p, A arg);
    R visit(lang.Absyn.SPLoopRangeStartStopEnd p, A arg);
    R visit(lang.Absyn.SCLoop p, A arg);
    R visit(lang.Absyn.SCLoopAssign p, A arg);

  }

}

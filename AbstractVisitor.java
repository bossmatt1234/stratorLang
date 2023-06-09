// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang;

/** Abstract Visitor */

public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
    /* Program */
    public R visit(lang.Absyn.PStms p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.PFunctionalModeStms p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Program p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Stm */
    public R visit(lang.Absyn.DefFun p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.DefConstructor p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.DefClass p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.DefClassInherits p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.STryCatch p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.STryCatchFinally p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SPrint p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SBreak p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SContinue p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SThrow p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.InitialiseStm p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.DeclareStm p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.AssignStm p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.LoopStm p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.IncrnDecrmStm p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SCall p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SAppend p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SRemove p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SSet p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SReturn p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SObjInit p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.IfS p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.Block p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Stm p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Item */
    public R visit(lang.Absyn.LstItem p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Item p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Stm_Initialise */
    public R visit(lang.Absyn.SInit p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Stm_Initialise p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Stm_Declare */
    public R visit(lang.Absyn.SDecl p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Stm_Declare p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Stm_Assign */
    public R visit(lang.Absyn.SAssign p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Stm_Assign p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Stm_IncrmDecrm */
    public R visit(lang.Absyn.SIncrmDecrm p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Stm_IncrmDecrm p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Stm_Loop */
    public R visit(lang.Absyn.SWhile p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SPLoopIdent p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SPLoopList p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SPLoopRangeStart p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SPLoopRangeStartStop p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SPLoopRangeStartStopEnd p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SCLoop p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SCLoopAssign p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Stm_Loop p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* If_Stm */
    public R visit(lang.Absyn.SIf p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SIfElse p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.SIfElseIf p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.If_Stm p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Arg */
    public R visit(lang.Absyn.ArgDecl p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Arg p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Exp */
    public R visit(lang.Absyn.EInt p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ENegInt p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EChar p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EDouble p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ENegDouble p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EString p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ETrue p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EFalse p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EId p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EType p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EListItem p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EListWith p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EListWithout p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EList p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EListSize p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EListIsEmpty p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EInput p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EInputString p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EStrLength p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ERand p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ETypeCast p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ELambda p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EMapIdent p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EMapList p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EFilterIdent p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EFilterList p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EReduceIdent p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EReduceList p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ESelect p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ESelectListItem p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ECall p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EObjCall p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EPow p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EMul p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EDiv p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EMod p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EAdd p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ESub p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ELt p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EGt p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ELEq p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EGEq p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EEq p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.ENEq p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EAnd p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.EOr p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Exp p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* Assign_Op */
    public R visit(lang.Absyn.Assign p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.AssignMul p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.AssignDiv p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.AssignMod p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.AssignAdd p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.AssignSub p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.Assign_Op p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* IncrmDecrm_Op */
    public R visit(lang.Absyn.Increment p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.Decrement p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.IncrmDecrm_Op p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* FuncType */
    public R visit(lang.Absyn.FuncType_void p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.FuncTypeVarType p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.FuncType p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* VarType */
    public R visit(lang.Absyn.VarType_int p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.VarType_double p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.VarType_bool p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.VarType_char p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.VarType_string p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.VarType_auto p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.VarType_func p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.VarType_object p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.VarType p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
    /* TypeCast */
    public R visit(lang.Absyn.TypeCast_toInt p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.TypeCast_toDouble p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.TypeCast_toBool p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.TypeCast_toChar p, A arg) { return visitDefault(p, arg); }
    public R visit(lang.Absyn.TypeCast_toString p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(lang.Absyn.TypeCast p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}

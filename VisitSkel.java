// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang;

/*** Visitor Design Pattern Skeleton. ***/

/* This implements the common visitor design pattern.
   Tests show it to be slightly less efficient than the
   instanceof method, but easier to use.
   Replace the R and A parameters with the desired return
   and context types.*/

public class VisitSkel
{
  public class ProgramVisitor<R,A> implements lang.Absyn.Program.Visitor<R,A>
  {
    public R visit(lang.Absyn.PStms p, A arg)
    { /* Code for PStms goes here */
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.PFunctionalModeStms p, A arg)
    { /* Code for PFunctionalModeStms goes here */
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
  }
  public class StmVisitor<R,A> implements lang.Absyn.Stm.Visitor<R,A>
  {
    public R visit(lang.Absyn.DefFun p, A arg)
    { /* Code for DefFun goes here */
      p.functype_.accept(new FuncTypeVisitor<R,A>(), arg);
      //p.ident_;
      for (lang.Absyn.Arg x: p.listarg_) {
        x.accept(new ArgVisitor<R,A>(), arg);
      }
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.DefConstructor p, A arg)
    { /* Code for DefConstructor goes here */
      //p.ident_;
      for (lang.Absyn.Arg x: p.listarg_) {
        x.accept(new ArgVisitor<R,A>(), arg);
      }
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.DefClass p, A arg)
    { /* Code for DefClass goes here */
      //p.ident_;
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.DefClassInherits p, A arg)
    { /* Code for DefClassInherits goes here */
      //p.ident_1;
      //p.ident_2;
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.STryCatch p, A arg)
    { /* Code for STryCatch goes here */
      for (lang.Absyn.Stm x: p.liststm_1) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      //p.ident_;
      for (lang.Absyn.Stm x: p.liststm_2) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.STryCatchFinally p, A arg)
    { /* Code for STryCatchFinally goes here */
      for (lang.Absyn.Stm x: p.liststm_1) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      //p.ident_;
      for (lang.Absyn.Stm x: p.liststm_2) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      for (lang.Absyn.Stm x: p.liststm_3) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SPrint p, A arg)
    { /* Code for SPrint goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.SBreak p, A arg)
    { /* Code for SBreak goes here */
      return null;
    }
    public R visit(lang.Absyn.SContinue p, A arg)
    { /* Code for SContinue goes here */
      return null;
    }
    public R visit(lang.Absyn.SThrow p, A arg)
    { /* Code for SThrow goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.InitialiseStm p, A arg)
    { /* Code for InitialiseStm goes here */
      p.stm_initialise_.accept(new Stm_InitialiseVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.DeclareStm p, A arg)
    { /* Code for DeclareStm goes here */
      p.stm_declare_.accept(new Stm_DeclareVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.AssignStm p, A arg)
    { /* Code for AssignStm goes here */
      p.stm_assign_.accept(new Stm_AssignVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.LoopStm p, A arg)
    { /* Code for LoopStm goes here */
      p.stm_loop_.accept(new Stm_LoopVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.IncrnDecrmStm p, A arg)
    { /* Code for IncrnDecrmStm goes here */
      p.stm_incrmdecrm_.accept(new Stm_IncrmDecrmVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.SCall p, A arg)
    { /* Code for SCall goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.SAppend p, A arg)
    { /* Code for SAppend goes here */
      //p.ident_;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.SRemove p, A arg)
    { /* Code for SRemove goes here */
      //p.ident_;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.SSet p, A arg)
    { /* Code for SSet goes here */
      //p.ident_;
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.SReturn p, A arg)
    { /* Code for SReturn goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.SObjInit p, A arg)
    { /* Code for SObjInit goes here */
      //p.ident_1;
      //p.ident_2;
      for (lang.Absyn.Exp x: p.listexp_) {
        x.accept(new ExpVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.IfS p, A arg)
    { /* Code for IfS goes here */
      p.if_stm_.accept(new If_StmVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.Block p, A arg)
    { /* Code for Block goes here */
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
  }
  public class ItemVisitor<R,A> implements lang.Absyn.Item.Visitor<R,A>
  {
    public R visit(lang.Absyn.LstItem p, A arg)
    { /* Code for LstItem goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
  }
  public class Stm_InitialiseVisitor<R,A> implements lang.Absyn.Stm_Initialise.Visitor<R,A>
  {
    public R visit(lang.Absyn.SInit p, A arg)
    { /* Code for SInit goes here */
      p.vartype_.accept(new VarTypeVisitor<R,A>(), arg);
      //p.ident_;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
  }
  public class Stm_DeclareVisitor<R,A> implements lang.Absyn.Stm_Declare.Visitor<R,A>
  {
    public R visit(lang.Absyn.SDecl p, A arg)
    { /* Code for SDecl goes here */
      p.vartype_.accept(new VarTypeVisitor<R,A>(), arg);
      //p.ident_;
      return null;
    }
  }
  public class Stm_AssignVisitor<R,A> implements lang.Absyn.Stm_Assign.Visitor<R,A>
  {
    public R visit(lang.Absyn.SAssign p, A arg)
    { /* Code for SAssign goes here */
      //p.ident_;
      p.assign_op_.accept(new Assign_OpVisitor<R,A>(), arg);
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
  }
  public class Stm_IncrmDecrmVisitor<R,A> implements lang.Absyn.Stm_IncrmDecrm.Visitor<R,A>
  {
    public R visit(lang.Absyn.SIncrmDecrm p, A arg)
    { /* Code for SIncrmDecrm goes here */
      //p.ident_;
      p.incrmdecrm_op_.accept(new IncrmDecrm_OpVisitor<R,A>(), arg);
      return null;
    }
  }
  public class Stm_LoopVisitor<R,A> implements lang.Absyn.Stm_Loop.Visitor<R,A>
  {
    public R visit(lang.Absyn.SWhile p, A arg)
    { /* Code for SWhile goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SPLoopIdent p, A arg)
    { /* Code for SPLoopIdent goes here */
      //p.ident_1;
      //p.ident_2;
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SPLoopList p, A arg)
    { /* Code for SPLoopList goes here */
      //p.ident_;
      for (lang.Absyn.Item x: p.listitem_) {
        x.accept(new ItemVisitor<R,A>(), arg);
      }
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SPLoopRangeStart p, A arg)
    { /* Code for SPLoopRangeStart goes here */
      //p.ident_;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SPLoopRangeStartStop p, A arg)
    { /* Code for SPLoopRangeStartStop goes here */
      //p.ident_;
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SPLoopRangeStartStopEnd p, A arg)
    { /* Code for SPLoopRangeStartStopEnd goes here */
      //p.ident_;
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      p.exp_3.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SCLoop p, A arg)
    { /* Code for SCLoop goes here */
      p.stm_initialise_.accept(new Stm_InitialiseVisitor<R,A>(), arg);
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      p.stm_incrmdecrm_.accept(new Stm_IncrmDecrmVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SCLoopAssign p, A arg)
    { /* Code for SCLoopAssign goes here */
      p.stm_initialise_.accept(new Stm_InitialiseVisitor<R,A>(), arg);
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      p.stm_assign_.accept(new Stm_AssignVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
  }
  public class If_StmVisitor<R,A> implements lang.Absyn.If_Stm.Visitor<R,A>
  {
    public R visit(lang.Absyn.SIf p, A arg)
    { /* Code for SIf goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SIfElse p, A arg)
    { /* Code for SIfElse goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_1) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      for (lang.Absyn.Stm x: p.liststm_2) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.SIfElseIf p, A arg)
    { /* Code for SIfElseIf goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      p.if_stm_.accept(new If_StmVisitor<R,A>(), arg);
      return null;
    }
  }
  public class ArgVisitor<R,A> implements lang.Absyn.Arg.Visitor<R,A>
  {
    public R visit(lang.Absyn.ArgDecl p, A arg)
    { /* Code for ArgDecl goes here */
      p.vartype_.accept(new VarTypeVisitor<R,A>(), arg);
      //p.ident_;
      return null;
    }
  }
  public class ExpVisitor<R,A> implements lang.Absyn.Exp.Visitor<R,A>
  {
    public R visit(lang.Absyn.EInt p, A arg)
    { /* Code for EInt goes here */
      //p.integer_;
      return null;
    }
    public R visit(lang.Absyn.ENegInt p, A arg)
    { /* Code for ENegInt goes here */
      //p.integer_;
      return null;
    }
    public R visit(lang.Absyn.EChar p, A arg)
    { /* Code for EChar goes here */
      //p.char_;
      return null;
    }
    public R visit(lang.Absyn.EDouble p, A arg)
    { /* Code for EDouble goes here */
      //p.double_;
      return null;
    }
    public R visit(lang.Absyn.ENegDouble p, A arg)
    { /* Code for ENegDouble goes here */
      //p.double_;
      return null;
    }
    public R visit(lang.Absyn.EString p, A arg)
    { /* Code for EString goes here */
      //p.string_;
      return null;
    }
    public R visit(lang.Absyn.ETrue p, A arg)
    { /* Code for ETrue goes here */
      return null;
    }
    public R visit(lang.Absyn.EFalse p, A arg)
    { /* Code for EFalse goes here */
      return null;
    }
    public R visit(lang.Absyn.EId p, A arg)
    { /* Code for EId goes here */
      //p.ident_;
      return null;
    }
    public R visit(lang.Absyn.EType p, A arg)
    { /* Code for EType goes here */
      //p.ident_;
      p.vartype_.accept(new VarTypeVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EListItem p, A arg)
    { /* Code for EListItem goes here */
      //p.ident_;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EListWith p, A arg)
    { /* Code for EListWith goes here */
      //p.ident_;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EListWithout p, A arg)
    { /* Code for EListWithout goes here */
      //p.ident_;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EList p, A arg)
    { /* Code for EList goes here */
      for (lang.Absyn.Item x: p.listitem_) {
        x.accept(new ItemVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.EListSize p, A arg)
    { /* Code for EListSize goes here */
      //p.ident_;
      return null;
    }
    public R visit(lang.Absyn.EListIsEmpty p, A arg)
    { /* Code for EListIsEmpty goes here */
      //p.ident_;
      return null;
    }
    public R visit(lang.Absyn.EInput p, A arg)
    { /* Code for EInput goes here */
      return null;
    }
    public R visit(lang.Absyn.EInputString p, A arg)
    { /* Code for EInputString goes here */
      //p.string_;
      return null;
    }
    public R visit(lang.Absyn.EStrLength p, A arg)
    { /* Code for EStrLength goes here */
      //p.ident_;
      return null;
    }
    public R visit(lang.Absyn.ERand p, A arg)
    { /* Code for ERand goes here */
      //p.integer_;
      return null;
    }
    public R visit(lang.Absyn.ETypeCast p, A arg)
    { /* Code for ETypeCast goes here */
      //p.ident_;
      p.typecast_.accept(new TypeCastVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.ELambda p, A arg)
    { /* Code for ELambda goes here */
      for (lang.Absyn.Arg x: p.listarg_) {
        x.accept(new ArgVisitor<R,A>(), arg);
      }
      for (lang.Absyn.Stm x: p.liststm_) {
        x.accept(new StmVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.EMapIdent p, A arg)
    { /* Code for EMapIdent goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      //p.ident_;
      return null;
    }
    public R visit(lang.Absyn.EMapList p, A arg)
    { /* Code for EMapList goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Item x: p.listitem_) {
        x.accept(new ItemVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.EFilterIdent p, A arg)
    { /* Code for EFilterIdent goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      //p.ident_;
      return null;
    }
    public R visit(lang.Absyn.EFilterList p, A arg)
    { /* Code for EFilterList goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Item x: p.listitem_) {
        x.accept(new ItemVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.EReduceIdent p, A arg)
    { /* Code for EReduceIdent goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      //p.ident_;
      return null;
    }
    public R visit(lang.Absyn.EReduceList p, A arg)
    { /* Code for EReduceList goes here */
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      for (lang.Absyn.Item x: p.listitem_) {
        x.accept(new ItemVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.ESelect p, A arg)
    { /* Code for ESelect goes here */
      //p.ident_1;
      //p.ident_2;
      return null;
    }
    public R visit(lang.Absyn.ESelectListItem p, A arg)
    { /* Code for ESelectListItem goes here */
      //p.ident_1;
      //p.ident_2;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.ECall p, A arg)
    { /* Code for ECall goes here */
      //p.ident_;
      for (lang.Absyn.Exp x: p.listexp_) {
        x.accept(new ExpVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.EObjCall p, A arg)
    { /* Code for EObjCall goes here */
      //p.ident_1;
      //p.ident_2;
      for (lang.Absyn.Exp x: p.listexp_) {
        x.accept(new ExpVisitor<R,A>(), arg);
      }
      return null;
    }
    public R visit(lang.Absyn.EPow p, A arg)
    { /* Code for EPow goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EMul p, A arg)
    { /* Code for EMul goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EDiv p, A arg)
    { /* Code for EDiv goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EMod p, A arg)
    { /* Code for EMod goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EAdd p, A arg)
    { /* Code for EAdd goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.ESub p, A arg)
    { /* Code for ESub goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.ELt p, A arg)
    { /* Code for ELt goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EGt p, A arg)
    { /* Code for EGt goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.ELEq p, A arg)
    { /* Code for ELEq goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EGEq p, A arg)
    { /* Code for EGEq goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EEq p, A arg)
    { /* Code for EEq goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.ENEq p, A arg)
    { /* Code for ENEq goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EAnd p, A arg)
    { /* Code for EAnd goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
    public R visit(lang.Absyn.EOr p, A arg)
    { /* Code for EOr goes here */
      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);
      return null;
    }
  }
  public class Assign_OpVisitor<R,A> implements lang.Absyn.Assign_Op.Visitor<R,A>
  {
    public R visit(lang.Absyn.Assign p, A arg)
    { /* Code for Assign goes here */
      return null;
    }
    public R visit(lang.Absyn.AssignMul p, A arg)
    { /* Code for AssignMul goes here */
      return null;
    }
    public R visit(lang.Absyn.AssignDiv p, A arg)
    { /* Code for AssignDiv goes here */
      return null;
    }
    public R visit(lang.Absyn.AssignMod p, A arg)
    { /* Code for AssignMod goes here */
      return null;
    }
    public R visit(lang.Absyn.AssignAdd p, A arg)
    { /* Code for AssignAdd goes here */
      return null;
    }
    public R visit(lang.Absyn.AssignSub p, A arg)
    { /* Code for AssignSub goes here */
      return null;
    }
  }
  public class IncrmDecrm_OpVisitor<R,A> implements lang.Absyn.IncrmDecrm_Op.Visitor<R,A>
  {
    public R visit(lang.Absyn.Increment p, A arg)
    { /* Code for Increment goes here */
      return null;
    }
    public R visit(lang.Absyn.Decrement p, A arg)
    { /* Code for Decrement goes here */
      return null;
    }
  }
  public class FuncTypeVisitor<R,A> implements lang.Absyn.FuncType.Visitor<R,A>
  {
    public R visit(lang.Absyn.FuncType_void p, A arg)
    { /* Code for FuncType_void goes here */
      return null;
    }
    public R visit(lang.Absyn.FuncTypeVarType p, A arg)
    { /* Code for FuncTypeVarType goes here */
      p.vartype_.accept(new VarTypeVisitor<R,A>(), arg);
      return null;
    }
  }
  public class VarTypeVisitor<R,A> implements lang.Absyn.VarType.Visitor<R,A>
  {
    public R visit(lang.Absyn.VarType_int p, A arg)
    { /* Code for VarType_int goes here */
      return null;
    }
    public R visit(lang.Absyn.VarType_double p, A arg)
    { /* Code for VarType_double goes here */
      return null;
    }
    public R visit(lang.Absyn.VarType_bool p, A arg)
    { /* Code for VarType_bool goes here */
      return null;
    }
    public R visit(lang.Absyn.VarType_char p, A arg)
    { /* Code for VarType_char goes here */
      return null;
    }
    public R visit(lang.Absyn.VarType_string p, A arg)
    { /* Code for VarType_string goes here */
      return null;
    }
    public R visit(lang.Absyn.VarType_auto p, A arg)
    { /* Code for VarType_auto goes here */
      return null;
    }
    public R visit(lang.Absyn.VarType_func p, A arg)
    { /* Code for VarType_func goes here */
      return null;
    }
    public R visit(lang.Absyn.VarType_object p, A arg)
    { /* Code for VarType_object goes here */
      return null;
    }
  }
  public class TypeCastVisitor<R,A> implements lang.Absyn.TypeCast.Visitor<R,A>
  {
    public R visit(lang.Absyn.TypeCast_toInt p, A arg)
    { /* Code for TypeCast_toInt goes here */
      return null;
    }
    public R visit(lang.Absyn.TypeCast_toDouble p, A arg)
    { /* Code for TypeCast_toDouble goes here */
      return null;
    }
    public R visit(lang.Absyn.TypeCast_toBool p, A arg)
    { /* Code for TypeCast_toBool goes here */
      return null;
    }
    public R visit(lang.Absyn.TypeCast_toChar p, A arg)
    { /* Code for TypeCast_toChar goes here */
      return null;
    }
    public R visit(lang.Absyn.TypeCast_toString p, A arg)
    { /* Code for TypeCast_toString goes here */
      return null;
    }
  }
}

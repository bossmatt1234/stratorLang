package lang.Interpret;

import lang.Absyn.*;

public class TypeCheck {

    public void typeCheck(Program p){
        p.accept(new ProgramVisitor(),new TypeEnv());
    }
    public class EnvLevel{

    }
    public class ProgramVisitor implements Program.Visitor<Integer, TypeEnv>{


        @Override
        public Integer visit(PStms p, TypeEnv arg) {
            return null;
        }

        @Override
        public Integer visit(PFunctionalModeStms p, TypeEnv arg) {
            return null;
        }
    }

    public class StmVisitor implements Stm.Visitor<Object,TypeEnv>{


        @Override
        public Object visit(DefFun p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(DefConstructor p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(DefClass p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(DefClassInherits p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SPrint p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SBreak p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SContinue p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(InitialiseStm p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(DeclareStm p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(AssignStm p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(LoopStm p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(IncrnDecrmStm p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SCall p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SAppend p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SRemove p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SReturn p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SObjInit p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(SConstInit p, TypeEnv arg) {
            return null;
        }

        @Override
        public Object visit(IfS p, TypeEnv arg) {
            return null;
        }



        @Override
        public Object visit(Block p, TypeEnv arg) {
            return null;
        }
    }

    public class Stm_InitialiseVisitor implements Stm_Initialise.Visitor<Object,TypeEnv>{

        @Override
        public Object visit(SInit p, TypeEnv env) {
            Type varType = p.vartype_.accept(new VarTypeVisitor(), env);
            Type expType = p.exp_.accept(new ExpVisitor(), env);
            if(varType != expType){
                throw new RuntimeException("Type check error");
            }
            return env;
        }
    }
    public class ExpVisitor implements Exp.Visitor<Type,TypeEnv>{

        @Override
        public Type visit(EInt p, TypeEnv arg) {
            return Type.TInt;
        }

        @Override
        public Type visit(ENegInt p, TypeEnv arg) {
            return Type.TInt;
        }

        @Override
        public Type visit(EChar p, TypeEnv arg) {
            return Type.TChar;
        }

        @Override
        public Type visit(EDouble p, TypeEnv arg) {
            return Type.TDouble;
        }

        @Override
        public Type visit(ENegDouble p, TypeEnv arg) {
            return Type.TDouble;
        }

        @Override
        public Type visit(EString p, TypeEnv arg) {
            return Type.TString;
        }

        @Override
        public Type visit(ETrue p, TypeEnv arg) {
            return Type.TBoolean;
        }

        @Override
        public Type visit(EFalse p, TypeEnv arg) {
            return Type.TBoolean;
        }

        @Override
        public Type visit(EId p, TypeEnv env) {
            return env.lookupVar(p.ident_);
        }

        @Override
        public Type visit(EListItem p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EList p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EListSize p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EInput p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EStrLength p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ERand p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ETypeCast p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ELambda p, TypeEnv arg) {
            return Type.TFunc;
        }

        @Override
        public Type visit(EMapIdent p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EMapList p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EFilterIdent p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EFilterList p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EReduceIdent p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EReduceList p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ESelect p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ESelectListItem p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ECall p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EObjCall p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EPow p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EMul p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EDiv p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EMod p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EAdd p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ESub p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ELt p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EGt p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ELEq p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EGEq p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EEq p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(ENEq p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EAnd p, TypeEnv arg) {
            return null;
        }

        @Override
        public Type visit(EOr p, TypeEnv arg) {
            return null;
        }


    }
    public static class FuncTypeVisitor implements FuncType.Visitor<Type,Object>
    {
        public Type visit(lang.Absyn.FuncType_void p, Object arg)
        { /* Code for FuncType_void goes here */
            return Type.TVoid;
        }
        public Type visit(lang.Absyn.FuncTypeVarType p, Object arg)
        { /* Code for FuncTypeVarType goes here */
            return p.vartype_.accept(new Interpreter.VarTypeVisitor(), arg);
        }
    }
    public static class VarTypeVisitor implements VarType.Visitor<Type,Object>
    {
        public Type visit(lang.Absyn.VarType_int p, Object arg)
        { /* Code for VarType_int goes here */
            return Type.TInt;
        }
        public Type visit(lang.Absyn.VarType_double p, Object arg)
        { /* Code for VarType_double goes here */
            return Type.TDouble;
        }
        public Type visit(lang.Absyn.VarType_bool p, Object arg)
        { /* Code for VarType_boolean goes here */
            return Type.TBoolean;
        }
        public Type visit(lang.Absyn.VarType_char p, Object arg)
        { /* Code for VarType_char goes here */
            return Type.TChar;
        }
        public Type visit(lang.Absyn.VarType_string p, Object arg)
        { /* Code for VarType_string goes here */
            return Type.TString;
        }
        public Type visit(lang.Absyn.VarType_auto p, Object arg)
        { /* Code for VarType_auto goes here */
            return Type.TAuto;
        }

        @Override
        public Type visit(VarType_func p, Object arg) {
            return Type.TFunc;
        }

        @Override
        public Type visit(VarType_object p, Object arg) {
            return Type.TObject;
        }
    }
}


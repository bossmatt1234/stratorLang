package lang.Interpret;

import com.google.common.collect.Iterables;
import lang.Absyn.*;
import lang.Interpret.Exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static lang.Interpret.Operator.*;

public class FunctionalModeVisitor {
    BinaryOperation BinaryOperation = new BinaryOperation();
    UnaryOperation UnaryOperation = new UnaryOperation();

    public Val executeReduce(Val x, Val y, Env env, VFunc func){
        env.newBlock();
        env.extendEnvVar(func.val.args.get(0).ident,x );
        env.extendEnvVar(func.val.args.get(1).ident,y );
        execFunc(func.val.listStm,env, func.val, func.funcType);
        env.emptyBlock();
        return  func.val.returnVal;
    }

    public void execFunc(ListStm p, Env env, Function function, Type type){
        try{
            for (Stm x: p) {
                x.accept(new FunctionalModeVisitor.StmVisitor(), env);
            }
        }catch(Return val){
            if(new TypeChecker().check(type, val.returnVal, val.lineNum, val.colNum)){
                function.returnVal = val.returnVal;
                function.returnVal.type = type;
            }else{
                if(type == Type.TVoid){
                    throw new FuncReturnVoidException(val.lineNum, val.colNum, type.toString());
                }
                throw new FuncReturnTypeException(val.lineNum, val.colNum, type.toString());
            }
        }
    }

    public Val execObjectMethod(Val method, ObjectVar objectVar, Env env,
                                ListExp listExp, int lineNum, int columnNum){
        //  Block 1 - object variables
        env.contexts.addLast(objectVar.objectVars);
        // Block 2 - class methods
        env.newBlock();

        VFunc methodVal = (VFunc) method;
        Function funcToExec = methodVal.val;
        HashMap<String,Val> args = new HashMap<>();
        if(funcToExec.args.size() != listExp.size()){
            throw new ArgNumError(lineNum,columnNum,funcToExec.args.size(),listExp.size());
        }
        int i=0;
        for (Exp x: listExp) {
            Val newval = x.accept(new ExpVisitor(), env);
            if(!new TypeChecker().check(funcToExec.args.get(i).type, newval,lineNum,columnNum)){
                throw new TypeArgException(lineNum,columnNum,funcToExec.args.get(i).type,newval.getClass().getSimpleName());
            }
            args.put(funcToExec.args.get(i).ident, newval);
            i++;
        }
        // Block 3 - object call parameters
        env.newBlock();
        for (HashMap.Entry<String, Val> entry: args.entrySet() ){
            env.extendEnvVar(entry.getKey(),entry.getValue());
        }
        execFunc(funcToExec.listStm, env, funcToExec, methodVal.funcType);

        // Empty Block 3
        env.emptyBlock();
        //Empty Block 2
        env.emptyBlock();
        objectVar.objectVars = env.contexts.getLast();
        //Empty Block 1
        env.emptyBlock();
        return funcToExec.returnVal;
    }

    public Val execObjectSelect(ObjectVar objectVar, Env env, String objectVars, int lineNum, int columnNum){
        env.newBlock();
        env.contexts.getLast().putAll(objectVar.objectVars);
        Val returnVal = env.lookupVar(objectVars,lineNum,columnNum);
        env.emptyBlock();
        return returnVal;
    }

    public Val execObjectSelectListItem(ObjectVar objectVar, Env env, String objectVars, VInteger index, int lineNum, int columnNum){
        env.newBlock();
        env.contexts.getLast().putAll(objectVar.objectVars);
        VList list = (VList) env.lookupVar(objectVars,lineNum,columnNum);
        Val returnVal = Iterables.get(list.listVal,index.val);
        env.emptyBlock();
        return returnVal;
    }


    public Val doBinaryOperation (Val x, Val y, Operator op, int lineNum, int columnNum){
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        Val returnVal = BinaryOperation.checkInstance(x,y,op, leftRight);
        if(returnVal == null){
            throw new BinaryOpException(lineNum,columnNum,x.getClass().getSimpleName(),y.getClass().getSimpleName(),op);
        }
        return returnVal;

    }

    public Val doUnaryOperation (Val x,Operator op, int lineNum, int columnNum){
        Val returnVal = UnaryOperation.checkInstance(x,op);
        if(returnVal == null){
            throw new UnaryOpException(lineNum,columnNum,x.getClass().getSimpleName(),op);
        }
        return returnVal;
    }
    public FunctionalModeVisitor(PFunctionalModeStms p , Env env){
        for (Stm x: p.liststm_) {
            x.accept(new StmVisitor(), env);
        }
    }
    public class StmVisitor implements Stm.Visitor<Object,Env>
    {
        public Object visit(DefFun p, Env env)
        { /* Code for DefFun goes here */
            Function newFunc = new Function();
            Type funcType = p.functype_.accept(new FuncTypeVisitor(),env);
            //p.ident_;
            for (lang.Absyn.Arg x: p.listarg_) {
                newFunc.args.add(x.accept(new ArgVisitor(), env));
            }
            newFunc.listStm = p.liststm_;
            for(int i = 0 ; i<env.contexts.size();i++){
                newFunc.closure.putAll(new HashMap<>(env.contexts.get(i)));
            }
            VFunc val = new VFunc(newFunc, funcType);
            try{
                return env.extendEnvVar(p.ident_, val);
            }catch (ExtendEnvThrow err){
                throw new ExtendEnvException(p.line_num, p.col_num, err.ident);
            }
        }

        public Object visit(DefConstructor p, Env env)
        { /* Code for DefConstructor goes here */

            Function constructor = new Function();
            for (lang.Absyn.Arg x: p.listarg_) {
                constructor.args.add(x.accept(new ArgVisitor(), env));
            }
            constructor.listStm = p.liststm_;

            return env.extendEnvVar("$constructor$", new VFunc(constructor, Type.TVoid));
        }
        public Object visit(DefClass p, Env env)
        { /* Code for DefClass goes here */
            //p.ident_;
            throw new FuncModeObjectException(p.line_num,p.col_num,p.ident_);
        }
        public Object visit(DefClassInherits p, Env env)
        { /* Code for DefClassInherits goes here */
            throw new FuncModeObjectException(p.line_num,p.col_num,p.ident_1);
        }

        public Object visit(SPrint p, Env env)
        { /* Code for SPrint goes here */
            return System.out.printf("%s\n", p.exp_.accept(new ExpVisitor(), env).toString());
        }

        public Object visit(InitialiseStm p, Env env)
        { /* Code for InitialiseStm goes here */
            return p.stm_initialise_.accept(new Stm_InitialiseVisitor(), env);
        }

        public Object visit(DeclareStm p, Env env)
        { /* Code for DeclareStm goes here */
            return p.stm_declare_.accept(new Stm_DeclareVisitor(), env);
        }
        public Object visit(AssignStm p, Env env)
        { /* Code for AssignStm goes here */

            return p.stm_assign_.accept(new Stm_AssignVisitor(), env);
        }
        public Object visit(LoopStm p, Env env)
        { /* Code for LoopStm goes here */
            return p.stm_loop_.accept(new Stm_LoopVisitor(), env);
        }
        public Object visit(IncrnDecrmStm p, Env env)
        { /* Code for IncrnDecrmStm goes here */

            return p.stm_incrmdecrm_.accept(new Stm_IncrmDecrmVisitor(), env);
        }


        public Object visit(SCall p, Env env)
        { /* Code for SCall goes here */
            return p.exp_.accept(new ExpVisitor(), env);
        }
        public Object visit(SAppend p, Env env)
        { /* Code for SAppend goes here */
            //p.ident_;
            throw new FuncModeAppendException(p.line_num,p.col_num,p.ident_);
        }

        @Override
        public Object visit(SRemove p, Env env) {
            throw new FuncModeRemoveException(p.line_num,p.col_num,p.ident_);
        }

        public Object visit(SReturn p, Env env)
        { /* Code for SReturn goes here */
            throw new Return(p.exp_.accept(new ExpVisitor(), env), p.line_num, p.col_num);
        }

        public Object visit(SObjInit p, Env env)
        { /* Code for SObjInit goes here */
            //p.ident_1;
            //p.ident_2;
            ObjectVar newObject = new ObjectVar();
            ObjectDef objectDef = env.lookupDef(p.ident_2, p.line_num, p.col_num);
            newObject.ofClass = objectDef.className;
            newObject.objectVars.putAll(objectDef.defVals);
            if(objectDef.isConstructorSet){
                execObjectMethod(objectDef.constructor,newObject,env, p.listexp_,p.line_num,p.col_num);
            }
            return env.extendEnvVar(p.ident_1, new VObject(newObject));
        }

        public Object visit(SConstInit p, Env env)
        { /* Code for SConstInit goes here */
            p.vartype_.accept(new VarTypeVisitor(), env);
            //p.ident_;
            p.exp_.accept(new ExpVisitor(), env);
            return null;
        }

        public Object visit(SBreak p, Env env)
        { /* Code for SBreak goes here */
            throw new Break();
        }

        @Override
        public Object visit(SContinue p, Env arg) {
            throw new Continue();
        }

        public Object visit(IfS p, Env env)
        { /* Code for IfS goes here */
            return p.if_stm_.accept(new If_StmVisitor(), env);
        }

        public Object visit(Block p, Env env)
        { /* Code for Block goes here */
            env.newBlock();

            for (Stm x: p.liststm_) {
                x.accept(new StmVisitor(), env);
            }
            env.emptyBlock();

            return env;
        }
    }

    public class ItemVisitor implements Item.Visitor<Val,Env>
    {
        public Val visit(LstItem p, Env env)
        { /* Code for LstItem goes here */
            return p.exp_.accept(new ExpVisitor(), env);
        }
    }
    public class Stm_InitialiseVisitor implements Stm_Initialise.Visitor<Object,Env>
    {
        public Object visit(SInit p, Env env)
        { /* Code for SInit goes here */
            Type t = p.vartype_.accept(new VarTypeVisitor(), env);
            //p.ident_;
            Val val =p.exp_.accept(new ExpVisitor(), env);
            if(new TypeChecker().check(t,val, p.line_num, p.col_num)){
                if(val instanceof VList list){
                    return env.extendEnvVar(p.ident_, new VList(new ArrayList<>(list.listVal),t) );
                }
                try{
                    return env.extendEnvVar(p.ident_, val);
                }catch (ExtendEnvThrow err){
                    throw new ExtendEnvException(p.line_num, p.col_num, err.ident);
                }
            }else{
                throw new TypeErrException(p.line_num, p.col_num,t,val.getClass().getSimpleName());
            }
        }
    }
    public class Stm_DeclareVisitor implements Stm_Declare.Visitor<Object,Env>
    {
        public Object visit(SDecl p, Env env)
        { /* Code for SDecl goes here */
            Type t = p.vartype_.accept(new VarTypeVisitor(), env);
            //p.ident_;
            try{
                return env.extendEnvVar(p.ident_, new TypeChecker().returnValOfType(t,p.line_num,p.col_num));
            }catch (ExtendEnvThrow err){
                throw new ExtendEnvException(p.line_num, p.col_num, err.ident);
            }
        }
    }
    public static class Stm_AssignVisitor implements Stm_Assign.Visitor<Object,Env>
    {
        public Object visit(SAssign p, Env env)
        { /* Code for SAssign goes here */
            //p.ident_;
            throw new FuncModeAssignException(p.line_num,p.col_num, p.ident_);
        }
    }
    public class Stm_IncrmDecrmVisitor implements Stm_IncrmDecrm.Visitor<Object,Env>
    {
        public Object visit(SIncrmDecrm p, Env env)
        { /* Code for SIncrmDecrm goes here */
            Val x = env.lookupVar(p.ident_,p.line_num, p.col_num);
            Operator op = p.incrmdecrm_op_.accept(new IncrmDecrm_OpVisitor(), env);
            return env.updateVar(p.ident_, doUnaryOperation(x,op, p.line_num,p.col_num));
        }
    }

    public class Stm_LoopVisitor implements Stm_Loop.Visitor<Env,Env>
    {
        public Env visit(SWhile p, Env env)
        { /* Code for SWhile goes here */
            throw new FuncModeLoopException(p.line_num,p.col_num);
        }

        @Override
        public Env visit(SPLoopIdent p, Env env) {
            throw new FuncModeLoopException(p.line_num,p.col_num);
        }

        @Override
        public Env visit(SPLoopList p, Env env) {
            throw new FuncModeLoopException(p.line_num,p.col_num);
        }

        @Override
        public Env visit(SPLoopRangeStart p, Env env) {
            throw new FuncModeLoopException(p.line_num,p.col_num);
        }

        @Override
        public Env visit(SPLoopRangeStartStop p, Env env) {
            throw new FuncModeLoopException(p.line_num,p.col_num);
        }

        @Override
        public Env visit(SPLoopRangeStartStopEnd p, Env env) {
            throw new FuncModeLoopException(p.line_num,p.col_num);
        }

        public Env visit(SCLoop p, Env env)
        { /* Code for SCLoop goes here */
            throw new FuncModeLoopException(p.line_num,p.col_num);
        }

        @Override
        public Env visit(SCLoopAssign p, Env env) {
            throw new FuncModeLoopException(p.line_num,p.col_num);
        }
    }

    public class If_StmVisitor implements If_Stm.Visitor<Env,Env>
    {
        public Env visit(SIf p, Env env)
        { /* Code for SIf goes here */
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            if (condition.val){
                for (Stm x: p.liststm_) {
                    x.accept(new StmVisitor(), env);
                }
            }
            return env;
        }
        public Env visit(SIfElse p, Env env)
        { /* Code for SIfElse goes here */
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            if(condition.val){
                for (Stm x: p.liststm_1) {
                    x.accept(new StmVisitor(), env);
                }
            }else{
                for (Stm x: p.liststm_2) {
                    x.accept(new StmVisitor(), env);
                }
            }
            return env;
        }
        public Env visit(SIfElseIf p, Env env)
        { /* Code for SIfElseIf goes here */
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            if (condition.val){
                for (Stm x: p.liststm_) {
                    x.accept(new StmVisitor(), env);
                }
            }else{
                p.if_stm_.accept(new If_StmVisitor(), env);
            }
            return env;
        }
    }

    public static class ArgVisitor implements lang.Absyn.Arg.Visitor<Arg,Env>
    {
        public Arg visit(ArgDecl p, Env env)
        { /* Code for ArgDecl goes here */

            //p.ident_;
            return new Arg(p.vartype_.accept(new VarTypeVisitor(), env), p.ident_);
        }
    }

    public class ExpVisitor implements Exp.Visitor<Val,Env>
    {
        public Val visit(EInt p, Env env)
        { /* Code for EInt goes here */
            //p.integer_;
            return new VInteger(p.integer_);
        }
        public Val visit(ENegInt p, Env env)
        { /* Code for EInt goes here */
            //p.integer_;
            return new VInteger(-p.integer_);
        }
        public Val visit(EChar p, Env arg) {
            return new VChar(p.char_);
        }

        public Val visit(EDouble p, Env env)
        { /* Code for EDouble goes here */
            //p.double_;
            return new VDouble(p.double_);
        }
        public Val visit(ENegDouble p, Env env)
        { /* Code for EDouble goes here */
            //p.double_;
            return new VDouble(- p.double_);
        }
        public Val visit(EString p, Env env)
        { /* Code for EString goes here */
            //p.string_;
            return new VString(p.string_);
        }
        public Val visit(ETrue p, Env env)
        { /* Code for ETrue goes here */
            return new VBool(true);
        }
        public Val visit(EFalse p, Env env)
        { /* Code for EFalse goes here */
            return new VBool(false);
        }
        public Val visit(EId p, Env env)
        { /* Code for EId goes here */
            //p.ident_;
            return env.lookupVar(p.ident_,p.line_num, p.col_num);
        }
        public Val visit(EListItem p, Env env)
        { /* Code for EListItem goes here */
            VInteger index = (VInteger) p.exp_.accept(new ExpVisitor(), env);
            VList val = (VList) env.lookupVar(p.ident_,p.line_num, p.col_num);
            ArrayList<Val> list = val.listVal ;
            return Iterables.get(list,index.val);
        }

        @Override
        public Val visit(EListWith p, Env env) {
            VList lookUp = (VList) env.lookupVar(p.ident_, p.line_num, p.col_num);
            Val val = p.exp_.accept(new ExpVisitor(),env);
            VList newList = new VList(new ArrayList<>(lookUp.listVal),lookUp.itemType);
            newList.listVal.add(val);
            return newList;
        }

        @Override
        public Val visit(EListWithout p, Env env) {
            VList lookUp = (VList) env.lookupVar(p.ident_, p.line_num, p.col_num);
            VInteger val = (VInteger) p.exp_.accept(new ExpVisitor(),env);
            VList newList = new VList(new ArrayList<>(lookUp.listVal),lookUp.itemType);
            newList.listVal.remove(val.val);
            return newList;
        }


        @Override
        public Val visit(EList p, Env env) {
            ArrayList<Val> list = new ArrayList<>();
            if(p.listitem_.size() != 0){
                for(Item item: p.listitem_){
                    Val val = item.accept(new ItemVisitor(),env);
                    val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                    list.add(val);
                }
            }
            return new VList(list,list.get(0).type);
        }

        @Override
        public Val visit(EListSize p, Env env) {
            VList list = (VList) env.lookupVar(p.ident_,p.line_num, p.col_num);
            return new VInteger(list.listVal.size());
        }

        @Override
        public Val visit(EInput p, Env arg) {
            Scanner prompt = new Scanner(System.in);
            return new TypeChecker().inferInput(prompt, p.line_num, p.col_num);
        }

        @Override
        public Val visit(EStrLength p, Env env) {
            VString string = (VString) env.lookupVar(p.ident_,p.line_num, p.col_num);
            return new VInteger(string.val.length());
        }

        @Override
        public Val visit(ERand p, Env arg) {
            Random rand = new Random();
            return new VInteger(rand.nextInt(p.integer_));
        }

        @Override
        public Val visit(ETypeCast p, Env env) {
            Val val =  env.lookupVar(p.ident_,p.line_num, p.col_num);
            return p.typecast_.accept(new TypeCastVisitor(),val);
        }

        @Override
        public Val visit(ELambda p, Env env) {
            Function lambda = new Function();
            for (lang.Absyn.Arg x: p.listarg_) {
                lambda.args.add(x.accept(new ArgVisitor(), env));
            }
            lambda.listStm = p.liststm_;
            for(int i = 0 ; i<env.contexts.size();i++){
                lambda.closure.putAll(env.contexts.get(i));
            }

            return new VFunc(lambda, Type.TAuto);
        }

        @Override
        public Val visit(EMapIdent p, Env env) {
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 1){
                throw new MapErrException(p.line_num, p.col_num);
            }
            VList list = (VList) env.lookupVar(p.ident_,p.line_num, p.col_num);
            ArrayList<Val> returnList = new ArrayList<>();
            for(Val val: list.listVal){
                env.newBlock();
                env.extendEnvVar(func.val.args.get(0).ident, val);
                execFunc(func.val.listStm, env,func.val, func.funcType);
                returnList.add(func.val.returnVal);

                env.emptyBlock();
            }
            return new VList(returnList, list.itemType);
        }

        @Override
        public Val visit(EMapList p, Env env) {
            ArrayList<Val> list = new ArrayList<>();
            for(Item item: p.listitem_){
                Val val = item.accept(new ItemVisitor(),env);
                val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                list.add(val);

            }
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 1){
                throw new MapErrException(p.line_num, p.col_num);
            }
            ArrayList<Val> returnList = new ArrayList<>();
            for(Val val: new VList(list,Type.TAuto).listVal){
                env.newBlock();
                env.extendEnvVar(func.val.args.get(0).ident, val);
                execFunc(func.val.listStm, env,func.val, func.funcType);
                returnList.add(func.val.returnVal);

                env.emptyBlock();
            }
            return new VList(returnList,list.get(0).type);
        }

        @Override
        public Val visit(EFilterIdent p, Env env) {
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 1){
                throw new FilterErrException(p.line_num, p.col_num);
            }
            VList list = (VList) env.lookupVar(p.ident_,p.line_num, p.col_num);
            ArrayList<Val> returnList = new ArrayList<>();
            for(Val val: list.listVal){
                env.newBlock();
                env.extendEnvVar(func.val.args.get(0).ident, val);
                execFunc(func.val.listStm, env,func.val, Type.TBoolean);
                VBool condition = (VBool)func.val.returnVal;
                if(condition.val){
                    returnList.add(val);
                }
                env.emptyBlock();
            }
            return new VList(returnList,list.itemType);
        }

        @Override
        public Val visit(EFilterList p, Env env) {
            ArrayList<Val> list = new ArrayList<>();
            for(Item item: p.listitem_){
                Val val = item.accept(new ItemVisitor(),env);
                val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                list.add(val);

            }
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 1){
                throw new FilterErrException(p.line_num, p.col_num);
            }
            ArrayList<Val> returnList = new ArrayList<>();
            for(Val val: new VList(list,Type.TAuto).listVal){
                env.newBlock();
                env.extendEnvVar(func.val.args.get(0).ident, val);
                execFunc(func.val.listStm, env,func.val, Type.TBoolean);
                VBool condition = (VBool)func.val.returnVal;
                if(condition.val){
                    returnList.add(val);
                }
                env.emptyBlock();
            }
            return new VList(returnList,list.get(0).type);
        }

        @Override
        public Val visit(EReduceIdent p, Env env) {
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 2){
                throw new ReduceErrException(p.line_num, p.col_num);
            }
            VList list = (VList) env.lookupVar(p.ident_,p.line_num, p.col_num);
            Val newVal = new TypeChecker().returnValOfType(list.listVal.get(0).type, p.line_num, p.col_num);
            newVal.type = new TypeChecker().returnType(list.listVal.get(0),p.line_num, p.col_num);
            return list.listVal.stream().reduce(newVal,((val, val2) -> executeReduce(val,val2,env,func) ));
        }

        @Override
        public Val visit(EReduceList p, Env env) {
            ArrayList<Val> listArr = new ArrayList<>();
            for(Item item: p.listitem_){
                Val val = item.accept(new ItemVisitor(),env);
                val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                listArr.add(val);
            }
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 2){
                throw new ReduceErrException(p.line_num, p.col_num);
            }
            VList list = new VList(listArr,listArr.get(0).type);
            Val newVal = new TypeChecker().returnValOfType(list.listVal.get(0).type,p.line_num, p.col_num);
            newVal.type = new TypeChecker().returnType(list.listVal.get(0),p.line_num, p.col_num);
            return list.listVal.stream().reduce(newVal,((val, val2) -> executeReduce(val,val2,env,func) ));
        }

        public Val visit(ESelect p, Env env)
        { /* Code for ESelect goes here */
            //p.ident_1;
            //p.ident_2;
            VObject val = (VObject) env.lookupVar(p.ident_1,p.line_num, p.col_num);
            ObjectVar objectVar = val.val;
            return execObjectSelect(
                    objectVar,
                    env, p.ident_2,
                    p.line_num, p.col_num);
        }
        @Override
        public Val visit(ESelectListItem p, Env env) {
            VObject val = (VObject) env.lookupVar(p.ident_1,p.line_num, p.col_num);
            ObjectVar objectVar = val.val;
            return execObjectSelectListItem(
                    objectVar,
                    env, p.ident_2,
                    (VInteger) p.exp_.accept(new ExpVisitor(),env),
                    p.line_num, p.col_num);
        }

        public Val visit(ECall p, Env env)
        { /* Code for ECall goes here */
            //p.ident_;
            VFunc val = (VFunc) env.lookupVar(p.ident_,p.line_num, p.col_num);
            Function funcToExec = val.val;
            if(funcToExec.args.size() != p.listexp_.size()){
                throw new ArgNumError(p.line_num,p.col_num,funcToExec.args.size(),p.listexp_.size());
            }
            // Block 1 - Function variables
            HashMap<String,Val> args = new HashMap<>();
            int i=0;
            for (Exp x: p.listexp_) {
                Val newval = x.accept(new ExpVisitor(), env);

                if(!new TypeChecker().check(funcToExec.args.get(i).type, newval, p.line_num, p.col_num)){
                    throw new TypeArgException(p.line_num, p.col_num,funcToExec.args.get(i).type, newval.getClass().getSimpleName() );
                }
                args.put(funcToExec.args.get(i).ident, newval);
                i++;
            }
            env.contexts.addLast(funcToExec.closure);
            // Block 2 - function call parameters
            env.newBlock();
            for (HashMap.Entry<String, Val> entry: args.entrySet() ){
                env.extendEnvVar(entry.getKey(),entry.getValue());
            }
            execFunc(funcToExec.listStm, env, funcToExec, val.funcType);
            // Empty Block 2
            env.emptyBlock();
            funcToExec.closure = env.contexts.getLast();
            // Empty Block 1
            env.emptyBlock();
            return funcToExec.returnVal;
        }
        public Val visit(EObjCall p, Env env)
        { /* Code for EObjCall goes here */
            //p.ident_1;
            //p.ident_2;
            VObject val = (VObject) env.lookupVar(p.ident_1,p.line_num, p.col_num);
            ObjectVar objectVar = val.val;
            ObjectDef objectDef = env.lookupDef(objectVar.ofClass,p.line_num, p.col_num);
            Val method = objectDef.lookUpMethod(p.ident_2);
            if(method == null){
                throw new NullMethodException(p.line_num,p.col_num,p.ident_2,objectVar.ofClass,objectDef.defMethods);
            }
            return execObjectMethod(
                    env.lookupDef(objectVar.ofClass,p.line_num, p.col_num).lookUpMethod(p.ident_2), objectVar,
                    env, p.listexp_, p.line_num,p.col_num);
        }
        public Val visit(EPow p, Env env)
        { /* Code for EPow goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, EXPONENT, p.line_num, p.col_num);
        }
        public Val visit(EMul p, Env env)
        { /* Code for EMul goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, MULTIPLY,p.line_num, p.col_num);
        }
        public Val visit(EDiv p, Env env)
        { /* Code for EDiv goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, DIVIDE,p.line_num, p.col_num);
        }

        @Override
        public Val visit(EMod p, Env env) {
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, MOD,p.line_num, p.col_num);
        }

        public Val visit(EAdd p, Env env)
        { /* Code for EAdd goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, ADD,p.line_num, p.col_num);
        }
        public Val visit(ESub p, Env env)
        { /* Code for ESub goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, SUBTRACT,p.line_num, p.col_num);
        }
        public Val visit(ELt p, Env env)
        { /* Code for ELt goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, LESS_THAN,p.line_num, p.col_num);
        }
        public Val visit(EGt p, Env env)
        { /* Code for EGt goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, GREATER_THAN,p.line_num, p.col_num);
        }
        public Val visit(ELEq p, Env env)
        { /* Code for ELEq goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, LESS_THAN_EQUALS,p.line_num, p.col_num);
        }
        public Val visit(EGEq p, Env env)
        { /* Code for EGEq goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, GREATER_THAN_EQUALS,p.line_num, p.col_num);
        }
        public Val visit(EEq p, Env env)
        { /* Code for EEq goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, EQUALS,p.line_num, p.col_num);
        }
        public Val visit(ENEq p, Env env)
        { /* Code for ENEq goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, NOT_EQUALS,p.line_num, p.col_num);
        }
        public Val visit(EAnd p, Env env)
        { /* Code for EAnd goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, AND,p.line_num, p.col_num);
        }
        public Val visit(EOr p, Env env)
        { /* Code for EOr goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, OR,p.line_num, p.col_num);
        }

    }



    public static class Assign_OpVisitor implements Assign_Op.Visitor<Operator,Env>
    {
        public Operator visit(Assign p, Env arg)
        { /* Code for Assign goes here */
            return ASSIGN;
        }
        public Operator visit(AssignMul p, Env arg)
        { /* Code for AssignMul goes here */
            return MULTIPLY;
        }
        public Operator visit(AssignDiv p, Env arg)
        { /* Code for AssignDiv goes here */
            return DIVIDE;
        }
        public Operator visit(AssignMod p, Env arg)
        { /* Code for AssignMod goes here */
            return MOD;
        }
        public Operator visit(AssignAdd p, Env arg)
        { /* Code for AssignAdd goes here */
            return ADD;
        }
        public Operator visit(AssignSub p, Env arg)
        { /* Code for AssignSub goes here */
            return SUBTRACT;
        }
    }
    public static class IncrmDecrm_OpVisitor implements IncrmDecrm_Op.Visitor<Operator,Env>
    {
        public Operator visit(Increment p, Env env)
        { /* Code for Increment goes here */
            return INCREMENT;
        }
        public Operator visit(Decrement p, Env env)
        { /* Code for Decrement goes here */
            return DECREMENT;
        }
    }
    public static class FuncTypeVisitor implements FuncType.Visitor<Type,Object>
    {
        public Type visit(FuncType_void p, Object arg)
        { /* Code for FuncType_void goes here */
            return Type.TVoid;
        }
        public Type visit(FuncTypeVarType p, Object arg)
        { /* Code for FuncTypeVarType goes here */
            return p.vartype_.accept(new VarTypeVisitor(), arg);
        }
    }
    public static class VarTypeVisitor implements VarType.Visitor<Type,Object>
    {
        public Type visit(VarType_int p, Object arg)
        { /* Code for VarType_int goes here */
            return Type.TInt;
        }
        public Type visit(VarType_double p, Object arg)
        { /* Code for VarType_double goes here */
            return Type.TDouble;
        }
        public Type visit(VarType_bool p, Object arg)
        { /* Code for VarType_boolean goes here */
            return Type.TBoolean;
        }
        public Type visit(VarType_char p, Object arg)
        { /* Code for VarType_char goes here */
            return Type.TChar;
        }
        public Type visit(VarType_string p, Object arg)
        { /* Code for VarType_string goes here */
            return Type.TString;
        }
        public Type visit(VarType_auto p, Object arg)
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
    public static class TypeCastVisitor implements TypeCast.Visitor<Val,Val>
    {
        public Val visit(TypeCast_toInt p, Val arg)
        { /* Code for TypeCast_toInt goes here */
            if(arg instanceof VInteger val){
                return val;
            } else if (arg instanceof VDouble val) {
                return new VInteger((int) val.val);
            } else if(arg instanceof VString val){
                return new VInteger(Integer.parseInt(val.val));
            } else if (arg instanceof VChar val) {
                return new VInteger(Integer.parseInt(String.valueOf(val.val)));
            } else if (arg instanceof VBool val) {
                return new VInteger(val.val ? 1 : 0);
            } else{
                throw new RuntimeException("Type cast error");
            }
        }
        public Val visit(TypeCast_toDouble p, Val arg)
        { /* Code for TypeCast_toDouble goes here */
            if(arg instanceof VInteger val){
                return new VDouble((double) val.val);
            } else if (arg instanceof VDouble val) {
                return val;
            } else if(arg instanceof VString val){
                return new VDouble(Double.parseDouble(val.val));
            } else if (arg instanceof VChar val) {
                return new VDouble(Double.parseDouble(String.valueOf(val.val)));
            } else if (arg instanceof VBool val) {
                return new VDouble(val.val ? 1.0 : 0);
            } else{
                throw new RuntimeException("Type cast error");
            }
        }
        public Val visit(TypeCast_toBool p, Val arg)
        { /* Code for TypeCast_toBoolean goes here */
            if(arg instanceof VInteger val){
                return new VBool(val.val != 0);
            }else if(arg instanceof VString val){
                return new VBool(Boolean.parseBoolean(val.val));
            }else if(arg instanceof VChar val){
                return new VBool(Boolean.parseBoolean(String.valueOf(val.val)));
            }else{
                throw new RuntimeException("Type cast error");
            }
        }
        public Val visit(TypeCast_toChar p, Val arg)
        { /* Code for TypeCast_toChar goes here */
            if(arg instanceof VInteger val){
                return new VChar((char) val.val);
            }else if(arg instanceof VString val){
                throw new RuntimeException("Type cast error");
            }else if(arg instanceof VDouble val){
                throw new RuntimeException("Type cast error");
            }else if(arg instanceof VBool val){
                return new VChar(val.val ? '1':'0');
            }else if(arg instanceof VChar val){
                return val;
            } else{
                throw new RuntimeException("Type cast error");
            }
        }
        public Val visit(TypeCast_toString p, Val arg)
        { /* Code for TypeCast_toString goes here */
            if(arg instanceof VInteger val){
                return new VString(String.valueOf(val.val));
            }else if(arg instanceof VString val){
                return val;
            }else if(arg instanceof VDouble val){
                return new VString(String.valueOf(val.val));
            }else if(arg instanceof VBool val){
                return new VString(String.valueOf(val.val));
            }else if(arg instanceof VChar val){
                return new VString(String.valueOf(val.val));
            } else{
                throw new RuntimeException("Type cast error");
            }
        }
    }
}

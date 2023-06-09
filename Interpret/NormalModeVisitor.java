package lang.Interpret;

import com.google.common.collect.Iterables;
import lang.Absyn.*;
import lang.Interpret.Exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

import static lang.Interpret.Operator.*;
import static lang.Interpret.Operator.DECREMENT;

public class NormalModeVisitor {
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
                x.accept(new NormalModeVisitor.StmVisitor(), env);
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

        }catch(Continue val) {
            throw new LoopCtrlStmException(val.lineNum, val.colNum, "Continue");
        }catch(Break val) {
            throw new LoopCtrlStmException(val.lineNum, val.colNum, "Break");
        }catch(Throw val) {
            throw new ThrowErrorException(val.lineNum, val.colNum);
        }
    }

    public Val execObjectMethod(Val method, ObjectVar objectVar, ObjectDef def, Env env,
                                ListExp listExp, int lineNum, int columnNum){
        //  Block 1 - object variables
        env.contexts.addLast(objectVar.objectVars);
        // Block 2 - class methods
        env.contexts.addLast(def.defMethods);

        VFunc methodVal = (VFunc) method;
        Function funcToExec = methodVal.val;
        HashMap<String,Val> args = new HashMap<>();
        if(funcToExec.args.size() != listExp.size()){
            throw new ArgNumError(lineNum,columnNum,funcToExec.args.size(),listExp.size());
        }
        int i=0;
        for (lang.Absyn.Exp x: listExp) {
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
    public NormalModeVisitor(PStms p ,Env env){
        for (lang.Absyn.Stm x: p.liststm_) {
            x.accept(new StmVisitor(), env);
        }
    }
    public class StmVisitor implements lang.Absyn.Stm.Visitor<Object,Env>
    {
        public Object visit(lang.Absyn.DefFun p, Env env)
        { /* Code for DefFun goes here */
            Function newFunc = new Function();
            Type funcType = p.functype_.accept(new FuncTypeVisitor(),env);
            //p.ident_;
            for (lang.Absyn.Arg x: p.listarg_) {
                newFunc.args.add(x.accept(new ArgVisitor(), env));
            }
            newFunc.listStm = p.liststm_;
            if(env.getScopeType() == 1){
                newFunc.closure.putAll(new HashMap<>(env.contexts.getLast()));
            }
            VFunc val = new VFunc(newFunc, funcType);
            try{
                return env.extendEnvVar(p.ident_, val);
            }catch (ExtendEnvThrow err){
                throw new ExtendEnvException(p.line_num, p.col_num, err.ident);
            }
        }

        public Object visit(lang.Absyn.DefConstructor p, Env env)
        { /* Code for DefConstructor goes here */
            Function constructor = new Function();
            for (lang.Absyn.Arg x: p.listarg_) {
                constructor.args.add(x.accept(new ArgVisitor(), env));
            }
            constructor.listStm = new ListStm();
            for (Stm x: p.liststm_){
                if(x instanceof SReturn){
                    throw new ClassConstructorStmException(p.line_num,p.col_num,"Return");
                } else if (x instanceof SBreak) {
                    throw new ClassConstructorStmException(p.line_num,p.col_num,"Break");
                } else if (x instanceof SContinue) {
                    throw new ClassConstructorStmException(p.line_num,p.col_num,"Continue");
                }
                constructor.listStm.add(x);
            }
            env.extendEnvVar("$constructor_name$", new VString(p.ident_));
            return env.extendEnvVar("$constructor$", new VFunc(constructor, Type.TVoid));
        }
        public Object visit(lang.Absyn.DefClass p, Env env)
        {   ObjectDef objectDef = new ObjectDef(p.ident_);
            for (lang.Absyn.Stm x: p.liststm_) {
                env.newBlock();
                if(x instanceof SPrint)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"Print");
                else if(x instanceof IfS)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"If/Else");
                else if(x instanceof LoopStm)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"Loop");
                else if(x instanceof Block)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"Block");
                else if(x instanceof STryCatch || x instanceof STryCatchFinally)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"Try/Catch");
                else if(x instanceof DefFun){
                    x.accept(new StmVisitor(), env);
                    objectDef.defMethods.putAll(env.contexts.getLast());
                }
                else if(x instanceof DefConstructor){
                    x.accept(new StmVisitor(), env);
                    VString constructorName = (VString) env.contexts.getLast().get("$constructor_name$");
                    if(Objects.equals(constructorName.val, objectDef.className)){
                        objectDef.constructorEnable();
                        objectDef.setConstructor(env.contexts.getLast().get("$constructor$"));
                    }else{
                        throw new ClassConstructorNameError(p.line_num, p.col_num, constructorName.val, objectDef.className);
                    }
                } else{
                    x.accept(new StmVisitor(), env);
                    objectDef.defVals.putAll(env.contexts.getLast());
                }
                env.emptyBlock();
            }
            return env.objectDefs.getLast().put(p.ident_,objectDef);
        }
        public Object visit(lang.Absyn.DefClassInherits p, Env env)
        { /* Code for DefClassInherits goes here */
            //p.ident_1;
            //p.ident_2;
            ObjectDef objectDef = new ObjectDef(p.ident_1);
            ObjectDef fromClass = env.lookupDef(p.ident_2,p.line_num, p.col_num);
            objectDef.defVals.putAll(fromClass.defVals);
            objectDef.defMethods.putAll(fromClass.defMethods);
            if(fromClass.isConstructorSet){
                objectDef.setConstructor((fromClass.constructor));
                objectDef.constructorEnable();
            }
            for (lang.Absyn.Stm x: p.liststm_) {
                env.newBlock();
                if(x instanceof SPrint)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"Print");
                else if(x instanceof IfS)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"If/Else");
                else if(x instanceof LoopStm)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"Loop");
                else if(x instanceof Block)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"Block");
                else if(x instanceof STryCatch || x instanceof STryCatchFinally)
                    throw new ClassInitErrorException(p.line_num,p.col_num,"Try/Catch");
                else if(x instanceof DefFun){
                    x.accept(new StmVisitor(), env);
                    objectDef.defMethods.putAll(env.contexts.getLast());
                }
                else if(x instanceof DefConstructor){
                    x.accept(new StmVisitor(), env);
                    VString constructorName = (VString) env.contexts.getLast().get("$constructor_name$");
                    if(Objects.equals(constructorName.val, objectDef.className)){
                        objectDef.constructorEnable();
                        objectDef.setConstructor(env.contexts.getLast().get("$constructor$"));
                    }else{
                        throw new ClassConstructorNameError(p.line_num, p.col_num, constructorName.val, objectDef.className);
                    }
                }
                else{
                    x.accept(new StmVisitor(), env);
                    objectDef.defVals.putAll(env.contexts.getLast());
                }
                env.emptyBlock();
            }

            return env.objectDefs.getLast().put(p.ident_1,objectDef);
        }

        @Override

        public Object visit(STryCatch p, Env env) {
            int envCurrentSize = env.contexts.size();
            try{
                env.newBlock();
                for (lang.Absyn.Stm x: p.liststm_1) {
                    x.accept(new StmVisitor(), env);
                }
                env.emptyBlock();
            }catch(Throw stm){
                env.emptyToInitialBlock(envCurrentSize); env.newBlock();
                env.extendEnvVar(p.ident_,stm.returnVal);
                for (lang.Absyn.Stm x: p.liststm_2) {
                    x.accept(new StmVisitor(), env);
                }
                env.emptyBlock();
            } catch (CommonError e) {
                env.emptyToInitialBlock(envCurrentSize); env.newBlock();
                env.extendEnvVar(p.ident_, new VString(e.getMessage()));
                for (Stm x : p.liststm_2) {
                    x.accept(new StmVisitor(), env);
                }
                env.emptyBlock();
            }
            return env;
        }

        @Override

        public Object visit(STryCatchFinally p, Env env) {
            int envCurrentSize = env.contexts.size();
            try{
                env.newBlock();
                for (lang.Absyn.Stm x: p.liststm_1) {
                    x.accept(new StmVisitor(), env);
                }
                env.emptyBlock();
            }catch(Throw stm){
                env.emptyToInitialBlock(envCurrentSize); env.newBlock();
                env.extendEnvVar(p.ident_,stm.returnVal);
                for (lang.Absyn.Stm x: p.liststm_2) {
                    x.accept(new StmVisitor(), env);
                }
                env.emptyBlock();
            } catch (CommonError e) {
                env.emptyToInitialBlock(envCurrentSize); env.newBlock();
                env.extendEnvVar(p.ident_, new VString(e.getMessage()));
                for (Stm x : p.liststm_2) {
                    x.accept(new StmVisitor(), env);
                }
                env.emptyBlock();
            } finally {
                env.newBlock();
                for (Stm x : p.liststm_3) {
                    x.accept(new StmVisitor(), env);
                }
                env.emptyBlock();
            }
            return env;
        }

        public Object visit(lang.Absyn.SPrint p, Env env)
        { /* Code for SPrint goes here */
            return System.out.printf("%s\n", p.exp_.accept(new ExpVisitor(), env).toString());
        }

        public Object visit(lang.Absyn.InitialiseStm p, Env env)
        { /* Code for InitialiseStm goes here */
            return p.stm_initialise_.accept(new Stm_InitialiseVisitor(), env);
        }

        public Object visit(lang.Absyn.DeclareStm p, Env env)
        { /* Code for DeclareStm goes here */
            return p.stm_declare_.accept(new Stm_DeclareVisitor(), env);
        }
        public Object visit(lang.Absyn.AssignStm p, Env env)
        { /* Code for AssignStm goes here */

            return p.stm_assign_.accept(new Stm_AssignVisitor(), env);
        }
        public Object visit(lang.Absyn.LoopStm p, Env env)
        { /* Code for LoopStm goes here */
            return p.stm_loop_.accept(new Stm_LoopVisitor(), env);
        }
        public Object visit(lang.Absyn.IncrnDecrmStm p, Env env)
        { /* Code for IncrnDecrmStm goes here */

            return p.stm_incrmdecrm_.accept(new Stm_IncrmDecrmVisitor(), env);
        }


        public Object visit(lang.Absyn.SCall p, Env env)
        { /* Code for SCall goes here */
            return p.exp_.accept(new ExpVisitor(), env);
        }
        public Object visit(lang.Absyn.SAppend p, Env env)
        { /* Code for SAppend goes here */
            //p.ident_;
            return ((VList) env.lookupVar(p.ident_,p.line_num, p.col_num)).listVal.add(p.exp_.accept(new ExpVisitor(), env));
        }

        @Override
        public Object visit(SRemove p, Env env) {
            VInteger val = (VInteger) p.exp_.accept(new ExpVisitor(), env);
            return ((VList) env.lookupVar(p.ident_,p.line_num, p.col_num)).listVal.remove(val.val);
        }

        @Override
        public Object visit(SSet p, Env env) {
            VList list = (VList) env.lookupVar(p.ident_,p.line_num, p.col_num);
            VInteger index = (VInteger) p.exp_1.accept(new ExpVisitor(), env);
            Val val = p.exp_2.accept(new ExpVisitor(), env);
            Type t = new TypeChecker().returnType(val, p.line_num, p.col_num);
            if(t != list.itemType){
                throw new TypeArgException(p.line_num, p.col_num, list.itemType, val.getClass().getSimpleName());
            }
            return list.listVal.set(index.val,val);
        }

        public Object visit(lang.Absyn.SReturn p, Env env)
        {
            throw new Return(p.exp_.accept(new ExpVisitor(), env), p.line_num, p.col_num);
        }

        public Object visit(lang.Absyn.SObjInit p, Env env)
        {
            ObjectVar newObject = new ObjectVar();
            ObjectDef objectDef = env.lookupDef(p.ident_2, p.line_num, p.col_num);
            newObject.ofClass = objectDef.className;
            newObject.objectVars = new HashMap<>(objectDef.defVals);
            for(Map.Entry<String, Val> entry: newObject.objectVars.entrySet()){
                if(entry.getValue() instanceof VList val){
                    newObject.objectVars.put(entry.getKey(), new VList(new ArrayList<>(val.listVal),val.itemType));
                }
                newObject.objectVars.put(entry.getKey(), entry.getValue());
            }
            if(objectDef.isConstructorSet){
                execObjectMethod(objectDef.constructor,newObject, objectDef, env, p.listexp_,p.line_num,p.col_num);
            }
            return env.extendEnvVar(p.ident_1, new VObject(newObject));
        }

        @Override
        public Object visit(lang.Absyn.SBreak p, Env env)
        { /* Code for SBreak goes here */
            throw new Break(p.line_num, p.col_num);
        }

        @Override
        public Object visit(SContinue p, Env env) {
            throw new Continue(p.line_num, p.col_num);
        }

        @Override
        public Object visit(SThrow p, Env env) {
            throw new Throw(p.exp_.accept(new ExpVisitor(),env),p.line_num,p.col_num);
        }

        public Object visit(lang.Absyn.IfS p, Env env)
        { /* Code for IfS goes here */
            return p.if_stm_.accept(new If_StmVisitor(), env);
        }

        public Object visit(lang.Absyn.Block p, Env env)
        { /* Code for Block goes here */
            env.newBlock();

            for (lang.Absyn.Stm x: p.liststm_) {
                x.accept(new StmVisitor(), env);
            }
            env.emptyBlock();

            return env;
        }
    }

    public class ItemVisitor implements lang.Absyn.Item.Visitor<Val,Env>
    {
        public Val visit(lang.Absyn.LstItem p, Env env)
        { /* Code for LstItem goes here */
            return p.exp_.accept(new ExpVisitor(), env);
        }
    }
    public class Stm_InitialiseVisitor implements lang.Absyn.Stm_Initialise.Visitor<Object,Env>
    {
        public Object visit(lang.Absyn.SInit p, Env env)
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
    public class Stm_DeclareVisitor implements lang.Absyn.Stm_Declare.Visitor<Object,Env>
    {
        public Object visit(lang.Absyn.SDecl p, Env env)
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
    public class Stm_AssignVisitor implements lang.Absyn.Stm_Assign.Visitor<Object,Env>
    {
        public Object visit(lang.Absyn.SAssign p, Env env)
        { /* Code for SAssign goes here */
            //p.ident_;
            Val x = env.lookupVar(p.ident_,p.line_num, p.col_num);
            Operator op = p.assign_op_.accept(new Assign_OpVisitor(), env);
            Val y = p.exp_.accept(new ExpVisitor(), env);
            return env.updateVar(p.ident_, doBinaryOperation(x, y, op,p.line_num, p.col_num));
        }
    }
    public class Stm_IncrmDecrmVisitor implements lang.Absyn.Stm_IncrmDecrm.Visitor<Object,Env>
    {
        public Object visit(lang.Absyn.SIncrmDecrm p, Env env)
        { /* Code for SIncrmDecrm goes here */
            Val x = env.lookupVar(p.ident_,p.line_num, p.col_num);
            Operator op = p.incrmdecrm_op_.accept(new IncrmDecrm_OpVisitor(), env);
            return env.updateVar(p.ident_, doUnaryOperation(x,op, p.line_num,p.col_num));
        }
    }

    public class Stm_LoopVisitor implements lang.Absyn.Stm_Loop.Visitor<Env,Env>
    {
        public Env visit(lang.Absyn.SWhile p, Env env)
        {
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            int envCurrentSize = env.contexts.size();
            try{
                while(condition.val){
                    env.newBlock();
                    for (lang.Absyn.Stm x: p.liststm_) {
                        try{
                            x.accept(new StmVisitor(), env);
                        }catch (Continue stm){
                            env.emptyToInitialBlock(envCurrentSize+1);
                            break;
                        }
                    }
                    condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
                    env.emptyToInitialBlock(envCurrentSize);

                }

            }catch(Break stm){
                env.emptyToInitialBlock(envCurrentSize);
                return env;
            }
            return env;
        }

        @Override

        public Env visit(SPLoopIdent p, Env env) {
            VList list = (VList) env.lookupVar(p.ident_2,p.line_num, p.col_num);
            int envCurrentSize = env.contexts.size();
            try{
                for(int i = 0; i < list.listVal.size();i++){
                    env.newBlock();

                    Val iter = list.listVal.get(i);
                    env.extendEnvVar(p.ident_1, iter);

                    for (lang.Absyn.Stm x: p.liststm_) {
                        try{
                            x.accept(new StmVisitor(), env);
                        }catch (Continue stm){
                            env.emptyToInitialBlock(envCurrentSize+1);
                            break;
                        }
                    }
                    env.emptyToInitialBlock(envCurrentSize);
                }
            }catch (Break stm){
                env.emptyToInitialBlock(envCurrentSize);
                return env;
            }
            return env;
        }

        @Override
        public Env visit(SPLoopList p, Env env) {
            ArrayList<Val> listArr = new ArrayList<>();
            int envCurrentSize = env.contexts.size();
            if(p.listitem_.size() != 0){
                for(lang.Absyn.Item item: p.listitem_){
                    Val val = item.accept(new ItemVisitor(),env);
                    val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                    listArr.add(val);
                }
            }
            VList list = new VList(listArr,Type.TAuto);
            try{
                for(int i = 0; i < list.listVal.size();i++){
                    env.newBlock();

                    Val iter = list.listVal.get(i);
                    env.extendEnvVar(p.ident_, iter);

                    for (lang.Absyn.Stm x: p.liststm_) {
                        try{
                            x.accept(new StmVisitor(), env);
                        }catch (Continue stm){
                            env.emptyToInitialBlock(envCurrentSize+1);
                            break;
                        }
                    }
                    env.emptyToInitialBlock(envCurrentSize);
                }
            }catch (Break stm){
                env.emptyToInitialBlock(envCurrentSize);
                return env;
            }
            return env;
        }

        @Override

        public Env visit(SPLoopRangeStart p, Env env) {
            VInteger start = (VInteger) p.exp_.accept(new ExpVisitor(), env);
            VInteger iter = new VInteger(0);
            int envCurrentSize = env.contexts.size();
            try{
                while(iter.val < start.val){
                    env.newBlock();
                    env.extendEnvVar(p.ident_, new VInteger(iter.val));

                    for (lang.Absyn.Stm x: p.liststm_) {
                        try{
                            x.accept(new StmVisitor(), env);
                        }catch (Continue stm){
                            env.emptyToInitialBlock(envCurrentSize+1);
                            break;
                        }
                    }
                    iter.val++;
                    env.emptyToInitialBlock(envCurrentSize);
                }
            }catch (Break stm){
                env.emptyToInitialBlock(envCurrentSize);
                return env;
            }
            return env;
        }

        @Override
        public Env visit(SPLoopRangeStartStop p, Env env) {
            VInteger start = (VInteger) p.exp_1.accept(new ExpVisitor(), env);
            VInteger stop = (VInteger) p.exp_2.accept(new ExpVisitor(), env);
            VInteger iter = new VInteger(start.val);
            int envCurrentSize = env.contexts.size();
            try{
                while(iter.val < Math.abs(stop.val)){
                    env.newBlock();
                    env.extendEnvVar(p.ident_, new VInteger(iter.val));

                    for (lang.Absyn.Stm x: p.liststm_) {
                        try{
                            x.accept(new StmVisitor(), env);
                        }catch (Continue stm){
                            env.emptyToInitialBlock(envCurrentSize+1);
                            break;
                        }
                    }
                    iter.val++;
                    env.emptyToInitialBlock(envCurrentSize);
                }
            }catch (Break stm){
                env.emptyToInitialBlock(envCurrentSize);
                return env;
            }
            return env;
        }

        @Override
        public Env visit(SPLoopRangeStartStopEnd p, Env env) {
            VInteger start = (VInteger) p.exp_1.accept(new ExpVisitor(), env);
            VInteger stop = (VInteger) p.exp_2.accept(new ExpVisitor(), env);
            VInteger incr = (VInteger) p.exp_3.accept(new ExpVisitor(), env);
            VInteger iter = new VInteger(start.val);
            int envCurrentSize = env.contexts.size();
            try{
                if(stop.val < 0 || stop.val < iter.val){
                    while(iter.val > stop.val){
                        env.newBlock();
                        env.extendEnvVar(p.ident_, new VInteger(iter.val));

                        for (lang.Absyn.Stm x: p.liststm_) {
                            try{
                                x.accept(new StmVisitor(), env);
                            }catch (Continue stm){
                                env.emptyToInitialBlock(envCurrentSize+1);
                                break;
                            }
                        }
                        env.emptyToInitialBlock(envCurrentSize);
                        iter.val+= incr.val;
                    }
                }else{
                    while(iter.val < stop.val){
                        env.newBlock();
                        env.extendEnvVar(p.ident_, new VInteger(iter.val));

                        for (lang.Absyn.Stm x: p.liststm_) {
                            try{
                                x.accept(new StmVisitor(), env);
                            }catch (Continue stm){
                                env.emptyToInitialBlock(envCurrentSize+1);
                                break;
                            }
                        }
                        env.emptyToInitialBlock(envCurrentSize);
                        iter.val+= incr.val;
                    }
                }
            }catch (Break stm){
                env.emptyToInitialBlock(envCurrentSize);
                return env;
            }
            return env;
        }

        public Env visit(lang.Absyn.SCLoop p, Env env)
        {
            p.stm_initialise_.accept(new Stm_InitialiseVisitor(), env);
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            int envCurrentSize = env.contexts.size();
            try{
                while(condition.val){
                    env.newBlock();
                    for (lang.Absyn.Stm x: p.liststm_) {
                        try{
                            x.accept(new StmVisitor(), env);
                        }catch (Continue stm){
                            env.emptyToInitialBlock(envCurrentSize+1);
                            break;
                        }
                    }
                    env.emptyToInitialBlock(envCurrentSize);
                    p.stm_incrmdecrm_.accept(new Stm_IncrmDecrmVisitor(), env);
                    condition = (VBool) p.exp_.accept(new ExpVisitor(), env);

                }
            }catch(Break stm){
                env.emptyToInitialBlock(envCurrentSize);
                return env;
            }
            return env;
        }

        @Override

        public Env visit(SCLoopAssign p, Env env) {
            p.stm_initialise_.accept(new Stm_InitialiseVisitor(), env);
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            int envCurrentSize = env.contexts.size();
            try{
                while(condition.val){
                    env.newBlock();
                    for (lang.Absyn.Stm x: p.liststm_) {
                        try{
                            x.accept(new StmVisitor(), env);
                        }catch (Continue stm){
                            env.emptyToInitialBlock(envCurrentSize+1);
                            break;
                        }
                    }
                    env.emptyToInitialBlock(envCurrentSize);
                    p.stm_assign_.accept(new Stm_AssignVisitor(), env);
                    condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
                }
            }catch(Break stm){
                env.emptyToInitialBlock(envCurrentSize);
                return env;
            }
            return env;
        }
    }

    public class If_StmVisitor implements lang.Absyn.If_Stm.Visitor<Env,Env>
    {
        public Env visit(lang.Absyn.SIf p, Env env)
        { /* Code for SIf goes here */
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            if (condition.val){
                for (lang.Absyn.Stm x: p.liststm_) {
                    x.accept(new StmVisitor(), env);
                }
            }

            return env;
        }
        public Env visit(lang.Absyn.SIfElse p, Env env)
        { /* Code for SIfElse goes here */
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            if(condition.val){
                for (lang.Absyn.Stm x: p.liststm_1) {
                    x.accept(new StmVisitor(), env);
                }
            }else{
                for (lang.Absyn.Stm x: p.liststm_2) {
                    x.accept(new StmVisitor(), env);
                }
            }
            return env;
        }
        public Env visit(lang.Absyn.SIfElseIf p, Env env)
        { /* Code for SIfElseIf goes here */
            VBool condition = (VBool) p.exp_.accept(new ExpVisitor(), env);
            if (condition.val){
                for (lang.Absyn.Stm x: p.liststm_) {
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
        public Arg visit(lang.Absyn.ArgDecl p, Env env)
        { /* Code for ArgDecl goes here */

            //p.ident_;
            return new Arg(p.vartype_.accept(new VarTypeVisitor(), env), p.ident_);
        }
    }

    public class ExpVisitor implements lang.Absyn.Exp.Visitor<Val,Env>
    {
        public Val visit(lang.Absyn.EInt p, Env env)
        { /* Code for EInt goes here */
            //p.integer_;
            return new VInteger(p.integer_);
        }
        public Val visit(lang.Absyn.ENegInt p, Env env)
        { /* Code for EInt goes here */
            //p.integer_;
            return new VInteger(-p.integer_);
        }
        public Val visit(EChar p, Env arg) {
            return new VChar(p.char_);
        }

        public Val visit(lang.Absyn.EDouble p, Env env)
        { /* Code for EDouble goes here */
            //p.double_;
            return new VDouble(p.double_);
        }
        public Val visit(lang.Absyn.ENegDouble p, Env env)
        { /* Code for EDouble goes here */
            //p.double_;
            return new VDouble(- p.double_);
        }
        public Val visit(lang.Absyn.EString p, Env env)
        { /* Code for EString goes here */
            //p.string_;
            return new VString(p.string_);
        }
        public Val visit(lang.Absyn.ETrue p, Env env)
        { /* Code for ETrue goes here */
            return new VBool(true);
        }
        public Val visit(lang.Absyn.EFalse p, Env env)
        { /* Code for EFalse goes here */
            return new VBool(false);
        }
        public Val visit(lang.Absyn.EId p, Env env)
        { /* Code for EId goes here */
            //p.ident_;
            return env.lookupVar(p.ident_,p.line_num, p.col_num);
        }

        @Override
        public Val visit(EType p, Env env) {
            Val val = env.lookupVar(p.ident_, p.line_num, p.col_num);
            Type type = new TypeChecker().returnType(val, p.line_num, p.col_num);
            Type type2 = p.vartype_.accept(new VarTypeVisitor(),env);
            if(val instanceof VList list){
                if(type2 == list.itemType){
                    return new VBool(true);
                }else{
                    return new VBool(false);
                }
            }
            if(type == type2){
                return new VBool(true);
            }else{
                return new VBool(false);
            }
        }

        public Val visit(lang.Absyn.EListItem p, Env env)
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
                for(lang.Absyn.Item item: p.listitem_){
                    Val val = item.accept(new ItemVisitor(),env);
                    val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                    list.add(val);
                }
            }else{
                return new VList(list, Type.TUnknown);
            }
            return new VList(list, Type.TUnknown);
        }

        @Override
        public Val visit(EListSize p, Env env) {
            VList list = (VList) env.lookupVar(p.ident_,p.line_num, p.col_num);
            return new VInteger(list.listVal.size());
        }


        @Override
        public Val visit(EListIsEmpty p, Env env) {
            VList list = (VList) env.lookupVar(p.ident_,p.line_num, p.col_num);
            return new VBool(list.listVal.isEmpty());
        }


        @Override
        public Val visit(EInput p, Env arg) {
            Scanner prompt = new Scanner(System.in);
            return new TypeChecker().inferInput(prompt, p.line_num, p.col_num);
        }

        @Override
        public Val visit(EInputString p, Env arg) {
            System.out.print(p.string_);
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
            if(env.getScopeType() == 1){
                lambda.closure.putAll(new HashMap<>(env.contexts.getLast()));
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
            return new VList(returnList,list.itemType);
        }

        @Override
        public Val visit(EMapList p, Env env) {
            ArrayList<Val> list = new ArrayList<>();
            for(lang.Absyn.Item item: p.listitem_){
                Val val = item.accept(new ItemVisitor(),env);
                val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                list.add(val);

            }
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 1){
                throw new MapErrException(p.line_num, p.col_num);
            }
            ArrayList<Val> returnList = new ArrayList<>();
            for(Val val: new VList(list, Type.TAuto).listVal){
                env.newBlock();
                env.extendEnvVar(func.val.args.get(0).ident, val);
                execFunc(func.val.listStm, env,func.val, func.funcType);
                returnList.add(func.val.returnVal);

                env.emptyBlock();
            }
            return new VList(returnList,returnList.get(0).type);
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
            for(lang.Absyn.Item item: p.listitem_){
                Val val = item.accept(new ItemVisitor(),env);
                val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                list.add(val);

            }
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 1){
                throw new FilterErrException(p.line_num, p.col_num);
            }
            ArrayList<Val> returnList = new ArrayList<>();
            for(Val val: new VList(list, Type.TAuto).listVal){
                env.newBlock();
                env.extendEnvVar(func.val.args.get(0).ident, val);
                execFunc(func.val.listStm, env,func.val,Type.TBoolean);
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
            for(lang.Absyn.Item item: p.listitem_){
                Val val = item.accept(new ItemVisitor(),env);
                val.type = new TypeChecker().returnType(val,p.line_num, p.col_num);
                listArr.add(val);
            }
            VFunc func = (VFunc) p.exp_.accept(new ExpVisitor(),env);
            if (func.val.args.size() != 2){
                throw new ReduceErrException(p.line_num, p.col_num);
            }
            VList list = new VList(listArr, listArr.get(0).type);
            Val newVal = new TypeChecker().returnValOfType(list.listVal.get(0).type,p.line_num, p.col_num);
            newVal.type = new TypeChecker().returnType(list.listVal.get(0),p.line_num, p.col_num);
            return list.listVal.stream().reduce(newVal,((val, val2) -> executeReduce(val,val2,env,func) ));
        }

        public Val visit(lang.Absyn.ESelect p, Env env)
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

        public Val visit(ESelectListItem p, Env env) {
            VObject val = (VObject) env.lookupVar(p.ident_1,p.line_num, p.col_num);
            ObjectVar objectVar = val.val;
            return execObjectSelectListItem(
                    objectVar,
                    env, p.ident_2,
                    (VInteger) p.exp_.accept(new ExpVisitor(),env),
                    p.line_num, p.col_num);
        }

        public Val visit(lang.Absyn.ECall p, Env env)
        { /* Code for ECall goes here */
            //p.ident_;

            env.setScopeTypeToFunc();
            VFunc val = (VFunc) env.lookupVar(p.ident_,p.line_num, p.col_num);
            Function funcToExec = val.val;
            if(funcToExec.args.size() != p.listexp_.size()){
                throw new ArgNumError(p.line_num,p.col_num,funcToExec.args.size(),p.listexp_.size());
            }


            //Adding given arguments to list
            HashMap<String,Val> args = new HashMap<>();
            int i=0;
            for (lang.Absyn.Exp x: p.listexp_) {
                Val newval = x.accept(new ExpVisitor(), env);

                if(!new TypeChecker().check(funcToExec.args.get(i).type, newval, p.line_num, p.col_num)){
                    throw new TypeArgException(p.line_num, p.col_num,funcToExec.args.get(i).type, newval.getClass().getSimpleName() );
                }
                args.put(funcToExec.args.get(i).ident, newval);
                i++;
            }
            // Block 1 - Function closure
            env.contexts.addLast(funcToExec.closure);
            // Block 2 - function call parameters
            env.newBlock();

                //Extend new block with given arguments
            for (HashMap.Entry<String, Val> entry: args.entrySet() ){
                env.extendEnvVar(entry.getKey(),entry.getValue());
            }
                //Execute function in block of parameters context

            execFunc(funcToExec.listStm, env, funcToExec, val.funcType);
            // Empty Block 2
            env.emptyBlock();
            funcToExec.closure = env.contexts.getLast();
            // Empty Block 1
            env.emptyBlock();
            env.setScopeTypeToOuter();
            return funcToExec.returnVal;
        }
        public Val visit(lang.Absyn.EObjCall p, Env env)
        {
            VObject val = (VObject) env.lookupVar(p.ident_1,p.line_num, p.col_num);
            ObjectVar objectVar = val.val;
            ObjectDef objectDef = env.lookupDef(objectVar.ofClass,p.line_num, p.col_num);
            Val method = objectDef.lookUpMethod(p.ident_2);
            if(method == null){
                throw new NullMethodException(p.line_num,p.col_num,p.ident_2,objectVar.ofClass,objectDef.defMethods);
            }
            return execObjectMethod(
                    env.lookupDef(objectVar.ofClass,p.line_num, p.col_num).lookUpMethod(p.ident_2), objectVar, objectDef,
                    env, p.listexp_, p.line_num,p.col_num);
        }
        public Val visit(lang.Absyn.EPow p, Env env)
        { /* Code for EPow goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, EXPONENT, p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.EMul p, Env env)
        { /* Code for EMul goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, MULTIPLY,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.EDiv p, Env env)
        { /* Code for EDiv goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, DIVIDE,p.line_num, p.col_num);
        }

        public Val visit(EMod p, Env env) {
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, MOD,p.line_num, p.col_num);
        }

        public Val visit(lang.Absyn.EAdd p, Env env)
        { /* Code for EAdd goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, ADD,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.ESub p, Env env)
        { /* Code for ESub goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, SUBTRACT,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.ELt p, Env env)
        { /* Code for ELt goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, LESS_THAN,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.EGt p, Env env)
        { /* Code for EGt goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, GREATER_THAN,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.ELEq p, Env env)
        { /* Code for ELEq goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, LESS_THAN_EQUALS,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.EGEq p, Env env)
        { /* Code for EGEq goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, GREATER_THAN_EQUALS,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.EEq p, Env env)
        { /* Code for EEq goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, EQUALS,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.ENEq p, Env env)
        { /* Code for ENEq goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, NOT_EQUALS,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.EAnd p, Env env)
        { /* Code for EAnd goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, AND,p.line_num, p.col_num);
        }
        public Val visit(lang.Absyn.EOr p, Env env)
        { /* Code for EOr goes here */
            Val x = p.exp_1.accept(new ExpVisitor(), env);
            Val y = p.exp_2.accept(new ExpVisitor(), env);
            return doBinaryOperation(x, y, OR,p.line_num, p.col_num);
        }

    }



    public static class Assign_OpVisitor implements lang.Absyn.Assign_Op.Visitor<Operator,Env>
    {
        public Operator visit(lang.Absyn.Assign p, Env arg)
        { /* Code for Assign goes here */
            return ASSIGN;
        }
        public Operator visit(lang.Absyn.AssignMul p, Env arg)
        { /* Code for AssignMul goes here */
            return MULTIPLY;
        }
        public Operator visit(lang.Absyn.AssignDiv p, Env arg)
        { /* Code for AssignDiv goes here */
            return DIVIDE;
        }
        public Operator visit(lang.Absyn.AssignMod p, Env arg)
        { /* Code for AssignMod goes here */
            return MOD;
        }
        public Operator visit(lang.Absyn.AssignAdd p, Env arg)
        { /* Code for AssignAdd goes here */
            return ADD;
        }
        public Operator visit(lang.Absyn.AssignSub p, Env arg)
        { /* Code for AssignSub goes here */
            return SUBTRACT;
        }
    }
    public static class IncrmDecrm_OpVisitor implements lang.Absyn.IncrmDecrm_Op.Visitor<Operator,Env>
    {
        public Operator visit(lang.Absyn.Increment p, Env env)
        { /* Code for Increment goes here */
            return INCREMENT;
        }
        public Operator visit(lang.Absyn.Decrement p, Env env)
        { /* Code for Decrement goes here */
            return DECREMENT;
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
            return p.vartype_.accept(new VarTypeVisitor(), arg);
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
    public static class TypeCastVisitor implements lang.Absyn.TypeCast.Visitor<Val,Val>
    {
        public Val visit(lang.Absyn.TypeCast_toInt p, Val arg)
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
        public Val visit(lang.Absyn.TypeCast_toDouble p, Val arg)
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
        public Val visit(lang.Absyn.TypeCast_toBool p, Val arg)
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
        public Val visit(lang.Absyn.TypeCast_toChar p, Val arg)
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
        public Val visit(lang.Absyn.TypeCast_toString p, Val arg)
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

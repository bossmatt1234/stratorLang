package lang.Interpret.UnitTests;

import lang.Interpret.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestEnv {

    @Test
    public void testExtendEnv(){
        Env env = new Env();
        VInteger val = new VInteger(5);
        String ident = "val";
        env.extendEnvVar(ident,val);
        VInteger returnVal = (VInteger) env.contexts.getLast().get(ident);
        assertEquals(5 ,returnVal.getVal());
    }

    @Test
    public void testUpdateEnv(){
        Env env = new Env();
        VInteger val = new VInteger(5);
        String ident = "val";
        env.extendEnvVar(ident,val);

        VInteger newVal = new VInteger(10);
        env.updateVar(ident,newVal);
        VInteger returnVal = (VInteger) env.contexts.getLast().get(ident);
        assertEquals(10 ,returnVal.getVal());
    }

    @Test
    public void testLookUpVar(){
        Env env = new Env();
        VInteger val = new VInteger(5);
        String ident = "val";
        env.extendEnvVar(ident,val);

        VInteger returnVal = (VInteger) env.lookupVar(ident,0,0);
        assertEquals(5 ,returnVal.getVal());
    }

    @Test
    public void testLookUpDef(){
        Env env = new Env();
        ObjectDef def = new ObjectDef("testClass");
        String ident = "def";
        env.objectDefs.getLast().put(ident,def);

        ObjectDef returnVal = env.lookupDef(ident,0,0);
        assertEquals("testClass" ,returnVal.getClassName());
    }

}

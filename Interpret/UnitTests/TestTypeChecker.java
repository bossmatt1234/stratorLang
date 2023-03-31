package lang.Interpret.UnitTests;


import lang.Interpret.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class TestTypeChecker {

    TypeChecker typeChecker = new TypeChecker();

    @Test
    public void testCheckTypeInt(){
        VInteger val = new VInteger(1);
        assertTrue(typeChecker.isType(Type.TInt,val));
        assertFalse(typeChecker.isType(Type.TDouble,val));
    }

    @Test
    public void testCheckTypeDouble(){
        VDouble val = new VDouble(1.0);
        assertTrue(typeChecker.isType(Type.TDouble,val));
        assertFalse(typeChecker.isType(Type.TInt,val));
    }

    @Test
    public void testCheckReturnType(){
        VInteger val = new VInteger(1);
        ArrayList<Val> list = new ArrayList<>();
        list.add(val);
        assertEquals(Type.TInt, new TypeChecker().returnType(list.get(0), 0, 0));
        assertNotEquals(Type.TDouble, new TypeChecker().returnType(list.get(0), 0, 0));
    }

    @Test
    public void testReturnValOfType(){
        VInteger valInt = (VInteger) typeChecker.returnValOfType(Type.TInt,0,0);
        VDouble valDouble = (VDouble) typeChecker.returnValOfType(Type.TDouble,0,0);
        assertEquals("VInteger",valInt.getClass().getSimpleName());
        assertEquals("VDouble",valDouble.getClass().getSimpleName());
        assertNotEquals("VDouble",valInt.getClass().getSimpleName());
        assertNotEquals("VInteger",valDouble.getClass().getSimpleName());
    }

    @Test
    public void testCheckTypeList(){
        VInteger x = new VInteger(1);
        VInteger y = new VInteger(2);
        ArrayList<Val> list = new ArrayList<>();
        list.add(x);
        list.add(y);
        for(Val item : list){
            item.setType(new TypeChecker().returnType(item, 0, 0));
        }

        VList vList = new VList(list,list.get(0).getType());
        assertTrue(typeChecker.isType(Type.TInt,vList));
        assertFalse(typeChecker.isType(Type.TDouble,vList));
    }
}

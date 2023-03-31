package lang.Interpret.UnitTests;

import lang.Interpret.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBinaryOperation {

    BinaryOperation BinaryOperation = new BinaryOperation();

    /*
    *
    *  Basic Arithmetic with VInteger and VDouble
    *
    * */

    @Test
    public void testAddInt(){
        VInteger x = new VInteger(10);
        VInteger y = new VInteger(15);
        Operator op = Operator.ADD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VInteger returnVal = (VInteger) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(25, returnVal.getVal());
    }

    @Test
    public void testAddDouble(){
        VDouble x = new VDouble(10.0);
        VDouble y = new VDouble(15.0);
        Operator op = Operator.ADD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VDouble returnVal = (VDouble) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(25.0, returnVal.getVal(), 0.1);
    }

    @Test
    public void testMultiplyInt(){
        VInteger x = new VInteger(10);
        VInteger y = new VInteger(15);
        Operator op = Operator.MULTIPLY;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VInteger returnVal = (VInteger) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(150, returnVal.getVal());
    }

    @Test
    public void testMultiplyDouble(){
        VDouble x = new VDouble(10.0);
        VDouble y = new VDouble(15.0);
        Operator op = Operator.MULTIPLY;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VDouble returnVal = (VDouble) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(150.0, returnVal.getVal(), 0.1);
    }

    @Test
    public void testSubtractInt(){
        VInteger x = new VInteger(20);
        VInteger y = new VInteger(15);
        Operator op = Operator.SUBTRACT;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VInteger returnVal = (VInteger) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(5, returnVal.getVal());
    }

    @Test
    public void testSubtractDouble(){
        VDouble x = new VDouble(20.0);
        VDouble y = new VDouble(15.0);
        Operator op = Operator.SUBTRACT;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VDouble returnVal = (VDouble) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(5.0, returnVal.getVal(), 0.1);
    }

    @Test
    public void testDivideInt(){
        VInteger x = new VInteger(10);
        VInteger y = new VInteger(5);
        Operator op = Operator.DIVIDE;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VInteger returnVal = (VInteger) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(2, returnVal.getVal());
    }

    @Test
    public void testDivideDouble(){
        VDouble x = new VDouble(10.0);
        VDouble y = new VDouble(5.0);
        Operator op = Operator.DIVIDE;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VDouble returnVal = (VDouble) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(2.0, returnVal.getVal(), 0.1);
    }

    @Test
    public void testPowerInt(){
        VInteger x = new VInteger(5);
        VInteger y = new VInteger(5);
        Operator op = Operator.EXPONENT;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VInteger returnVal = (VInteger) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(3125, returnVal.getVal());
    }

    @Test
    public void testPowerDouble(){
        VDouble x = new VDouble(5.0);
        VDouble y = new VDouble(5.0);
        Operator op = Operator.EXPONENT;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VDouble returnVal = (VDouble) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(3125.0, returnVal.getVal(), 0.1);
    }

    @Test
    public void testModInt(){
        VInteger x = new VInteger(4);
        VInteger y = new VInteger(2);
        Operator op = Operator.MOD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VInteger returnVal = (VInteger) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(0, returnVal.getVal());
    }

    @Test
    public void testModDouble(){
        VDouble x = new VDouble(5.0);
        VDouble y = new VDouble(2.0);
        Operator op = Operator.MOD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VDouble returnVal = (VDouble) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals(1.0, returnVal.getVal(), 0.1);
    }


    /*
     *
     *  Comparisons with VInteger, VDouble and VBool
     *
     * */

    @Test
    public void testEqualInt(){
        VInteger x = new VInteger(5);
        VInteger y = new VInteger(5);
        Operator op = Operator.EQUALS;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testEqualDouble(){
        VDouble x = new VDouble(5.0);
        VDouble y = new VDouble(5.0);
        Operator op = Operator.EQUALS;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testUnEqualInt(){
        VInteger x = new VInteger(5);
        VInteger y = new VInteger(10);
        Operator op = Operator.NOT_EQUALS;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testUnEqualDouble(){
        VDouble x = new VDouble(5.0);
        VDouble y = new VDouble(10.0);
        Operator op = Operator.NOT_EQUALS;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }
    @Test
    public void testGreaterThanInt(){
        VInteger x = new VInteger(10);
        VInteger y = new VInteger(5);
        Operator op = Operator.GREATER_THAN;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testGreaterThanDouble(){
        VDouble x = new VDouble(10.0);
        VDouble y = new VDouble(5.0);
        Operator op = Operator.GREATER_THAN;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testLessThanInt(){
        VInteger x = new VInteger(5);
        VInteger y = new VInteger(10);
        Operator op = Operator.LESS_THAN;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testLessThanDouble(){
        VDouble x = new VDouble(5.0);
        VDouble y = new VDouble(10.0);
        Operator op = Operator.LESS_THAN;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testGreaterThanEqualInt(){
        VInteger x = new VInteger(10);
        VInteger y = new VInteger(9);
        Operator op = Operator.GREATER_THAN_EQUALS;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
        y = new VInteger(10);
        returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testGreaterThanEqualDouble(){
        VDouble x = new VDouble(10.0);
        VDouble y = new VDouble(9.0);
        Operator op = Operator.GREATER_THAN_EQUALS;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
        y = new VDouble(10.0);
        returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testLessThanEqualInt(){
        VInteger x = new VInteger(9);
        VInteger y = new VInteger(10);
        Operator op = Operator.LESS_THAN_EQUALS;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
        x = new VInteger(10);
        returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testLessThanEqualDouble(){
        VDouble x = new VDouble(9.0);
        VDouble y = new VDouble(10.0);
        Operator op = Operator.LESS_THAN_EQUALS;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
        x = new VDouble(10.0);
        returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testAndBool(){
        VBool x = new VBool(true);
        VBool y = new VBool(true);
        Operator op = Operator.AND;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    @Test
    public void testOrBool(){
        VBool x = new VBool(true);
        VBool y = new VBool(false);
        Operator op = Operator.OR;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VBool returnVal = (VBool) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertTrue(returnVal.isVal());
    }

    /*
     *
     *  VString and VChar operations
     *
     * */

    @Test
    public void testAddStringString(){
        VString x = new VString("He");
        VString y = new VString("llo");
        Operator op = Operator.ADD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VString returnVal = (VString) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals("Hello", returnVal.getVal());
    }

    @Test
    public void testAddStringInt(){
        VString x = new VString("Number: ");
        VInteger y = new VInteger(5);
        Operator op = Operator.ADD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VString returnVal = (VString) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals("Number: 5", returnVal.getVal());
    }

    @Test
    public void testAddStringDouble(){
        VString x = new VString("Number: ");
        VDouble y = new VDouble(5.0);
        Operator op = Operator.ADD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VString returnVal = (VString) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals("Number: 5.0", returnVal.getVal());
    }

    @Test
    public void testAddStringBool(){
        VString x = new VString("Value: ");
        VBool y = new VBool(true);
        Operator op = Operator.ADD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VString returnVal = (VString) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals("Value: true", returnVal.getVal());
    }

    @Test
    public void testAddStringChar(){
        VString x = new VString("Char: ");
        VChar y = new VChar('A');
        Operator op = Operator.ADD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VString returnVal = (VString) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals("Char: A", returnVal.getVal());
    }

    @Test
    public void testAddCharChar(){
        VChar x = new VChar('A');
        VChar y = new VChar('B');
        Operator op = Operator.ADD;
        String leftRight = x.getClass().getSimpleName() + y.getClass().getSimpleName();
        VString returnVal = (VString) BinaryOperation.checkInstance(x,y,op,leftRight);
        assertEquals("AB", returnVal.getVal());
    }
}

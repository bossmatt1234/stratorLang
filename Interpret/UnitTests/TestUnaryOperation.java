package lang.Interpret.UnitTests;

import lang.Interpret.UnaryOperation;
import lang.Interpret.*;
import org.junit.Test;

import static org.junit.Assert.*;
public class TestUnaryOperation {
    UnaryOperation UnaryOperation = new UnaryOperation();

    @Test
    public void incrementInt(){
        VInteger x = new VInteger(1);
        Operator op = Operator.INCREMENT;
        VInteger returnVal = (VInteger) UnaryOperation.checkInstance(x,op);
        assertEquals(2,returnVal.getVal());
    }

    @Test
    public void incrementDouble(){
        VDouble x = new VDouble(1.0);
        Operator op = Operator.INCREMENT;
        VDouble returnVal = (VDouble) UnaryOperation.checkInstance(x,op);
        assertEquals(2.0,returnVal.getVal(),0.1);
    }

    @Test
    public void decrementInt(){
        VInteger x = new VInteger(2);
        Operator op = Operator.DECREMENT;
        VInteger returnVal = (VInteger) UnaryOperation.checkInstance(x,op);
        assertEquals(1,returnVal.getVal());
    }

    @Test
    public void decrementDouble(){
        VDouble x = new VDouble(2.0);
        Operator op = Operator.DECREMENT;
        VDouble returnVal = (VDouble) UnaryOperation.checkInstance(x,op);
        assertEquals(1.0,returnVal.getVal(),0.1);
    }
}

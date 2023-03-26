// File generated by the BNF Converter (bnfc 2.9.4.1).

package lang;

import lang.Interpret.Interpreter;
import lang.Interpret.LangError;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLOutput;
import java.util.Arrays;


public class TestInterpreter
{
  Yylex l;
  parser p;

  public TestInterpreter(String[] args)
  {
    try
    {
      Reader input;
      if (args.length == 0) input = new InputStreamReader(System.in);
      else input = new FileReader(args[0]);
      l = new Yylex(input);
    }
    catch(IOException e)
    {
      System.err.println("Error: File not found: " + args[0]);
      System.exit(1);
    }
    p = new parser(l, l.getSymbolFactory());
  }

  public void interpret() throws Exception
  {
    lang.Absyn.Program ast = p.pProgram();
      Interpreter i = new Interpreter();
      i.interpret(ast);
  }

  public static void main(String[] args) throws Exception
  {
    TestInterpreter t = new TestInterpreter(args);
    try
    {
      t.interpret();
    }
    catch(Throwable e)
    {
      System.err.println("\nAt line " + t.l.line_num() + ", near \"" + t.l.buff() + "\" :");
      System.err.println("     " + e.getMessage() + "\n");
      System.exit(1);
    }
  }
}

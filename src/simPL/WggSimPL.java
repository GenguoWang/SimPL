package simPL;

import java.io.*;
import javacc.*;
import syntax.*;

public class WggSimPL {
 protected int m_iNum_of_Simple_Statements = 0;
 protected int Weight_of_VarDecl = 0;
 protected java.util.List<Token> tokenList = new java.util.LinkedList<Token>();
 protected java.util.Map<String,Integer> tokenNum = new java.util.HashMap<String,Integer>();
 public static void main(String args[])  {
	 if(args.length == 1)
     {
    	 if(args[0].equals("-s"))
    	 {
    		 cmd();
    	 }
    	 if(args[0].equals("-t"))
    	 {
    		 //runwp("wp_test");
    		 rundir("test");
    	 }
    	 else
    	 {
    		 usage();
    	 }
     }
     else if(args.length==2)
     {
    	 if(args[0].equals("-f"))
    	 {
    		 runfile(args[1]);
    	 }
    	 else if(args[0].equals("-d"))
    	 {
    		 rundir(args[1]);
    	 }
    	 else
    	 {
    		 usage();
    	 }
     }
     else
     {
    	 usage();
     }
 }
 public static void cmd()
 {
	 StringBuilder sb = new StringBuilder();
	 int c;
	 System.out.print("SimPL> ");
	 while(true)
	 {
		 
		 try {
			c = System.in.read();
			if(c=='$')
			{
				System.out.print("SimPL> ");
			    System.out.println(run(sb.toString()));
			    System.out.print("SimPL> ");
				sb = new StringBuilder();
			}
			else
			{
				sb.append((char)c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
 }
 public static void rundir(String dirname)
 {
	 File current_dir=new File(dirname); 
	 String file_list[]=current_dir.list();
	 if(file_list == null)
	 {
		 System.out.println("directory not existed");
		 return;
	 }
	 for(int i=0;i<file_list.length;i++){
         String filename=dirname+File.separator+file_list[i];
         File current_file=new File(filename); 
         if(current_file.isFile()&&filename.matches(".*\\.spl$")){
        	 runfile(current_file.getPath()); 
         }                                      
	 } 
 }
 public static void runfile(String filename)
 {
	 StringBuilder sb = new StringBuilder();
	 File file = new File(filename);
	 boolean flag =  false;
	 String res=null;
	 try {
		FileInputStream fis=new FileInputStream(file);
		int c;
		 while((c = fis.read())>=0)
		 { 
			if(c=='$')
			{
			    res = run(sb.toString()).toString();
			    flag = true;
			}
			else
			{
				sb.append((char)c);
			}
		 }
		 if(flag)
		 {
			 String ofString = filename.replaceAll("\\.spl$", ".rst");
			 FileOutputStream fos = new FileOutputStream(ofString);
			 fos.write(res.getBytes());
			 System.out.print("SimPL> ");
			 System.out.println(res);
			 fos.close();
		 }
		 else
		 {
			 System.out.println("no $ found");
		 }
		 fis.close();
	} catch (FileNotFoundException e1) {
		System.out.println("file not found!");
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("io error!");
	}
	 
 }
 public static void runwpfile(String filename)
 {
	 System.out.println(filename);
	 StringBuilder sb = new StringBuilder();
	 File file = new File(filename);
	 String res=null;
	 try {
		FileInputStream fis=new FileInputStream(file);
		int c;
		 while((c = fis.read())>=0)
		 { 
			
				sb.append((char)c);
		 }
		 res = run(sb.toString()).toString();
		 String ofString = filename.replaceAll("\\.txt$", ".rst");
		 FileOutputStream fos = new FileOutputStream(ofString);
		 fos.write(res.getBytes());
		 System.out.print("SimPL> ");
		 System.out.println(res);
		 fos.close();
		 fis.close();
	} catch (FileNotFoundException e1) {
		System.out.println("file not found!");
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("io error!");
	}
	 
 }
public static void runwp(String dirname)
{
	File current_dir=new File(dirname); 
	 String file_list[]=current_dir.list();
	 if(file_list == null)
	 {
		 System.out.println("directory not existed");
		 return;
	 }
	 for(int i=0;i<file_list.length;i++){
        String filename=dirname+File.separator+file_list[i];
        File current_file=new File(filename); 
        if(current_file.isFile()&&filename.matches(".*\\.txt$")){
       	 runwpfile(current_file.getPath()); 
        }                                      
	 } 
}

 static public void usage()
 {
	 System.out.println("Usage: java -jar SimPL.jar -s");
	 System.out.println("or java -jar SimPL.jar -f filename");
	 System.out.println("or java -jar SimPL.jar -d directoryname");
 }
 static public Expression generateSynatx(SimpleNode node)
 {
	 Expression program = null;
	 switch(node.getId())
	 {
	  case WggSimPLParserTreeConstants.JJTCONDITIONALNODE:
		  break;
	  case WggSimPLParserTreeConstants.JJTVARIABLE:
		  syntax.Variable v = new syntax.Variable();
		  program = v;
		  v.name = node.jjtGetValue().toString();
		  break;
	  case WggSimPLParserTreeConstants.JJTSEQUENCE:
	  {
		  syntax.Sequence s = new syntax.Sequence();
		  program = s;
		  s.e1 = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  syntax.Sequence pre = s;
		  syntax.Sequence now;
		  int i=1;
		  for(;i+1 < node.jjtGetNumChildren();++i)
		  {
			  now = new syntax.Sequence();
			  now.e1 = generateSynatx((SimpleNode)node.jjtGetChild(i));
			  pre.e2 = now;
			  pre = now;
		  }
		  pre.e2 = generateSynatx((SimpleNode)node.jjtGetChild(i));
	  }
		  break;
	  case WggSimPLParserTreeConstants.JJTANONYMOUSFUNCTION:
		  syntax.AnonymousFunction f = new syntax.AnonymousFunction();
		  program = f;
		  f.arg = new syntax.Variable();
		  f.arg.name = ((SimpleNode)(node.jjtGetChild(0))).jjtGetValue().toString();
		  f.body = generateSynatx((SimpleNode)node.jjtGetChild(1));
		  break;
	  case WggSimPLParserTreeConstants.JJTASSIGNEXPRESSION:
	  {
		  syntax.Assignment exp = new syntax.Assignment();
		  int i=node.jjtGetNumChildren()-1;
		  exp.val = generateSynatx((SimpleNode)node.jjtGetChild(i));
		  exp.var = generateSynatx((SimpleNode)node.jjtGetChild(i-1));
		  i = i-2;
		  syntax.Assignment pre = exp;
		  for(;i>=0;--i)
		  {
			  exp = new syntax.Assignment();
			  exp.var = generateSynatx((SimpleNode)node.jjtGetChild(i));
			  exp.val = pre;
			  pre = exp;
		  }
		  program = pre;
	  }
		  break;
	  case WggSimPLParserTreeConstants.JJTLISTEXPRESSION:
	  {
		  syntax.List exp = new syntax.List();
		  program = exp;
		  exp.head = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  syntax.List pre = exp;
		  syntax.List now;
		  int i=1;
		  for(;i+1 < node.jjtGetNumChildren();++i)
		  {
			  now = new syntax.List();
			  now.head = generateSynatx((SimpleNode)node.jjtGetChild(i));
			  pre.tail = now;
			  pre = now;
		  }
		  pre.tail = generateSynatx((SimpleNode)node.jjtGetChild(i));
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTADDOREXPRESSION:
	  {
		  syntax.BinaryOperation exp = new syntax.BinaryOperation();
		  exp.e1 = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  exp.setOP(((SimpleNode)node.jjtGetChild(1)).jjtGetValue().toString());
		  exp.e2 = generateSynatx((SimpleNode)node.jjtGetChild(2));
		  syntax.BinaryOperation pre = exp;
		  syntax.BinaryOperation now;
		  int i=3;
		  //ע��,����Ҫ����
		  for(;i+1 < node.jjtGetNumChildren();i+=2)
		  {
			  now = new syntax.BinaryOperation();
			  now.e1 = pre;
			  now.setOP(((SimpleNode)node.jjtGetChild(i)).jjtGetValue().toString());
			  now.e2 = generateSynatx((SimpleNode)node.jjtGetChild(i+1));
			  pre = now;
		  }
		  program = pre;
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTADDOR_OP:
		  break;
	  case WggSimPLParserTreeConstants.JJTCONDEXPRESSION:
	  {
		  syntax.BinaryOperation exp = new syntax.BinaryOperation();
		  exp.e1 = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  exp.setOP(((SimpleNode)node.jjtGetChild(1)).jjtGetValue().toString());
		  exp.e2 = generateSynatx((SimpleNode)node.jjtGetChild(2));
		  syntax.BinaryOperation pre = exp;
		  syntax.BinaryOperation now;
		  int i=3;
		  //ע��,����Ҫ����
		  for(;i+1 < node.jjtGetNumChildren();i+=2)
		  {
			  now = new syntax.BinaryOperation();
			  now.e1 = pre;
			  now.setOP(((SimpleNode)node.jjtGetChild(i)).jjtGetValue().toString());
			  now.e2 = generateSynatx((SimpleNode)node.jjtGetChild(i+1));
			  pre = now;
		  }
		  program = pre;
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTCOND_OP:
		  break;
	  case WggSimPLParserTreeConstants.JJTADDSUBEXPRESSION:
	  {
		  syntax.BinaryOperation exp = new syntax.BinaryOperation();
		  exp.e1 = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  exp.setOP(((SimpleNode)node.jjtGetChild(1)).jjtGetValue().toString());
		  exp.e2 = generateSynatx((SimpleNode)node.jjtGetChild(2));
		  syntax.BinaryOperation pre = exp;
		  syntax.BinaryOperation now;
		  int i=3;
		  //ע��,����Ҫ����
		  for(;i+1 < node.jjtGetNumChildren();i+=2)
		  {
			  now = new syntax.BinaryOperation();
			  now.e1 = pre;
			  now.setOP(((SimpleNode)node.jjtGetChild(i)).jjtGetValue().toString());
			  now.e2 = generateSynatx((SimpleNode)node.jjtGetChild(i+1));
			  pre = now;
		  }
		  program = pre;
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTADDSUB_OP:
		  break;
	  case WggSimPLParserTreeConstants.JJTMULDIVEXPRESSION:
	  {
		  syntax.BinaryOperation exp = new syntax.BinaryOperation();
		  exp.e1 = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  exp.setOP(((SimpleNode)node.jjtGetChild(1)).jjtGetValue().toString());
		  exp.e2 = generateSynatx((SimpleNode)node.jjtGetChild(2));
		  syntax.BinaryOperation pre = exp;
		  syntax.BinaryOperation now;
		  int i=3;
		  //ע��,����Ҫ����
		  for(;i+1 < node.jjtGetNumChildren();i+=2)
		  {
			  now = new syntax.BinaryOperation();
			  now.e1 = pre;
			  now.setOP(((SimpleNode)node.jjtGetChild(i)).jjtGetValue().toString());
			  now.e2 = generateSynatx((SimpleNode)node.jjtGetChild(i+1));
			  pre = now;
		  }
		  program = pre;
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTMULDIV_OP:
		  break;
	  case WggSimPLParserTreeConstants.JJTUOPEXPRESSION:
	  {
		  syntax.Expression pre=null;
		  int i = node.jjtGetNumChildren()-1;
		  pre = generateSynatx((SimpleNode)node.jjtGetChild(i));
		  i--;
		  for(; i >= 0; i--)
		  {
			  String op = ((SimpleNode)node.jjtGetChild(i)).jjtGetValue().toString();
			  if(op.equals("head"))
			  {
				  syntax.Head now = new syntax.Head();
				  now.e = pre;
				  pre = now;
			  }
			  else if(op.equals("tail"))
			  {
				  syntax.Tail now = new syntax.Tail();
				  now.e = pre;
				  pre = now;
			  }
			  else if(op.equals("snd"))
			  {
				  syntax.Second now = new syntax.Second();
				  now.e = pre;
				  pre = now;
			  }
			  else if(op.equals("fst"))
			  {
				  syntax.First now = new syntax.First();
				  now.e = pre;
				  pre = now;
			  }
			  else if(op.equals("~"))
			  {
				  syntax.UnaryOperation now = new syntax.UnaryOperation();
				  now.e = pre;
				  now.op = syntax.UnaryOperation.UnaryOperator.negative;
				  pre = now;
			  }
			  else if(op.equals("not"))
			  {
				  syntax.UnaryOperation now = new syntax.UnaryOperation();
				  now.e = pre;
				  now.op = syntax.UnaryOperation.UnaryOperator.not;
				  pre = now;
			  }
		  }
		  program = pre;
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTUOP:
		  break;
	  case WggSimPLParserTreeConstants.JJTVINT:
	  {
		  syntax.IntValue exp = new syntax.IntValue();
		  program = exp;
		  String str = node.jjtGetValue().toString();
		  if(str.equals("undef"))
		  {
			  exp.isUndef = true;
		  }
		  else
		  {
			  exp.isUndef = false;
			  exp.value = Integer.parseInt(str);
		  }
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTVBOOL:
	  {
		  syntax.BoolValue exp = new syntax.BoolValue();
		  program = exp;
		  String str = node.jjtGetValue().toString();
		  if(str.equals("true"))
		  {
			  exp.value = true;
		  }
		  else
		  {
			  exp.value = false;
		  }
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTVNIL:
	  {
		  syntax.Nil exp = new syntax.Nil();
		  program = exp;
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTVNOP:
	  {
		  syntax.Nop exp = new syntax.Nop();
		  program = exp;
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTPAIREXP:
	  {
		  syntax.Pair exp = new syntax.Pair();
		  program = exp;
		  exp.e1 = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  exp.e2 = generateSynatx((SimpleNode)node.jjtGetChild(1));
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTAPPEXP:
	  {
		  syntax.Application exp = new syntax.Application();
		  program = exp;
		  exp.func = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  exp.param = generateSynatx((SimpleNode)node.jjtGetChild(1));
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTLETEXP:
	  {
		  syntax.LetInEnd exp = new syntax.LetInEnd();
		  program = exp;
		  exp.x = new syntax.Variable();
		  exp.x.name = ((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString();
		  exp.definition = generateSynatx((SimpleNode)node.jjtGetChild(1));
		  exp.body = generateSynatx((SimpleNode)node.jjtGetChild(2));
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTIFEXP:
	  {
		  syntax.IfThenElse exp = new syntax.IfThenElse();
		  program = exp;
		  exp.condition = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  exp.thenClause=generateSynatx((SimpleNode)node.jjtGetChild(1));
		  exp.elseClause = generateSynatx((SimpleNode)node.jjtGetChild(2));
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTWHILEEXP:
	  {
		  syntax.WhileDoEnd exp = new syntax.WhileDoEnd();
		  program = exp;
		  exp.condition=generateSynatx((SimpleNode)node.jjtGetChild(0));
		  exp.body=generateSynatx((SimpleNode)node.jjtGetChild(1));
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTBRAEXP:
	  {
		  syntax.Bracket exp = new syntax.Bracket();
		  program = exp;
		  exp.e = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  break;
	  }
	  case WggSimPLParserTreeConstants.JJTPRINTEXP:
	  {
		  syntax.WggPrint exp = new syntax.WggPrint();
		  program = exp;
		  exp.e = generateSynatx((SimpleNode)node.jjtGetChild(0));
		  break;
	  }
	 }
	 return program;
 }
 static public String run(String programStr)
 {
	 try {
		    WggSimPLParser parser = new WggSimPLParser(new java.io.ByteArrayInputStream(programStr.getBytes()));
		    parser.syntax_Analysis();
		    SimpleNode node = (SimpleNode) parser.getTreeRoot();
		    Expression program = null;
		    program= generateSynatx(node);
		    return program.measure(new Env()).toString();
		} 
		catch (SimPLException e2)
		{
			return e2.toString();//+" "+e2.msg;
		}
		catch (ParseException e1) {
			return "Syntax Error!";
		}
		catch(TokenMgrError e3)
		{
			return "Syntax Error!";
		}
 }
}

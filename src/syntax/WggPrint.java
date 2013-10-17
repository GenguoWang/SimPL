package syntax;

import syntax.Type.TypeEnum;

public class WggPrint extends Expression{
	public Expression e;
	
	public String toString(){
		return "wggprint " + e.toString();
	}
	public Type checkType(TypeMap tm)
	{
		return e.checkType(tm);
	}
	public Value measure(Env env) throws SimPLException
	{
		System.out.println(e.measure(env));
		return new Nop();
	}
}
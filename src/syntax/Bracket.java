package syntax;

public class Bracket extends Expression{
	public Expression e;
	
	public String toString(){
		return "(" + e.toString() + ")";
	}
	public Type checkType(TypeMap tm)
	{
		return e.checkType(tm);
	}
	public Value measure(Env env) throws SimPLException
	{
		return e.measure(env);
	}
}
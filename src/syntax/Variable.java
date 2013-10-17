package syntax;

public class Variable extends Expression{
	public String name;
	
	public String toString(){
		return name;
	}
	public Type checkType(TypeMap tm)
	{
		//System.out.print("here");
		Type t = tm.getType(name);
		if(!t.OK())
		{
			if(Type.isDebug)
			{
				System.out.print("TypeError! var "+name+" "+t);
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		return env.get(name);
	}
}
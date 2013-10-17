package syntax;

public class AnonymousFunction extends Value{
	public Variable arg;
	public Expression body;
	public Env env = null;
	public Env scope = null;
	public AnonymousFunction()
	{
		this.type.typeid = Type.TypeEnum.t_function;
		this.type.fun = this;
	}
	public String toString(){
		return "fun " + arg.toString() + " -> " + body.toString();
	}
	
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_function;
		t.fun = this;
		t.tm = new TypeMap(tm);
		return t;
	}
	public Type runType(TypeMap tm,Type argt)
	{
		tm.addType(arg.name, argt);
		return body.checkType(tm);
	}
	public Value measure(Env env) throws SimPLException
	{
		//this.type.typeid = Type.TypeEnum.t_function;
		//if(Env.funEnv != null)
		//{
			AnonymousFunction cur = new AnonymousFunction();
			cur.arg = this.arg;
			cur.body = this.body;
			cur.scope = env;
			return cur;
		//}
		//return this;
	}
	public Value run(Env env,Value par)  throws SimPLException
	{
		env = new Env(this.scope);
		/*
		if(this.env!=null)
		{
			env.add(this.env);
			env = new Env(env);
		}
		*/
		env.newValue(arg.name, par);
		Value v = body.measure(env);
		/*
		if(v.type.typeid == Type.TypeEnum.t_function)
		{
			AnonymousFunction tempf= (AnonymousFunction)v;
			tempf.env = new Env();
			tempf.env.add(this.env);
			tempf.env.add(arg.name, par);
		}
		*/
		return v;
	}
	public boolean equals(Value t)
	{
		if(this.type.equals(t.type))
		{
			return this == t;
		}
		return false;
	}
}
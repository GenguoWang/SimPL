package syntax;

import syntax.Type.TypeEnum;

public class LetInEnd extends Expression{
	public Variable x;
	public Expression definition;
	public Expression body;
	
	public String toString(){
		return "let " + x.toString() + " = " + definition.toString() + " in " + body.toString() + " end";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		t.t1 = definition.checkType(tm);
		if(t.t1.OK())
		{
			TypeMap newtm = new TypeMap(tm);
			newtm.addType(x.name, t.t1);
			if(t.t1.typeid==Type.TypeEnum.t_function)
			{
				t.t1.tm = new TypeMap(newtm);
			}
			t.t2 = body.checkType(newtm);
			if(t.t2.OK())
			{
				t.typeid = t.t2.typeid;
			}
			else
			{
				if(Type.isDebug)System.out.println(this);
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		Value vdef = definition.measure(env);
		Env newenv = null;
		if(vdef.type.typeid == TypeEnum.t_function)
		{
			AnonymousFunction fun = (AnonymousFunction)vdef;
			env = new Env(env);
			env.newValue(x.name, vdef);
			fun.scope = env;
			newenv = new Env(env);
		}
		else
		{
			newenv = new Env(env);
			newenv.newValue(x.name, vdef);
		}
		
		return body.measure(newenv);
	}
}
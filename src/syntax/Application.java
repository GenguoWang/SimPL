package syntax;

import syntax.SimPLException.errortype;

public class Application extends Expression{
	public Expression func;
	public Expression param;

	public String toString(){
		return "(" + func.toString() + " " + param.toString() + ")";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		t.t1 = func.checkType(tm);
		t.t2 = param.checkType(tm);
		if(t.t1.typeid!=Type.TypeEnum.t_error && t.t2.typeid!=Type.TypeEnum.t_error )
		{
			if(t.t1.typeid==Type.TypeEnum.t_function)
			{
				TypeMap newtm = new TypeMap(t.t1.tm);
				Type rt = t.t1.fun.runType(newtm,t.t2);
				if(rt.OK())
				{
					t =rt;
				}
			}
			else
			{
				if(Type.isDebug)
				{
					System.out.println("TypeError! App not func: "+t.t1.typeid);
				}
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		Value f =  func.measure(env);
		Value p = param.measure(env);
		if(f.type.typeid == Type.TypeEnum.t_function)
		{
			AnonymousFunction fun = (AnonymousFunction)f;
			Env newenv = new Env(env);
			return fun.run(newenv,p);
		}
		else
		{
			throw new SimPLException("not function",errortype.type_error);
		}
	}
}
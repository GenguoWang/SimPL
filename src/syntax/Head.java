package syntax;

import syntax.SimPLException.errortype;

public class Head extends Expression{
	public Expression e;
	
	public String toString(){
		return "head " + e.toString();
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		Type tmp = e.checkType(tm);
		if(tmp.OK())
		{
			if(tmp.typeid == Type.TypeEnum.t_list)
			{
				t.typeid = tmp.t1.typeid;
			}
			else
			{
				if(Type.isDebug)
				{
					System.out.println("TypeError! head: "+tmp.typeid);
					System.out.println(this);
				}
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		Value v = e.measure(env);
		if(v.type.typeid != Type.TypeEnum.t_list)
		{
			throw new SimPLException("not t_list",errortype.type_error);
		}
		if(v.type.t1.typeid == Type.TypeEnum.t_nil)
		{
			return v;
		}
		else return ((ListValue)v).head;
	}
}
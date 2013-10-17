package syntax;

import syntax.SimPLException.errortype;

public class Second extends Expression{
	public Expression e;
	
	public String toString(){
		return "snd " + e.toString();
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		Type tmp = e.checkType(tm);
		if(tmp.OK())
		{
			if(tmp.typeid == Type.TypeEnum.t_pair)
			{
				t.typeid = tmp.t2.typeid;
			}
			else
			{
				if(Type.isDebug)
				{
					System.out.println("TypeError! snd: "+tmp.typeid);
					System.out.println(this);
				}
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		Value v = e.measure(env);
		if(v.type.typeid != Type.TypeEnum.t_pair)
		{
			throw new SimPLException("not pair",errortype.type_error);
		}
		return ((PairValue)v).e2;
	}
}
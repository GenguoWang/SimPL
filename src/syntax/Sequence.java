package syntax;

import syntax.SimPLException.errortype;

public class Sequence extends Expression{
	public Expression e1;
	public Expression e2;

	public String toString(){
		return e1.toString() + "; " + e2.toString();
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		t.t1 = e1.checkType(tm);
		t.t2 = e2.checkType(tm);
		if(t.t1.OK()&&t.t2.OK())
		{
			if(t.t1.typeid == Type.TypeEnum.t_unit)
			{
				t.typeid = t.t2.typeid;
			}
			else
			{
				if(Type.isDebug)
				{
					System.out.println("TypeError! seq: "+t.t1.typeid);
					System.out.println(this);
				}
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		Value v1 = e1.measure(env);
		if(v1.type.typeid != Type.TypeEnum.t_unit)
		{
			throw new SimPLException("seq not unit: "+v1.type,errortype.type_error);
		}
		return e2.measure(env);
	}
}
package syntax;

import syntax.SimPLException.errortype;

public class IfThenElse extends Expression{
	public Expression condition;
	public Expression thenClause;
	public Expression elseClause;
	
	public String toString(){
		return "if " + condition.toString() + " then " + thenClause.toString() + " else " + elseClause.toString() + " end ";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		t.t1 = condition.checkType(tm);
		if(t.t1.OK())
		{
			if(t.t1.typeid==Type.TypeEnum.t_bool)
			{
				t.t1 = thenClause.checkType(tm);
				t.t2 = elseClause.checkType(tm);
				if(t.t1.typeid == t.t2.typeid)
				{
					t.typeid = t.t1.typeid;
				}
				else
				{
					if(Type.isDebug)
					{
						System.out.println("TypeError! If then else: "+t.t1.typeid+ " "+t.t2.typeid);
						System.out.println(this);
					}
				}
			}
			else
			{
				if(Type.isDebug)
				{
					System.out.println("TypeError! If cond: "+t.t1.typeid);
					System.out.println(this);
				}
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		Value vcod = condition.measure(env);
		if(vcod.type.typeid==Type.TypeEnum.t_bool)
		{
			if(((BoolValue)vcod).value)
			{
				return thenClause.measure(env);
			}
			else
			{
				return elseClause.measure(env);
			}
		}
		else
		{
			throw new SimPLException("not bool",errortype.type_error);
		}
	}
}
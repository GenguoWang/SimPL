package syntax;

import syntax.SimPLException.errortype;

public class WhileDoEnd extends Expression{
	public Expression condition;
	public Expression body;
	
	public String toString(){
		return "while " + condition.toString() + " do " + body.toString() + " end";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		t.t1 = condition.checkType(tm);
		t.t2 = body.checkType(tm);
		if(t.t1.OK()&&t.t2.OK())
		{
			if(t.t1.typeid == Type.TypeEnum.t_bool && t.t2.typeid==Type.TypeEnum.t_unit)
			{
				t.typeid = Type.TypeEnum.t_unit;
			}
			else
			{
				if(Type.isDebug)
				{
					System.out.println("TypeError! while: "+t.t1.typeid+ " "+t.t2.typeid);
					System.out.println(this);
				}
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		Value vcond = condition.measure(env);
		if(vcond.type.typeid != Type.TypeEnum.t_bool)
		{
			throw new SimPLException("not bool",errortype.type_error);
		}
		if(((BoolValue)vcond).value)
		{
			Value v = body.measure(env);
			if(v.type.typeid != Type.TypeEnum.t_unit)
			{
				throw new SimPLException("body not t_unit",errortype.type_error);
			}
			return this.measure(env);
		}
		else
		{
			Nop res = new Nop();
			res.type.typeid = Type.TypeEnum.t_unit;
			return res;
		}
	}
}

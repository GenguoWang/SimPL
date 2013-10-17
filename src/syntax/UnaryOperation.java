package syntax;

import syntax.SimPLException.errortype;

public class UnaryOperation extends Expression{
	public enum UnaryOperator{
		not, negative
	}
	
	public Expression e;
	public UnaryOperator op;

	public String toString(){
		String operator = "";
		switch(op){
		case not:
			operator = "not"; break;
		case negative:
			operator = "~ "; break;
		}
		return operator + e.toString();
	}
	
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		switch(this.op){
		case negative:
			t.t1 = e.checkType(tm);
			if(t.t1.OK())
			{
				if(t.t1.typeid ==  Type.TypeEnum.t_int)
				{
					t.typeid = Type.TypeEnum.t_int;
				}
				else
				{
					if(Type.isDebug)
					{
						System.out.println("TypeError! negative: "+t.t1.typeid);
						System.out.println(this);
					}
				}
			}
			break;
		case not:
			t.t1 = e.checkType(tm);
			if(t.t1.OK())
			{
				if(t.t1.typeid ==  Type.TypeEnum.t_bool)
				{
					t.typeid = Type.TypeEnum.t_bool;
				}
				else
				{
					if(Type.isDebug)
					{
						System.out.println("TypeError! not: "+t.t1.typeid);
						System.out.println(this);
					}
				}
			}
			break;
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		Value v1 = e.measure(env);
		switch(this.op){
		case negative:
			if(v1.type.typeid ==  Type.TypeEnum.t_int)
			{
				IntValue res = new IntValue();
				res.type.typeid = Type.TypeEnum.t_int;
				res.value = -((IntValue)v1).value;
				return res;
			}
			else
			{
				throw new SimPLException("not int",errortype.type_error);
			}
			//break;
		case not:
			if(v1.type.typeid ==  Type.TypeEnum.t_bool)
			{
				BoolValue res = new BoolValue();
				res.type.typeid = Type.TypeEnum.t_bool;
				res.value = !((BoolValue)v1).value;
				return res;
			}
			else
			{
				throw new SimPLException("not bool",errortype.type_error);
			}
			//break;
		}
		throw new SimPLException("unreachable unary oper");
	}
}
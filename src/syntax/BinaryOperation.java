package syntax;

import syntax.SimPLException.errortype;

public class BinaryOperation extends Expression{
	public enum BinaryOperator{
		plus, minus, times, devide, biggerThan, lessThan, equal, and, or
	}
	
	public Expression e1;
	public Expression e2;
	public BinaryOperator op;
	public void setOP(String str)
	{
		if(str=="+")
		{
			op = BinaryOperator.plus;
		}
		else if(str=="-")
		{
			op = BinaryOperator.minus;
		}
		else if(str=="*")
		{
			op = BinaryOperator.times;
		}
		else if(str=="/")
		{
			op = BinaryOperator.devide;
		}
		else if(str==">")
		{
			op = BinaryOperator.biggerThan;
		}
		else if(str=="<")
		{
			op = BinaryOperator.lessThan;
		}
		else if(str=="=")
		{
			op = BinaryOperator.equal;
		}
		else if(str=="and")
		{
			op = BinaryOperator.and;
		}
		else if(str=="or")
		{
			op = BinaryOperator.or;
		}
	}
	public String toString(){
		String operator = "";
		switch(this.op){
		case plus:
			operator = "+"; break;
		case minus:
			operator = "-"; break;
		case times:
			operator = "*"; break;
		case devide:
			operator = "/"; break;
		case biggerThan:
			operator = ">"; break;
		case lessThan:
			operator = "<"; break;
		case equal:
			operator = "="; break;
		case and:
			operator = "and"; break;
		case or:
			operator = "or"; break;
		}
		return e1.toString() + " " + operator + " " + e2.toString();
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		switch(this.op){
		case plus:
		case minus:
		case times:
		case devide:
			t.t1 = e1.checkType(tm);
			t.t2 = e2.checkType(tm);
			if(t.t1.OK() && t.t2.OK())
			{
				if(t.t1.typeid ==  Type.TypeEnum.t_int&& t.t2.typeid ==Type.TypeEnum.t_int)
				{
					t.typeid = Type.TypeEnum.t_int;
				}
				else
				{
					if(Type.isDebug)
					{
						System.out.println("TypeError! +-*/: "+t.t1.typeid+" "+t.t2.typeid);
						System.out.println(this);
					}
				}
			}
			break;
		case biggerThan:
		case lessThan:
			t.t1 = e1.checkType(tm);
			t.t2 = e2.checkType(tm);
			if(t.t1.OK() && t.t2.OK())
			{
				if(t.t1.typeid ==  Type.TypeEnum.t_int&& t.t2.typeid ==Type.TypeEnum.t_int)
				{
					t.typeid = Type.TypeEnum.t_bool;
				}
				else
				{
					if(Type.isDebug)
					{
						System.out.println("TypeError! ><: "+t.t1.typeid+" "+t.t2.typeid);
						System.out.println(this);
					}
				}
			}
			break;
		case equal:
			t.t1 = e1.checkType(tm);
			t.t2 = e2.checkType(tm);
			if(t.t1.OK() && t.t2.OK())
			{
				if(t.t1.typeid == t.t2.typeid)
				{
					t.typeid = Type.TypeEnum.t_bool;
				}
				else
				{
					if(Type.isDebug)
					{
						System.out.println("TypeError! =: "+t.t1.typeid+" "+t.t2.typeid);
						System.out.println(this);
					}
				}
			}
			break;
		case and:
		case or:
			t.t1 = e1.checkType(tm);
			t.t2 = e2.checkType(tm);
			if(t.t1.OK() && t.t2.OK())
			{
				if(t.t1.typeid ==  Type.TypeEnum.t_bool&& t.t2.typeid ==Type.TypeEnum.t_bool)
				{
					t.typeid = Type.TypeEnum.t_bool;
				}
				else
				{
					if(Type.isDebug)
					{
						System.out.println("TypeError! and or: "+t.t1.typeid+" "+t.t2.typeid);
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
		Value v1 = e1.measure(env);
		Value v2 = e2.measure(env);
		//Value res;
		switch(this.op){
		case plus:
			if(v1.type.typeid ==  Type.TypeEnum.t_int&& v2.type.typeid ==Type.TypeEnum.t_int)
			{
				IntValue iv1 = (IntValue)v1;
				IntValue iv2 = (IntValue)v2;
				IntValue res = new IntValue();
				res.type.typeid = Type.TypeEnum.t_int;
				if(iv1.isUndef||iv2.isUndef)
				{
					res.isUndef = true;
				}
				else
				{
					res.value = iv1.value+iv2.value;
				}
				return res;
			}
			else
			{
				throw new SimPLException(this.op+" "+v1+" "+v2,errortype.type_error);
			}
			//break;
		case minus:
			if(v1.type.typeid ==  Type.TypeEnum.t_int&& v2.type.typeid ==Type.TypeEnum.t_int)
			{
				IntValue iv1 = (IntValue)v1;
				IntValue iv2 = (IntValue)v2;
				IntValue res = new IntValue();
				res.type.typeid = Type.TypeEnum.t_int;
				if(iv1.isUndef||iv2.isUndef)
				{
					res.isUndef = true;
				}
				else
				{
					res.value = iv1.value-iv2.value;
				}
				return res;
			}
			else
			{
				throw new SimPLException(this.op+" "+v1+" "+v2,errortype.type_error);
			}
			//break;
		case times:
			if(v1.type.typeid ==  Type.TypeEnum.t_int&& v2.type.typeid ==Type.TypeEnum.t_int)
			{
				IntValue iv1 = (IntValue)v1;
				IntValue iv2 = (IntValue)v2;
				IntValue res = new IntValue();
				res.type.typeid = Type.TypeEnum.t_int;
				if(iv1.isUndef||iv2.isUndef)
				{
					res.isUndef = true;
				}
				else
				{
					res.value = iv1.value*iv2.value;
				}
				return res;
			}
			else
			{
				throw new SimPLException(this.op+" "+v1+" "+v2,errortype.type_error);
			}
			//break;
		case devide:
			if(v1.type.typeid ==  Type.TypeEnum.t_int&& v2.type.typeid ==Type.TypeEnum.t_int)
			{
				IntValue iv1 = (IntValue)v1;
				IntValue iv2 = (IntValue)v2;
				IntValue res = new IntValue();
				res.type.typeid = Type.TypeEnum.t_int;
				if(iv1.isUndef||iv2.isUndef)
				{
					res.isUndef = true;
				}
				else
				{
					res.value = iv1.value/iv2.value;
				}
				return res;
			}
			else
			{
				throw new SimPLException(this.op+" "+v1+" "+v2,errortype.type_error);
			}
			//break;
		case biggerThan:
			if(v1.type.typeid ==  Type.TypeEnum.t_int&& v2.type.typeid ==Type.TypeEnum.t_int)
			{
				IntValue iv1 = (IntValue)v1;
				IntValue iv2 = (IntValue)v2;
				BoolValue res = new BoolValue();
				res.type.typeid = Type.TypeEnum.t_bool;
				if(iv1.isUndef||iv2.isUndef)
				{
					throw new SimPLException("compare undef",errortype.runtime_error);
				}
				else
				{
					res.value = iv1.value > iv2.value;
				}
				return res;
			}
			else
			{
				throw new SimPLException(this.op+" "+v1+" "+v2,errortype.type_error);
			}
			//break;
		case lessThan:
			if(v1.type.typeid ==  Type.TypeEnum.t_int&& v2.type.typeid ==Type.TypeEnum.t_int)
			{
				IntValue iv1 = (IntValue)v1;
				IntValue iv2 = (IntValue)v2;
				BoolValue res = new BoolValue();
				res.type.typeid = Type.TypeEnum.t_bool;
				if(iv1.isUndef||iv2.isUndef)
				{
					throw new SimPLException("compare undef",errortype.runtime_error);
				}
				else
				{
					res.value = iv1.value < iv2.value;
				}
				return res;
			}
			else
			{
				throw new SimPLException(this.op+" "+v1+" "+v2,errortype.type_error);
			}
			//break;
		case equal:

			if(v1.type.equals(v2.type))
			{
				BoolValue res = new BoolValue();
				res.type.typeid = Type.TypeEnum.t_bool;
				res.value = v1.equals(v2);
				return res;
			}
			else
			{
				throw new SimPLException("not same type",errortype.type_error);
			}
			//break;
		case and:
			if(v1.type.typeid ==  Type.TypeEnum.t_bool&& v2.type.typeid ==Type.TypeEnum.t_bool)
			{
				BoolValue iv1 = (BoolValue)v1;
				BoolValue iv2 = (BoolValue)v2;
				BoolValue res = new BoolValue();
				res.type.typeid = Type.TypeEnum.t_bool;
				res.value = iv1.value && iv2.value;
				return res;
			}
			else
			{
				throw new SimPLException("not t_bool",errortype.type_error);
			}
			//break;
		case or:
			if(v1.type.typeid ==  Type.TypeEnum.t_bool&& v2.type.typeid ==Type.TypeEnum.t_bool)
			{
				BoolValue iv1 = (BoolValue)v1;
				BoolValue iv2 = (BoolValue)v2;
				BoolValue res = new BoolValue();
				res.type.typeid = Type.TypeEnum.t_bool;
				res.value = iv1.value || iv2.value;
				return res;
			}
			else
			{
				throw new SimPLException("not t_bool",errortype.type_error);
			}
			//break;
		}
		return null;
	}
}
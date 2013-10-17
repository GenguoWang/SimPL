package syntax;

import syntax.SimPLException.errortype;

public class List extends Expression{
	public Expression head;
	public Expression tail;	
	
	public String toString(){
		return "[" + head.toString() + ", " + tail.toString() + "]";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.t1 = head.checkType(tm);
		t.t2 = tail.checkType(tm);
		if(t.t1.typeid != Type.TypeEnum.t_error && t.t2.typeid != Type.TypeEnum.t_error )
		{
			if(t.t2.typeid==Type.TypeEnum.t_list && (t.t2.t1.typeid == Type.TypeEnum.t_nil||t.t1.typeid == t.t2.t1.typeid))
			{
				t.typeid = Type.TypeEnum.t_list;
			}
			else
			{
				t.typeid = Type.TypeEnum.t_error;
				if(Type.isDebug)
				{
					System.out.println("TypeError! List head: "+t.t1.typeid+" tail: "+t.t2.typeid);
				}
			}
		}
		else
		{
			t.typeid = Type.TypeEnum.t_error;
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		ListValue v = new ListValue();
		v.type.typeid = Type.TypeEnum.t_list;
		v.head = head.measure(env);
		v.tail = tail.measure(env);
		if(v.tail.type.typeid==Type.TypeEnum.t_list && (v.tail.type.t1.typeid == Type.TypeEnum.t_nil||v.head.type.typeid == v.tail.type.t1.typeid))
		{
			v.type.t1 = v.head.type;
		}
		else
		{
			throw new SimPLException("list type error",errortype.type_error);
		}
		
		return v;
	}
}
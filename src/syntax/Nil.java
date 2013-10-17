package syntax;

import syntax.Type.TypeEnum;

public class Nil extends Value{
	public Nil()
	{
		type.typeid = TypeEnum.t_list;
		type.t1 = new Type();
		type.t1.typeid = TypeEnum.t_nil;
	}
	public String toString(){
		return "nil";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_list;
		t.t1 = new Type();
		t.t1.typeid = Type.TypeEnum.t_nil;
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		this.type.typeid = Type.TypeEnum.t_list;
		this.type.t1 = new Type();
		this.type.t1.typeid = Type.TypeEnum.t_nil;
		return this;
	}
	public boolean equals(Value t)
	{
		if(this.type.equals(t.type))
		{
			return true;
		}
		return false;
	}
}
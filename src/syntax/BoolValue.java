package syntax;

public class BoolValue extends Value{
	public boolean value;
	public BoolValue()
	{
		this.type.typeid = Type.TypeEnum.t_bool;
	}
	public String toString(){
		if(value)
			return "true";
		else
			return "false";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_bool;
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		this.type.typeid = Type.TypeEnum.t_bool;
		return this;
	}
	public boolean equals(Value t)
	{
		if(this.type.equals(t.type))
		{
			return this.value == ((BoolValue)t).value;
		}
		return false;
	}
}
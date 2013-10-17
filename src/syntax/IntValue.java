package syntax;

public class IntValue extends Value{
	public boolean isUndef;
	public int value;
	public IntValue()
	{
		type.typeid = Type.TypeEnum.t_int;
	}
	public String toString(){
		if(isUndef)
			return "undef";
		else
			return String.valueOf(value);
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_int;
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		return this;
	}
	public boolean equals(Value t)
	{
		if(this.type.equals(t.type))
		{
			return this.value == ((IntValue)t).value;
		}
		return false;
	}
}
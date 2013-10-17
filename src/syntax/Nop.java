package syntax;

public class Nop extends Value{
	public Nop()
	{
		this.type.typeid = Type.TypeEnum.t_unit;
	}
	public String toString(){
		return "()";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_unit;
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		this.type.typeid = Type.TypeEnum.t_unit;
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
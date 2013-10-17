package syntax;

public class PairValue extends Value{
	public Value e1;
	public Value e2;
	public PairValue()
	{
		this.type.typeid = Type.TypeEnum.t_pair;
	}
	public String toString(){
		return "(" + e1.toString() + ", " + e2.toString() + ")";
	}
	public Value measure(Env env) throws SimPLException
	{
		this.type.typeid = Type.TypeEnum.t_pair;
		this.type.t1 = e1.type;
		this.type.t2 = e2.type;
		return this;
	}
	public boolean equals(Value t)
	{
		if(this.type.equals(t.type))
		{
			return e1.equals(((PairValue)t).e1) && e2.equals(((PairValue)t).e2);
		}
		return false;
	}
}
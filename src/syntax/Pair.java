package syntax;

public class Pair extends Expression{
	public Expression e1;
	public Expression e2;
	
	public String toString(){
		return "(" + e1.toString() + ", " + e2.toString() + ")";
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.t1 = e1.checkType(tm);
		t.t2 = e2.checkType(tm);
		if(t.t1.typeid != Type.TypeEnum.t_error && t.t2.typeid != Type.TypeEnum.t_error )
		{
			t.typeid = Type.TypeEnum.t_pair;
		}
		else
		{
			t.typeid = Type.TypeEnum.t_error;
		}
		return t;
	}
	
	public Value measure(Env env) throws SimPLException
	{
		PairValue v = new PairValue();
		v.type.typeid = Type.TypeEnum.t_pair;
		v.e1 = e1.measure(env);
		v.e2 = e2.measure(env);
		v.type.t1 = v.e1.type;
		v.type.t2 = v.e2.type;
		return v;
	}
	
}
package syntax;

public class Expression{
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		return t;
	};
	public Value measure(Env env) throws SimPLException
	{
		SimPLException e = new SimPLException();
		e.msg = "function not implemented";
		throw e;
	}
}
package syntax;

import syntax.SimPLException.errortype;

public class Assignment extends Expression{
	public Expression var;
	public Expression val;
	
	public String toString(){
		return var.toString() + " := " + val.toString();
	}
	public Type checkType(TypeMap tm)
	{
		Type t = new Type();
		t.typeid = Type.TypeEnum.t_error;
		t.t1 = var.checkType(tm);
		t.t2 = val.checkType(tm);
		if(t.t1.OK() && t.t2.OK())
		{
			if(t.t1.typeid==t.t2.typeid)
			{
				t.typeid = Type.TypeEnum.t_unit;
			}
			else
			{
				if(Type.isDebug)
				{
					System.out.println("TypeError!Assign : "+t.t1.typeid+" "+t.t2.typeid);
				}
			}
		}
		return t;
	}
	public Value measure(Env env) throws SimPLException
	{
		if(!var.getClass().getName().equals("syntax.Variable"))
		{
			throw new SimPLException("left exp not Variable: "+var.getClass().getName(),errortype.runtime_error);
		}
		else
		{
			Value vvar = var.measure(env);
			Value vval = val.measure(env);
			if(!vvar.type.equals(vval.type))
			{
				throw new SimPLException("assgin type not match:"+vvar.type+" "+vval.type,errortype.type_error);
			}
			env.put(((Variable)var).name, vval);
			Nop res = new Nop();
			return res;
		}
	}
}
package syntax;

public class Type {
	public enum TypeEnum{
		t_error,t_int,t_bool,t_pair,t_list,t_function,t_unit,t_nil
	}
	static boolean isDebug = false;
	public TypeEnum typeid;
	public Type t1=null,t2=null;
	public AnonymousFunction fun=null;
	public TypeMap tm=null;
	public boolean OK()
	{
		if(typeid != TypeEnum.t_error)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isInt()
	{
		return typeid==TypeEnum.t_int;
	}
	public String toString()
	{
		switch(this.typeid)
		{
		case t_error:
		case t_int:
		case t_bool:
		case t_unit:
		case t_nil:
		case t_function:
			return this.typeid.toString();
		case t_pair:
			return this.t1.toString()+" * " +this.t2.toString();
		case t_list:
			return this.t1.toString()+" list";
		}
		return "";
	}
	public boolean equals(Type t)
	{
		switch(this.typeid)
		{
		case t_error:
		case t_int:
		case t_bool:
		case t_unit:
		case t_nil:
		case t_function:
			return this.typeid == t.typeid;
		case t_pair:
			return this.typeid == t.typeid && this.t1.equals(t.t1) && this.t2.equals(t.t2);
		case t_list:
			//nil equals any list type
			return this.typeid == t.typeid && (this.t1.typeid == TypeEnum.t_nil||t.t1.typeid==TypeEnum.t_nil||this.t1.equals(t.t1));
		}
		return false;
	}
}

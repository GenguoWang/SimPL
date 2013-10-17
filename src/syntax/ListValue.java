package syntax;

public class ListValue extends Value{
	public Value head;
	public Value tail;
	public ListValue()
	{
		this.type.typeid = Type.TypeEnum.t_list;
	}
	public String toString(){
		return "[" + head.toString() + ", " + tail.toString() + "]";
	}
	public Value measure(Env env) throws SimPLException
	{
		this.type.typeid = Type.TypeEnum.t_list;
		this.type.t1 = head.type;
		return this;
	}
	public boolean equals(Value t)
	{
		if(this.type.equals(t.type))
		{
			if(this.type.t1.typeid == Type.TypeEnum.t_nil && t.type.t1.typeid == Type.TypeEnum.t_nil)
			{
				//both nil
				return true;
			}
			else if(this.type.t1.typeid == Type.TypeEnum.t_nil || t.type.t1.typeid == Type.TypeEnum.t_nil)
			{
				//only one nil
				return false;
			}
			return head.equals(((ListValue)t).head) && tail.equals(((ListValue)t).tail);
		}
		return false;
	}
}
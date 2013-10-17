package syntax;
import java.util.HashMap;
public class TypeMap {
	protected HashMap<String,Type> map = null;
	@SuppressWarnings("unchecked")
	public TypeMap(TypeMap old)
	{
		if(old.map!=null)
		{
			map = (HashMap<String,Type>)old.map.clone();
		}
		else
		{
			map = new HashMap<String,Type>();
		}
	}
	public TypeMap()
	{
		map = new HashMap<String,Type>();
	}
	public Type getType(String name)
	{
		if(map.containsKey(name))
		{
			return map.get(name);
		}
		else
		{
			Type t = new Type();
			t.typeid = Type.TypeEnum.t_error;
			return t;
		}
	}
	public void addType(String name, Type t)
	{
		map.put(name, t);
	}
}

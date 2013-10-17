package syntax;
import java.util.HashMap;

import syntax.SimPLException.errortype;
public class Env {
	static Env funEnv = null;
	protected HashMap<String,Value> map = null;
	protected Env parent = null;
	public Env()
	{
		map = new HashMap<String,Value>();
	}
	public Env(Env par)
	{
		map = new HashMap<String,Value>();
		parent = par;
	}
	public Value get(String name) throws SimPLException
	{
		Env cur = this;
		while(cur!=null)
		{
			if(cur.map.containsKey(name))
			{
				return cur.map.get(name);
			}
			cur = cur.parent;
		}
		throw new SimPLException(name +" not existed",errortype.runtime_error);
	}
	public void newValue(String name,Value v) throws SimPLException
	{
		if(map.containsKey(name))
		{
			throw new SimPLException(name +" duplicate declaration",errortype.runtime_error);
		}
		else
		{
			map.put(name, v);
		}
	}
	public void put(String name,Value v) throws SimPLException
	{
		Env cur = this;
		boolean flag = true;
		while(cur!=null)
		{
			if(cur.map.containsKey(name))
			{
				cur.map.put(name, v);
				flag = false;
				break;
			}
			cur = cur.parent;
		}
		if(flag)throw new SimPLException(name +" not existed",errortype.runtime_error);
	}
	public void add(Env old)
	{
		if(old==null)return;
		for(String key:old.map.keySet())
		{
			map.put(key, old.map.get(key));
		}
	}
	public void add(String name,Value v)
	{
		map.put(name, v);
	}
}

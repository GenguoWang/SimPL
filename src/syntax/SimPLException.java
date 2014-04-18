package syntax;

public class SimPLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5563214176562406987L;
	public String msg;
	public errortype etype;
	public enum errortype{synatax_error,type_error,runtime_error}; 
	public SimPLException(String msg)
	{
		this.msg = msg;
	}
	public SimPLException(String msg,errortype tt)
	{
		this.msg = msg;
		this.etype = tt;
	}
	public SimPLException()
	{
	}
	public String toString()
	{
		if(etype!=null)
		{
			switch(etype)
			{
			case synatax_error:
				return "Syntax Error!"+msg;
			case type_error:
				return "Type Error!"+msg;
			case runtime_error:
				return "Runtime Error!"+msg;
			}
		}
		return "no type";
	}
}

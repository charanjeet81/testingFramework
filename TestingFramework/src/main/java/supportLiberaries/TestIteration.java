package supportLiberaries;

public enum TestIteration
{
	RunSingleIteration("done"),
	RunAllIteration("fail"), 
	RunRangeIteration("pass"); 
	
	private String value;
	
	TestIteration(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}

}

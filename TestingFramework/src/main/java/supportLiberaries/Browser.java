package supportLiberaries;

public enum Browser 
{
	Chrome("Chrome"),
	Firefox("Firefox"), 
	Internet("Internet"), 
	Android("Android"),
	iPhone("iPhone"),
	HeadLessExecution("headless");
	
	private String value;
	
	Browser(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}

}

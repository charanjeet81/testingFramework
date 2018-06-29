package supportLiberaries;

public enum Status 
{
	DONE("done"),
	FAIL("fail"), 
	PASS("pass"), 
	SCREENSHOT("screenshot");
	
	private String value;
	
	Status(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}

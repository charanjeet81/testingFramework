package businessComponents;

import applicationPages.SanityTest;
import supportLiberaries.ReusableLiberaries;
import supportLiberaries.ScriptHelper;

public class BASIC_SANITY_TEST extends ReusableLiberaries
{
	public BASIC_SANITY_TEST(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	public void tC01_Sanity_Test_For_SIGMA_CPQ_Standalone()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToSIGMA()
				  .customerSearch();
	}
	public void tC02_Sanity_Test_For_SIGMA_CPQ_Salesforce_Plugin()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .validateBlankPage();
	}
	public void tC03_Sanity_Test_For_SIGMA_CPQ_Salesforce_Website_Login()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToSalesforce();
	}
	public void tC04_Sanity_Test_For_SIGMA_Catalogue()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToSIGMACatalog();
		
	}
	public void tC05_Sanity_Test_For_SIGMA_Catalogue_API()
	{
		
	}
	public void tC06_Sanity_Test_For_SIGMA_Catalogue_Identity()
	{
		
	}
	public void tC07_Sanity_Test_For_SIGMA_Catalogue_Management()
	{
		
	}
	public void tC08_Sanity_Test_For_SIGMA_Catalogue_Services()
	{
		
	}
	public void tC09_Sanity_Test_For_SIGMA_OM_Sigma_Admin()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToSIGMA_OM("OM Admin");
	}
	public void tC10_Sanity_Test_For_SIGMA_OM_Sigma_AnA()
	{
		
	}
	public void tC11_Sanity_Test_For_SIGMA_OM_Designer()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToSIGMA_OM("Designer");
	}
	public void tC12_Sanity_Test_For_SIGMA_OM_Runtime()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToSIGMA_OM("Runtime");
	}
	public void tC13_Sanity_Test_For_SIGMA_OM_Admin()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToSIGMA_OM("Admin");
	}
	public void tC14_Sanity_Test_For_NOKIA_PnA_Instant_Link()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
		          .loginToNOKIA_OM("Order Management");
	}
	public void tC15_Sanity_Test_For_NOKIA_SOA_WS()
	{
		
	}
	public void tC16_Sanity_Test_For_NOKIA_OpenAM()
	{
		
	}
	public void tC17_Sanity_Test_For_NOKIA_Service_Catalogue()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToComptelCatalog();
	}
	public void tC18_Sanity_Test_For_NOKIA_FlowOne_API()
	{
		
	}
	public void tC19_Sanity_Test_For_NOKIA_OrderHub()
	{
		
	}
	public void tC20_Sanity_Test_For_Stub_Server()
	{
		
	}
	public void tC21_Sanity_Test_For_SIS_Network_Platform()
	{
		
	}
	public void tC22_Sanity_Test_For_NOKIA_PnA_Workflow_Client()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
		          .loginToNOKIA_OM("Workflow Client");
	}
	public void tC23_Sanity_Test_For_IDAM()
	{
		SanityTest sanityTest = new SanityTest(scriptHelper);
		sanityTest.invokeApplication()
				  .loginToIDAM();
	}

	
	
}

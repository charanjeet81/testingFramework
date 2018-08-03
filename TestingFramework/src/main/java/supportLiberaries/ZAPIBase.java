//package supportLiberaries;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import org.json.JSONException;
//import org.json.simple.parser.ParseException;
//
//import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
//import com.thed.zephyr.cloud.rest.client.JwtGenerator;
//import com.zapi.Utilities.AddAttachment;
//import com.zapi.Utilities.AddTestsToCycle;
//import com.zapi.Utilities.CloneExecution;
//import com.zapi.Utilities.CreateCycle;
//import com.zapi.Utilities.CreateIssue;
//import com.zapi.Utilities.GetCycleList;
//import com.zapi.Utilities.GetIssueID;
//import com.zapi.Utilities.GetTestList;
//import com.zapi.Utilities.GetVersions;
//
//public class ZAPIBase {
//
//	public ZFJCloudRestClient client = null;
//
//	public final String ZEPHYR_URL = "https://prod-api.zephyr4jiracloud.com/connect";
//	public String JIRA_URL = null;
//
//	public String ACCESS_KEY = null;
//	public String SECRET_KEY = null;
//
//	public String USERNAME = null;
//	public String PASSWORD = null;
//	public String projectID = null;
//
//	public String GET_VERSIONS_URI = "{BASE}/rest/api/2/project/{projectId}/versions";
//	public String CREATE_CYCLE_URI = "{BASE}/public/rest/api/1.0/cycle";
//	public String GET_CYCLES_URI = "{BASE}/public/rest/api/1.0/cycles/search?";
//	public String ADD_TESTS_URI = "{BASE}/public/rest/api/1.0/executions/add/cycle/";
//	public String GET_TESTS_IN_CYCLE_URI = "{BASE}/public/rest/api/1.0/executions/search/cycle/";
//	public String ADD_ATTACHMENT_URI = "{BASE}/public/rest/api/1.0/attachment";
//	public String ADD_TEST_STEPS_URI = "{BASE}/public/rest/api/1.0/teststep/";
//	public String EXECUTE_TESTS_URI = "{BASE}/public/rest/api/1.0/execution/";
//	public String CREATE_TEST_URI = "{BASE}/rest/api/2/issue";
//	public String SEARCH_ISSUE_URI = "{BASE}/rest/api/2/search";
//
//	public String currentTest = null;
//
//	public static String Version_ID = null;
//	public static String Cycle_Name = null;
//	public static String Issue_ID = null;
//	public static String Issue_Key = null;
//	public static String Comment = null;
//	public static String Execution = "execution";
//
//	public ZapiBase(String currentTest) {
//		try {
//			this.currentTest = currentTest;
//			getData(currentTest);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		client = ZFJCloudRestClient.restBuilder(ZEPHYR_URL, ACCESS_KEY, SECRET_KEY, USERNAME).build();
//	}
//
//	public ZapiBase() {
//		try {
//			projectID = getAppProperties("JIRA_ProjectID");
//			JIRA_URL = getAppProperties("JIRA_URL");
//			ACCESS_KEY = getAppProperties("JIRA_AccessKey");
//			SECRET_KEY = getAppProperties("JIRA_SecretKey");
//			USERNAME = getAppProperties("JIRA_UserName");
//			PASSWORD = getAppProperties("JIRA_Password");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		client = ZFJCloudRestClient.restBuilder(ZEPHYR_URL, ACCESS_KEY, SECRET_KEY, USERNAME).build();
//	}
//
//	*//**
//	 * This method fetches the JIRA-Zephyr data from the data.properties file and
//	 * assigns them for execution
//	 * 
//	 * @param currentTest Current Test Name
//	 * @throws Exception
//	 *//*
//	public void getData(String currentTest) throws Exception {
//		com.mop.qa.Utilities.ReadDataSheet rd = new com.mop.qa.Utilities.ReadDataSheet();
//
//		Cycle_Name = rd.getValue("DATA", currentTest, "JIRA_CycleName");
//		Issue_Key = rd.getValue("DATA", currentTest, "JIRA_IssueKey");
//
//		USERNAME = getAppProperties("JIRA_UserName");
//		PASSWORD = getAppProperties("JIRA_Password");
//		ACCESS_KEY = getAppProperties("JIRA_AccessKey");
//		SECRET_KEY = getAppProperties("JIRA_SecretKey");
//		projectID = getAppProperties("JIRA_ProjectID");
//		JIRA_URL = getAppProperties("JIRA_URL");
//
//	}
//
//	*//**
//	 * Generate token to authenticate user
//	 * 
//	 * @param requestType GET/POST/PUT/DELETE
//	 * @param requestUri  URL for token to be generated
//	 * @return JWT Token
//	 *//*
//	public String generateJwtToken(String requestType, String requestUri) {
//
//		URI uri = null;
//		int expirationInSec = 360;
//		JwtGenerator jwtGenerator = null;
//		String jwt = null;
//		try {
//			uri = new URI(requestUri);
//			jwtGenerator = client.getJwtGenerator();
//			jwt = jwtGenerator.generateJWT(requestType, uri, expirationInSec);
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//
//		return jwt;
//	}
//
//	*//**
//	 * This method is used to create an issue/bug in JIRA
//	 * 
//	 * @param summary Summary of the issue to be created
//	 * @return ID of the created Issue
//	 * @throws Exception
//	 *//*
//	public String createIssue(String summary) throws Exception {
//		CreateIssue createTestObj = new CreateIssue(currentTest);
//		return createTestObj.createIssue(projectID, summary);
//	}
//
//	*//**
//	 * This method is used to get the version ID of all existing versions in Plan
//	 * Test Cycle component in Zephyr. If the version is invalid or does not exists,
//	 * it returns an unscheduled version ID
//	 * 
//	 * @param versionName Version Name
//	 * @return version ID of the version name
//	 * @throws ParseException
//	 *//*
//	public String getVersionID(String versionName) throws ParseException {
//		GetVersions getVersions = new GetVersions();
//
//		Map<String, String> map = getVersions.getVersionList(ACCESS_KEY);
//		String versionID = "-1";
//		if (map.size() > 0) {
//			for (Map.Entry<String, String> m : map.entrySet()) {
//				if (versionName.equalsIgnoreCase("Unscheduled")) {
//					versionID = "-1";
//					break;
//				} else if (versionName.equalsIgnoreCase(m.getValue())) {
//					versionID = m.getKey();
//					break;
//				}
//			}
//		} else {
//			System.out.println("No Versions created. Using unscheduled version for reference.");
//		}
//		return versionID;
//	}
//
//	*//**
//	 * This method is used to execute a Zephyr test case
//	 * 
//	 * @param versionName  Version Name of the test cycle
//	 * @param issueKey     Issue ID of the test case
//	 * @param comment      optional Comments to be added to test execution
//	 * @param listOfTests  List of tests in the current cycle
//	 * @param listOfCycles List of cycles in the current version
//	 * @param status       Status to be updated for the test execution
//	 * @throws Exception
//	 *//*
//	public void cloneExecution(String versionName, String issueKey, String comment, Map<String, String> listOfTests,
//			Map<String, Map<String, String>> listOfCycles, String status) throws Exception {
//		CloneExecution executeTestObj = new CloneExecution();
//
//		String executionID = null;
//		String cycleID = null;
//		String tempKey;
//
//		GetIssueID getIssueObj = new GetIssueID();
//		String issueID = getIssueObj.getIssueID(issueKey);
//
//		Map<String, String> tempList = new HashMap<>();
//
//		for (Map.Entry<String, Map<String, String>> entry1 : listOfCycles.entrySet()) {
//			tempKey = (String) entry1.getKey();
//			if (tempKey.equals(Version_ID)) {
//				tempList = (Map<String, String>) entry1.getValue();
//				for (Map.Entry<String, String> entry2 : tempList.entrySet()) {
//					if (Cycle_Name.equals(entry2.getValue())) {
//						cycleID = (String) entry2.getKey();
//					}
//				}
//			}
//		}
//
//		for (Map.Entry<String, String> entry : listOfTests.entrySet()) {
//			if (issueID.equals(entry.getValue())) {
//				executionID = (String) entry.getKey();
//			}
//		}
//
//		executeTestObj.updateExectionStatus(status, cycleID, projectID, Version_ID, ACCESS_KEY, comment, executionID,
//				issueID);
//
//	}
//
//	/**
//	 * This method is used to attach a file to a test execution in Zephyr test cycle
//	 * 
//	 * @param listOfTests    List of tests in the current cycle
//	 * @param listOfCycles   List of cycles in the current version
//	 * @param attachmentPath File path of the file to be attached
//	 * @param issueKey       JIRA issue ID of the test case
//	 * @throws Exception
//	 */
//	public void addAttachment(Map<String, String> listOfTests, Map<String, Map<String, String>> listOfCycles,
//			String attachmentPath, String issueKey) throws Exception {
//		AddAttachment attachmentObj = new AddAttachment();
//		String entityName = Execution;
//		String versionID = Version_ID;
//		String cycleName = Cycle_Name;
//		String tempKey;
//
//		String cycleID = null;
//		String entityID = null;
//		GetIssueID getIssueObj = new GetIssueID();
//		String issueID = getIssueObj.getIssueID(issueKey);
//
//		Map<String, String> tempList = new HashMap<>();
//
//		for (Map.Entry<String, Map<String, String>> entry1 : listOfCycles.entrySet()) {
//			tempKey = (String) entry1.getKey();
//			if (tempKey.equals(versionID)) {
//				tempList = (Map<String, String>) entry1.getValue();
//				for (Map.Entry<String, String> entry2 : tempList.entrySet()) {
//					if (cycleName.equals(entry2.getValue())) {
//						cycleID = (String) entry2.getKey();
//					}
//				}
//			}
//		}
//
//		for (Map.Entry<String, String> entry : listOfTests.entrySet()) {
//			if (issueID.equals(entry.getValue())) {
//				entityID = (String) entry.getKey();
//			}
//		}
//
//		attachmentObj.attachFile(projectID, versionID, ACCESS_KEY, cycleID, issueID, entityName, entityID,
//				attachmentPath, "");
//	}
//
//	/**
//	 * This method is used to get the list of tests in the current cycle
//	 * 
//	 * @param listOfCycles List of cycles in the current version
//	 * @param cycleName    Cycle name of the test to be executed
//	 * @return Map of test cases in the current cycle in the format of {Test
//	 *         Execution ID = IssueID}
//	 * @throws IOException
//	 * @throws URISyntaxException
//	 * @throws JSONException
//	 */
//	public Map<String, String> getTestList(Map<String, Map<String, String>> listOfCycles, String cycleName)
//			throws IOException, URISyntaxException, JSONException {
//		GetTestList getTestListObj = new GetTestList();
//
//		Map<String, String> testList = new HashMap<>();
//		Map<String, String> tempList = new HashMap<>();
//
//		String cycleID = null;
//		String tempkey;
//
//		Cycle_Name = cycleName;
//
//		for (Map.Entry<String, Map<String, String>> entry1 : listOfCycles.entrySet()) {
//			tempkey = (String) entry1.getKey();
//			if (tempkey.equals(Version_ID)) {
//				tempList = entry1.getValue();
//				for (Map.Entry<String, String> entry2 : tempList.entrySet()) {
//					if (Cycle_Name.equals(entry2.getValue())) {
//						cycleID = (String) entry2.getKey();
//					}
//				}
//			}
//		}
//
//		testList = getTestListObj.getListOfTests(cycleID, projectID, Version_ID, ACCESS_KEY);
//		return testList;
//	}
//
//	*//**
//	 * This method is used to get the list of cycles in the current Version
//	 * 
//	 * @param versionName
//	 * @return Map of test cases in the current cycle in the format of {Version ID =
//	 *         {Cycle ID = Cycle Name}}
//	 * @throws Exception
//	 *//*
//	public Map<String, Map<String, String>> getListOfCycles(String versionName) throws Exception {
//
//		GetCycleList getCyclesObj = null;
//		getCyclesObj = new GetCycleList();
//
//		Map<String, String> listOfCycles = new HashMap<>();
//		Map<String, Map<String, String>> mapOfMaps = new HashMap<>();
//
//		Version_ID = getVersionID(versionName);
//
//		listOfCycles = getCyclesObj.getCycleList(projectID, Version_ID, ACCESS_KEY);
//		mapOfMaps.put(Version_ID, listOfCycles);
//
//		return mapOfMaps;
//
//	}
//
//	*//**
//	 * This method is used to get the properties of a particular key from the
//	 * data.properties file
//	 * 
//	 * @param key Key name
//	 * @return Value of the key
//	 * @throws IOException
//	 *//*
//	public String getAppProperties(String key) throws IOException {
//		String value = "";
//		try {
//
//			FileInputStream fileInputStream = new FileInputStream("data.properties");
//			Properties property = new Properties();
//			property.load(fileInputStream);
//
//			value = property.getProperty(key);
//
//			fileInputStream.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return value;
//
//	}
//
//	*//**
//	 * This method is used to create a cycle in Zephyr
//	 * 
//	 * @param cycleName   Cycle Name
//	 * @param description Description of the created cycle
//	 * @return Cycle ID
//	 * @throws IOException
//	 * @throws JSONException
//	 *//*
//	public String createTestCycle(String cycleName, String description) throws IOException, JSONException {
//		CreateCycle cycleObj = new CreateCycle();
//
//		String createdCycleID = cycleObj.createCycle(projectID, Version_ID, ACCESS_KEY, cycleName, description);
//		return createdCycleID;
//	}
//
//	*//**
//	 * This method is used to add tests to a cycle
//	 * 
//	 * @param listOfCycles List of the cycles in current version
//	 * @throws IOException
//	 * @throws URISyntaxException
//	 *//*
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void addTestsToCycle(Map<String, Map<String, String>> listOfCycles) throws IOException, URISyntaxException {
//		AddTestsToCycle addTestObj = new AddTestsToCycle();
//		boolean result = false;
//
//		String issueList = getAppProperties("JIRA_AddTestList");
//		String[] issues = issueList.split(",");
//
//		String tempkey;
//		Map<String, String> testList = new HashMap<>();
//		String cycleID = null;
//
//		for (Map.Entry entry1 : listOfCycles.entrySet()) {
//			tempkey = (String) entry1.getKey();
//			if (tempkey.equals(Version_ID)) {
//				testList = (Map<String, String>) entry1.getValue();
//				for (Map.Entry entry2 : testList.entrySet()) {
//					if (Cycle_Name.equals(entry2.getValue())) {
//						cycleID = (String) entry2.getKey();
//					}
//				}
//			}
//		}
//
//		result = addTestObj.addTestCycle(projectID, Version_ID, ACCESS_KEY, cycleID, issues);
//
//		if (result) {
//			System.out.println("Added Tests successfully");
//		} else {
//			System.out.println("Failed to add Tests");
//		}
//
//	}
//
//}
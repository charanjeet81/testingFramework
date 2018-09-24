Feature: SANITY

Scenario Outline: TC02_Sanity_Test_Login_Correct_Credential

Given user is already on Login Page
When title of login page is Free CRM
Then user enters "<username>" and "<password>"
Then Close the browser
And quit the browser
#Then user clicks on login button
#Then user is on home page

Examples:
	| username | password |
	| naveenk  | test@123 | 
	| charan   | test@123 | 



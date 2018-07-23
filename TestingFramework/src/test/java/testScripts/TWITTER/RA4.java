package testScripts.TWITTER;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class RA4 
{
	String ConsumerKey = "FJdE5pGTAbPtPPntW7E38iiYV";
	String ConsumerSecret = "Wbqelqu4Y2JctGLv2WqOvhxEJDXKXvNBZSE5ZFgY8ggPZGY0r4";
	String Token = "1068525116-VjoxRfnvi1uOwIM3o0hWFxKi7HSV7xldIq7xSLg";
	String TokenSecret = "mvMJToCxbkc22KjoPdZAXcFLkW4edWJj1O1Gg7ffMNEnp";
	String id;

	@Test
	public void getLatestTweet() 
	{
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		Response res = given()
				.auth()
				.oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret)
				.queryParam("count", "1")
				.when()
				.get("/home_timeline.json")
				.then()
				.extract()
				.response();

		String response = res.asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);

		System.out.println(js.get("text").toString());
		System.out.println(js.get("id").toString());
	}

	@Test
	public void createTweet() 
	{
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		Response res = given()
				.auth()
				.oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret)
				.queryParam("status", "I am creating this tweet with Automation other one")
				.when()
				.post("/update.json")
				.then()
				.extract()
				.response();

		String response = res.asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		System.out.println("Below is the tweet added========>>>");
		System.out.println(js.get("text").toString());
		System.out.println(js.get("id").toString());
	}

	/*@Test
	public void E2E()
	{
		createTweet();
		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		Response res = given().auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret).when()
				.post("/destroy/" + id + ".json").then().extract().response();

		String response = res.asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		// System.out.println(js.get("text"));
		System.out.println("Tweet which got deleted with automation is below");
		System.out.println(js.get("text").toString());
		System.out.println(js.get("truncated").toString());
	}*/
}

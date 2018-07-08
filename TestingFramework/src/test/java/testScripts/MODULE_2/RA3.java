package testScripts.MODULE_2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RA3 
{
	String placeId;
	@Test
  public void createPlace_Test() 
  {
	  RestAssured.baseURI = "https://maps.googleapis.com";
	  String createPlace_Request = "{  \"location\": {    \"lat\": -33.8669710,    \"lng\": 151.1958750  },  \"accuracy\": 50,  \"name\": \"Google Shoes!\",   \"phone_number\": \"(02) 9374 4000\",   \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\",   \"types\": [\"shoe_store\"],  \"website\": \"http://www.google.com.au/\",  \"language\": \"en-AU\" }";
	  
	  // Creating Place
	  Response createPlace_Response = given()
						  .queryParam("key", "AIzaSyDlxWVxZPkOVTslECBOqX8PjpK68t8EZF0")
						  .body(createPlace_Request)
						  .when()
						  .post("/maps/api/place/add/json")
						  .then()
						  .assertThat()
						  .statusCode(200)
						  .and()
						  .body("status", equalTo("OK"))
						  .and()
						  .extract()
						  .response();
	  //System.out.println(response);
	  System.out.println(createPlace_Response.asString()); // RawResponse
	  
	  JsonPath jsonPath = new JsonPath(createPlace_Response.asString()); 
	  placeId = jsonPath.get("place_id");
	  Assert.assertEquals(jsonPath.get("status"), "OK");
  }
  
  @Test ()
  public void deletePlace_Test() 
  {
	  RestAssured.baseURI = "https://maps.googleapis.com";
	  String deletePlace_Request = "{ \"place_id\": \""+placeId+"\" }";
	  
	  // Delete Place
	  Response deletePlace_Response = given()
						  .queryParam("key", "AIzaSyDlxWVxZPkOVTslECBOqX8PjpK68t8EZF0")
						  .body(deletePlace_Request)
						  .when()
						  .post("/maps/api/place/delete/json")
						  .then()
						  .extract()
						  .response();
	  //System.out.println(response);
	  System.out.println(deletePlace_Response.asString()); // RawResponse
	  
	  JsonPath jsonPath = new JsonPath(deletePlace_Response.asString()); 
	  Assert.assertEquals(jsonPath.get("status"), "OK");
  }
}

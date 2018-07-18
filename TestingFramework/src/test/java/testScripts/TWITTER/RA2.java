package testScripts.MODULE_2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class RA2 {
  @Test
  public void validatingGet() 
  {
	  RestAssured.baseURI = "https://maps.googleapis.com";
	  given()
	  .queryParam("key", "AIzaSyDlxWVxZPkOVTslECBOqX8PjpK68t8EZF0")
	  .body("{  \"location\": {    \"lat\": -33.8669710,    \"lng\": 151.1958750  },  \"accuracy\": 50,  \"name\": \"Google Shoes!\",   \"phone_number\": \"(02) 9374 4000\",   \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\",   \"types\": [\"shoe_store\"],  \"website\": \"http://www.google.com.au/\",  \"language\": \"en-AU\" }")
	  .when()
	  .post("/maps/api/place/add/json")
	  .then()
	  .assertThat()
	  .statusCode(200)
	  .and()
	  .body("status", equalTo("OK"))
	  .and().header("Content-Type", "application/json; charset=UTF-8");
	  
  }
}

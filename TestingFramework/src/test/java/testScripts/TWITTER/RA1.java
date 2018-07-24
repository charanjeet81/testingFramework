package testScripts.TWITTER;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class RA1 {
  @Test
  public void validatingGet() 
  {
//	  RestAssured.baseURI = "https://maps.googleapis.com";
//	  given()
//	  .param("location", "-33.8670522,151.1957362")
//	  .param("radius", "1500")
//	  .param("key", "AIzaSyDlxWVxZPkOVTslECBOqX8PjpK68t8EZF0")
//	  .when()
//	  .get("/maps/api/place/nearbysearch/json")
//	  .then()
//	  .assertThat()
//	  .statusCode(200)
//	  .and()
//	  .contentType(ContentType.JSON)
//	  .and()
//	  .body("results[0].vicinity", equalTo("Sydney"))
//	  .and().body("results[1].place_id", equalTo("ChIJFfyzTTeuEmsRuMxvFyNRfbk"))
//	  .and().header("server", "scaffolding on HTTPServer2");
	  
/*	  RestAssured.baseURI = "https://maps.googleapis.com";
	  String response1 = given()
	  .when()
	  .get("/maps/api/place/nearbysearch/json")
	  .then()
	  .assertThat()
	  .statusCode(200)
	  .and()
	  .contentType(ContentType.JSON)
	  .and().extract().asString();
	  System.out.println(response1);*/
	  
	  RestAssured.baseURI = "https://reqres.in";
	  String response3 = given()
			  .when()
			  .get("/api/users/2")
			  .then()
			  .assertThat()
			  .contentType(ContentType.JSON)
			  .and().extract().response().body().prettyPrint();
			  
	  RestAssured.baseURI = "https://reqres.in";
	  String response2 = given()
			  .param("page", "2")
			  .when()
			  .get("/api/users")
			  .then()
			  .assertThat()
			  .statusCode(200)
			  .and()
			  .contentType(ContentType.JSON)
			  .and().extract().response().body().prettyPrint();
  }
}

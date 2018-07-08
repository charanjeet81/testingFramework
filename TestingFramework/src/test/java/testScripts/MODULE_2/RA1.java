package testScripts.MODULE_2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class RA1 {
  @Test
  public void validatingGet() 
  {
	  RestAssured.baseURI = "https://maps.googleapis.com";
	  given()
	  .param("location", "-33.8670522,151.1957362")
	  .param("radius", "1500")
	  .param("key", "AIzaSyDlxWVxZPkOVTslECBOqX8PjpK68t8EZF0")
	  .when()
	  .get("/maps/api/place/nearbysearch/json")
	  .then()
	  .assertThat()
	  .statusCode(200)
	  .and()
	  .contentType(ContentType.JSON)
	  .and()
	  .body("results[0].vicinity", equalTo("Sydney"))
	  .and().body("results[1].place_id", equalTo("ChIJFfyzTTeuEmsRuMxvFyNRfbk"))
	  .and().header("server", "scaffolding on HTTPServer2");
	  
  }
}

package SwaggerUIWithRestAssured;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredAPI {

	private static final String JSONSerializer = null;
	public String accessToken;
	public String id;

	@Test(priority=0)
	public void getAccessToken() {

		RestAssured.baseURI = "http://rest-api.upskills.in/api/rest_admin/";
		String response = given().header("content-Type", "application/json")
				.header("Authorization", "Basic "
						+ "dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9jbGllbnQ6dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9zZWNyZXQ=")
				.when().post("/oauth2/token/client_credentials").then().extract().response().asString();
		System.out.println("Client response " + response);
		JSONObject jsonobj= new JSONObject(response);
		accessToken= jsonobj.getJSONObject("data").getString("access_token");
		System.out.println("accessToken is: "+accessToken);
		
	}

	@Test(priority=1)
	public void LogIntoSwaggerUIApplicationAsAdmin() {

		RestAssured.baseURI = "http://rest-api.upskills.in/api/rest_admin";
        System.out.println("access Token"+accessToken);
		String requestBody = "{\"username\" : \"upskills_admin\",\r\n" + " \"password\" : \"Talent4$$\"\r\n" + " }";
		Response response = given().header("content-Type", "application/json")
				.header("Authorization", "Bearer "+ accessToken)
				.body(requestBody).when().post("/login").then().assertThat().statusCode(200).extract().response();
	}

	@Test(priority=2)
	public void AddNewCustomers() {

		RestAssured.baseURI = "http://rest-api.upskills.in/api/rest_admin";
		String requestBody = "{\r\n" + "	\"firstname\": \"Ian\",\r\n" + "\r\n" + "	\"lastname\": \"Baxter\",\r\n"
				+ "\r\n" + "	\"email\": \"ianbaxternew1@mailinator.com\",\r\n" + "\r\n"
				+ "	\"password\": \"Password1\",\r\n" + "\r\n" + "	\"confirm\": \"Password1\",\r\n" + "\r\n"
				+ "	\"telephone\": \"234567890\"\r\n" + "}";

		Response response = given().header("content-Type", "application/json").header("Authorization", "Bearer "+ accessToken)
				.body(requestBody).when().post("/customers")
				.then().assertThat().statusCode(200).extract().response();
		System.out.println("Adding Customer "+response.asString());


		requestBody = "{\r\n" + "	\"firstname\": \"Rachel\",\r\n" + "\r\n" + "	\"lastname\": \"Worm\",\r\n"
				+ "\r\n" + "	\"email\": \"rachelwormnew1@mailinator.com\",\r\n" + "\r\n"
				+ "	\"password\": \"Password90\",\r\n" + "\r\n" + "	\"confirm\": \"Password90\",\r\n" + "\r\n"
				+ "	\"telephone\": \"2789567890\"\r\n" + "}";

		response = given().header("content-Type", "application/json").header("Authorization", "Bearer "+ accessToken)
				.body(requestBody).when().post("/customers").then().assertThat().statusCode(200).extract().response();
		System.out.println("Adding Customer "+response.asString());

		requestBody = "{\r\n" + "	\"firstname\": \"David\",\r\n" + "\r\n" + "	\"lastname\": \"John\",\r\n" + "\r\n"
				+ "	\"email\": \"davidjohnnew1@mailinator.com\",\r\n" + "\r\n" + "	\"password\": \"Password902\",\r\n"
				+ "\r\n" + "	\"confirm\": \"Password902\",\r\n" + "\r\n" + "	\"telephone\": \"27895656890\"\r\n"
				+ "}";

		response = given().header("content-Type", "application/json").header("Authorization", "Bearer "+ accessToken)
				.body(requestBody).when().post("/customers").then().assertThat().statusCode(200).extract().response();
		System.out.println("Adding Customer "+response.asString());

	}

	@Test(priority=3)
	public void getCustomerDetailsById() {

		RestAssured.baseURI = "http://rest-api.upskills.in/api/rest_admin/";
		String response = given().header("content_Type", "application/json").header("Authorization", "Bearer "+ accessToken)
				.when().get("/customers/4043").then().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println("Customer Details of the ID-4043--->");
		System.out.println(response);
	}

	@Test(priority=4)
	public void getAddedFromDetails() {

		RestAssured.baseURI = "http://rest-api.upskills.in/api/rest_admin/";
		String response = given().header("content_Type", "application/json").header("Authorization", "Bearer "+ accessToken)
				.when().get("/customers/added_from/2021-08-21").then().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println("List of all customers added from 21/08/2021--->");
		System.out.println(response);
	}
}

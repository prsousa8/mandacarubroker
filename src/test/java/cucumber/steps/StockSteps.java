package cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@CucumberContextConfiguration
public class StockSteps {

    public RequestSpecification httpRequest;
    public Response response;

    public ResponseBody body;
    public JSONObject requestParams;

    public String s;

    // Cenário 1 - GET - Teste de status code 200 para endpoint Get All Stocks
    @Given("I hit the url of get stocks api endpoint")
    public void iHitTheUrlOfGetStocksApiEndpoint() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @When("I pass the url in the request")
    public void iPassTheUrlInTheRequest() {
        httpRequest = RestAssured.given();
        response = httpRequest.get("stocks");
    }

    @Then("I receive the response code as {int}")
    public void iReceiveTheResponseCodeAs(int arg0) {
        int ResponseCode = response.getStatusCode();
        assertEquals(ResponseCode, arg0);
    }

    // Cenário 2 - GET - Teste de status code 200 e valores dos atributos para endpoint Get Id
    @When("I pass the url {} in the request")
    public void iPassTheUrlInTheRequest(String arg0) {
        httpRequest = RestAssured.given();
        response = httpRequest.get("stocks/"+arg0);
    }
    @And("Compare to {} e {} e {}")
    public void compareToEE(String symbol, String companyName, String price) {
        body = response.getBody();

        //JSON Representation from Response Body
        JsonPath jsnpath = response.jsonPath();

        String getSymbol = jsnpath.getJsonObject("symbol").toString();
        String getCompanyName = jsnpath.getJsonObject("companyName").toString();
        String getPrice = jsnpath.getJsonObject("price").toString();

        assertEquals(getSymbol,symbol);
        assertEquals(getCompanyName,companyName);
        assertEquals(getPrice,price);

    }

    // Cenário 3 - GET - Teste de status code 200 e quantidade de atributos em uma das ações para endpoint Get All Stocks
    @Then("I verify that the id of the first product is {int} letters")
    public void iVerifyThatTheIdOfTheFirstProductIsLetters(int arg0) {
        body = response.getBody();

        //JSON Representation from Response Body
        JsonPath jsnpath = response.jsonPath();

        String s = jsnpath.getJsonObject("id[1]").toString();


        assertTrue(s.length() <= arg0, "I verify that the id of the first product is {int} letters");
    }
    // Cenário 1 - POST - Teste de status code 200 e criação de uma ação
    @Given("I hit the url of post stocks api endpoint")
    public void iHitTheUrlOfPostStocksApiEndpoint() {
        RestAssured.baseURI = "http://localhost:8080/";
        httpRequest = RestAssured.given();
        requestParams = new JSONObject();
    }


    @And("I pass the request body of stock id {}")
    public void iPassTheRequestBodyOfStockId(String arg0) {
        requestParams.put("id", arg0);
        requestParams.put("CompanyName", "Cucumber Bank");
        requestParams.put("price", 67.90);
        requestParams.put("symbol", "SD4");

        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post("stocks");
        body = response.getBody();
        JsonPath jsnpath = response.jsonPath();


        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }
    // Cenário 1 - PUT - Teste de status code 200 e atualização de uma ação
    @Given("I hit the url of put stocks api endpoint")
    public void iHitTheUrlOfPutStocksApiEndpoint() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @When("I pass the url in the request with {}")
    public void iPassTheUrlInTheRequestWith(String arg0) {
        httpRequest = RestAssured.given();
        requestParams = new JSONObject();

        //requestParams.put("id","3fda04c0-26dc-4dc5-ae94-c53c925848ac");
        requestParams.put("companyName", "Cucumber Bank");
        requestParams.put("price", 67.90);
        requestParams.put("symbol", "SD4");

        httpRequest.body(requestParams.toJSONString());
        httpRequest.header("Content-Type", "application/json");
        response = httpRequest.put("stocks/" + arg0);
    }

    @Given("I hit the url of delete stocks api endpoint")
    public void iHitTheUrlOfDeleteStocksApiEndpoint() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @When("I pass the url in the request delete with {}")
    public void iPassTheUrlInTheRequestDeleteWith(String arg0) {
        httpRequest = RestAssured.given();
        response = httpRequest.delete("stocks/" + arg0);
    }
}

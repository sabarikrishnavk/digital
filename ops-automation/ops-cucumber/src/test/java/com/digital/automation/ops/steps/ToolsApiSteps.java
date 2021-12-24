package com.digital.automation.ops.steps;

import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ToolsApiSteps implements En {

  private static final String BASE_URL = "http://localhost:3080/apis/ops/ops-tools-api/tools/version";

  private static String token;
  private static Response response;
  private static String jsonString;
  private static String bookId;

  @Autowired
  public ToolsApiSteps() {

    RestAssured.baseURI = BASE_URL;
    RequestSpecification request = RestAssured.given();

    Given("I validate the version of api", () -> {
      request.header("Content-Type", "application/json");
      response = request.get();
//    request.body("{ \"userName\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
//              .post("/Account/v1/GenerateToken");

    });

    When("I get a http 200 response", () -> {
        Assert.assertEquals(200, response.getStatusCode());
    });

    Then("get the version of current deployment", () -> {

      jsonString = response.asString();
//      List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
      Assert.assertEquals(jsonString , "1.0");

//      bookId = books.get(0).get("isbn");
    });
  }
}

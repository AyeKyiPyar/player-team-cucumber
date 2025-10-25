package com.example.demo.cucumber.team;



import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TeamStepsDefinition 
{

    private Response response;
    private String baseUrl = "http://localhost:8080"; // replace with your API base URL
   // private String teamJson;
    private Map<String, Object> teamJson;


    // -------- Add a new team --------
    @Given("user have a team JSON")
    public void user_have_a_team_json() 
    {
        // Example team JSON
//        teamJson = "{ \"name\": \"Juventus\", \"country\": \"Italy\" }";
    	teamJson = new HashMap<>();
        teamJson.put("name", "Juventus");
        teamJson.put("country", "Italy");
    }

    @When("user send a POST request to {string}")
    public void user_send_post_request(String endpoint) 
    {
        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(teamJson)
                .post(baseUrl + endpoint);
    }
 // -------- Then the response status should be {int} --------
    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) 
    {
        assertThat(response.getStatusCode(), is(statusCode));
    }

    @Then("the response should contain the team details")
    public void the_response_should_contain_team_details() 
    {
    	System.out.println(response.getBody().asString());
//        assertThat(response.jsonPath().getString("name"), equalTo("Juventus"));
//        assertThat(response.jsonPath().getString("country"), equalTo("Italy"));
    	 assertThat(response.jsonPath().getString("name"), equalTo(teamJson.get("name")));
    	 assertThat(response.jsonPath().getString("country"), equalTo(teamJson.get("country")));
    }

   
}

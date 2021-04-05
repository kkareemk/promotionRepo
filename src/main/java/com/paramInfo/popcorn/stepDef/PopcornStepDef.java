package com.paramInfo.popcorn.stepDef;

import com.paramInfo.popcorn.utilities.RestAPIUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PopcornStepDef {

    RestAPIUtility restAPIUtility;

    public PopcornStepDef() {
        restAPIUtility = new RestAPIUtility();
    }

    @Given("I want to execute {string} api")
    public void i_want_to_execute_api(String apiName) {
        System.out.println("Executing "+apiName +" API");
    }

    @When("I submit GET request with endPoint as {string} with valid apiKey")
    public void i_submit_get_request_with_end_point_as(String endPoint) {
        restAPIUtility.getRequest(endPoint);
    }

    @When("I submit GET request with endPoint as {string} with invalid apiKey as {string}")
    public void i_submit_get_request_with_end_point_with_invalidKey(String endPoint,String invalidApiKey) {
        restAPIUtility.getRequest(endPoint,invalidApiKey);
    }

    @Then("I should get {string} as StatusCode in response")
    public void i_should_get_as_status_code_in_response(String statusCode) throws Exception {
        restAPIUtility.validateResponseCode(statusCode);
    }

    @Then("I should get response within {string} milliseconds")
    public void i_should_get_response_within_milliseconds(String timeThreshold) throws Exception {
        restAPIUtility.validateResponseTimeWithIn(timeThreshold);
    }

    @Then("I should validate response for the key {string} should have value as {string}")
    public void i_should_validate_response_should_have_value(String key,String expValue) throws Exception {
        restAPIUtility.iValidateResponseBodyWithKey(key,expValue);
    }

    @Then("I should validate response for the key {string} should not be null")
    public void i_should_validate_response_should_have_value(String key) throws Exception {
        restAPIUtility.iValidateResponseBodyWithKeyNotNull(key);
    }


    @Then("I should validate json schema in {string} the response")
    public void i_should_validate_json_schema_response(String schemaFileName) throws Exception {
        restAPIUtility.iValidateJsonSchema(schemaFileName);
    }


    @Then("I should validate response for the key {string} should be of type {string}")
    public void i_should_validate_response_should_be_of_type(String key,String type) throws Exception {
        restAPIUtility.iValidateResponseBodyWithKeyOfType(key,type);
    }


}

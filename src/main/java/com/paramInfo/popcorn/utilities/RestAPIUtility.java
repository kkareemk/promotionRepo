package com.paramInfo.popcorn.utilities;

import com.jayway.jsonpath.Configuration;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.minidev.json.JSONArray;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.jayway.jsonpath.JsonPath.*;
import static org.hamcrest.Matchers.lessThan;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class RestAPIUtility {

    RestConfigurator restConfigurator;
    Response response;
    String responseAsString;

    /**
     * This method will perform GET operation
     * @param endPoint -- this is the endpoint resource
     * @return -- will return Response object
     */
    public String getRequest(String endPoint, String... apiKey){
        restConfigurator=new RestConfigurator();
        response = restConfigurator.getResponse(endPoint,apiKey);
        responseAsString=response.getBody().asString();
        return responseAsString;
    }

    /**
     * This method will perform POST operation
     * @param endPoint -- this is the endpoint resource
     * @param payloadJsonFilePath -- this is the payload json filePath location
     */
    public void postRequest(String endPoint, String payloadJsonFilePath){
    }

    /**
     * This method will perform PUT operation
     * @param endPoint -- this is the endpoint resource
     * @param payloadJsonFilePath -- this is the payload json filePath location
     */
    public void putRequest(String endPoint, String payloadJsonFilePath){
    }

    /**
     * This method will validate response code
     * @param expectedStatusCode -- is the expected status code to be validated
     * @throws Exception
     */
    public void validateResponseCode(String expectedStatusCode) throws Exception {
        if(response==null){
            throw new Exception("Response is null, Status Code is not expected or unable to validate");
        }else{
            Assert.assertEquals(expectedStatusCode,String.valueOf(response.getStatusCode()));
        }
    }


    /**
     * This method will validate the response time
     * @param timeThreshold -- is the threshold time that response should be received
     * @throws Exception
     */
    public void validateResponseTimeWithIn(String timeThreshold) throws Exception {
        if(response==null){
            throw new Exception("Response is null, unable to validate response time");
        }else{
            try {
                long thresholdTime = Long.parseLong(timeThreshold);
                ValidatableResponse responseTime = response.then().time(lessThan(thresholdTime));
                responseTime.assertThat().log();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * This method will validate value for a particular key from the response object
     * @param key -- is the key for which value to be is validated
     * @param expValueToValidate -- is the expected value to check in the actual response
     * @throws Exception
     */
    public void iValidateResponseBodyWithKey(String key, String expValueToValidate) throws Exception {
        if (responseAsString.isEmpty()) {
            throw new Exception("Response is null, couldn't proceed further in validating");
        }else{
            Object parseResponseString = Configuration.defaultConfiguration().jsonProvider().parse(responseAsString);
            Object actual = read(parseResponseString, key);
            if(expValueToValidate.contains("|")){
                String[] actualStringArray = getStringArray((JSONArray) actual);
                String[] expElements = expValueToValidate.split("\\|");
                ArrayList<String> ar = new ArrayList<String>();
                for(int i = 0; i < actualStringArray.length; i++){
                    if(!Arrays.asList(expElements).contains(actualStringArray[i]))
                        ar.add(actualStringArray[i]);
                }
                if(ar.size()>0){
                    Assert.fail("This is not expected as "+ar+" is the value which is not from the list provided, that is :"+Arrays.asList(expValueToValidate));
                }else{
                    System.out.println("This is as expected as it matches the values from the list provided");
                }

            }else{
                if(expValueToValidate.equalsIgnoreCase(String.valueOf(actual))){
                    System.out.println("Value for the key:"+key+ " which is "+expValueToValidate +" present in the response");
                }else{
                    Assert.fail("Value for the key: "+key+ " which is "+expValueToValidate +" not present in the response");
                }
            }
        }
    }

    /**
     * This method will validate value for a particular key from the response object
     * @param key -- is the key for which value to be is validated
     * @throws Exception
     */
    public void iValidateResponseBodyWithKeyNotNull(String key) throws Exception {
        if (responseAsString.isEmpty()) {
            throw new Exception("Response is null, couldn't proceed further in validating");
        }else{
            Object parseResponseString = Configuration.defaultConfiguration().jsonProvider().parse(responseAsString);
            Object actual = read(parseResponseString, key);
            if(!(actual.toString().isEmpty())){
                System.out.println("Value for the key:"+key +" is not null and present in the response as "+actual);
            }else{
                Assert.fail("Value for the key:"+key +" is null and is not expected  ");
            }
        }
    }


    /**
     * This method is to verify json schema for the file passed
     * @param schemaFileName -- this is the schema file name
     */
    public void iValidateJsonSchema(String schemaFileName){
        response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaFileName));
    }



    public void iValidateResponseBodyWithKeyOfType(String key, String type) throws Exception {
        if (responseAsString.isEmpty()) {
            throw new Exception("Response is null, couldn't proceed further in validating");
        } else {
            Object parseResponseString = Configuration.defaultConfiguration().jsonProvider().parse(responseAsString);
            Object actual = read(parseResponseString, key);
            if(actual instanceof JSONArray){
                Iterator<Object> iterator = ((JSONArray) actual).iterator();
                while (iterator.hasNext()){
                    Object next = iterator.next();
                    if(next instanceof Integer){
                        next="integer";
                    }else if(next instanceof String){
                        next="string";
                    }else if(next instanceof Boolean){
                        next="boolean";
                    }else{
                        System.out.println("Incorrect datatype");
                    }
                    Assert.assertEquals(type,next);
                }
            }
        }
    }

    public static String[] getStringArray(JSONArray jsonArray) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<jsonArray.size(); i++) {
            list.add((String) jsonArray.get(i));
        }
        return list.toArray(new String[list.size()]);
    }
}

package com.paramInfo.popcorn.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.PropertyConfigurator;

public class RestConfigurator {

    public RestConfigurator() {
        PropertyConfigurator.configure(FileHandler.getResourcesFilePath(Constants.logProperties));
    }

    private RequestSpecification getRequestSpecification(){
        return RestAssured.given().contentType(ContentType.JSON);
    }

    private void configureURI() {
        String baseURI= PropReader.getProperty(FileHandler.getResourcesFilePath(Constants.commonProperties), Constants.hostName);
        System.out.println(baseURI);
        RestAssured.baseURI=baseURI;
        System.out.println("baseURI = "+baseURI);
    }

    Response getResponse(String endPoint,String... sApiKey){
        String apiKey=null;
        if(sApiKey.length==0){
            apiKey = PropReader.getProperty(FileHandler.getResourcesFilePath(Constants.commonProperties), Constants.apiKey);
        }else{
            apiKey= sApiKey[0];
        }
        String promotionEndpoint = PropReader.getProperty(FileHandler.getResourcesFilePath(Constants.commonProperties), endPoint);
        System.out.println(apiKey+" ***  "+promotionEndpoint);
        configureURI();
        RequestSpecification request=getRequestSpecification();
        return request.queryParam(Constants.apiKey,apiKey).get(promotionEndpoint).then().extract().response();
    }
}

package com.Apitest.Helpers;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.is;

import org.testng.Assert;

public class ReusableMethods {

	
	public static RequestSpecification setRequestSpec(int id)
	{
		RequestSpecBuilder rsb = new RequestSpecBuilder();
		RequestSpecification spec = rsb.addPathParam("id",id).build();
		return spec;
	}
	
	public static RequestSpecification setRequestSpec(String str)
	{
		RequestSpecBuilder rsb = new RequestSpecBuilder();
		RequestSpecification spec = rsb.setContentType(ContentType.JSON)
										.setBody(str).build();
		return spec;
	}
	
	public static ResponseSpecification setResponseSpec()
	{
		ResponseSpecBuilder rsb = new ResponseSpecBuilder();
		rsb.expectContentType(ContentType.JSON);
		rsb.log(LogDetail.STATUS);
		ResponseSpecification resp = rsb.build();
		return resp;
	}
	
	public static void validateStatusCode(Response  resp,int expectedStatusCode)
	{
	    int actualStatusCode = resp.statusCode();
	    Assert.assertEquals(actualStatusCode,expectedStatusCode);
	   
	}
	public static void validateStatusMessage(Response resp,String expectedMessage)
	{
		String actualMessage = resp.body().jsonPath().getString("status");
		Assert.assertEquals(actualMessage, expectedMessage);
		
	}
	public static void validateContentType(Response resp,String expectedContentType)
	{
		String actualContentType = resp.contentType();
		Assert.assertEquals(actualContentType,expectedContentType);
	}
}

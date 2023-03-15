package com.Apitest.Helpers;

import java.util.ArrayList;

import java.util.List;

import com.ApiTestUtils.*;

import com.TestModels.PojoClasses.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserServiceHelper {

	
	public static void  setBaseUri()
	{
		RestAssured.baseURI = ReadPropertiesFile.getConfigProperties("baseuri");
		System.out.println("uri : " + ReadPropertiesFile.getConfigProperties("baseuri"));
	}
	
	public static Response getUserData()
	{
		Response resp = RestAssured.given().when().get(EndPoints.EMPLOYEES);
		return resp;
	}
	
	public static Response createEmployee(String str)
	{
		RequestSpecification reqSpec = ReusableMethods.setRequestSpec(str);
		Response resp = RestAssured.given(reqSpec).when().post(EndPoints.CREATE);
		return resp;
	}
	
	public static Response pathParameterAdding(int id,String action)
	{
		RequestSpecification reqSpec = ReusableMethods.setRequestSpec(id);
		Response  resp = null;
		if(action.equalsIgnoreCase("delete"))
		    resp = RestAssured.given(reqSpec).when().delete(EndPoints.DELET);
		else
			if(action.equalsIgnoreCase("get"))
				resp = RestAssured.given(reqSpec).when().delete(EndPoints.EMPID);
		return resp;
	}
}

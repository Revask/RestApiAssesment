package com.Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.Apitest.Helpers.ReusableMethods;
import com.Apitest.Helpers.UserServiceHelper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.TestModels.PojoClasses.*;

import static org.hamcrest.Matchers.*;

import java.util.List;


public class ApiAssesment {
	
//	public static final String BaseUri = "https://dummy.restapiexample.com/api/v1";
	public static final String  emp = "/employees";
	public static final String create = "/create";
	public static final String delete = "/delete/{id}";
	public static final String  empid = "/employees/{id}";
	public static int id;
	
	@BeforeClass
	public void initialSetup()
	{
		UserServiceHelper.setBaseUri();
		
	}
		@Test
		
		public void getEmployeeRecords()
		{
			Response resp = UserServiceHelper.getUserData();

			ReusableMethods.validateStatusCode(resp,200);
			ReusableMethods.validateStatusMessage(resp,"success");
			GetEmployeeRecordPojo result = resp.as(GetEmployeeRecordPojo.class);
			System.out.println("Status " + result.getStatus());
			List<DatumPojo> list = result.getData();
			for(DatumPojo d : list)
			{
				System.out.println("Id :  "+d.getId());
				System.out.println("Employee Name: "+d.getEmployeeName());
				System.out.println("Emplyee Age : "+d.getEmployeeAge());
				System.out.println("Emplyee salary : "+d.getEmployeeSalary());
				System.out.println("Emplyee Profile Image : "+d.getProfileImage());
				System.out.println(" ");
			}
			System.out.println("Number of records : " + result.getData().size());
		}
		
		@Test
		public void createEmployeeRecord()
		{
			Response resp = UserServiceHelper.createEmployee("{\"name\":\"test\",\"salary\":\"123\",\"age\":\"23\"}");
			SingleEmployeeRecordPojo emprec = resp.as(SingleEmployeeRecordPojo.class);
			DataPojo dat = emprec.getData();
			ReusableMethods.validateStatusCode(resp,200);
			ReusableMethods.validateStatusMessage(resp,"success");
			Assert.assertEquals(dat.getName(), "test");
			Assert.assertEquals(dat.getSalary(), "123");
			Assert.assertEquals(dat.getAge(), "23");
			id = dat.getId();
			System.out.println("Id " + id);
		}
		
		@Test(dependsOnMethods = "createEmployeeRecord")
		public void deleteEmployeeRecord()
		{
			System.out.println("id in delete method " + id);//deleting employee record with id received from createEmployeeREcord method
			Response resp = UserServiceHelper.pathParameterAdding(id,"delete") ;
			ReusableMethods.validateStatusCode(resp,200);
			ReusableMethods.validateStatusMessage(resp,"success");
			String data = resp.body().jsonPath().getString("data");
			String message = resp.body().jsonPath().getString("message");
			System.out.println("Data From Response Body after deletion : " + data);
			System.out.println("Message From Response Body after deletion : " + message);
		}
		
		@Test
		public void deleteInvalidEmployeeRecord()
		{
			Response resp = UserServiceHelper.pathParameterAdding(0,"delete") ;//deleting employee record with id 0
			ReusableMethods.validateStatusCode(resp,400);
			ReusableMethods.validateStatusMessage(resp,"error");
			String message = resp.body().jsonPath().getString("message");
			System.out.println("Message From Response Body after deletion : " + message);
		}
		
		@Test
		
		public void readEmployeeRecord()
		{
			id =2;
			Response resp = UserServiceHelper.pathParameterAdding(id,"delete") ;//reading employee record with id 2
			ReusableMethods.validateStatusCode(resp,200);
			ReusableMethods.validateContentType(resp,"ContentType.JSON");
			String name = resp.body().jsonPath().getString("data.name");
			String salary = resp.body().jsonPath().getString("data.salary");
			String age = resp.body().jsonPath().getString("data.age");
			Assert.assertEquals(name,"Garrett Winters");
			Assert.assertEquals(salary,"170750");
			Assert.assertEquals(age,"63");
		}

}

package com.qa.listeners;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.ITestListener;
import org.testng.ITestResult;
// We are creating this LISTENER class so that we get to see the details of exceptions in "console" when any test case gives exceptions.

// To work it, now we'll need to call this LISTENER for testNG xml . Doing it from testNG at Suite level will make this work for all TEST CLASSES automatically other you will need to call it in every class separarely which is not good approach.
public class TestListener implements ITestListener{// We're creating this LISTENER Class and implementing ITestListener so that we don't have use TRY/CATCH block in every test case to get the exception detail if one occurs.Annotation @listeneer will be used to use this

	// READ the method "invalidPassword" in green code  inside LOGINPAGE test class to understand this java class
	
 public void onTestFailure(ITestResult result) {   // ITestResult Class > This method will print the Exception details if any test method fails
	 if (result.getThrowable()!=null) {
		 StringWriter sw = new StringWriter();   // If this line was not added, TestNG in case of exception will simply tell that there is error but it won't display detail of exception in results. Adding this line would do it
		 PrintWriter pw = new PrintWriter(sw);
		 result.getThrowable().printStackTrace(pw);  //  using "getThrowable" method we're reading the exception from our test method and printing it to 'PrintWriter'
		 System.out.println(sw.toString()); // Converting the details of exception into String format ie readable format which will be shown in "rests of running suite" tab which from testNG
	 }
	 
 }
	
}



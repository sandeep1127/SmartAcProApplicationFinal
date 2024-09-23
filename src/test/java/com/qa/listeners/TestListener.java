package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
//sandeep
import com.qa.baseClass.BaseTest;
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
      
	 
	 
	 
	 // below code is added to take SCREENSHOT if any TESTCASE FAILS. SO we included in this method itself. 
	 BaseTest base = new BaseTest();  // created object for BasTest class . Now got o BaseTest class and create a method to get the driver.
	 
	 File file = base.getDriver().getScreenshotAs(OutputType.FILE);   // we used the method to get the screenshot and output type is File. We collect it in "File Object"
	  
	 // Below code we are doing to shift our Failed test cases Screenshots to our desired Directory:
	 Map<String, String> params = new HashMap<String, String>();
	 params = result.getTestContext().getCurrentXmlTest().getAllParameters();  // here we are fetching the testNG global paramters  as we need them for our directory so we use "Result" argument to fetch them which return them in hasMap so we created HashMap to store them
	 
	 /* below is the directiry structure we created for storing the failed screenshots.
	                                                             Structure is:  \Screenshots    {This Folder will get automatically created which will contain all below folders }
	 
	  																			\<platformNAme>_<platormVersion>_<deviceName>   {helps capturing ss in case of parellel excecution. It will create separate device which you'll use to test}
	                                                              				\<datetime   {ensures SS are not overwritten so each time test runs, auto folder will get created - we can also use build number CI/CD}
	                                                              				\<testClass>   {lists all method as per Test classeswhich failed}
	                                                              				\methodName.png {helps identify the failed method}
	 
	 */
	String failedImagePath = "ScreenShots" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName") + File.separator + base.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png" ;  // This is relative path
	 
	 
	String completeImagePath = System.getProperty("user.dir") + File.separator + failedImagePath;	 // we used to get the complete path from root directory	adn we are using it in our HTML report location to add the failed cases ss 
	 // Below code is used to copy Screenshot File.
	 try {
		FileUtils.copyFile(file, new File (failedImagePath));      // Now we use FileUtils of which we added dependency to copy the File .  Source will be our File and destination file will be our Image name object thaty used 'new' keyword . This will by default create the screnshot File in the Root Directory which is "SmartACProAPP" which we handle above
	   Reporter.log("This is the Screenshot of faile test cases"); // This code line will simply add the failed test cases screenshot to the defualt generated HTML Report. If we didnt add this, it would show the console error in reports.
	   Reporter.log("<a href='"+ completeImagePath + "'> <img src='"+ completeImagePath + "' height='100' width='100'/> </a>");  // we gavethe image  reference and image source
	 } catch (IOException e) {
		
		e.printStackTrace();
	}           
	 
	 
 }
	

















}



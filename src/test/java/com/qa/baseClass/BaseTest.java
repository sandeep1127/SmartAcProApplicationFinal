package com.qa.baseClass;

import org.testng.annotations.Test;
import com.qa.utility.Utility;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class BaseTest {
  //sandeep 23/9
	protected static AppiumDriver driver;  // we made it static coz when we'll run the TestNG file, it will 1st come to TESTCLASS > Since it extends BASE class, it will come to BASE class and will initialize Drive>  then it will go back to TESTCLASS  BEFORE method and will initilize loginPage class where its again extending BASECLASS > It will again go to base class and trying to initializing UI elements and driver become null.
	protected static Properties props;   // This is created to load PROPERTIES files while initializing the driver.
	InputStream inputStream;	        // Created to store PROPERTIES File
	
	protected static HashMap<String, String> ExpectedResultStrings = new HashMap<String, String>() ; // Created this for using when we extract EXPECTED RESULTS data/strings stored inside 'expectedresults.xml' file. we're initializing it in BASE CLASS since we'll be using HasMap STRINGS all our automation. This was added because we added method to read "ExpectedResult" xml file in UTILS class for storing our expected results data in that xml.
	//protected static String platform;  // Its also part of reading "ExpectedResults xml' file. We've created it for method "getText" which is used to get the value of the attribute
	InputStream stringsis;   // Doing it for expectedResults XML file
	Utility utils;        // Instance created of UTILS class which we used for EXPECTED RESULTS xml whose method is stored in UTILITY class
	
 @Parameters({"platformName", "deviceName","platformVersion" , "udid"  })	 // We're using @PARAMTERS annotation of testNG to fetch our device specific capabilities Parameters from TESTNG file. Now pass these parameters to beforetest() method so that these parametrs acn be used there.
 @BeforeTest
  public void beforeTest(String platformName, String deviceName ,String platformVersion,  String udid) throws Exception {  // Initialize driver in this method so that driver is available for all TEST classes. We just need to initiate it once and install app once since all test cases will be executed on 1 device only. In SELENIUM we used to initialize it @BefireMethod level coz in there we initiated driver after every test case.,

	  try {
		  // Below 4 lines are to load CONFIG.property file used to store capabilities.
		  props = new Properties();
		  String propFileName= "config.properties";  //  complete file path is not  needed since the property file is directly at root location under ClassPath [ie src/main/resource]. Now we can use "InputStream" to load the properties file.	 		  		
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);     //Created InputStream object to store properties file. Since we created CONFIG file inside src/test/resouces, we can use "Input stream" to load the Config File. 	
		  props.load(inputStream);  // We're loading Properties file here. Now in order to get properties key/value, all we need to do is use "getProperty()" method
		  
		
		  //XYZ
          //Below  4 lines coding was for reading expectedResult.xml as InputStream which is used for storing expected Results statements but it did not work as it showed InputStream as Null . So we used FilterInputStream and avoided using InputStream :-
    /*
     String xmlFileName = "expectedResults/expectedResults.xml";            // saving our ExpectedResusults xml file as String. This xml we created inside src/test/resources and contains all our expected results data.     
     stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);  //  Read the expectedResults xml file as InputStream.
     
     //Printing the complete path of InputStream resource xml file to check if Path is correct
     URL resourceUrl = getClass().getClassLoader().getResource(xmlFileName);
     stringsis = resourceUrl.openStream();                                        // We found that its going to TARGET FOLDER and not to our specified Location, hence it gave Null InputStream. So we need to shift to FilerInputStream
     System.out.println("The path of xml resource file :" + resourceUrl);
     
     utils = new Utility();
     ExpectedResultStrings = utils.parseStringXML(stringsis);
     if (stringsis == null) {                                          // To check if our XML is not null and loader successfully.
			    System.out.println("expectedResults.xml not found!");
			} else {
			    System.out.println("expectedResults.xml loaded successfully.");
			}
		  
     */
     
     
 
		  // ************ This code was asked by Tutor to use when "parseStringXML" was giving error of "InputStream is null" and for this had to update utility method to parseStringXML2  to take input as FileInputStream instead of InputStream.
		      
		//Below coding is for reading expectedResult.xml which is used for storing expected Results statements:-
		     
		       String xmlFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "expectedResults" + File.separator + "expectedResults.xml"; 	 // Storing the path of xml file inside String.	     		  		      
		       System.out.println("Path of expectedResults xml file used as FileInputStream is: " + xmlFilePath);  // Printing the path of the XML file		       
		       FileInputStream xmlFileInputStream = new FileInputStream(xmlFilePath);   // storing XML file as FileInputStream
		   
		       if (xmlFileInputStream == null) {           // To check if resource xml file is null or not
			        System.out.println("expectedResults.xml not found!");
			       } 
			       else {
			       System.out.println("expectedResults.xml loaded successfully.");
			        }		   		  		   			        
			       utils = new Utility();
			       ExpectedResultStrings = utils.parseStringXML2(xmlFileInputStream);  // using the Ultility method to fetch XML data into a hashMap
		  
		  

		  //DesiredCapabilities caps = new DesiredCapabilities();   // DesiredCapabilities is getting depricated, so used Options Class.
		  UiAutomator2Options options = new UiAutomator2Options(); // used for Android
		  XCUITestOptions options1 = new XCUITestOptions();  // Used for IOS
		  
		// ***** We set below 3 Capabilities in TESTNG XML file ********
		  options.setPlatformName(platformName);             
		  options.setDeviceName(deviceName);                 
		  options.setPlatformVersion(platformVersion);       
		  options.setUdid(udid);                                  
		  				   
		// ***** We set below 3 Capabilities in CONFIG Properties File ********
		  options.setAutomationName(props.getProperty("androidAutomationName"));  // Fetching Capability stored in properties File by key
		  options.setAppPackage(props.getProperty("androidAppPackage"));          // Fetching Capability stored in properties File by key
		  options.setAppActivity(props.getProperty("androidAppActivity"));        // Fetching Capability stored in properties File
		

		  
		  
		  //String androidAppUrl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();   // fetching relative location of our pro app from config file. But it won't work it give null value so instead we used direct location.
		  //System.out.println("Url of the app location is:" + androidAppUrl);
		  
		  String appAndroidUrl = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + "app-stage-release.apk";
		  System.out.println("Url of the app location is:" + appAndroidUrl);
		  
		  // another way from lecture to get the complete path of app using Relative path as well.
			/*
			 * String appUrlRelativePath = props.getProperty("androidAppLocation"); String
			 * appUrl = System.getProperty("user.dir") + File.separator + "src" +
			 * File.separator + "test" + File.separator + "resources" + File.separator +
			 * appUrlRelativePath;
			 */
		  
		  
		 // caps.setCapability("app", "androidAppUrl"); // fetching URL of Pro app to download it to use in this capability.
		  options.setApp(appAndroidUrl);
		  URL url = new URL (props.getProperty("appiumURL")); // Fetching Capability stored in "properties File" by key
			
		  
		  
		  
			driver = new AndroidDriver(url, options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			  
	  }
	  
	  catch(Exception e) {
		  e.printStackTrace();
		  throw e;   // If driver initialization fails, the program should immediately exit and report the error, preventing further execution.
	  }
	finally {      // we're closing both the InputStreams "inputStream and 'stringsis' (which we used for Properties file & EXPECTED RESULTS xml) by using Finally block which we used above in TRY  block. Now we go to TEST class and replace all Expected Results with our STRINGS used in XML of Expected results.
		if(inputStream !=null) {    
			inputStream.close();
			
		}
		if (stringsis !=null) {
			stringsis.close();
		}
	}
  }

  
 
//CREATING METHOD which will be used by DRIVER all around our cases:-

public void waitForVisibility(WebElement e) {
	  
	  WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(Utility.wait)); //  Here we are using DYNAMIC WAIT which is mentioned in UTILITY class
	  wait.until(ExpectedConditions.visibilityOf(e));  
	  }
  
  
//Method for Clicking Element
public void click (WebElement e){
	  waitForVisibility(e);  // calling wait for visibility of element before we click it
	  e.click();
}

//Method for sendKeys 
public void sendKeys (WebElement e, String txt){
	  waitForVisibility(e);  // calling wait for visibility of element before we click it
	  e.sendKeys(txt);
}

//Method for clearing field of Element
public void clear (WebElement e){
	  waitForVisibility(e);  // method for clearing the field. 
	  e.clear();
}


//Method to get the value of any Attribute
public String getAttribute(WebElement e, String attribute) {
	  waitForVisibility(e); // calling wait for visibility of element before we click it
	   return e.getAttribute(attribute); 
	   }
  
  
  
  
  
  @AfterTest             // runs only once after running all TEST CLASSES under <test> tag in testNG xml.
  public void afterTest() {
	  driver.quit();
  }

}

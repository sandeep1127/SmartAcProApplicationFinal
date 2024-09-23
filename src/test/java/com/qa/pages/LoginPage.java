package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.baseClass.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest {

	// For Defining the UI elements, we use @AndroidFindBy provide by PageFactopry Class
	
		// UI Elements
		
		@AndroidFindBy (accessibility = "email")   //For ANDROID , we use "accessibility" when parameter is "accessibility Id" 
		//@iOSXCUITFindBy(id="test-Username")                // For IOS, we use "id" when parameter is "accessibility Id" 
		private WebElement usernameTxtfld;                 // common name of the web element for ISO and ANDROID
		
		
		@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"Login\"]/android.widget.TextView") 
	   //@iOSXCUITFindBy(id="test-LOGIN")
		private WebElement EmailLoginBtn;
		
	
		@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"Email, Email address not found.\"]/android.widget.TextView[2]")
		//	@iOSXCUITFindBy(xpath="//android.view.ViewGroup[@content-desc="Email, Email address not found."]/android.widget.TextView[2]")
			 private WebElement errEmailTxt;
		
		// loginPage2
		
		
			
			@AndroidFindBy (accessibility = "password") 
			//@iOSXCUITFindBy(id="test-Password")
			private WebElement passwordTxtfld;        	// common name of the web element for ISO and ANDROID
			
			
			@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"Email, Password, Invalid credentials\"]/android.widget.TextView[3]")
		//	@iOSXCUITFindBy(xpath="//XCUIElementTyoeOther[@name=\"test-Error message\"]/child::XCUITElementTypeStaticText")
			 private WebElement errPasswordTxt;
		
			
			@AndroidFindBy (accessibility = "Forgot email?")
			//	@iOSXCUITFindBy(xpath="//android.view.ViewGroup[@content-desc="Forgot email?"]")
				 private WebElement forgotEmailLink;
			
			@AndroidFindBy (accessibility = "Forgot password?")
			//	@iOSXCUITFindBy(xpath="//android.view.ViewGroup[@content-desc="Forgot email?"]")
				 private WebElement forgotPasswordLink;
			
			@AndroidFindBy (accessibility = "Login") 
			//@iOSXCUITFindBy(id="Login")
			private WebElement loginBtn;
			
			
			@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"\"]/android.widget.TextView")
			//	@iOSXCUITFindBy(xpath="//XCUIElementTyoeOther[@name=\"test-Error message\"]/child::XCUITElementTypeStaticText")
				 private WebElement eyeIcon;
			
			
		
		
		
		
		// Initializing UI elements via Page Factory :-
		public LoginPage() {
			
			PageFactory.initElements(new AppiumFieldDecorator(driver), this);  // With the version of java client 9.1, now we need to initialize Page factory in every Page Class.	
		}
		
	
	
	
		// CREATING METHODS:-
		
		public LoginPage enterUserName(String username) {
			clear(usernameTxtfld);
			sendKeys(usernameTxtfld, username);
		  return this;                                       // Since we will stay on the same screen, so returing the object of same page class
		}
		
		
		public LoginPage clickEmailLoginBtn() {
			click(EmailLoginBtn);
			return this;
			
		}
		
		public String emailErrText() {
	    	 return getAttribute(errEmailTxt, "text");    // Fetching the Value of the attribute 'Text' of the webelement 'errPasswordTxt'
	    	 
	     }
		
   	public LoginPage enterPassword(String username) {
   		clear(passwordTxtfld);
			sendKeys(passwordTxtfld, username);
			return this;                                       // Since we will stay on the same screen, so returing the object of same page
		}
		
		
     public HomePage pressLoginBtn() {
			click(loginBtn);
			return new HomePage();                                       // Since we fo to HOMEPAGE, so returing the object of HOMEAPGE page class
		
     }
		
	
     public String passwordErrText() {
    	 return getAttribute(errPasswordTxt, "text");    // Fetching the Value of the attribute 'Text' of the webElement 'errPasswordTxt'
    	 
     }
		
     
}

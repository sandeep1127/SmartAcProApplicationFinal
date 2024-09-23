package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.qa.baseClass.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage extends BaseTest {

	// For Defining the UI elements, we use @AndroidFindBy provide by PageFactopry Class
	
		// UI Elements
		
		@AndroidFindBy (accessibility = "Onboard new member")   //For ANDROID , we use "accessibility" when parameter is "accessibility Id" 
		//@iOSXCUITFindBy(id="Onboard new member")                // For IOS, we use "id" when parameter is "accessibility Id" 
		private WebElement onboardNewMemberBtn;                 // common name of the web element for ISO and ANDROID
		
		@AndroidFindBy (accessibility = "MyTech Chats") 
		//@iOSXCUITFindBy(id="MyTech Chats")
		public WebElement myTechChatsBtn;
		
		@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"Login\"]/android.widget.TextView") 
	   //@iOSXCUITFindBy(id="test-LOGIN")
		private WebElement EmailLoginBtn;
		
		
		
		
		public HomePage() {
			
			PageFactory.initElements(new AppiumFieldDecorator(driver), this);  // With the version of java client 9.1, now we need to initialize Page factory in every Page Class.	
		}
		
	
	
	public String getTitle() {
		return getAttribute(EmailLoginBtn, "text");  // Element, Attribute type           NEED CORRECT XPATH here                 
		 
	}
		
		
		
	
}

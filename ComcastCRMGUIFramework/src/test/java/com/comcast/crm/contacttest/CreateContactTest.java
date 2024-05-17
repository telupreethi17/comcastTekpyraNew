package com.comcast.crm.contacttest;


import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.comcast.crm.baseTest.BaseClass;
import com.comcast.crm.baseTest.BaseClass2;
import com.comcast.crm.generic.fileytility.ExcelUtility;
import com.comcast.crm.generic.fileytility.FileUtility;
import com.comcast.crm.generic.webdriverUtility.JavaUtility;
import com.comcast.crm.generic.webdriverUtility.WebDriverUtility;
import com.comcast.crm.objrctrepositoryutility.ContactInfoPage;
import com.comcast.crm.objrctrepositoryutility.ContactsPage;
import com.comcast.crm.objrctrepositoryutility.HomePage;
import com.comcast.crm.objrctrepositoryutility.LoginPage;

public class CreateContactTest extends BaseClass2 {
	@Test
	public void test() throws IOException, InterruptedException
	{
	
	//public static void main(String[] args) throws InterruptedException, IOException {
		//create object to get property file
			FileUtility flib= new FileUtility();
			
			ExcelUtility elib= new ExcelUtility();
			JavaUtility jlib= new JavaUtility();
			WebDriverUtility  wlib= new WebDriverUtility();
			
			
			String BROWSER=flib.getDataFromPropertiesFile("browser");
			String URL=flib.getDataFromPropertiesFile("url");
			String USERNAME=flib.getDataFromPropertiesFile("username");
			String PASSWORD=flib.getDataFromPropertiesFile("password");
		
			//read data from excel
			
			String lastname=elib.getDataFromExcel("contact", 1, 2)+jlib.getRandomNumber();

			//at compile time driver is null based on user input it goes this is runtime polymorphism
			WebDriver driver= null;
			
			if(BROWSER.equals("chrome"))
			{
				 driver= new ChromeDriver();
				
			}
			else if(BROWSER.equals("firefox"))
			{
				 driver= new FirefoxDriver();
			}
			else if(BROWSER.equals("edge"))
			{
				driver= new EdgeDriver();
			}
			else
			{
				 driver= new ChromeDriver(); 
			}
			
			
			wlib.waitForPageToLoad(driver);
			//Step 1:Login to APP
		
			
			LoginPage lp=new LoginPage(driver);
			lp.LoginToApp(URL,USERNAME, PASSWORD);
			
			//sTEP2: NAVIGATE TO contacts
			
			HomePage op= new HomePage(driver);
			op.getcontactlink().click();
			
		//step enter all details and create organization
			ContactsPage cp= new ContactsPage(driver);
			cp.createContact(lastname);
			
			ExtentTest test= report.createTest("CreateContactTest");
			test.log(Status.PASS, "contact create");
						
			//verify the header organization info msg expected result
			ContactInfoPage ci= new ContactInfoPage(driver);
			String actualastname=ci.getcontactdetail().getText();
			if(actualastname.equals(lastname))
			{
				System.out.println(lastname+" contact is created==PASS");
			}
			else
			{
				System.out.println(lastname+" contact is not created==FAIL");
			}
			
		
			
			
		}


	}



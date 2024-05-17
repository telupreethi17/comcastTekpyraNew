package com.comcast.crm.baseTest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.generic.databaseUtility.DatabaseUtility;
import com.comcast.crm.generic.fileytility.ExcelUtility;
import com.comcast.crm.generic.fileytility.FileUtility;
import com.comcast.crm.generic.webdriverUtility.JavaUtility;
import com.comcast.crm.generic.webdriverUtility.WebDriverUtility;
import com.comcast.crm.objrctrepositoryutility.HomePage;
import com.comcast.crm.objrctrepositoryutility.LoginPage;

public class BaseClass2 {
	
	
	public ExcelUtility elib= new ExcelUtility();
	public JavaUtility jlib= new JavaUtility();
	public WebDriverUtility  wlib= new WebDriverUtility();
	public DatabaseUtility dblib= new DatabaseUtility();
	public FileUtility flib= new FileUtility();
	public WebDriver driver= null;
	public ExtentReports report;
//	public ExtentSparkReporter spark;
	//because we use in after method we have to make it global
	public static WebDriver s1driver= null;

	@BeforeSuite(groups= {"smokeTest","RegreesionTest"})
	public void configbeforesuit() throws SQLException
	{
		
		System.out.println("===Connect to DB, Report config==");
		dblib.getDbConnection();
	}
	//@Parameters("BROWSER")
	@BeforeClass(groups= {"smokeTest","RegreesionTest"})
	
	//public void configbeforeclass(String browser) throws IOException
	public void configbeforeclass() throws IOException

	{
		System.out.println("===Launch the browser===");
		String BROWSER =System.getProperty("browser",flib.getDataFromPropertiesFile("browser"));
		
		
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
		s1driver=driver;
	}
	
	@BeforeMethod(groups= {"smokeTest","RegreesionTest"})
	public void beforeMethod() throws IOException
	{
		System.out.println("===Login to application===");
		String URL=System.getProperty("url",flib.getDataFromPropertiesFile("url"));
		String USERNAME=System.getProperty("username",flib.getDataFromPropertiesFile("username"));
		String PASSWORD=System.getProperty("password",flib.getDataFromPropertiesFile("password"));
	
		LoginPage lp= new LoginPage(driver);
		lp.LoginToApp(URL,USERNAME, PASSWORD);
	
	}
	@AfterMethod(groups= {"smokeTest","RegreesionTest"})
	public void afterMethod()
	{
		System.out.println("===Logout====");
		HomePage hp= new HomePage(driver);
		hp.logout();
	}
	@AfterClass(groups= {"smokeTest","RegreesionTest"})
	public void Afterclass() 
	{
		System.out.println("===Close the Browser===");
		driver.quit();
	}
	@AfterSuite(groups= {"smokeTest","RegreesionTest"})
	public void aftersuit() throws SQLException
	{
		
		System.out.println("Close DB , Report Backup");
		dblib.closeDbconnection();
		
	}

}

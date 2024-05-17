package com.comcast.crm.ListenerUtility;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.baseTest.BaseClass2;
import com.comcast.crm.generic.webdriverUtility.UtilityClassObject;

public class Listener_implementationClass implements ITestListener, ISuiteListener {
	public static ExtentReports report;
	public  ExtentTest test;
	
	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		
		//spark report config
		String Time= new Date().toString().replace(" ","_").replace(":","_");

		ExtentSparkReporter spark= new ExtentSparkReporter("./AdvanceReport/report"+Time+".html");
		spark.config().setDocumentTitle("CRM TES SUITE RESULTS");
		spark.config().setReportName("CRM Report");
		spark.config().setTheme(Theme.DARK);
		//Add Env information and create test
		
	 report= new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("os", "Wndows-10");
		report.setSystemInfo("BROWSER", "Chrome-100");
		System.out.println("Report configuration");

	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		System.out.println("Report Bachup");
		report.flush();

	}

	
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("======  ==>" + result.getMethod().getMethodName() + " test started");
		 test= report.createTest(result.getMethod().getMethodName());
		 UtilityClassObject.setTest(test);
		 test.log(Status.INFO,result.getMethod().getMethodName()+"==> STARTED==");
//execute before each test
		// displaying name of the test casse

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("======  ==>" + result.getMethod().getMethodName() + " test ended");
		 test.log(Status.PASS,result.getMethod().getMethodName()+"==> COMPLETED==");


	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		String testname = result.getMethod().getMethodName();
		TakesScreenshot tks= (TakesScreenshot)UtilityClassObject.getdriver();
		String Filepath=tks.getScreenshotAs(OutputType.BASE64);
		
		//EventFiringWebDriver edriver = new EventFiringWebDriver(BaseClass2.s1driver);
		//File src = edriver.getScreenshotAs(OutputType.FILE);

		// step2 Use getScreenshot method to get file type of screeshot
		String Time= new Date().toString().replace(" ","_").replace(":","_");
		
		test.addScreenCaptureFromBase64String(Filepath, testname+"_"+Time);
		 test.log(Status.FAIL,result.getMethod().getMethodName()+"==> FAILED ==");


		// step 3:store screenshot on local driver
	//	File dst = new File("./ScreenSho/"+testname+"+"+Time+".png");

//		try {
//			FileUtils.copyFile(src, dst);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		

	}

}

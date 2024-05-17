package com.comcast.crm.orgaAllTest;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.comcast.crm.ListenerUtility.Listener_implementationClass;
import com.comcast.crm.baseTest.BaseClass;
import com.comcast.crm.baseTest.BaseClass2;
import com.comcast.crm.generic.webdriverUtility.UtilityClassObject;
import com.comcast.crm.objrctrepositoryutility.CreateNewOrganizationPage;
import com.comcast.crm.objrctrepositoryutility.HomePage;
import com.comcast.crm.objrctrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objrctrepositoryutility.OrganizationPage;
@Listeners(com.comcast.crm.ListenerUtility.Listener_implementationClass.class)

public class CreateOrganizationTest extends BaseClass2 {

	@Test(groups = "smokeTest")
	public void CreateOrgganizationTest() throws IOException, InterruptedException {
		UtilityClassObject.getTest().log(Status.INFO, "read data from Excel");
		String orgname = elib.getDataFromExcel("org", 1, 2) + jlib.getRandomNumber();

		// at compile time driver is null based on user input it goes this is runtime
		// polymorphism

		// Step 1:Login to APP

		// sTEP2: NAVIGATE TO ORGANIZATION
		
	       UtilityClassObject.getTest().log(Status.INFO, "Navigate to org page");

		HomePage op = new HomePage(driver);
		op.getorglink().click();

		// sTEP 3:cREATE ORGANIZATION
		UtilityClassObject.getTest().log(Status.INFO, "Navigate to create org page");

		OrganizationPage og = new OrganizationPage(driver);
		og.getcreateNewOrg().click();

		// step enter all details and create organization
		UtilityClassObject.getTest().log(Status.INFO, "Create org");

		CreateNewOrganizationPage cnp = new CreateNewOrganizationPage(driver);
		cnp.CreateOrg(orgname);
		
		UtilityClassObject.getTest().log(Status.INFO,orgname+ "read data from Excel");

		// verify the header msg expected result
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String headerinfo = oip.getheaderMsg().getText();
		boolean status = headerinfo.contains(orgname);
		Assert.assertTrue(status);
		

//			if (headerinfo.contains(orgname)) {
//				System.out.println(orgname + "is created==PASS");
//			} else {
//				System.out.println(orgname + "is not created==FAIL");
//			}

		// verify the header organization info msg expected result
		String actualorgname = oip.getdetailorgName().getText();
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(actualorgname, orgname);
//			if (actualorgname.equals(orgname)) {
//				System.out.println(actualorgname + "is created==PASS");
//			} else {
//				System.out.println(actualorgname + "is not created==FAIL");
//			}
	}

	@Test(groups = "RegreesionTest")
	public void CreateOrganizationWithIndustryTest() throws EncryptedDocumentException, IOException {

		String orgname = elib.getDataFromExcel("org", 4, 2) + jlib.getRandomNumber();

		String Industries = elib.getDataFromExcel("org", 4, 3);
		String Type = elib.getDataFromExcel("org", 4, 4);
		HomePage op = new HomePage(driver);
		op.getorglink().click();

		// sTEP 3:cREATE ORGANIZATION
		CreateNewOrganizationPage cog = new CreateNewOrganizationPage(driver);
		cog.CreateOrg(orgname, Industries, Type);

		// verify

		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String actualindustry = oip.getindustrydetail().getText();
		Assert.assertEquals(actualindustry, Industries);

//		if (actualindustry.contains(Industries)) {
//			System.out.println(Industries + " is verified==PASS");
//		} else {
//			System.out.println(Industries + " is not verified==FAIL");
//		}
//		
		String actualType = oip.getTypedetail().getText();

		Assert.assertEquals(actualType, Type);

//		if (actualType.contains(Type)) {
//			System.out.println(Type + "is verified==PASS");
//		} else {
//			System.out.println(Type + "is not verified==FAIL");
//		}

	}

	@Test(groups = "RegreesionTest")
	public void CreateOrganizationWithPhoneNumberTest() throws EncryptedDocumentException, IOException {

		String orgname = elib.getDataFromExcel("org", 7, 2) + jlib.getRandomNumber();
		String phonenum = elib.getDataFromExcel("org", 7, 3);
		HomePage op = new HomePage(driver);
		op.getorglink().click();

		// sTEP 3:cREATE ORGANIZATION
		CreateNewOrganizationPage cog = new CreateNewOrganizationPage(driver);

		// step enter all details and create organization

		cog.CreateOrgPhone(orgname, phonenum);

		// verify header phone number

		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String actualph = oip.getphonedetail().getText();

		Assert.assertEquals(actualph, phonenum);

//		if(actualph.equals(phonenum))
//		{
//			System.out.println(phonenum +"is verified==PASS");
//		}
//		else
//		{
//			System.out.println(phonenum+"is not verified==FAIL");
//		}

	}

	@Test(groups = "RegreesionTest")
	public void DeleteOrgnization() throws EncryptedDocumentException, IOException {
		String orgname = elib.getDataFromExcel("org", 10, 2) + jlib.getRandomNumber();

		HomePage op = new HomePage(driver);
		op.getorglink().click();

		// sTEP 3:cREATE ORGANIZATION

		OrganizationPage og = new OrganizationPage(driver);
		og.getcreateNewOrg().click();

		// step enter all details and create organization

		CreateNewOrganizationPage cnp = new CreateNewOrganizationPage(driver);
		cnp.CreateOrg(orgname);

		// verify the header msg expected result
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String headerinfo = oip.getheaderMsg().getText();
		boolean ststus1 = headerinfo.contains(orgname);
		Assert.assertEquals(ststus1, true);
//		if (headerinfo.contains(orgname)) {
//			System.out.println(orgname + "is created==PASS");
//		} else {
//			System.out.println(orgname + "is not created==FAIL");
//		}

		// verify the header organization info msg expected result
		String actualorgname = oip.getdetailorgName().getText();
		Assert.assertEquals(actualorgname, orgname);

//		if (actualorgname.equals(orgname)) {
//			System.out.println(actualorgname + "is created==PASS");
//		} else {
//			System.out.println(actualorgname + "is not created==FAIL");
//		}

		// go back to organization

		op.getorglink().click();
		og.getsearchOrg().sendKeys(orgname);
		wlib.selectBasedOnValue(og.getsearchOrgDB(), "Organization Name");
		og.getsearchbtn().click();

		driver.findElement(By.xpath("//a[text()='" + orgname + "']/../../td[8]/a[text()='del']")).click();
		wlib.switchTOAlertAndAccept(driver);

		System.out.println(orgname + " got deleted Sucessfully");

	}

}

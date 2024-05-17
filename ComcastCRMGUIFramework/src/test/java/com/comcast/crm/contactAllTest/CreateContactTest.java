package com.comcast.crm.contactAllTest;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.comcast.crm.baseTest.BaseClass;
import com.comcast.crm.baseTest.BaseClass2;
import com.comcast.crm.objrctrepositoryutility.ContactInfoPage;
import com.comcast.crm.objrctrepositoryutility.ContactsPage;
import com.comcast.crm.objrctrepositoryutility.CreateNewOrganizationPage;
import com.comcast.crm.objrctrepositoryutility.CreateNewcontactsPage;
import com.comcast.crm.objrctrepositoryutility.HomePage;
import com.comcast.crm.objrctrepositoryutility.OrganizationInfoPage;
/**
 * @author Preethi
 */
public class CreateContactTest extends BaseClass2 {
	
	@Test(groups="smokeTest")
	public void CreateConatctTest() throws InterruptedException, IOException {
		/*read data from excel*/

		String lastName = elib.getDataFromExcel("contact", 1, 2) + jlib.getRandomNumber();
		/*Step 1:Login to APP*/

		/*sTEP2: NAVIGATE TO contacts*/

		HomePage op = new HomePage(driver);
		op.getcontactlink().click();

		// step enter all details and create organization
		ContactsPage cp = new ContactsPage(driver);
		cp.createContact(lastName);

		// verify the header organization info msg expected result
		ContactInfoPage ci = new ContactInfoPage(driver);
		
		String contacInfo= ci.getcontactInfodetail().getText();
		boolean status = contacInfo.contains(lastName);
		Assert.assertTrue(status);
		
		String actualastname = ci.getcontactdetail().getText();
		 SoftAssert soft= new SoftAssert();
		 soft.assertEquals(actualastname, lastName);
		 soft.assertAll();
		 



	}
	
	@Test(groups="RegreesionTest")
	public void createContactWithSupportDateTest() throws EncryptedDocumentException, IOException
	{
		String lastname=elib.getDataFromExcel("contact", 4, 2)+jlib.getRandomNumber();
		
		driver.findElement(By.linkText("Contacts")).click();
		//sTEP 3:cREATE ORGANIZATION
		
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		//step enter all details and create new organization
		
		String startdate=jlib.getSystemDateYYYYMMDD();
		String enddate = jlib.getRequiredDateYYYYDDMM(30);
		ContactsPage cp= new ContactsPage(driver);
		cp.getlastname().sendKeys(lastname);
		CreateNewcontactsPage cnp= new CreateNewcontactsPage(driver);
		cnp.start_endDate(startdate, enddate);
	//step enter all details and create organization
		cp.getsaveBtn().click();			
		//verify the header organization info msg expected result
		
		ContactInfoPage cip= new ContactInfoPage(driver);
		String actualastartdate=cip.getdetailStartDate().getText();
		Assert.assertEquals(actualastartdate, startdate);
//		if(actualastartdate.equals(startdate))
//		{
//			System.out.println(startdate+"is created==PASS");
//		}
//		else
//		{
//			System.out.println(startdate+"is not created==FAIL");
//		}
		
		String actualaenddate=cip.getdetailEndDate().getText();
		Assert.assertEquals(actualaenddate, enddate);

//		if(actualaenddate.equals(enddate))
//		{
//			System.out.println(enddate+"is created==PASS");
//		}
//		else
//		{
//			System.out.println(enddate+"is not created==FAIL");
//		}
	}

	
	@Test(groups="RegreesionTest")
	public void createContactWithOrgTest() throws EncryptedDocumentException, IOException
	{

		String orgname=elib.getDataFromExcel("contact", 7, 2)+jlib.getRandomNumber();
		String contactLastname=elib.getDataFromExcel("contact", 7, 3);
		

		//at compile time driver is null based on user input it goes this is runtime polymorphism
		
		//Step 1:Login to APP
		//sTEP2: NAVIGATE TO ORGANIZATION
		HomePage op= new HomePage(driver);
		op.getorglink().click();
		
		
		//sTEP 3:cREATE ORGANIZATION
		CreateNewOrganizationPage corg= new CreateNewOrganizationPage(driver);
		corg.CreateOrg(orgname);
		
	
//		
		//verify the header msg expected result
		
		OrganizationInfoPage oig= new OrganizationInfoPage(driver);
		String headerInfo=oig.getheaderMsg().getText();
	boolean status = headerInfo.contains(orgname);
	Assert.assertEquals(status, true);
//		if(headerInfo.contains(orgname))
//		{
//			System.out.println(orgname+" organization is verified==PASS");
//		}
//		else
//		{
//			System.out.println(orgname+"organization not verified==FAIL");
//		}
		
		//step 5: navigate to contact module
		op.getcontactlink().click();
		//step6 
		
		
//	//step enter all details and create organization
	
		ContactsPage cp= new ContactsPage(driver);
		cp.createContactWithOrg(contactLastname);
		
		//switch to child window
		
		wlib.switchToTabOnURL(driver, "module=Accounts");
		//here
		cp.getSearchtextinChildwin().sendKeys(orgname);
		cp.getsearchbtnChildWind().click();
		
		
		driver.findElement(By.xpath("//a[text()='"+orgname+"']")).click();
		
		//switch to parent window
		wlib.switchToTabOnURL(driver, "Contacts&action");
		cp.getsaveBtn().click();
		
		
		//verify the header message
		ContactInfoPage cip=new ContactInfoPage(driver);
		headerInfo=cip.getcontactInfodetail().getText();
		boolean status2 = headerInfo.contains(contactLastname);
		Assert.assertEquals(status2, true);
//		if(headerInfo.contains(contactLastname))
//		{
//			System.out.println(contactLastname+ " contact header is verified==PASS");
//		}
//		else
//		{
//			System.out.println(contactLastname+ " contact header not is verified==FAIL");
//		}
		
		//verify header orgname info expected
		
		String actorgname=cip.getActualOrgInfo().getText();
		
		boolean status1 = actorgname.contains(orgname);
		Assert.assertEquals(status1, true);
//		if(actorgname.equals(actorgname))
//		{
//			System.out.println(orgname+ " information is verified==PASS");
//		}
//		else
//		{
//			System.out.println(orgname+ " information not is verified==FAIL");
//		}
	}


}

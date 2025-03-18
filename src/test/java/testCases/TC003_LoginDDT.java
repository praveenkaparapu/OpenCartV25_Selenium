package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseTest{

	@Test(dataProvider = "LoginData",dataProviderClass = DataProviders.class,groups = "DataDriven")
	public void verify_loginDDT(String email, String pwd, String expres) throws InterruptedException
	{
		logger.info("****TC003 is sarted execution*****");
		try {
		//home page
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//login page
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		System.out.println(email);
		lp.setPassword(pwd);
		System.out.println(pwd);
		lp.clickLogin();
		
		//MyAccount page
		MyAccountPage map= new MyAccountPage(driver);
		boolean targetPage = map.isMyAccountPageExists();
		
		if(expres.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true)
			{
				Assert.assertTrue(true);
				map.clickLogout();
			}else {
				Assert.assertTrue(false);
			}
		}else{
			if(targetPage==true)
			{
				map.clickLogout();
				Assert.assertTrue(false);
			}else {
				Assert.assertTrue(true);
			}
		}
	}catch(Exception e) {
		Assert.fail();
	}
		Thread.sleep(3000);
		logger.info("****TC003 is finished execution*****");
}
}
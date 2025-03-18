package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseTest{

	@Test(groups= {"Regression","Master"})
	public void verrify_login()
	{
		try {
			logger.info("****TC002 is sarted execution*****");
		//home page
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//login page
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(props.getProperty("email"));
		System.out.println(props.getProperty("email"));
		lp.setPassword(props.getProperty("password"));
		System.out.println(props.getProperty("password"));
		lp.clickLogin();
		Thread.sleep(500);
		//MyAccount page
		MyAccountPage map= new MyAccountPage(driver);
		boolean targetPage = map.isMyAccountPageExists();
		
		Assert.assertTrue(targetPage);
		
		}catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("***test case execution finished****");
	}
}

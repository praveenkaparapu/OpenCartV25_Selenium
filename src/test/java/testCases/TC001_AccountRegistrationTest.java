package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseTest {

	//public WebDriver driver;
	
	@Test(groups = {"Sanity","Master"})
	public void verrify_account_registration()
	{
		
		try {
		logger.info("*****started the test case******");
	HomePage hp= new HomePage(driver);
	hp.clickMyAccount();
	logger.info("clicked on my account link");
	hp.clickRegister();
	logger.info("clickedd on registration link ");
	
	AccountRegistrationPage ar= new AccountRegistrationPage(driver);
	//"praveen"
	ar.setFirstName(randomString().toUpperCase());
	//"kaparapu"
	ar.setLastName(randomString().toUpperCase());
	//"kpk20255@gmail.com"
	ar.setEmail(randomString()+"@gmail.com");
	//"7989324567"
	ar.setTelehone(randomNumbers());
	//"P1W@i*n4"
	String randompwd= randomAlphaNumeric();
	ar.setPassword(randompwd);
	ar.setConfirmPwd(randompwd);
	ar.setPrivacyPolicy();
	ar.clickContinue();
	logger.info("providing customer details");
	String confirmmsg= ar.getConfirmMsg();
	//Assert.assertEquals(confirmmsg,"Your Account Has Been Created!");
	if(confirmmsg.equals("Your Account Has Been Created!"))
	{
		Assert.assertTrue(true);
	}else {
		logger.error("test failed");
		logger.debug("debug logs");
		Assert.assertTrue(false);
	}
	}catch(Exception e) {
			Assert.fail();
		}
		
		logger.info("******finished the execution*****");
	}
	
	

	
}

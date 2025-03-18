package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath ="//input[@id='input-firstname']")
	WebElement txtFirstname;
	
	@FindBy(xpath ="//input[@id='input-lastname']")
	WebElement txtLastname;
	
	@FindBy(xpath ="//input[@id='input-email']")
	WebElement txtEmail;
	
	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement telephone;
	
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement password;
	
	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement confirmpassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement privacypolicy;
	
	@FindBy(xpath = "//input[@value='Continue']")
	WebElement continueBtn;
	
	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	
    public void setFirstName(String fname)
    {
    	txtFirstname.sendKeys(fname);
    }
    public void setLastName(String lname)
    {
    txtLastname.sendKeys(lname);
    }
    
    public void setEmail(String email)
    {
    	txtEmail.sendKeys(email);
    }	
    
    public void setTelehone(String mobile)
    {
    	telephone.sendKeys(mobile);
    }
    public void setPassword(String pwd)
    {
    	password.sendKeys(pwd);
    }
    
    public void setConfirmPwd(String cfmpwd)
    {
    	confirmpassword.sendKeys(cfmpwd);
    }

    public void setPrivacyPolicy()
    {
    	privacypolicy.click();
    }
    public void clickContinue()
    {
    	continueBtn.click();
    }
public String getConfirmMsg()
{
	try {
		return msgConfirmation.getText();
		}catch(Exception e)
	{
		return e.getMessage();	
	}
	
	
			}

}

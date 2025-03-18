package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;//logManager
import org.apache.logging.log4j.Logger;//log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

public static WebDriver driver;
public Logger logger;
public Properties props;

	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setUp(String os, String browser) throws IOException
	{
		
		FileReader file = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
		props= new Properties();
		props.load(file);
		logger = LogManager.getLogger(this.getClass());
		
		if(props.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			if(os.equalsIgnoreCase("windows"))
			{
			capabilities.setPlatform(Platform.WIN10);
			}else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}else {
				System.out.println("No matching OS");
			return;
			}		
			
			switch(browser.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome");
			break;
			case "edge" : capabilities.setBrowserName("MicrosoftEdge");
			break;
			case "firefox" : capabilities.setBrowserName("firefox");
			break;
			default: 
				System.out.println("no matching browser");
				return;
			}
			
		driver= new RemoteWebDriver(new URL("http://192.168.1.66:4444/wd/hub"),capabilities);
		}
		if(props.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(browser.toLowerCase())
			{
			case "chrome" : driver=new ChromeDriver();
			break;
			case "edge" : driver=new EdgeDriver();
			break;
			case "firefox" : driver= new FirefoxDriver();
			break;
			default: System.out.println("invalid browser");return;
			}
		}
		
	
		//loading the properties file
		//FileReader file = new FileReader("./src//test//resources//config.properties");
	
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//	https://tutorialsninja.com/demo/index.php?route=account/login
		//reading url property from the config.properties file
		driver.get(props.getProperty("appURL"));
		//driver.get("https://tutorialsninja.com/demo/index.php?route=account/login");
		driver.manage().window().maximize();
		
	}
	public String randomString()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(7);
		return generatedString;
	}
	
	public String randomNumbers()
	{
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}
	
	public String randomAlphaNumeric()
	{
		String generatedAlphaNumeric = RandomStringUtils.randomAlphanumeric(8);
		return generatedAlphaNumeric;
	}
	
	public String captureScreen(String tname) throws IOException
	{
		String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takeScreenshot=(TakesScreenshot) driver;
		File sourceFile=takeScreenshot.getScreenshotAs(OutputType.FILE);
		String targetFilePath= System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
		File targetFile= new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tetarDown()
	{
		driver.quit();
		
	}
	
}

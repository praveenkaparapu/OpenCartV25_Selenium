package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseTest;

public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public String repName;
	
	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("YYYY.MM.DD.HH.mm.ss").format(new Date());//time stamp
		repName="Test_Report_"+timeStamp+".html";
		sparkReporter= new ExtentSparkReporter(".\\reports\\"+repName);//specify location of the report
		sparkReporter.config().setDocumentTitle("Open Cart Automtion Report");//title of the report
		sparkReporter.config().setReportName("Open cart functional testing");//name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module","Admin");
		extent.setSystemInfo("SubModule","Customers");
		extent.setSystemInfo("UserName",System.getProperty("user.name"));
		extent.setSystemInfo("Environment","QA");
		
		//way to read xml parameters
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operting System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Broswer", browser);
		
		//stores the groups used in XML file
		List<String> includedGroups= testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups",includedGroups.toString());
		}		
	}
	
	//onTestSuccess
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());//to display groups in report
		test.log(Status.PASS,result.getName()+"got successfully executed");
		
	}
	
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());//to display groups in report
		test.log(Status.FAIL,result.getName()+"got failed");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		try {
			String imgPath=new BaseTest().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());//to display groups in report
		test.log(Status.SKIP,result.getName()+"got skipped");
		test.log(Status.INFO,result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}catch(Exception e)
		{
			
		}
	}

}

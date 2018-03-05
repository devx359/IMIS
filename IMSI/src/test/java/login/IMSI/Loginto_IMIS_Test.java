package login.IMSI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Utilities.ExtentManager;


public class Loginto_IMIS_Test {
	ExtentReports reports;
	WebDriver driver;
	ExtentTest test;
	int count=1;

	
	@BeforeClass
	public void setup()
	{
		reports = ExtentManager.GetExtent("IMIS Login");
		test = reports.createTest("IMIS Login: ");
		System.setProperty("webdriver.chrome.driver", "D:\\selenium jars\\chromedriver.exe");

		IOExcel.excelSetup("D:\\testdata\\imis_test.xlsx");
		

	}
	
	
	
	@Test(dataProvider="DataSource")
	public void loginToProj(String username ,String Password) throws InterruptedException
	{
		
		
		driver = new ChromeDriver();
		//clearing session
		
		
		driver.get("http://192.168.2.245/imis/index.php");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='userpass']")).sendKeys(Password);
		driver.findElement(By.xpath("//button[@value='Login']")).click();
		
	
		//Project Selection
		
		
		Select sel = new Select (driver.findElement(By.xpath("//select[@id='emp_shift']")));
		sel.selectByVisibleText("General");
		Thread.sleep(1000);
		Select sel2 = new Select (driver.findElement(By.xpath("//select[@id='emp_center']")));
		sel2.selectByVisibleText("Saltlake");
		Thread.sleep(1000);
		Select sel3 = new Select (driver.findElement(By.xpath("//select[@id='domain_id']")));
		sel3.selectByVisibleText("Internet Process");
		Thread.sleep(1000);
		
		Select sel4 = new Select (driver.findElement(By.xpath("//select[@id='project_id']")));
		sel4.selectByVisibleText("GTY3 [SMS_001]");
		
		
		Thread.sleep(1000);
		Select sel5 = new Select(driver.findElement( By.xpath("//select[@id='subproject_id']")));
		sel5.selectByVisibleText("Hourly rate [SMS_001_1]");
		
		Thread.sleep(1400);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement button= wait.until(ExpectedConditions.presenceOfElementLocated(By.id("project_login_btn")))	;
		button.submit();
		Thread.sleep(2000);
		
		//Click logout
	/*	driver.findElement(By.xpath("//i[@class='fa fa-gears']")).click();
		driver.findElement(By.xpath("//button[@id='logout_btn']"));
		
		Thread.sleep(3000);*/
		System.out.println("Log in operation successfull for : "+username);
		test.info("User:"+username +"Logged in Succesfully");
		((ChromeDriver) driver).getSessionStorage().clear();
		((ChromeDriver) driver).getLocalStorage().clear();
		driver.quit();
	}
	
	
	  @DataProvider(name="DataSource")
	  public static Object [][] exceldatasource()
	  {
		  int count=IOExcel.Getrowcount("Sheet1");
		  System.out.println("row count"+count);
		  
		  Object arr[][]=new Object[count][2];
	
		  
		  int n=0;int k=0;
		  for(int i=1;i<=count;i++)
		  {
			  k=0;
			  for(int j=0;j<=1;j++)
			  { 
				 arr[n][k]= IOExcel.getExcelStringData(i, j,"Sheet1");
				 System.out.println(arr[n][k]);
				 k++;
				
			  }
			  n++;
		  }
		
		  
		  return arr;
		
	  }
	
	
	@AfterClass
	public void teardown() throws InterruptedException
	{
		System.out.println("closing now....");
		 reports.flush();
		//driver.quit();
	}

}

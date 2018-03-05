package login.IMSI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

import Utilities.ExtentManager;





public class Login {
	ExtentReports reports;
	WebDriver driver;

	
	@BeforeClass
	public void setup()
	{

		System.setProperty("webdriver.chrome.driver", "D:\\selenium jars\\chromedriver.exe");
		reports = ExtentManager.GetExtent("lOGIN");
		IOExcel.excelSetup("D:\\testdata\\imsi.xlsx");
		

	}
	
	
	
	@Test(dataProvider="DataSource")
	public void loginToProj(String username ,String Password) throws InterruptedException
	{
		driver = new ChromeDriver();
		//LOGIN PAGE
		driver.get("https://imis.imerit.net/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='userpass']")).sendKeys(Password);
		driver.findElement(By.xpath("//button[@value='Login']")).click();
		
	
		//Project Selection
		Select sel = new Select (driver.findElement(By.xpath("//select[@id='emp_center']")));
		sel.selectByVisibleText("Saltlake");
		
		Select sel2 = new Select (driver.findElement(By.xpath("//select[@id='domain_id']")));
		sel2.selectByVisibleText("General");
		
		Thread.sleep(3000);
		Select sel3 = new Select(driver.findElement( By.xpath("//select[@id='project_id']")));
		sel3.selectByVisibleText("SGA [ITS_003]");
		
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement button= wait.until(ExpectedConditions.presenceOfElementLocated(By.id("project_login_btn")))	;
		button.submit();

		
		//Click logout
	/*	driver.findElement(By.xpath("//i[@class='fa fa-gears']")).click();
		driver.findElement(By.xpath("//button[@id='logout_btn']"));
		
		Thread.sleep(3000);*/
		System.out.println("Log in operation successfull for : "+username);
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
		//driver.quit();
	}

}
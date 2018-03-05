package login.IMSI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SessionLogout {

	WebDriver driver;
	@BeforeClass
	public void setup()
	{

		System.setProperty("webdriver.chrome.driver", "D:\\selenium jars\\chromedriver.exe");
	//	driver = new ChromeDriver();
		IOExcel.excelSetup("D:\\testdata\\imsi.xlsx");
		

	}
	
	@Test(dataProvider="DataSource")
	
	public void logoutToProj(String username ,String Password) throws InterruptedException
	{
		driver = new ChromeDriver();
		driver.get("https://imis.imerit.net/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='userpass']")).sendKeys(Password);
	//	Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@value='Login']")).click();
		

		 WebDriverWait wait = new WebDriverWait(driver, 10);
		 wait.ignoring( NoSuchSessionException.class);
		 Thread.sleep(2000);
		// WebElement popup =driver.findElement(By.xpath("//input[@value='restore_session']"));
		Boolean elepresent= driver.findElements(By.xpath("//input[@value='restore_session']")).isEmpty();
		System.out.println("popup present"+elepresent);
		if(elepresent==false)
		 {
			System.out.println("Entering popup present block");
		// List<WebElement> radio = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//input[@name='login_option']"))));
		WebElement ele= wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@value='restore_session']"))));
		ele.click();
		
		Thread.sleep(1000);
		//2nd alert box
		Alert promptAlert  = driver.switchTo().alert();
		promptAlert.accept();
		
		 }
		//Thread.sleep(3000);
		//Click logout
		driver.findElement(By.xpath("//i[@class='fa fa-gears']")).click();
	
		driver.findElement(By.xpath("//button[@id='logout_btn']")).click();
		driver.quit();
	//	Thread.sleep(2000);
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
	
}

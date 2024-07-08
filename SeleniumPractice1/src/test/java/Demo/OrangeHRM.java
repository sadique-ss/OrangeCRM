package Demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;



public class OrangeHRM {
	
	public String baseUrl="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("Before test executed");
		driver=new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.get(baseUrl);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		
	}
	
	public void login()
	{
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		//driver.findElement(By.xpath("//input[@placeholder='Password']"));
		driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys("admin123");
		
		driver.findElement(By.xpath("//button[@type='submit']")).submit();
						
	}
	
	
	@Test(priority=2, enabled=false)
	public void loginValidCreentials()
	{
		login();
		
		//Verify login successful
		String pageTitle=driver.getTitle();
		
		/*
		 * if(pageTitle=="OrangeHRM") { System.out.println("Login successful"); } else {
		 * System.out.println("Login unsuccessful"); }
		 */
		
		Assert.assertEquals("OrangeHRM", pageTitle);
		
		logout();
	}
	
	@Test(priority=1, enabled=false)
	public void loginInvalidCredentials() throws InterruptedException {
		
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		
		driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys("123");
		
		driver.findElement(By.xpath("//button[@type='submit']")).submit();
		
		String actual_message =driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
		String expected_message="Invalid credentials";
		
		Assert.assertEquals(expected_message, actual_message);
		
		Thread.sleep(1500);
		
	}
	
	@Test(priority=3, enabled=false)
	public void addEmployee() throws InterruptedException {
		
		login();
		
		driver.findElement(By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][normalize-space()='PIM']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='Add']")).click();
		driver.findElement(By.xpath("(//input[@placeholder='First Name'])")).sendKeys("Majo");
		driver.findElement(By.xpath("(//input[@placeholder='Last Name'])")).sendKeys("Shaikh");
		driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]")).sendKeys("6767");
		driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
		Thread.sleep(5000);
		
		String personalDetail_msg=driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']")).getText();
		String exp_personalDetail_msg="Personal Details";
		
		Assert.assertEquals(personalDetail_msg,exp_personalDetail_msg);
		
		logout();		
		
	}
	
	@Test(priority=4, enabled=false)
	public void searchEmployee() throws InterruptedException
	{
		login();
		
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[text()='Employee List']")).click();
	    driver.findElements(By.tagName("input")).get(1).sendKeys("Majo");
	    
	    driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
	    
	    Thread.sleep(3000);
	    List<WebElement> element =driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));
	    String actual_msg=element.get(0).getText();
	    String expected_msg="Record Found";
	    
	    System.out.println(actual_msg);
	    
	    Thread.sleep(1000);
	    
	    logout();	    
	    
	    Assert.assertTrue(actual_msg.contains(expected_msg));		
	}
	
	@Test(priority=5, enabled=true)
	public void searchEmployeeID() throws InterruptedException
	{
		login();
		
		String exp_id="0381";
		String actual_id="";
		
		
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[text()='Employee List']")).click();
	    driver.findElements(By.tagName("input")).get(2).sendKeys(exp_id);
	    
	    driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
	    
	    Thread.sleep(10000);
	    
	    JavascriptExecutor executor = (JavascriptExecutor) driver;
	    executor.executeScript("window.scrollBy(0," + 500 + ")");
	    
	    Thread.sleep(10000);
	    
	    List<WebElement> rows=driver.findElements(By.xpath("(//div[@role='row'])"));
	    
	    if(rows.size()>1)
	    {
	    	actual_id=driver.findElement(By.xpath("(((//div[@role='row'])[2])/div[@role='cell'])[2]")).getText();
	    }
	    
	    logout();
	    
	    Assert.assertEquals(actual_id, exp_id);
		
	}
	
	public void logout()
	{
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")).click();
		//driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
		
		List <WebElement> element_drp =driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
		
		
		element_drp.get(3).click();
	}
	
	@AfterTest
	public void tearDown() throws InterruptedException
	{
		//logout();
		Thread.sleep(5000);
		
		driver.close();
		//driver.quit();
	}
	

}

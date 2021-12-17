package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Main {
	public static void main (String[] args) {
		System.setProperty ("webdriver.chrome.driver", "C:/Program Files/chromedriver/chromedriver.exe");
		
		WebDriver driver = new ChromeDriver ();
		
		try {
			for (int i = 1; i < 4; i++) {
				driver.get ("http://3.137.152.242/login/");
				
				WebElement usernameInput = driver.findElement (By.id ("usernameInput"));
				WebElement passwordInput = driver.findElement (By.id ("passwordInput"));
				
				WebElement loginButton = driver.findElement (By.id ("loginButton"));
				
				usernameInput.sendKeys ("employee" + i);
				passwordInput.sendKeys ("password");
				
				loginButton.click ();
				
				Thread.sleep (2000);
				
				for (int j = 0; j < 3; j++) {
					WebElement requestsCreateAmount = driver.findElement (By.id ("requestsCreateAmount"));
					WebElement requestsCreateType = driver.findElement (By.id ("requestsCreateType"));
					WebElement requestsCreateDescription = driver.findElement (By.id ("requestsCreateDescription"));
					WebElement requestsCreateSubmitButton = driver.findElement (By.id ("requestsCreateSubmitButton"));
					
					requestsCreateAmount.clear ();
					requestsCreateAmount.sendKeys("10101.01");
					
					new Select (requestsCreateType).selectByIndex (3);
					
					requestsCreateDescription.clear ();
					requestsCreateDescription.sendKeys ("AUTOMATED DESCRIPTION");
					
					requestsCreateSubmitButton.click ();
					
					Thread.sleep (2000);
				}
			}
		}
		
		catch (Exception exception) {
			System.out.println ("Error!");
			exception.printStackTrace ();
		}
		
		finally {
			driver.quit ();
		}
	}
}

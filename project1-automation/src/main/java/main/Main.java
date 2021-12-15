package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class Main {
	public static void main (String[] args) {
		System.setProperty ("webdriver.chrome.driver", "C:/Program Files/chromedriver/chromedriver.exe");
		
		WebDriver driver = new ChromeDriver ();
		WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds (5));
		
		driver.get ("http://localhost/login");
		
		WebElement usernameInput = driver.findElement (By.id ("usernameInput"));
		WebElement passwordInput = driver.findElement (By.id ("passwordInput"));
		
		WebElement loginButton = driver.findElement (By.id ("loginButton"));
		
		usernameInput.sendKeys ("employee1");
		passwordInput.sendKeys ("p4ssw0rd");
		
		loginButton.click ();
		
		By requestsCreateAmountSelector = By.id ("requestsCreateAmount");
		By requestsCreateTypeSelector = By.id ("requestsCreateType");
		By requestsCreateDescriptionSelector = By.id ("requestsCreateDescription");
		By requestsCreateSubmitButtonSelector = By.id ("requestsCreateSubmitButton");
		
		wait.until (ExpectedConditions.elementToBeClickable (requestsCreateAmountSelector));
		wait.until (ExpectedConditions.elementToBeClickable (requestsCreateTypeSelector));
		wait.until (ExpectedConditions.elementToBeClickable (requestsCreateDescriptionSelector));
		wait.until (ExpectedConditions.elementToBeClickable (requestsCreateSubmitButtonSelector));
		
		driver.findElement (requestsCreateAmountSelector).sendKeys ("101.01");
		new Select (driver.findElement (requestsCreateTypeSelector)).selectByIndex (3);
		driver.findElement (requestsCreateDescriptionSelector).sendKeys ("AUTOMATED DESCRIPTION");
		
		driver.findElement (requestsCreateSubmitButtonSelector).click ();
		
		driver.quit ();
	}
}

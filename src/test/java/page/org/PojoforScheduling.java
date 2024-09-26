package page.org;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import SchedulingSalesDemo.BaseClassAthena;

public class PojoforScheduling extends BaseClassAthena {

	
	public static void optvalfield(String label) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@optval='"+label+"'])[1]")));
		driver.findElement(By.xpath("(//input[@optval='"+label+"'])[1]")).click();

	}
	
	public static void optvalRadiofield(String label) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='questions_parent active last_of_child']//input[@type='radio'][@optval='"+label+"']")));
		driver.findElement(By.xpath("//div[@class='questions_parent active last_of_child']//input[@type='radio'][@optval='"+label+"']")).click();

	}

	public static void pcontains(String label) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[contains(.,'" + label + "')])[1]")));
		driver.findElement(By.xpath("(//p[contains(.,'" + label + "')])[1]")).click();

	}

	public static void picktime() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='flex flex-col left_block active']")));
		String attribute = driver.findElement(By.xpath("//div[@class='flex flex-col left_block active']"))
				.getAttribute("data-provider_id");

		driver.findElement(
				By.xpath("(//*[@id='timeslot_" + attribute + "']//div[@class='flex flex-col slotgroup'])[1]")).click();

	}

	public static void reasonforvisit(String label, String option) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement visit = driver.findElement(By.xpath("//span[@data-labelname='" + label + "']"));
		wait.until(ExpectedConditions.elementToBeClickable(visit)).click();
		driver.findElement(By.xpath("//ul//li[contains(.,'" + option + "')]")).click();

	}

	public static void informationRadiofield(String label, String ans) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement radiobtn = driver.findElement(By.xpath(
				"//input[@data-labelname='" + label + "']//following-sibling::label[contains(.,'" + ans + "')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radiobtn);
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(radiobtn));
		radiobtn.click();

	}

	public static void informationTextfield(String label, String ans) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement text = driver.findElement(By.xpath("//input[@data-labelname='" + label + "']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", text);
		wait.until(ExpectedConditions.elementToBeClickable(text));
		text.sendKeys(ans, Keys.ENTER);

	}

	public static void patientinfo(String id, String value) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[@id='register_appointment'][@style='display: block;']//input[@id='patient_first_name']")));
		WebElement element = driver.findElement(By.xpath("//div[@class='col-md-6 col-12']//input[@id='" + id + "']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		driver.findElement(By.xpath("//div[@class='col-md-6 col-12']//input[@id='" + id + "']")).sendKeys(value);

	}

}

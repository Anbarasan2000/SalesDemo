package page.org;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import SchedulingSalesDemo.BaseClassAthena;

public class IntakePojo extends BaseClassAthena {

	public static void Textfield(String values, String ans) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@data-labelname='" + values + "'])[1]")));
		WebElement element = driver.findElement(By.xpath("(//input[@data-labelname='" + values + "'])[1]"));
		element.clear();
		element.sendKeys(ans);

	}

	public static void SocialHistoryText(String id, String value) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-labelname='" + id + "']")));

		WebElement element = driver.findElement(By.xpath("//input[@data-labelname='" + id + "']"));

		element.sendKeys(value);

	}

	public static void SocialHistoryRadio(String id, String value) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//input[@data-labelname='" + id + "']//following-sibling::label[contains(.,'" + value + "')]")));

		WebElement element = driver.findElement(By
				.xpath("//input[@data-labelname='" + id + "']//following-sibling::label[contains(.,'" + value + "')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(1000);
		element.click();

	}

	public static void Selectvalue(String val) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//ul[@class='select2-results__options']//*[text()='" + val + "']")));
		WebElement Marital = driver
				.findElement(By.xpath("//ul[@class='select2-results__options']//*[text()='" + val + "']"));

		Marital.click();

	}

	public static void dropdownoptions(String name, String option) throws InterruptedException {
		Actions ac = new Actions(driver);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement dropdown = driver.findElement(By.xpath("//span[@data-labelname='" + name + "']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
		Thread.sleep(1000);
		ac.moveToElement(dropdown).click().build().perform();
		WebElement txt = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='" + option + "']")));
		txt.click();

	}

	public static void careteam(String label, String name) throws InterruptedException {
		Actions ac = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebElement PCP = driver.findElement(By.xpath("//span[@data-labelname='" + label + "']"));
		js.executeScript("arguments[0].scrollIntoView(true);", PCP);
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOf(PCP));
		ac.moveToElement(PCP).click().build().perform();
		WebElement srch = driver.findElement(By.xpath("//input[@role='searchbox']"));
		srch.sendKeys(name);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@aria-selected='true']")));
		ac.moveToElement(srch).keyDown(Keys.SHIFT.ARROW_DOWN).build().perform();
		ac.moveToElement(srch).keyDown(Keys.ENTER).build().perform();
	}

	public static void pastmedical(String label, String option, String rel) throws InterruptedException {
		// have any past/existing medical condition
		Actions ac = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement medicalcon = driver
				.findElement(By.xpath("//div[@data-accessname='" + label + "']//strong[contains(.,'Yes')]"));
		wait.until(ExpectedConditions.elementToBeClickable(medicalcon));
		Thread.sleep(2000);
		ac.moveToElement(medicalcon).click().build().perform();
		Thread.sleep(2000);
		WebElement medical = driver
				.findElement(By.xpath("//span[@role='textbox'][contains(.,'Type to search or select')]"));
		wait.until(ExpectedConditions.elementToBeClickable(medical));
		ac.moveToElement(medical).click().build().perform();
		WebElement medicalcondi = driver.findElement(By.xpath("(//input[@type='search'])[2]"));
		medicalcondi.sendKeys(option);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul//*[text()='" + option + "']")));
		Thread.sleep(2000);
		ac.moveToElement(medicalcondi).keyDown(Keys.SHIFT.ARROW_DOWN).build().perform();
		ac.moveToElement(medicalcondi).keyDown(Keys.ENTER).build().perform();

		WebElement medicalfam = driver.findElement(By.xpath("//input[@type='search']"));
		wait.until(ExpectedConditions.elementToBeClickable(medicalfam));
		ac.moveToElement(medicalfam).click().build().perform();
		Thread.sleep(2000);
		WebElement medicalcondifam = driver.findElement(By.xpath("(//input[@type='search'])[2]"));
		medicalcondifam.sendKeys(rel);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul//span[contains(.,'" + rel + "')]")));
		ac.moveToElement(medicalcondifam).keyDown(Keys.SHIFT.ARROW_DOWN).build().perform();
		ac.moveToElement(medicalcondifam).keyDown(Keys.ENTER).build().perform();
		ac.moveToElement(medicalcondifam).keyDown(Keys.ENTER).build().perform();

		WebElement add = driver
				.findElement(By.xpath("//div[@data-accessname='family_medical_condition'][@data-type='form-submit']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", add);
		Thread.sleep(2000);
		add.click();
		Thread.sleep(2000);
		WebElement contin = driver.findElement(By.xpath("(//span[contains(.,'Continue')])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(contin)).click();

	}

	public static void healthhistory(String quest, String option, String addbtn) throws InterruptedException {
		Actions ac = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

		WebElement pst = driver
				.findElement(By.xpath("//div[@data-accessname='" + quest + "']//strong[contains(.,'Yes')]"));
		wait.until(ExpectedConditions.elementToBeClickable(pst));
		Thread.sleep(2000);
		ac.moveToElement(pst).click().build().perform();
		Thread.sleep(2000);
		WebElement pastmedi = driver
				.findElement(By.xpath("(//span[@role='textbox'][contains(.,'Type to search or select')])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(pastmedi));
		ac.moveToElement(pastmedi).click().build().perform();
		WebElement pastMedicals = driver.findElement(By.xpath("//input[@type='search']"));
		pastMedicals.sendKeys(option);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul//*[text()='" + option + "']")));
		ac.moveToElement(pastMedicals).keyDown(Keys.SHIFT.ARROW_DOWN).build().perform();
		ac.moveToElement(pastMedicals).keyDown(Keys.ENTER).build().perform();

		WebElement add = driver
				.findElement(By.xpath("//div[@data-accessname='" + addbtn + "'][@data-type='form-submit']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", add);
		Thread.sleep(2000);
		add.click();
		Thread.sleep(2000);
		WebElement contin = driver.findElement(By.xpath("(//span[contains(.,'Continue')])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(contin)).click();
	}

	public static void birthpage(String label, String ans) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement childbirth = driver.findElement(By.xpath(
				"//input[@data-labelname='" + label + "']//following-sibling::label[contains(.,'" + ans + "')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", childbirth);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(childbirth));
		childbirth.click();
	}

	public static void ROSPage(String qus) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement ros = driver.findElement(By.xpath("//h2[contains(.,'" + qus
				+ "')]//following-sibling::div//following-sibling::input[@type='checkbox']//following-sibling::label"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ros);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(ros));
		ros.click();
	}

	public static void CardonFIle(String text) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'" + text + "')]")));

		driver.findElement(By.xpath("//button[contains(.,'" + text + "')]")).click();

	}

	public static void addCardonFIle(String id, String value) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='" + id + "']")));

		WebElement element = driver.findElement(By.xpath("//input[@id='" + id + "']"));

		element.sendKeys(value);

	}

}

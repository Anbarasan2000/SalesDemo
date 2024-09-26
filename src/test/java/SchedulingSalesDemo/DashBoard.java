//package SchedulingSalesDemo;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.time.Duration;
//
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import io.cucumber.java.en.Then;
//import page.org.IntakePojo;
//
//@SuppressWarnings("resource")
//public class DashBoard {
//
//	static WebDriver driver;
//	IntakePojo intake = new IntakePojo();
//
//	@Then("Dashboard Login")
//	public void Dashboard_Login() throws InterruptedException, IOException {
//
//		File f = new File("D:\\SalesDemoWalkin.xlsx");
//		FileInputStream file = new FileInputStream(f);
//		XSSFWorkbook workbook = new XSSFWorkbook(file);
//		XSSFSheet sheet = workbook.getSheet("Dashboard");
//		XSSFSheet sheet1 = workbook.getSheet("PatientCreation");
//
//		int rowCount = sheet.getLastRowNum();
//		sheet.getRow(1).getLastCellNum();
//
//		// For Loop Used for performing a certain action with specific number of Times
//		for (int i = 1; i <= 1; i++) {
//			XSSFRow celldata = sheet.getRow(i);
//
//			driver = new ChromeDriver();
//			driver.navigate().to("https://demoportal.yosicare.com/index");
//			driver.manage().window().maximize();
//
//			driver.findElement(By.xpath("//input[@id='email']")).sendKeys(celldata.getCell(0).getStringCellValue());
//			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(celldata.getCell(1).getStringCellValue());
//			driver.findElement(By.xpath("//input[@id='lgn_sub']")).click();
//			Thread.sleep(3000);
//			driver.findElement(By.xpath("//*[@id='locationpop']/div/div/div/div")).click();
//
//			WebElement location = driver.findElement(
//					By.xpath("(//div[@class='nice-select wide location location-select open']//ul//li[text()='"
//							+ celldata.getCell(2).getStringCellValue() + "'])[1]"));
//			Thread.sleep(1000);
//			location.click();
//
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//			int size = driver
//					.findElements(By.xpath("//tr[@class='info group-by collapsed']/td[contains(.,'David Carter')]"))
//					.size();
//			if (size == 1) {
//				driver.findElement(By.xpath("//tr[@class='info group-by collapsed']/td[contains(.,'David Carter')]"))
//						.click();
//			}
////			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(.,'Jessica Brown (1)')]")));
////			driver.findElement(By.xpath("//td[contains(.,'Jessica Brown (1)')]")).click();
//
//			wait.until(ExpectedConditions.visibilityOfElementLocated(
//					By.xpath("(//*[contains(text(),'" + celldata.getCell(3).getStringCellValue() + "')])[1]")));
//			boolean displayed = driver
//					.findElement(
//							By.xpath("(//*[contains(text(),'" + celldata.getCell(3).getStringCellValue() + "')])[1]"))
//					.isDisplayed();
//			System.out.println(displayed);
//
//			// wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@data-pname='"
//			// + celldata.getCell(3).getStringCellValue() + "'])[1]")));
//			String STAT = driver.findElement(By.xpath("(//a[@data-pfname='"+sheet1.getRow(1).getCell(4).getStringCellValue()+"'])[1]")).getAttribute("data-checkinflag");
//			
//			System.out.println(STAT);
//			sheet.getRow(1).createCell(4).setCellValue(STAT);
//			FileOutputStream FOS=new FileOutputStream(f);
//			workbook.write(FOS);
//
//			
//		}
//	}
//
//}

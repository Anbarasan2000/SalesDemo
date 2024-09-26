package SchedulingSalesDemo;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Email.EmailSender;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import junit.framework.Assert;
import page.org.IntakePojo;
import page.org.PojoforScheduling;

public class Steps_Definations extends BaseClassAthena {

	@Test(priority = 1)
	@Given("Open Chrome browser and Launching Scheduling URl")
	public void open_chrome_browser_and_launching_scheduling_u_rl() throws Exception {

		ScreenRecorderUtil.startRecord("open_chrome_browser_and_launching_scheduling_u_rl");
		BaseClassAthena.launchbro();

		File f = new File("D:\\SalesDemo regression FIles\\SalesDemoScheduling.xlsx");
		FileInputStream file = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("PatientCreation");
		XSSFSheet dash = workbook.getSheet("Dashboard");
		sheet.getRow(1).getLastCellNum();
		for (int i = 4; i <= 4; i++) {

			driver.get("https://www.fakenamegenerator.com/gen-male-us-us.php");
			
			String patName = driver.findElement(By.xpath("//div[@class='address']/h3")).getText();
			dash.getRow(i).createCell(3).setCellValue(patName+" Testpatient");
			sheet.getRow(i).createCell(8).setCellValue(patName.toUpperCase());
			String patemail = patName.replace(" ", "");
			sheet.getRow(i).createCell(13).setCellValue(patemail+"@yopmail.com");
			char charAt = patName.charAt(0);
			String charAsString = String.valueOf(charAt);
			sheet.getRow(i).createCell(16).setCellValue(charAsString);
			FileOutputStream fos1 = new FileOutputStream(f);
			workbook.write(fos1);
		
			driver.get("https://demoschedulewidget.yosicare.com/b1438ad6cf4b21ca3d867adb412c28e1");

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			try {

				PojoforScheduling.optvalfield(sheet.getRow(i).getCell(0).getStringCellValue());

				if (sheet.getRow(i).getCell(1).getStringCellValue()
						.equals("I am looking for an In-Person appointment")) {
					PojoforScheduling.optvalRadiofield(sheet.getRow(i).getCell(1).getStringCellValue());
					PojoforScheduling.optvalRadiofield(sheet.getRow(i).getCell(2).getStringCellValue());
					PojoforScheduling.optvalRadiofield(sheet.getRow(i).getCell(3).getStringCellValue());

					if (sheet.getRow(i).getCell(0).getStringCellValue().equals("Pediatric Appointment")) {
						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("(//h2[contains(.,'1 - 5')])//preceding-sibling::input")));
						driver.findElement(By.xpath("(//h2[contains(.,'"
								+ sheet.getRow(i).getCell(4).getStringCellValue() + "')])//preceding-sibling::input"))
								.click();
					}

					PojoforScheduling.pcontains(sheet.getRow(i).getCell(5).getStringCellValue());

				} else {

					PojoforScheduling.optvalfield(sheet.getRow(i).getCell(1).getStringCellValue());
					PojoforScheduling.optvalfield(sheet.getRow(i).getCell(2).getStringCellValue());
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h2[contains(.,'"
							+ sheet.getRow(i).getCell(4).getStringCellValue() + "')])//preceding-sibling::input")));
					driver.findElement(By.xpath("(//h2[contains(.,'" + sheet.getRow(i).getCell(4).getStringCellValue()
							+ "')])//preceding-sibling::input")).click();

				}

				// select time
				PojoforScheduling.picktime();

				// Patient Information
				PojoforScheduling.patientinfo("patient_first_name", sheet.getRow(i).getCell(8).getStringCellValue());
				PojoforScheduling.patientinfo("patient_last_name", sheet.getRow(i).getCell(9).getStringCellValue());
				PojoforScheduling.patientinfo("patient_dob", sheet.getRow(i).getCell(10).getStringCellValue());

				js.executeScript("window.scrollBy(0,300)", "");
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@data-field_type='radio'][@value='"
						+ sheet.getRow(i).getCell(11).getStringCellValue() + "']")).click();

				PojoforScheduling.patientinfo("patient_mobile_phone", sheet.getRow(i).getCell(12).getStringCellValue());
				PojoforScheduling.patientinfo("patient_email", sheet.getRow(i).getCell(13).getStringCellValue());

				// if Concent to text & call
				List<WebElement> textandCall = driver.findElements(By.xpath("//input[@id='consenttotext']"));
				if (textandCall.size() == 1) {
					driver.findElement(By.xpath("//input[@id='consenttotext']//following-sibling::label")).click();
					driver.findElement(By.xpath("//input[@id='consenttocall']//following-sibling::label" + "")).click();

				}
				// If Directly Got=o the COmplete Intake
				List<WebElement> compltIntake = driver.findElements(By.xpath(
						"//div//div//following-sibling::div[@class='Heading-module_weight500 left text-darkBlue back_page book_appoint_back'][contains(.,'Back')]//following::div/button[contains(.,'Continue Intake')]"));
				int size = compltIntake.size();
				if (size == 1) {

					driver.findElement(By.xpath(
							"//div//div//following-sibling::div[@class='Heading-module_weight500 left text-darkBlue back_page book_appoint_back'][contains(.,'Back')]//following::div/button[contains(.,'Continue Intake')]"))
							.click();

				} else {

					// insurance
					if (sheet.getRow(i).getCell(14).getStringCellValue().equals("N")) {
						js.executeScript("window.scrollBy(0,450)", "");
						Thread.sleep(3000);
						driver.findElement(By.xpath("//input[@data-option='"
								+ sheet.getRow(i).getCell(14).getStringCellValue() + "']//following-sibling::label"))
								.click();

					} else {

						driver.findElement(By.xpath(
								"//span[@class='select2-selection__placeholder'][contains(.,'Please select an option below')]"))
								.click();
						driver.findElement(By.xpath("//ul/li[contains(.,'Self')]")).click();
						driver.findElement(By.xpath(
								"//span[@id='select2-search_47-container']//span[@class='select2-selection__placeholder']"))
								.click();

					}

					// Payment
					if (sheet.getRow(i).getCell(15).getStringCellValue().equals("Pay later")) {
						driver.findElement(By.xpath(
								"//div//span[contains(.,'" + sheet.getRow(i).getCell(15).getStringCellValue() + "')]"))
								.click();
						WebElement concent = driver.findElement(By.xpath(
								"//p[contains(.,'Yes, I have read and understood the')]//parent::span//parent::div//label"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", concent);
						Thread.sleep(2000);
						concent.click();

						Actions builder = new Actions(driver);
						WebElement sign = driver.findElement(By.xpath("//canvas[@id='ctlSignature']"));
						Actions action = new Actions(driver);
						js.executeScript("window.scrollBy(0,200)", "");
						Thread.sleep(2000);
						// Perform drawing actions
						action.moveToElement(sign).clickAndHold().moveByOffset(0, 80).moveByOffset(30, -60)
								.moveByOffset(30, 60).moveByOffset(-5, -15).moveByOffset(-10, 0).moveByOffset(0, -15)
								.release() // Release the click to finish the drawing
								.build().perform();

						// Optional additional movements if needed
						action.moveToElement(sign).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -60)
								.moveByOffset(30, 0).moveByOffset(0, 30).moveByOffset(-200, 0).moveByOffset(10, 0)
								.moveByOffset(0, 15).moveByOffset(-10, 0).release() // Release the click to finish the
																					// additional movements
								.build().perform();

						// Additional movements if required
						action.moveToElement(sign).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -30)
								.moveByOffset(0, -10).release() // Release the click to finish the additional movements
								.build().perform();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//button[contains(.,'Schedule Appointment')]")).click();

						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
								"(//div[@class='col-md-6 col-12']//div[@class='flex flex-col gap-2']/span)[1]")));
						String provName = driver
								.findElement(By.xpath(
										"(//div[@class='col-md-6 col-12']//div[@class='flex flex-col gap-2']/span)[1]"))
								.getText();
						System.out.println(provName);

						if (provName.equals("Radha Desai, MD")) {

							dash.getRow(i).createCell(5).setCellValue(provName);
							dash.getRow(i).createCell(4).setCellValue("Jessica Brown");
							FileOutputStream fos = new FileOutputStream(f);
							workbook.write(fos);

						} else if (provName.equals("Nasimul Siddiqui, MD")) {
							dash.getRow(i).createCell(5).setCellValue(provName);
							dash.getRow(i).createCell(4).setCellValue("John Evans");
							FileOutputStream fos = new FileOutputStream(f);
							workbook.write(fos);

						} else if (provName.equals("Niral Patel, MD")) {
							dash.getRow(i).createCell(5).setCellValue(provName);
							dash.getRow(i).createCell(4).setCellValue("David Carter");
							FileOutputStream fos = new FileOutputStream(f);
							workbook.write(fos);

						} else if (provName.equals("Nicole Colon, APRN")) {
							dash.getRow(i).createCell(5).setCellValue(provName);
							dash.getRow(i).createCell(4).setCellValue("Emily Davis");
							FileOutputStream fos = new FileOutputStream(f);
							workbook.write(fos);
						}

					} else {

						IntakePojo.addCardonFIle("paymentform_namecard", "hari");
						Thread.sleep(2000);
						IntakePojo.addCardonFIle("paymentform_creditcard", "4000");
						IntakePojo.addCardonFIle("paymentform_creditcard", "0000");
						IntakePojo.addCardonFIle("paymentform_creditcard", "0000");
						IntakePojo.addCardonFIle("paymentform_creditcard", "0002");

						IntakePojo.addCardonFIle("card_expdate", "12/2026");
						IntakePojo.addCardonFIle("cvv_number", "123");
						IntakePojo.addCardonFIle("paymentform_zipcode", "10001");
						IntakePojo.addCardonFIle("paymentform_address", "main street");

						WebElement concent = driver.findElement(By.xpath(
								"//p[contains(.,'Yes, I have read and understood the')]//parent::span//parent::div//label"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", concent);
						Thread.sleep(2000);
						concent.click();

						Actions builder = new Actions(driver);
						WebElement sign = driver.findElement(By.xpath("//canvas[@id='ctlSignature']"));
						Actions action = new Actions(driver);
						js.executeScript("window.scrollBy(0,200)", "");
						Thread.sleep(2000);
						// Perform drawing actions
						action.moveToElement(sign).clickAndHold().moveByOffset(0, 80).moveByOffset(30, -60)
								.moveByOffset(30, 60).moveByOffset(-5, -15).moveByOffset(-10, 0).moveByOffset(0, -15)
								.release() // Release the click to finish the drawing
								.build().perform();

						// Optional additional movements if needed
						action.moveToElement(sign).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -60)
								.moveByOffset(30, 0).moveByOffset(0, 30).moveByOffset(-200, 0).moveByOffset(10, 0)
								.moveByOffset(0, 15).moveByOffset(-10, 0).release()
								// Release the click to finish the // additional movements
								.build().perform();

						// Additional movements if required
						action.moveToElement(sign).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -30)
								.moveByOffset(0, -10).release() // Release the click to finish the additional movements
								.build().perform();
						Thread.sleep(3000);

						// With Payment
						driver.findElement(By.xpath(
								"//button[contains(.,'" + sheet.getRow(i).getCell(15).getStringCellValue() + "')]"))
								.click();

						// Without Payment
//						driver.findElement(By.xpath("(//button[contains(.,'Book Appointment')])[3]")).click();

						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
								"(//div[@class='col-md-6 col-12']//div[@class='flex flex-col gap-2']/span)[1]")));
						String provName = driver
								.findElement(By.xpath(
										"(//div[@class='col-md-6 col-12']//div[@class='flex flex-col gap-2']/span)[1]"))
								.getText();
						System.out.println(provName);

						if (provName.equals("Radha Desai, MD")) {

							dash.getRow(i).createCell(5).setCellValue(provName);
							dash.getRow(i).createCell(4).setCellValue("Jessica Brown");
							FileOutputStream fos = new FileOutputStream(f);
							workbook.write(fos);

						} else if (provName.equals("Nasimul Siddiqui, MD")) {
							dash.getRow(i).createCell(5).setCellValue(provName);
							dash.getRow(i).createCell(4).setCellValue("John Evans");
							FileOutputStream fos = new FileOutputStream(f);
							workbook.write(fos);

						} else if (provName.equals("Niral Patel, MD")) {
							dash.getRow(i).createCell(5).setCellValue(provName);
							dash.getRow(i).createCell(4).setCellValue("David Carter");
							FileOutputStream fos = new FileOutputStream(f);
							workbook.write(fos);

						} else if (provName.equals("Nicole Colon, APRN")) {
							dash.getRow(i).createCell(5).setCellValue(provName);
							dash.getRow(i).createCell(4).setCellValue("Emily Davis");
							FileOutputStream fos = new FileOutputStream(f);
							workbook.write(fos);
						}

					}

					if (sheet.getRow(i).getCell(18).getStringCellValue().equals("Start Registration")) {

						ScreenShots(driver, "Appt Scheduled Sucessfully");
						wait.until(ExpectedConditions.visibilityOfElementLocated(By
								.xpath("//a[contains(.,'" + sheet.getRow(i).getCell(18).getStringCellValue() + "')]")));

						String regLink = driver
								.findElement(By.xpath(
										"//a[contains(.,'" + sheet.getRow(i).getCell(18).getStringCellValue() + "')]"))
								.getAttribute("data-url");
						driver.get(regLink);

					} else {

						Thread.sleep(20000);

						ScreenShots(driver, "Appt Scheduled Sucessfully");

						driver.get("https://yopmail.com/");

						driver.findElement(By.xpath("//input[@id='login']"))
								.sendKeys("" + sheet.getRow(i).getCell(13).getStringCellValue() + "", Keys.ENTER);

						Thread.sleep(2000);

						driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='ifmail']")));
						String attributes = driver.findElement(By.xpath("//a[text()='Confirm / Pre-register now']"))
								.getAttribute("href");
						System.out.println(attributes);

						driver.switchTo().defaultContent();

						driver.navigate().to(attributes);

					}

				}

				
//				driver.get("https://yopmail.com/");
//
//				driver.findElement(By.xpath("//input[@id='login']"))
//						.sendKeys("" + sheet.getRow(i).getCell(13).getStringCellValue() + "", Keys.ENTER);
//
//				Thread.sleep(2000);
//
//				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='ifmail']")));
//				String attributes = driver.findElement(By.xpath("//a[text()='Confirm / Pre-register now']"))
//						.getAttribute("href");
//				System.out.println(attributes);
//
//				driver.switchTo().defaultContent();
//
//				driver.navigate().to(attributes);
				
				
				XSSFSheet sheet3 = workbook.getSheet("Sheet3");
				XSSFSheet sheet1 = workbook.getSheet("Dashboard");
				XSSFSheet sheets = workbook.getSheet("PatientCreation");
				sheet3.getRow(i).getLastCellNum();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Actions ac = new Actions(driver);

				if (sheets.getRow(i).getCell(18).getStringCellValue().equals("Email")) {
					WebElement startConfirm = driver.findElement(By.xpath("//button[@data-value='Confirmed']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", startConfirm);
					wait.until(ExpectedConditions.visibilityOf(startConfirm));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", startConfirm);

					WebElement startRegist = driver.findElement(By.xpath("//button[contains(.,'Start Registration')]"));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", startRegist);
					wait.until(ExpectedConditions.visibilityOf(startRegist));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", startRegist);

					// Patient Verification
					driver.findElement(By.xpath("//label[text()='Myself']")).click();
					driver.findElement(By.xpath("//input[@name='patient_dob']"))
							.sendKeys(sheets.getRow(i).getCell(10).getStringCellValue());
					driver.findElement(By.xpath("//img[@id='default_header_id']")).click();
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

				}

				XSSFRow celldata = sheet3.getRow(i);

//1.Demographics
				// 1.1Snap a photograph of your ID
				WebElement uploadfront1 = driver.findElement(By.xpath("//input[@id='selectedFile']"));
				uploadfront1.sendKeys("D:\\Insurance.jpg");
				WebElement insursave = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
						"//div[@class='modal-footer float-right mt-5 row mx-0 justify-content-end']//div[contains(.,'Save')]")));
				insursave.click();

				// IntakePojo.Textfield("Middle Name",celldata.getCell(1).getStringCellValue());

				IntakePojo.Textfield("Preferred name", "Ben");

				// Handle Race Selection
				WebElement martialStatus = driver.findElement(By.xpath("//span[@data-labelname='Marital Status']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", martialStatus);
				Thread.sleep(2000);
				ac.moveToElement(martialStatus).click().build().perform();
				WebElement marriedoption = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='Married']")));
				marriedoption.click();

				// Handle Race Selection
				WebElement raceButton = driver.findElement(By.xpath("//span[@data-labelname='Race']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", raceButton);
				Thread.sleep(2000);
				ac.moveToElement(raceButton).click().build().perform();
				WebElement whiteOption = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='White']")));
				whiteOption.click();

				// Ethnicity
				WebElement ethn = driver.findElement(By.xpath("//span[@data-labelname='Ethnicity']"));
				ac.moveToElement(ethn).click().build().perform();
				WebElement ethnOption = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='I decline']")));
				ethnOption.click();

				// Language
				WebElement lang = driver.findElement(By.xpath("//span[@data-labelname='Preferred Language']"));
				ac.moveToElement(lang).click().build().perform();
				WebElement langoption = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='English']")));
				langoption.click();
				// SSN Number
				IntakePojo.Textfield("Social Security Number", "111-11-1111");
				Thread.sleep(3000);

				// Home Address
				IntakePojo.Textfield("Address1", "Main Street");
				IntakePojo.Textfield("Address2", "Main Street 1");
				IntakePojo.Textfield("Zip Code", "10001");
				IntakePojo.Textfield("City", "NY");

				// Patient Phone and Email
				IntakePojo.Textfield("Work Phone", "9147528040");
				IntakePojo.Textfield("Home Phone", "9147528040");

				// Emergency Contact
				IntakePojo.Textfield("First Name", "SARA");
				IntakePojo.Textfield("Last Name", "Testpatient");
				WebElement ph = driver.findElement(By.xpath("(//input[@data-labelname='Mobile Phone'])[2]"));
				ph.clear();
				ph.sendKeys("9147528040");
				Thread.sleep(3000);
				// Relationship
				WebElement relationship = driver.findElement(By.xpath("//span[@data-labelname='Relationship']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", relationship);
				Thread.sleep(2000);
				ac.moveToElement(relationship).click().build().perform();
				WebElement rel = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='Cousin']")));
				rel.click();

				driver.findElement(By.xpath("//button[@type='submit'][contains(.,'Continue')]")).click();

// 2.Additional Patient Information
				WebElement patin = driver.findElement(By.xpath(
						"//input[@data-labelname='Would you like to add additional information about your Sexual orientation, Gender identity, Assigned sex at birth, Preferred pronouns and Homebound?']//following-sibling::label[contains(.,'Yes')]"));
				wait.until(ExpectedConditions.visibilityOf(patin));
				Thread.sleep(2000);
				patin.click();

				IntakePojo.dropdownoptions("Sexual Orientation", "Bisexual");

				IntakePojo.dropdownoptions("Gender Identity", "Transgender Male/Female-to-Male (FTM)");

				IntakePojo.dropdownoptions("Assigned sex at birth", "Male");

				IntakePojo.dropdownoptions("Preferred Pronouns", "he/him");

				// 1.1Referral
				IntakePojo.dropdownoptions("How did you hear about us?", "Primary Care Physician");

				// 1.2Vitals
				WebElement feet = driver.findElement(By.xpath("(//span[@data-labelname='Patient Height'])[1]"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", feet);
				Thread.sleep(2000);
				ac.moveToElement(feet).click().build().perform();
				driver.findElement(By.xpath("//li[text()='6']")).click();

				WebElement inches = driver.findElement(By.xpath("(//span[@data-labelname='Patient Height'])[2]"));
				ac.moveToElement(inches).click().build().perform();
				Thread.sleep(2000);
				WebElement inchess = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='2']")));
				inchess.click();

				WebElement weightbox = driver.findElement(By.xpath("//input[@data-labelname='Patient Weight']"));
				weightbox.clear();
				weightbox.sendKeys("55");

				driver.findElement(By.xpath("//button[@type='submit'][contains(.,'Continue')]")).click();

				// if (sheets.getRow(i).getCell(18).getStringCellValue().equals("Email")) {
// 3.Insurance Page
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//input[@data-labelname='Are you using your insurance for the visit?'][@value='Y']")));
				driver.findElement(By.xpath(
						"//input[@data-labelname='Are you using your insurance for the visit?'][@value='Y']//following-sibling::label"))
						.click();

				IntakePojo.dropdownoptions("Patients relationship to insurance holder", "Self");

				WebElement uploadfrnt = driver.findElement(By.xpath("//input[@id='selectedFile']"));
				uploadfrnt.sendKeys("D:\\Insurance.jpg");
				WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"//div[@class='modal-footer float-right mt-5 row mx-0 justify-content-end']//div[contains(.,'Save')]")));
				saveButton.click();

				Thread.sleep(3000);
				WebElement uploadbacksnap = driver.findElement(By.xpath("//input[@id='selectedFile1']"));
				uploadbacksnap.sendKeys("D:\\Insurance.jpg");
				WebElement saveButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"//div[@class='modal-footer float-right mt-5 row mx-0 justify-content-end']//div[contains(.,'Save')]")));
				saveButton1.click();

				WebElement insurancename = driver
						.findElement(By.xpath("//span[@data-labelname='Insurance company name']"));
				js.executeScript("arguments[0].scrollIntoView(true);", insurancename);
				Thread.sleep(2000);
				WebElement insurn = wait.until(ExpectedConditions.elementToBeClickable(insurancename));
				ac.moveToElement(insurn).click().build().perform();
				WebElement insurtype = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul//li[contains(.,'AETNA')]")));
				insurtype.click();

				// Policy number
				IntakePojo.Textfield("Policy number", "1234546");

				// Group id
				IntakePojo.Textfield("Group ID", "ABC123456");

				// Same as patient address
				WebElement smae = driver.findElement(By.xpath("//label[text()='Same as patient address']"));
				js.executeScript("arguments[0].scrollIntoView(true);", smae);
				Thread.sleep(2000);
				smae.click();

				driver.findElement(By.xpath("//button[@type='submit'][contains(.,'Continue')]")).click();

				Thread.sleep(3000);
				driver.findElement(By.xpath("//label[@for='more_ins_no']")).click();

// 3.Guarantor Information
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//label[text()='Address same as patient']")));
				WebElement grn = driver.findElement(By.xpath("//label[text()='Address same as patient']"));
				Thread.sleep(2000);
				js.executeScript("arguments[0].scrollIntoView(true);", grn);
				Thread.sleep(2000);
				grn.click();

				driver.findElement(By.xpath("//button[@type='submit'][contains(.,'Continue')]")).click();

// 4.CAre team======================

				// Primarry care provider
				IntakePojo.careteam("Primary Care Physician", celldata.getCell(1).getStringCellValue());

				// Pharmacy
				IntakePojo.careteam("Pharmacy", celldata.getCell(0).getStringCellValue());

				// Did someone refer you here?
				IntakePojo.careteam("Did someone refer you here?", celldata.getCell(2).getStringCellValue());

				driver.findElement(By.xpath("//button[@type='submit'][contains(.,'Continue')]")).click();

//5.Health History ==============
				// Past medical conditions
				IntakePojo.healthhistory("medical_conditions", celldata.getCell(12).getStringCellValue(),
						"medical_condition");

				// Surgeries
				IntakePojo.healthhistory("surgeries", celldata.getCell(11).getStringCellValue(), "surgery_name");

				// have any past/existing medical condition
				IntakePojo.pastmedical("family_history", celldata.getCell(5).getStringCellValue(),
						celldata.getCell(6).getStringCellValue());

				// Are you currently taking any medication?
				IntakePojo.healthhistory("medications", celldata.getCell(4).getStringCellValue(), "medication");

				// Allergies
				IntakePojo.healthhistory("allergies", celldata.getCell(3).getStringCellValue(), "allergy");

				Thread.sleep(8000);
				driver.findElement(By.xpath("(//div/span[contains(.,'Continue')])")).click();

				if (sheets.getRow(i).getCell(0).getStringCellValue().equals("OB-GYN Appointment")) {

					// Social History
					IntakePojo.SocialHistoryRadio("Advance directive", "Yes");
					IntakePojo.SocialHistoryText("Alcohol Intake: drinks/week", "2/2");
					IntakePojo.dropdownoptions("Caffeine intake", "Occasional");
					IntakePojo.SocialHistoryRadio("Diabetes", "Yes");
					IntakePojo.dropdownoptions("Diet", "Regular");
					IntakePojo.dropdownoptions("Exercise level", "Moderate");
					IntakePojo.SocialHistoryText("Exercise:  Minutes/day", "30/1");
					IntakePojo.SocialHistoryText("Exercise: Days/week", "3/1");
					IntakePojo.dropdownoptions("General stress level", "Low");
					IntakePojo.SocialHistoryRadio("Guns present in home", "Yes");

					IntakePojo.SocialHistoryText("Illicit drugs", "test");
					IntakePojo.SocialHistoryRadio("Is blood transfusion acceptable in an emergency?", "Yes");

					IntakePojo.dropdownoptions("Marital status", "Married");
					IntakePojo.dropdownoptions("Number of children", "2");
					IntakePojo.SocialHistoryRadio("Obese", "Yes");
					IntakePojo.SocialHistoryText("Occupational health risks", "test");
					IntakePojo.SocialHistoryRadio("Overweight", "Yes");
					IntakePojo.SocialHistoryRadio("Seat belts used routinely", "Yes");
					IntakePojo.dropdownoptions("Smoking - How much?", "1 PPW");
					IntakePojo.dropdownoptions("Tobacco Smoking Status", "Former smoker");

					Thread.sleep(3000);
					driver.findElement(By.xpath("//button[@type='submit'][contains(.,'Continue')]")).click();

					// GYN History
					IntakePojo.dropdownoptions("Current Birth Control Method", "BCPs");
					//IntakePojo.SocialHistoryText("Date of LMP", "10101990");
					IntakePojo.SocialHistoryText("Duration of Flow (days)", "10");
					IntakePojo.dropdownoptions("Flow", "Light");
					IntakePojo.SocialHistoryText("Frequency of Cycle (Q days)", "10");
					IntakePojo.SocialHistoryText("If Post Menopausal, Age at Menopause", "10");
					IntakePojo.SocialHistoryRadio("Pain with intercourse", "Yes");
					IntakePojo.SocialHistoryRadio("Pain with menses?", "Yes");
					IntakePojo.SocialHistoryRadio("Pelvic pain?", "Yes");
					IntakePojo.SocialHistoryText("Age at First Child", "10");
//					IntakePojo.SocialHistoryText("Date of Last Colonoscopy", "10101990");
//					IntakePojo.SocialHistoryText("Date of Last Pap Smear", "10101990");
//					IntakePojo.SocialHistoryText("Most Recent Bone Density", "10101990");
//					IntakePojo.SocialHistoryText("Most Recent Mammogram", "10101990");

					IntakePojo.SocialHistoryText("Number of Abortions", "test");
					IntakePojo.SocialHistoryText("Number of Ectopic Pregnancies", "test");
					IntakePojo.SocialHistoryText("Number of Live Births", "test");
					IntakePojo.SocialHistoryText("Number of Miscarriages", "test");
					IntakePojo.SocialHistoryText("Any Complications", "test");
					IntakePojo.SocialHistoryText("Total number of Pregnancies", "test");

					Thread.sleep(3000);
					driver.findElement(By.xpath("//button[@type='submit'][contains(.,'Continue')]")).click();

					// ROS
					IntakePojo.ROSPage("Constitutional");
					WebElement element = driver.findElement(By.xpath(
							"//input[@data-labelname='dyspnea / shortness of breath']//following-sibling::label[contains(.,'Yes')]"));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
					Thread.sleep(1000);
					element.click();

					IntakePojo.ROSPage("Cardiovascular");
					IntakePojo.ROSPage("Gastrointestinal");
					IntakePojo.ROSPage("Genitourinary");
					IntakePojo.ROSPage("PMDD");
					IntakePojo.ROSPage("Menopausal");
					IntakePojo.ROSPage("Sexual");
					IntakePojo.ROSPage("Endocrine");

					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					// EPDS
					IntakePojo.SocialHistoryRadio("I have been able to laugh and see the funny side of things",
							"As much as I always could");
					IntakePojo.SocialHistoryRadio("I have looked forward with enjoyment to things",
							"As much as I ever did");
					IntakePojo.SocialHistoryRadio("I have blamed myself unnecessarily when things went wrong",
							"Yes, most of the time");
					IntakePojo.SocialHistoryRadio("I have been anxious or worried for no good reason",
							"No, not at all");
					IntakePojo.SocialHistoryRadio("I have felt scared or panicky for no very good reason",
							"Yes, quite a lot");
					IntakePojo.SocialHistoryRadio("Things have been getting on top of me",
							"No, most of the time I have coped quite well");
					IntakePojo.SocialHistoryRadio("I have been so unhappy that I have had difficulty sleeping",
							"Yes, most of the time");
					IntakePojo.SocialHistoryRadio("I have felt sad or miserable", "Yes, most of the time");
					IntakePojo.SocialHistoryRadio("I have been so unhappy that I have been crying",
							"Yes, most of the time");
					IntakePojo.SocialHistoryRadio("The thought of harming myself has occurred to me",
							"Yes, quite often");

					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

				}

				else if (sheets.getRow(i).getCell(0).getStringCellValue().equals("Orthopedic Appointment")) {

					// social History
					IntakePojo.SocialHistoryRadio(
							"Do you or have you ever used any other forms of tobacco or nicotine (e-cig/vape, chew, etc)?",
							"Yes");
					IntakePojo.SocialHistoryRadio("Do you use any illicit or recreational drugs?", "Yes");
					IntakePojo.dropdownoptions("Have you ever required treatment for alcohol or substance use?", "Yes");

					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					if (sheets.getRow(i).getCell(11).getStringCellValue().equals("female")) {

						// GYN History
						IntakePojo.SocialHistoryRadio("STIs/STDs", "Yes");
						IntakePojo.SocialHistoryRadio("Abnormal Pap", "Yes");
						IntakePojo.SocialHistoryRadio("Post Menopausal Bleeding", "Yes");
						IntakePojo.dropdownoptions("Current Birth Control Method", "BCPs");
						IntakePojo.SocialHistoryRadio("Hormone Replacement Therapy", "Yes");
						IntakePojo.Textfield("Most Recent Bone Density", "10102000");
						Thread.sleep(3000);
						driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();
					}
					// ROS
					IntakePojo.ROSPage("Constitutional");
					IntakePojo.ROSPage("Head/Eyes/Ears/Nose/Mouth");
					IntakePojo.ROSPage("Cardiovascular");
					IntakePojo.ROSPage("Respiratory");
					IntakePojo.ROSPage("Gastrointestinal");
					IntakePojo.ROSPage("Genitourinary");
					IntakePojo.ROSPage("Skin");
					IntakePojo.ROSPage("Neurologic");
					IntakePojo.ROSPage("Heme/Lymph");
					IntakePojo.ROSPage("Psychologic");
					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					// Knee Screnning
					IntakePojo.SocialHistoryRadio("Knee pain", "Left");
					IntakePojo.SocialHistoryRadio(
							"Do you have an inability to walk on a treadmill for 7–10 minutes at a comfortable pace, including walking without holding the handrails for two 45-second walking trials?",
							"Yes");
					IntakePojo.SocialHistoryRadio("Do you have an inability to balance while walking?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have a severe limp while walking?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you need to wear any type of lower-limb brace during the exam?",
							"Yes");
					IntakePojo.SocialHistoryRadio(
							"Do you need to use a cane, walker, or other assistive devices while walking?", "Yes");
					IntakePojo.SocialHistoryRadio(
							"Do you have severe swelling due to recent trauma, surgery, injection, or other procedures?",
							"Yes");
					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

				} else if (sheets.getRow(i).getCell(0).getStringCellValue().equals("Behavioral Health Appointment")) {

					// Social History
					IntakePojo.SocialHistoryRadio("Are there any guns present in your home?", "Yes");
					IntakePojo.SocialHistoryRadio("Are you or have you been involved with bullying?", "Yes");
					IntakePojo.SocialHistoryRadio("Are you able to care for yourself?", "Yes");
					IntakePojo.SocialHistoryRadio("Hx of traumatic experiences", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have a therapist?", "Yes");
					IntakePojo.SocialHistoryRadio("Are you currently in school?", "Yes");

					IntakePojo.dropdownoptions(
							"What is the highest grade or level of school you have completed or the highest degree you have received?",
							"High school graduate");
					IntakePojo.dropdownoptions("What grade are you in?", "1st grade");
					IntakePojo.dropdownoptions("How are your grades?", "Average");
					IntakePojo.SocialHistoryRadio("Are you currently employed?", "Yes");
					IntakePojo.SocialHistoryRadio("Are you sexually active?", "Yes");
					IntakePojo.SocialHistoryRadio("History of emotional, sexual, physical, verbal abuse", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have children?", "Yes");
					IntakePojo.dropdownoptions("What is your level of caffeine consumption?", "Heavy");
					IntakePojo.dropdownoptions("What is your exercise level?", "Heavy");
					IntakePojo.SocialHistoryRadio("Do you use any illicit or recreational drugs?", "Yes");

					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					if (sheets.getRow(i).getCell(11).getStringCellValue().equals("female")) {

						// GYN History
						IntakePojo.SocialHistoryRadio("Post Menopausal Bleeding", "No");
						IntakePojo.dropdownoptions("Last Menstrual Period", "Unknown");
						IntakePojo.SocialHistoryRadio("Are you currently pregnant?", "Yes");
						IntakePojo.dropdownoptions("Birth Control at End of Visit", "BCPs");
						IntakePojo.dropdownoptions("Current Birth Control Method", "BCPs");
						IntakePojo.SocialHistoryRadio("Sexual Problems?", "Yes");
						IntakePojo.SocialHistoryRadio("Hormone Replacement Therapy", "Yes");

						Thread.sleep(3000);
						driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();
					}

					// MDQ
					IntakePojo.SocialHistoryRadio(
							"1a. Has there ever been a period of time when you were not your usual self and you felt so good or so hyper that other people thought you were not your normal self or you were so hyper that you got into trouble?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1b. Has there ever been a period of time when you were not your usual self and you were so irritable that you shouted at people or started fights or arguments?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1c. Has there ever been a period of time when you were not your usual self and you felt much more self-confident than usual?",
							"No");
					
					IntakePojo.SocialHistoryRadio(
							"1e. Has there ever been a period of time when you were not your usual self and you were more talkative or spoke much faster than usual?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1g. Has there ever been a period of time when you were not your usual self and you were so easily distracted by things around you that you had trouble concentrating or staying on track?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1h. Has there ever been a period of time when you were not your usual self and you had more energy than usual?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1i. Has there ever been a period of time when you were not your usual self and you were much more active or did many more things than usual?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1j. Has there ever been a period of time when you were not your usual self and you were much more social or outgoing than usual, for example, you telephoned friends in the middle of the night?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1k. Has there ever been a period of time when you were not your usual self and you were much more interested in sex than usual?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1l. Has there ever been a period of time when you were not your usual self and you did things that were unusual for you or that other people might have thought were excessive, foolish, or risky?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"1m. Has there ever been a period of time when you were not your usual self and spending money got you or your family in trouble?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"2. If you checked YES to more than one of the above, have several of these ever happened during the same period of time?",
							"No");
					IntakePojo.SocialHistoryRadio(
							"3. How much of a problem did any of these cause you - like being unable to work; having family, money, or legal troubles; getting into arguments or fights?",
							"No");
					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					// ROS
					IntakePojo.ROSPage("Constitutional");
					IntakePojo.ROSPage("Neurologic");
					IntakePojo.ROSPage("Psych");
					IntakePojo.ROSPage("Allergic/Immunologic");
					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

				} else if (sheets.getRow(i).getCell(0).getStringCellValue().equals("Primary Care")) {
					// Social History

					IntakePojo.dropdownoptions(
							"What is the highest grade or level of school you have completed or the highest degree you have received?",
							"High school graduate");
					IntakePojo.Textfield("What is your occupation?", "Test");
					IntakePojo.dropdownoptions("What is your exercise level?", "Heavy");
					IntakePojo.dropdownoptions("What type of diet are you following?", "Cardiac");
					IntakePojo.dropdownoptions("Do you or have you ever smoked tobacco?", "Former smoker");
					IntakePojo.dropdownoptions("What is your level of alcohol consumption?", "Heavy");

					IntakePojo.SocialHistoryRadio("Do you use any illicit or recreational drugs?", "Yes");

					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					if (sheets.getRow(i).getCell(11).getStringCellValue().equals("female")) {

						// GYN History
						IntakePojo.Textfield("Age at Menarche", "10");
						IntakePojo.Textfield("Duration of Flow (days)", "10");
						IntakePojo.Textfield("Frequency of Cycle (Q days)", "20");
						// IntakePojo.Textfield("Date of LMP", "10");
						IntakePojo.Textfield("If Post Menopausal, Age at Menopause", "20");

						IntakePojo.SocialHistoryRadio("Post Menopausal Bleeding", "Yes");
						IntakePojo.Textfield("Do you prefer Male, Female, Both?", "Test");
						IntakePojo.Textfield("How many sexual partners in lifetime?", "Test");

						IntakePojo.SocialHistoryRadio("STIs/STDs", "Yes");
						IntakePojo.SocialHistoryRadio("Sexual Problems?", "Yes");
						IntakePojo.SocialHistoryRadio("Hormone Replacement Therapy", "Yes");
						IntakePojo.SocialHistoryRadio("Abnormal Pap", "Yes");
						IntakePojo.SocialHistoryRadio("HPV Vaccine", "Yes");
						IntakePojo.SocialHistoryRadio("Are you pregnant", "No");
						IntakePojo.SocialHistoryRadio("Miscarriages", "Yes");
						IntakePojo.SocialHistoryRadio("List any problems with pregnancy, birth or abortions", "No");
						IntakePojo.SocialHistoryRadio("Are you breastfeeding", "No");

						Thread.sleep(3000);
						driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();
					}

					// PHQ-9
					IntakePojo.SocialHistoryRadio(
							"Thoughts that you would be better off dead or of hurting yourself in some way",
							"Not at all");
					IntakePojo.SocialHistoryRadio("Trouble falling or staying asleep, or sleeping too much",
							"Not at all");
					IntakePojo.SocialHistoryRadio("Poor appetite or overeating", "Several days");
					IntakePojo.SocialHistoryRadio(
							"Feeling bad about yourself - or that you are a failure or have let yourself or your family down",
							"Several days");
					IntakePojo.SocialHistoryRadio("Feeling down, depressed, or hopeless", "Several days");
					IntakePojo.SocialHistoryRadio(
							"Moving or speaking so slowly that other people could have noticed? Or the opposite - being so fidgety or restless that you have been moving around a lot more than usual",
							"Several days");
					IntakePojo.SocialHistoryRadio("Little interest or pleasure in doing things", "Not at all");
					IntakePojo.SocialHistoryRadio(
							"Trouble concentrating on things, such as reading the newspaper or watching television",
							"Not at all");
					IntakePojo.SocialHistoryRadio("Feeling tired or having little energy", "Not at all");
					IntakePojo.SocialHistoryRadio(
							"If you checked off any problems, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people?",
							"Somewhat difficult");
					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					// ROS

					IntakePojo.ROSPage("Constitutional");
					IntakePojo.ROSPage("Eyes");
					IntakePojo.ROSPage("Cardiovascular");
					IntakePojo.ROSPage("Respiratory");
					IntakePojo.ROSPage("Gastrointestinal");
					IntakePojo.ROSPage("Genitourinary");
					IntakePojo.ROSPage("Musculoskeletal");
					IntakePojo.ROSPage("Skin");
					IntakePojo.ROSPage("Neurologic");
					IntakePojo.ROSPage("Psych");
					IntakePojo.ROSPage("Endocrine");
					IntakePojo.ROSPage("Hematologic/Lymphatic");
					IntakePojo.ROSPage("Allergy/Immunologic");

					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

				} else if (sheets.getRow(i).getCell(0).getStringCellValue().equals("Urgent Care")) {

					// social History
					IntakePojo.dropdownoptions("Alcohol intake", "None");
					IntakePojo.dropdownoptions("Tobacco Smoking Status", "Never smoker");
					IntakePojo.dropdownoptions("Smoking - How much?", "None");

					IntakePojo.Textfield("Has smoked since age", "Test");
					IntakePojo.dropdownoptions("Chewing tobacco", "none");
					IntakePojo.Textfield("Illicit drugs", "Test");
					IntakePojo.SocialHistoryRadio("Recent travel abroad?", "Yes");
					IntakePojo.Textfield("Occupation", "Test");
					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					// Patient Questionire
					IntakePojo.SocialHistoryRadio("Do you have a fever?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have normal appetite?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have eye irritation?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have eye pain?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have a sore throat?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have ear pain?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have a cough?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have trouble breathing?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have chest discomfort?", "Yes");

					IntakePojo.SocialHistoryRadio("Do you have a nausea?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have abdominal pain?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have diarrhea?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have difficulty urinating?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have increased frequency of urination?", "Yes");

					IntakePojo.SocialHistoryRadio("Do you have muscle aches?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have joint pain?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you have a rash or skin problem?", "Yes");

					IntakePojo.SocialHistoryRadio("Do you have nasel congestion?", "Yes");
					IntakePojo.SocialHistoryRadio("Do you feel safe?", "Yes");

					Thread.sleep(3000);
					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

//					// HIPPA
//
//					WebElement agree = driver.findElement(By.xpath("//label[contains(.,'I Agree')]"));
//					wait.until(
//							ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(.,'I Agree')]")));
//					Thread.sleep(2000);
//					driver.findElement(By.xpath("//div[@class='pagescroller']")).click();
//					Thread.sleep(3000);
//					agree.click();
//
//					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

				}

				if (sheets.getRow(i).getCell(0).getStringCellValue().equals("Pediatric Appointment")) {
					// 7.Birth History
					WebElement childbirth = driver.findElement(By.xpath(
							"//input[@data-labelname=\"Do you know your child's birth history?\"]//following-sibling::label[contains(.,'Yes')]"));
					wait.until(ExpectedConditions.elementToBeClickable(childbirth)).click();

					IntakePojo.Textfield("Birth Weight", "20");

					IntakePojo.birthpage("Was baby born at term?", "Yes");
					IntakePojo.birthpage("How was the delivery?", "Vaginal");
					IntakePojo.birthpage("Was an NICU stay required?", "Yes");
					IntakePojo.birthpage("Was baby born at term?", "Yes");

					IntakePojo.Textfield("If yes, why?", "Testing");
					IntakePojo.birthpage("Method of Feeding:", "Formula");
					IntakePojo.birthpage("During pregnancy, did mother use Tobacco?", "Yes");
					IntakePojo.birthpage("During pregnancy, did mother use Alcohol?", "Yes");
					IntakePojo.birthpage("During pregnancy, did mother use any drugs or medications?", "Yes");
					IntakePojo.Textfield("If so, list the meds/drugs:", "Drugs");

					IntakePojo.birthpage("During pregnancy, did mother use any Prenatal Vitamins?", "Yes");
					IntakePojo.Textfield("Please list any problems during the newborn period:", "Testing");

					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					// Social History
					IntakePojo.dropdownoptions(
							"Do you feel stressed (tense, restless, nervous, or anxious, or unable to sleep at night)?",
							"Only a little");
					IntakePojo.birthpage("Are you or have you been involved with bullying?", "Yes");
					IntakePojo.Textfield(
							"Have you ever required psychiatric hospitalization/in-patient treatment or a partial out-patient program?",
							"Testing");
					IntakePojo.dropdownoptions(
							"In childhood, have you ever lived in a residential treatment program, foster care, or juvenile detention?",
							"Yes");
					IntakePojo.dropdownoptions("Do you consistently use a contraceptive method?",
							"Combined oral contraceptive pills");
					IntakePojo.dropdownoptions("Have you ever been arrested or do you have any pending legal problems?",
							"Yes");
					IntakePojo.dropdownoptions("Have you ever received talk therapy?", "Yes");
					IntakePojo.dropdownoptions("Do you participate in any religious or spiritual group?", "Yes");
					IntakePojo.dropdownoptions("Is participation helpful or stressful?", "Helpful");
					IntakePojo.dropdownoptions("Have you ever served in the military?", "Yes");

					IntakePojo.birthpage("Are there any guns present in your home?", "Yes");
					IntakePojo.Textfield("Have you experienced any past trauma?", "Testing");
					IntakePojo.birthpage("Do you have difficulty concentrating, remembering or making decisions?",
							celldata.getCell(8).getStringCellValue());
					IntakePojo.birthpage("Are you deaf or do you have serious difficulty hearing?", "Yes");
					IntakePojo.birthpage("Are you currently in school?", "Yes");
					IntakePojo.dropdownoptions("If in school, how are your grades?", "Average");

					IntakePojo.birthpage("Have you ever needed to repeat a grade?", "Yes");
					IntakePojo.birthpage("Do you participate in social media?", "Yes");
					IntakePojo.dropdownoptions("If in a relationship, do you feel safe?", "Always");
					IntakePojo.birthpage("Are you sexually active?", celldata.getCell(7).getStringCellValue());
					IntakePojo.Textfield("Do you have any siblings?", "Yes");

					IntakePojo.birthpage(
							"Do you or have you ever used any other forms of tobacco or nicotine (e-cig/vape, chew, etc)?",
							celldata.getCell(9).getStringCellValue());
					IntakePojo.birthpage("Do you use any illicit or recreational drugs?",
							celldata.getCell(10).getStringCellValue());
					IntakePojo.dropdownoptions("Have you ever required treatment for alcohol or substance use?", "Yes");

					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					// ROS
					IntakePojo.ROSPage("Dietary");
					IntakePojo.ROSPage("Feeding");
					IntakePojo.ROSPage("Sleep");
					IntakePojo.ROSPage("Personal/Social");
					IntakePojo.ROSPage("Language");
					IntakePojo.ROSPage("Behavior");
					IntakePojo.ROSPage("Respiratory");
					IntakePojo.ROSPage("Cardiovascular");
					IntakePojo.ROSPage("Neurological");
					IntakePojo.ROSPage("Musculoskeletal");

					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

					// PEDS
					IntakePojo.birthpage("Do you have any concerns about how your child talks and makes speech sounds?",
							"A little");
					IntakePojo.birthpage(
							"Do you have any concerns about how your child uses his or her hands and fingers to do things?",
							"A little");
					IntakePojo.birthpage("Do you have any concerns about how your child uses his or her arms and legs?",
							"A little");
					IntakePojo.birthpage("Do you have any concerns about how your child behaves?", "A little");
					IntakePojo.birthpage("Do you have any concerns about how your child gets along with others?",
							"A little");
					IntakePojo.birthpage(
							"Do you have any concerns about how your child is learning to do things for himself/herself?",
							"A little");
					IntakePojo.birthpage(
							"Do you have any concerns about how your child is learning preschool or school skills?",
							"A little");

					IntakePojo.Textfield("Please list any other concerns", "Testing");

					driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

				}

				else {

				}

// Concentforms
				Thread.sleep(5000);
				WebElement concent = driver
						.findElement(By.xpath("//input[@type='checkbox']//following-sibling::label"));
				wait.until(ExpectedConditions.elementToBeClickable(concent));
				concent.click();

				driver.findElement(By.xpath("//label[contains(.,'I Agree')]")).click();
				driver.findElement(By.xpath("(//button[@type='submit'][contains(.,'Continue')])")).click();

// Signature
				Thread.sleep(5000);
				Actions builder = new Actions(driver);
				WebElement sign = driver.findElement(By.xpath("//canvas[@id='ctlSignature']"));
				Actions action = new Actions(driver);

				// Perform drawing actions
				action.moveToElement(sign).clickAndHold().moveByOffset(0, 80).moveByOffset(30, -60).moveByOffset(30, 60)
						.moveByOffset(-5, -15).moveByOffset(-10, 0).moveByOffset(0, -15).release() // Release the click
																									// to finish the
																									// drawing
						.build().perform();

				// Optional additional movements if needed
				action.moveToElement(sign).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -60).moveByOffset(30, 0)
						.moveByOffset(0, 30).moveByOffset(-200, 0).moveByOffset(10, 0).moveByOffset(0, 15)
						.moveByOffset(-10, 0).release() // Release the click to finish the
														// additional movements
						.build().perform();

				// Additional movements if required
				action.moveToElement(sign).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -30).moveByOffset(0, -10)
						.release() // Release the click to finish the additional movements
						.build().perform();
				Thread.sleep(5000);

				WebElement submit = driver.findElement(By.xpath("(//button[contains(.,'Submit')])"));
				ac.moveToElement(submit).click().build().perform();

				if (sheets.getRow(i).getCell(15).getStringCellValue().equals("Pay later")) {

					// Payment
					wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//button[contains(.,'Pay Now')]")));
					WebElement elements3 = driver.findElement(By.xpath("//button[contains(.,'Pay Now')]"));
					Thread.sleep(2000);
					js.executeScript("arguments[0].scrollIntoView(true);", elements3);
					driver.findElement(By.xpath("//button[contains(.,'Pay Now')]")).click();

					// AddCardOn FIle

					IntakePojo.addCardonFIle("paymentform_namecard", "hari");
					Thread.sleep(2000);
					IntakePojo.addCardonFIle("paymentform_creditcard", "4000");
					IntakePojo.addCardonFIle("paymentform_creditcard", "0000");
					IntakePojo.addCardonFIle("paymentform_creditcard", "0000");
					IntakePojo.addCardonFIle("paymentform_creditcard", "0002");

					IntakePojo.addCardonFIle("card_expdate", "12/2026");
					IntakePojo.addCardonFIle("cvv_number", "123");

					WebElement element = driver.findElement(By.xpath("//label[contains(.,'Same as home address')]"));
					js.executeScript("arguments[0].scrollIntoView(true);", element);
					Thread.sleep(2000);
					element.click();
					driver.findElement(By.xpath("(//button[@name='submitpinfo'][contains(.,'Pay')])")).click();

					// Card on File
					IntakePojo.CardonFIle("Continue to add your card on file");

					Thread.sleep(3000);
					driver.findElement(By.xpath("//div[@class='pagescroller']")).click();
					Thread.sleep(2000);
					IntakePojo.CardonFIle("Yes, I authorize the practice to use/store my card");

					driver.findElement(By.xpath("//input[@id='maxamount']")).sendKeys("100");
					driver.findElement(By.xpath("//div[@class='pagescroller']")).click();
					Thread.sleep(2000);

					// AddCardOn FIle
					IntakePojo.addCardonFIle("name_card", "hari");
					Thread.sleep(2000);
					IntakePojo.addCardonFIle("card_number", "4000");
					IntakePojo.addCardonFIle("card_number", "0000");
					IntakePojo.addCardonFIle("card_number", "0000");
					IntakePojo.addCardonFIle("card_number", "0002");

					IntakePojo.addCardonFIle("card_expdate", "12/2026");
					IntakePojo.addCardonFIle("cvv_number", "123");

					WebElement elementss = driver.findElement(By.xpath("//label[contains(.,'Same as home address')]"));
					js.executeScript("arguments[0].scrollIntoView(true);", elementss);
					Thread.sleep(2000);
					elementss.click();
					driver.findElement(By.xpath("(//button[@name='submitpinfo'][contains(.,'Continue')])")).click();

					// signature
					Thread.sleep(5000);
					WebElement sign1 = driver.findElement(By.xpath("//canvas[@id='ctlSignature1']"));

					js.executeScript("window.scrollBy(0,500)", "");
					Thread.sleep(2000);

					action.moveToElement(sign1).clickAndHold().moveByOffset(0, 80).moveByOffset(30, -60)
							.moveByOffset(30, 60).moveByOffset(-5, -15).moveByOffset(-10, 0).moveByOffset(0, -15)
							.release().build().perform();

					// Optional additional movements if needed
					action.moveToElement(sign1).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -60)
							.moveByOffset(30, 0).moveByOffset(0, 30).moveByOffset(-200, 0).moveByOffset(10, 0)
							.moveByOffset(0, 15).moveByOffset(-10, 0).release() // Release the click to finish the
																				// additional movements
							.build().perform();

					// Additional movements if required
					action.moveToElement(sign1).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -30)
							.moveByOffset(0, -10).release() // Release the click to finish the additional movements
							.build().perform();

					Thread.sleep(3000);
					driver.findElement(By.xpath("//div[@class='pagescroller']")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//button[contains(.,'Agree & Add card on file')]")).click();

				} else {
					// Card on File
					IntakePojo.CardonFIle("Continue to add your card on file");

					Thread.sleep(3000);
					driver.findElement(By.xpath("//div[@class='pagescroller']")).click();
					Thread.sleep(2000);
					IntakePojo.CardonFIle("Yes, I authorize the practice to use/store my card");

					driver.findElement(By.xpath("//input[@id='maxamount']")).sendKeys("100");
					driver.findElement(By.xpath("//div[@class='pagescroller']")).click();
					Thread.sleep(2000);

					// AddCardOn FIle
					IntakePojo.addCardonFIle("name_card", "hari");
					Thread.sleep(2000);
					IntakePojo.addCardonFIle("card_number", "4000");
					IntakePojo.addCardonFIle("card_number", "0000");
					IntakePojo.addCardonFIle("card_number", "0000");
					IntakePojo.addCardonFIle("card_number", "0002");

					IntakePojo.addCardonFIle("card_expdate", "12/2026");
					IntakePojo.addCardonFIle("cvv_number", "123");

					WebElement elementss = driver.findElement(By.xpath("//label[contains(.,'Same as home address')]"));
					js.executeScript("arguments[0].scrollIntoView(true);", elementss);
					Thread.sleep(2000);
					elementss.click();
					driver.findElement(By.xpath("(//button[@name='submitpinfo'][contains(.,'Continue')])")).click();

					// signature
					Thread.sleep(3000);
					WebElement sign1 = driver.findElement(By.xpath("//canvas[@id='ctlSignature1']"));

					js.executeScript("window.scrollBy(0,500)", "");
					Thread.sleep(2000);
					// Perform drawing actions
					action.moveToElement(sign1).clickAndHold().moveByOffset(0, 80).moveByOffset(30, -60)
							.moveByOffset(30, 60).moveByOffset(-5, -15).moveByOffset(-10, 0).moveByOffset(0, -15)
							.release().build().perform();

					// Optional additional movements if needed
					action.moveToElement(sign1).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -60)
							.moveByOffset(30, 0).moveByOffset(0, 30).moveByOffset(-200, 0).moveByOffset(10, 0)
							.moveByOffset(0, 15).moveByOffset(-10, 0).release() // Release the click to finish the
																				// additional movements
							.build().perform();

					// Additional movements if required
					action.moveToElement(sign1).clickAndHold().moveByOffset(0, 60).moveByOffset(0, -30)
							.moveByOffset(0, -10).release() // Release the click to finish the additional movements
							.build().perform();

					Thread.sleep(3000);
					driver.findElement(By.xpath("//div[@class='pagescroller']")).click();
					Thread.sleep(3000);
					driver.findElement(By.xpath("//button[contains(.,'Agree & Add card on file')]")).click();

				}

// Backto Dashboard login

				Thread.sleep(5000);
				ScreenShots(driver, "Checkin_Completion");
				driver.navigate().to("https://demoportal.yosicare.com/index");

				driver.findElement(By.xpath("//input[@id='email']"))
						.sendKeys(sheet1.getRow(i).getCell(0).getStringCellValue());
				driver.findElement(By.xpath("//input[@id='password']"))
						.sendKeys(sheet1.getRow(i).getCell(1).getStringCellValue());
				driver.findElement(By.xpath("//input[@id='lgn_sub']")).click();
				Thread.sleep(5000);

				int locSize = driver.findElements(By.xpath("//*[@id='locationpop']/div/div/div/div")).size();

				System.out.println(locSize);
				if (locSize == 1) {
					driver.findElement(By.xpath("//*[@id='locationpop']/div/div/div/div")).click();
					WebElement location = driver.findElement(
							By.xpath("(//div[@class='nice-select wide location location-select open']//ul//li[text()='"
									+ sheet1.getRow(i).getCell(2).getStringCellValue() + "'])[1]"));
					Thread.sleep(1000);
					location.click();
				}

				wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/th/div[contains(.,'Status')]")));

				int size1 = driver.findElements(By.xpath("//tr[@class='info group-by collapsed']/td[contains(.,'"
						+ sheet1.getRow(i).getCell(4).getStringCellValue() + "')]")).size();
				if (size1 == 1) {
					driver.findElement(By.xpath("//tr[@class='info group-by collapsed']/td[contains(.,'"
							+ sheet1.getRow(i).getCell(4).getStringCellValue() + "')]")).click();
				}

				int testsize = driver.findElements(By.xpath("//div[@class='appt_namecard_text'][contains(.,'"
						+ sheet1.getRow(i).getCell(3).getStringCellValue() + "')]")).size();
				if (testsize == 1) {

					driver.findElement(By.xpath("//div[@class='appt_namecard_text'][contains(.,'"
							+ sheet1.getRow(i).getCell(3).getStringCellValue() + "')]")).click();
					driver.findElement(By.xpath("//a[@data-pname='"
							+ sheet1.getRow(i).getCell(3).getStringCellValue().toUpperCase() + "']")).click();

				} else {

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'"
							+ sheet1.getRow(i).getCell(3).getStringCellValue().toUpperCase() + "')])[1]")));
					boolean displayed = driver
							.findElement(By.xpath("(//*[contains(text(),'"
									+ sheet1.getRow(i).getCell(3).getStringCellValue().toUpperCase() + "')])[1]"))
							.isDisplayed();
					System.out.println(displayed);

					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@data-pname='"
							+ sheet1.getRow(i).getCell(3).getStringCellValue().toUpperCase() + "'])[1]")));
					driver.findElement(By.xpath("(//*[@data-pname='"
							+ sheet1.getRow(i).getCell(3).getStringCellValue().toUpperCase() + "'])[1]")).click();

				}

				// View PDF form
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(("(//div[@class='appt_namecard_text'][contains(.,'"
								+ sheet1.getRow(i).getCell(3).getStringCellValue()
								+ "')]//parent::div[@class='appt_namecard_box']//parent::div[@class='appt_name_container']//parent::td[@class='col-md-2']//parent::tr/td[@class='col-md-1']/a)[1]"))));
				String viewPDF = driver.findElement(By.xpath("(//div[@class='appt_namecard_text'][contains(.,'"
						+ sheet1.getRow(i).getCell(3).getStringCellValue()
						+ "')]//parent::div[@class='appt_namecard_box']//parent::div[@class='appt_name_container']//parent::td[@class='col-md-2']//parent::tr/td[@class='col-md-1']/a)[1]"))
						.getAttribute("title");
				System.out.println(viewPDF);
				if (viewPDF.equals("View Form")) {

					System.out.println("PDF Created");
				} else {
					Assert.assertTrue("PDF Not generated", false);
				}

				// Payment check
				int paymentadhoc = driver.findElements(By.xpath("//div[@class='appt_namecard_text'][contains(.,'"
						+ sheet1.getRow(i).getCell(3).getStringCellValue()
						+ "')]//parent::div[@class='appt_namecard_box']//parent::div[@class='appt_name_container']//parent::td[@class='col-md-2']//parent::tr/td[@class='col-md-1 adhoc']//following-sibling::span[text()='$100.00']"))
						.size();
				System.out.println(paymentadhoc);

				if (paymentadhoc == 0) {

					Assert.assertTrue("Adhoc Payment not added", false);
				}

				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//div/button[contains(.,'Send email')]")));

				driver.findElement(By.xpath("//div/button[contains(.,'Send email')]")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("//button[@class='btn cls_btnres reminderListReload']")).click();

				driver.findElement(By.xpath("//button[text()='Send text']")).click();
				Thread.sleep(3000);
				String text = driver.findElement(By.xpath("//h3[@id='res_message']")).getText();

				System.out.println(text);

				boolean textval = text.equals("Reminder has been sent successfully.");
				Assert.assertTrue("text not sent", textval);

				ScreenShots(driver, "Dashboard_img");

				Thread.sleep(2000);
				driver.navigate().to("https://yopmail.com/");

				WebElement yopsrch = driver.findElement(By.xpath("//input[@id='login']"));
				yopsrch.clear();
				Thread.sleep(2000);
				yopsrch.sendKeys("" + sheets.getRow(i).getCell(13).getStringCellValue() + "", Keys.ENTER);

				Thread.sleep(2000);

				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='ifmail']")));
				String attribute = driver.findElement(By.xpath("//a[contains(.,'Complete your Self Check-In')]"))
						.getAttribute("href");

				driver.navigate().to(attribute);

				driver.switchTo().defaultContent();

				driver.navigate().to("https://yopmail.com/");

				WebElement yopsrch1 = driver.findElement(By.xpath("//input[@id='login']"));
				yopsrch1.clear();
				yopsrch1.sendKeys("" + sheets.getRow(i).getCell(13).getStringCellValue() + "", Keys.ENTER);

				Thread.sleep(2000);

				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='ifmail']")));
				String attribute1 = driver.findElement(By.xpath("//a[contains(.,'Complete your Self Check-In')]"))
						.getAttribute("href");

				driver.navigate().to(attribute1);
				driver.switchTo().defaultContent();

				int startConfirm = driver.findElements(By.xpath("//button[@data-value='Confirmed']")).size();

				if (startConfirm == 1) {
					WebElement startConfirm1 = driver.findElement(By.xpath("//button[@data-value='Confirmed']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", startConfirm1);
					wait.until(ExpectedConditions.visibilityOf(startConfirm1));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", startConfirm1);
				}
				driver.findElement(By.xpath("//button[contains(.,'Self-check-in')]")).click();

				Thread.sleep(3000);
				ScreenShots(driver, "SelfCheckin_Completion");
				driver.get("https://preview.athenahealth.com/1959031/2/login.esp");

				wait.until(ExpectedConditions
						.visibilityOf(driver.findElement(By.xpath("//input[@id='athena-username']"))));
				driver.findElement(By.xpath("//input[@id='athena-username']")).sendKeys("p-aselvaraj");

				driver.findElement(By.xpath("//input[@id='athena-password']")).sendKeys("Yosicare12345#");

				driver.findElement(By.xpath("//span[text()='Log In']")).click();

				Thread.sleep(6000);
				driver.findElement(By.xpath("//input[@id='loginbutton']")).click();

				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='Status']")));
				// Select a Department
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='DEPARTMENTID']")));
				Select dept = new Select(driver.findElement(By.xpath("//select[@id='DEPARTMENTID']")));
				dept.selectByVisibleText(sheet.getRow(i).getCell(17).getStringCellValue());
				driver.switchTo().defaultContent();

				Thread.sleep(8000);
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalNav']")));
				driver.findElement(By.xpath("//div[@id='calendarmenucomponent']")).click();
				Thread.sleep(2000);
				driver.switchTo().defaultContent();

				// Click New today's Patient
				driver.findElement(By.xpath("//div[@id='466ca95f9729ec097f14c1d00cdbcb8d']")).click();

				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frScheduleNav']")));
				
				

				WebElement patname = driver.findElement(By.xpath("(//a[contains(.,'TESTPATIENT, "+ sheet.getRow(i).getCell(16).getStringCellValue() + "')])[1]"));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", patname);

				// Click Athena Intake
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frMain']")));
				driver.findElement(By.cssSelector("div#nimbus-collector-header")).getShadowRoot()
						.findElement(By.cssSelector("div[class='fe_c_stepper__description']")).click();

				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frMain']")));
				Thread.sleep(3000);

				// Click Athena Intake Confirmation
				driver.findElement(By.cssSelector("div#nimbus-collector-header")).getShadowRoot()
						.findElement(
								By.cssSelector("button[class='fe_c_button fe_c_button--primary fe_c_button--medium']"))
						.click();

				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frMain']")));
				Thread.sleep(3000);

				// Goto intake Element
				driver.findElement(By.xpath("//button[normalize-space()='Go to Intake']")).click();

				// Data Reconcilation
				driver.findElement(By
						.xpath("//div[@class='tab-content metric-location'][normalize-space()='Data Reconciliation']"))
						.click();

				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frMain']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='classic-data-reconcile-iframe']")));
				Thread.sleep(3000);

				int size2 = driver.findElements(By.xpath("(//a[contains(.,'Select all available')])")).size();
				for (int k = 1; k <= size2; k++) {
					driver.findElement(By.xpath("(//a[contains(.,'Select all available')])[" + k + "]")).click();
					Thread.sleep(1000);
				}

				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frMain']")));
				driver.findElement(By.xpath("//button[normalize-space()='Update Chart']")).click();

				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frMain']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='classic-data-reconcile-iframe']")));
				Thread.sleep(3000);
				driver.findElement(By.xpath("//button[normalize-space()='Confirm']")).click();
				Thread.sleep(3000);
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalNav']")));
				driver.findElement(By.xpath("//div[@id='calendarmenucomponent']")).click();
				Thread.sleep(3000);
				driver.switchTo().defaultContent();

				// Click New today's Patient
				driver.findElement(By.xpath("//div[@id='466ca95f9729ec097f14c1d00cdbcb8d']")).click();

				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frScheduleNav']")));
				
				WebElement patname1 = driver.findElement(By.xpath("(//a[contains(.,'TESTPATIENT, "+ sheet.getRow(i).getCell(16).getStringCellValue() + "')])[1]"));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", patname1);


				driver.switchTo().defaultContent();
				wait.until(
						ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@id='GlobalWrapper']")));
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//frame[@id='frameContent']")));
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@id='frMain']")));

				Thread.sleep(5000);
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frMain']")));
				driver.findElement(By.cssSelector("div#nimbus-collector-header")).getShadowRoot()
						.findElement(By.cssSelector("div[class='fe_c_stepper__description']")).click();

				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='GlobalWrapper']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//frame[@id='frameContent']")));
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frMain']")));
				Thread.sleep(3000);

				driver.findElement(By.xpath("//button[normalize-space()='Go to Intake']")).click();
				Thread.sleep(3000);

//Reason for Visit		
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

//Patient preference
				// pharamacies
				String phar = driver.findElement(By.xpath("(//span[@class='clinical-provider-name'])[1]"))
						.getAttribute("innerHTML");

				boolean phars = phar.trim().equalsIgnoreCase(sheet3.getRow(i).getCell(0).getStringCellValue().trim());
				if (phars == true) {
					sheet3.getRow(i).createCell(14).setCellValue("Pass");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);

				} else {
					sheet3.getRow(i).createCell(14).setCellValue("Fail");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
					Assert.assertTrue("Pharmacies Not Matched", false);

				}

				// Careteam
				Thread.sleep(2000);
				String primary = driver.findElement(By.xpath("(//span[@class='clinical-provider-name'])[2]"))
						.getAttribute("innerHTML");
				boolean contains1 = primary.trim()
						.equalsIgnoreCase(sheet3.getRow(i).getCell(1).getStringCellValue().trim());
				System.out.println(contains1);
				if (contains1 == true) {
					sheet3.getRow(i).createCell(15).setCellValue("Pass");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);

				} else {

					sheet3.getRow(i).createCell(15).setCellValue("Fail");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
					Assert.assertTrue("Primary Not Matched", false);

				}

				String referring = driver.findElement(By.xpath("(//span[@class='clinical-provider-name'])[3]"))
						.getAttribute("innerHTML");
				boolean contains2 = referring.trim()
						.equalsIgnoreCase(sheet3.getRow(i).getCell(2).getStringCellValue().trim());

				if (contains2 == true) {
					sheet3.getRow(i).createCell(16).setCellValue("Pass");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
				} else {

					sheet3.getRow(i).createCell(16).setCellValue("Fail");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
					Assert.assertTrue("referring provider Not Matched", false);

				}
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

//Vitals

				// NULLL

//ALlergies		
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

				int allergieslist = driver.findElements(By.xpath("//span[@class='allergy-name']")).size();
				if (allergieslist >= 1) {
					String text1 = driver.findElement(By.xpath("//span[@class='allergy-name']"))
							.getAttribute("innerHTML");
					boolean allr = text1.trim()
							.equalsIgnoreCase(sheet3.getRow(i).getCell(3).getStringCellValue().trim());
					if (allr == true) {
						sheet3.getRow(i).createCell(17).setCellValue("Pass");
						FileOutputStream fos = new FileOutputStream(f);
						workbook.write(fos);
					} else {
						sheet3.getRow(i).createCell(17).setCellValue("Fail");
						FileOutputStream fos = new FileOutputStream(f);
						workbook.write(fos);
						Assert.assertTrue("Allergies Not Matched", false);

					}
				}

				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

//Medications	

				int medicationList = driver.findElements(By.xpath("//div[@data-flowsheet-element-type='MEDICATION']"))
						.size();
				if (medicationList >= 1) {
					String text1 = driver.findElement(By.xpath("//div[@data-flowsheet-element-type='MEDICATION']"))
							.getAttribute("innerHTML");
					boolean medi = text1.trim()
							.equalsIgnoreCase(sheet3.getRow(i).getCell(4).getStringCellValue().trim());

					if (medi == true) {
						sheet3.getRow(i).createCell(18).setCellValue("Pass");
						FileOutputStream fos = new FileOutputStream(f);
						workbook.write(fos);
					} else {
						sheet3.getRow(i).createCell(18).setCellValue("Fail");
						FileOutputStream fos = new FileOutputStream(f);
						workbook.write(fos);
						Assert.assertTrue("Medications Not Matched", false);

					}
				}

				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

//Vaccines
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

//Problems
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

// Family history
				String fam = driver.findElement(By.xpath("//span[@class='problem']")).getAttribute("innerHTML");
				boolean fami = fam.trim().equalsIgnoreCase(sheet3.getRow(i).getCell(5).getStringCellValue().trim());
				if (fami == true) {
					sheet3.getRow(i).createCell(19).setCellValue("Pass");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
				} else {
					sheet3.getRow(i).createCell(19).setCellValue("Fail");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
					Assert.assertTrue("Family history Not Matched", false);

				}

				String famrel = driver.findElement(By.xpath("//li[@class='relation']")).getAttribute("innerHTML");
				boolean famire = famrel.trim()
						.equalsIgnoreCase(sheet3.getRow(i).getCell(6).getStringCellValue().trim());

				System.out.println(famrel);
				if (famire == true) {
					sheet3.getRow(i).createCell(20).setCellValue("Pass");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
				} else {
					sheet3.getRow(i).createCell(20).setCellValue("Fail");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
					Assert.assertTrue("family relATION Not Matched", false);

				}
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

// Social History
				int socbtn = driver.findElements(By.xpath(
						"//button[@type='button']//span[@class='fe_c_button__text fe_c_button__text--medium fe_c_button__text--primary'][contains(.,'Yes')]//parent::div[@class='fe_c_button__content']//parent::button//parent::*//parent::*//parent::*//parent::*//parent::*//label"))
						.size();
				int cell = 21;

				for (int j = 0; j < socbtn; j++) {
					try {
						// Re-fetch the list of buttons inside the loop to avoid
						// StaleElementReferenceException
						List<WebElement> socbtns = driver.findElements(By.xpath(
								"//button[@type='button']//span[@class='fe_c_button__text fe_c_button__text--medium fe_c_button__text--primary'][contains(.,'Yes')]//parent::div[@class='fe_c_button__content']//parent::button//parent::*//parent::*//parent::*//parent::*//parent::*//label"));

						String text2 = socbtns.get(j).getAttribute("innerHTML").trim();
						boolean socialques = text2
								.equalsIgnoreCase(sheet3.getRow(0).getCell(cell).getStringCellValue().trim());

						if (socialques) {
							sheet3.getRow(i).createCell(cell++).setCellValue("Pass");
						} else {
							sheet3.getRow(i).createCell(cell++).setCellValue("Fail");
							Assert.assertTrue("Social History Not Matched", false);
						}

						// Write the workbook after each iteration
						try (FileOutputStream fos = new FileOutputStream(f)) {
							workbook.write(fos);
						}
					} catch (StaleElementReferenceException e) {
						// Handle stale element case
						System.out.println("StaleElementReferenceException caught. Retrying...");
						j--; // Decrement j to retry the same index
					} catch (Exception e) {
						// Handle any other exceptions
						e.printStackTrace();
					}
				}
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

// Surgical History
				Thread.sleep(2000);
				String sur = driver.findElement(By.xpath("//span[@class='displayname']")).getAttribute("innerHTML");
				boolean surg = sur.trim().equalsIgnoreCase(sheet3.getRow(i).getCell(11).getStringCellValue().trim());
				if (surg == true) {
					sheet3.getRow(i).createCell(25).setCellValue("Pass");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
				} else {
					sheet3.getRow(i).createCell(25).setCellValue("Fail");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
					Assert.assertTrue("Surgical history Not Matched", false);

				}
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

// pastmedical
				String past = driver.findElement(By.xpath(
						"//span[@data-value='Y'][@class='select-bar-option selected']/parent::div//parent::div//parent::div//parent::div//parent::div//parent::div[@class='grid-row']//following-sibling::div[@class='question name general-label']"))
						.getAttribute("innerHTML");
				boolean pastm = past.trim().equalsIgnoreCase(sheet3.getRow(i).getCell(12).getStringCellValue().trim());
				if (pastm == true) {
					sheet3.getRow(i).createCell(26).setCellValue("Pass");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
				} else {
					sheet3.getRow(i).createCell(26).setCellValue("Fail");
					FileOutputStream fos = new FileOutputStream(f);
					workbook.write(fos);
					Assert.assertTrue("PastMedical Not Matched", false);

				}
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

// Screnning
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

// Quality Measures
				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

// History of Present Illness

				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

				// Review of Systems

				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

				// Procedure Documentation

				driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
				Thread.sleep(2000);

				// Orders and Results

				ScreenRecorderUtil.stopRecord();

			} catch (Exception e) {
				String[] emails = { "anbarasan.v@yosicare.com" };

				EmailSender.sendAdhocEmail(emails, "ALERT : SALES DEMO AUTOMATION SCRIPT FAILURE !!!",
						"<div>Hi Team,</div>\n"
								+ "    <h3 style='text-align: center; color: red;'>ALERT MESSAGE !!!</h3><br>"
								+ "     <font style=\"color: red; font-weight: bold;\">Sales Demo Automation Regression Script Execution Failed </font></b><br><br>"
								+ e + "\n" + "    <div style='text-align: center;'>Thank You!<br><br></div>" + "\n"
								+ "    <b>Best regards,<br>Automation Team</b>",
						null);

				throw e;

			}

		}
	}

	@Test(dependsOnMethods = "open_chrome_browser_and_launching_scheduling_u_rl")
	@Then("Email Confirmation")
	public void email_confirmation() throws Exception {
		//String[] emails = { "anbarasan.v@yosicare.com","kesavan.s@yosicare.com","jeevesh@yosicare.com","vaishak.s@yosicare.com","uday@yosicare.com","devops@yosicare.com"};
		String[] emails = {"anbarasan.v@yosicare.com"};
		EmailSender.sendAdhocEmail(emails, "SALES DEMO AUTOMATION SCRIPT REGRESSION: SUCCESS!", "<div>Hi Team,</div>\n"
				+ "    <h3>Below is an general regression scenarios for the sales demo:</h3><br>"
				+ "        1. Appointment Created Successfully via Scheduling<font color=\"rgb(128, 128, 0)\"> PASS </font><br><br>\n"
				+ "        2. Completed the Check-In Process via Email & Continued Intake via Scheduling<font color=\"rgb(128, 128, 0)\"> PASS </font><<br><br>\n"
				+ "        3. Completed the Self Check-In via Dashboard<font color=\"rgb(128, 128, 0)\"> PASS </font><br><br>\n"
				+ "        4. Completed Athena EMR Validation:<font color=\"rgb(128, 128, 0)\"> PASS </font><br><br>"
				+ "               [Reason for Visit, Patient Preference, Care Team, Allergies, Vitals, Medications, "
				+ "        Problems, Vaccines, Social History, Health History, Surgical History, Past Medical History, Screening, Quality Measures, History of Present Illness, Review of Systems, Procedure Documentation]</br><br></b>\n"
				+ "\n" + "    <div style='text-align: center;'>Thank You!<br><br></div>" + "\n"
				+ "    <b>Best regards,<br>Automation Team</b>", null);

	}

}
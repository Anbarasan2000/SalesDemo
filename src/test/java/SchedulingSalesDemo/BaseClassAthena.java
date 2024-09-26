package SchedulingSalesDemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseClassAthena {

	public static WebDriver driver;

	public static void launchbro() throws InterruptedException {

		// Headless Mode
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless=new");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}
	
	public void ScreenShots(WebDriver driver, String file) throws IOException {

		byte[] DestFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

		FileOutputStream fos = new FileOutputStream(new File("./Screenshots/" + file + ".jpg"));

		fos.write(DestFile);

	}

}

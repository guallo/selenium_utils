package selenium_utils_test;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

import selenium_utils.Driver;

public class ScreenshotTest extends CommonTestSetup {
	protected static Logger logger = Logger.getLogger(ScreenshotTest.class.getName());

	@BeforeClass
	public static void ScreenshotTest_setupClass() throws IOException {
		logger.entering(ScreenshotTest.class.getName(), "ScreenshotTest_setupClass");
		
		installWebPage("alert.html");
	}
	
	@AfterClass
	public static void ScreenshotTest_tearDownClass() throws IOException {
		logger.entering(ScreenshotTest.class.getName(), "ScreenshotTest_tearDownClass");
		
		uninstallWebPage("alert.html");
	}
	
	@Test
	public void testAlert() {
		WebDriver webDriver = getWebDriver();
		Driver driver = new Driver(webDriver);
		
		webDriver.get("file://" + getWebPage("alert.html").getAbsolutePath());

		driver.getScreenshotAs(OutputType.BYTES);
		
		webDriver.switchTo().alert().accept();
		webDriver.close();
	}

}

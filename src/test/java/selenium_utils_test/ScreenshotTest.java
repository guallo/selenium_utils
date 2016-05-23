package selenium_utils_test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

import selenium_utils.Driver;
import selenium_utils.None;

public class ScreenshotTest {

	@Test
	public void testAlert() {
		WebDriver webDriver = CommonTestParameters.webDriverFactory.apply(None.getInstance());
		Driver driver = new Driver(webDriver);
		
		webDriver.get("file://" + CommonTestParameters.webPagesDirectory.getAbsolutePath() + "/alert.html");

		driver.getScreenshotAs(OutputType.BYTES);
		
		webDriver.switchTo().alert().accept();
		webDriver.close();
	}

}

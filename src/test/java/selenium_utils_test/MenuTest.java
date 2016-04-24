package selenium_utils_test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium_utils.Driver;
import selenium_utils.Element;
import selenium_utils.None;

public class MenuTest {

	@Test
	public void testMouseOver() {
		WebDriver webDriver = CommonTestParameters.webDriverFactory.apply(None.getInstance());
		Driver driver = new Driver(webDriver);
		
		webDriver.get("file://" + CommonTestParameters.webPagesDirectory.getAbsolutePath() + "/menu.html");
		
		String grayColor = "^rgba\\(128, 128, 128, 1\\)$";
		String lightgrayColor = "^rgba\\(211, 211, 211, 1\\)$";
		
		for (int triesNum = 1; triesNum <= 10; triesNum++) {
			List<Element> rootMenuItems = driver.getElements(By.cssSelector("#main_menu > .menuitem"));
			
			for (int index = 0; index < rootMenuItems.size(); index ++) {
				Element rootMenuItem = rootMenuItems.get(index);
				
				rootMenuItem.scrollIntoView();
				assertTrue("Root menu item background color is not " + grayColor, rootMenuItem.cssPropertyContainsTheRegex("background-color", grayColor));
				
				List<Element> subMenuItems = rootMenuItem.getElements(By.cssSelector(".menu > .menuitem"));
				
				for (int index2 = 0; index2 < subMenuItems.size(); index2 ++) {
					Element subMenuItem = subMenuItems.get(index2);
					
					subMenuItem.scrollIntoView();
					assertTrue("Sub menu item background color is not " + lightgrayColor, subMenuItem.cssPropertyContainsTheRegex("background-color", lightgrayColor));
				}
			}
		}
		
		webDriver.close();
	}
}

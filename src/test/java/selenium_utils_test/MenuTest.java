package selenium_utils_test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium_utils.Driver;
import selenium_utils.Element;

public class MenuTest extends CommonTestSetup {
	protected static Logger logger = Logger.getLogger(MenuTest.class.getName());
	
	@BeforeClass
	public static void MenuTest_setupClass() throws IOException {
		logger.entering(MenuTest.class.getName(), "MenuTest_setupClass");
		
		installWebPage("menu.html");
	}
	
	@AfterClass
	public static void MenuTest_tearDownClass() throws IOException {
		logger.entering(MenuTest.class.getName(), "MenuTest_tearDownClass");
		
		uninstallWebPage("menu.html");
	}

	@Test
	public void testMouseOver() {
		WebDriver webDriver = getWebDriver();
		Driver driver = new Driver(webDriver);
		
		webDriver.get("file://" + getWebPage("menu.html").getAbsolutePath());
		
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
	}
}

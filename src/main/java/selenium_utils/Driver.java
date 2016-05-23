package selenium_utils;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

public class Driver implements WrapsDriver, AdvancedSearchContext, TakesScreenshot {
	private WebDriver _webDriver;  // wrapped WebDriver
	
	public Driver(WebDriver webDriver) {
		_webDriver = webDriver;
	}
	
	/**
	 * getWrappedDriver
	 */
	@Override
	public WebDriver getWrappedDriver() {
		return _webDriver;
	}
	
	/**
	 * getElement
	 */
	@Override
	public Element getElement(By locator) {
		return getElement(locator, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	@Override
	public Element getElement(By locator, long timeOutInSeconds) {
		return getElement(locator, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	@Override
	public Element getElement(By locator, long timeOutInSeconds, long sleepInMillis) {
		return getElements(locator, timeOutInSeconds, sleepInMillis, Constants.AT_LEAST_ONE).get(0);
	}
	
	/**
	 * getElements
	 */
	@Override
	public List<Element> getElements(By locator) {
		return getElements(locator, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS, Globals.DEFAULT_AT_LEAST);
	}
	
	@Override
	public List<Element> getElements(By locator, long timeOutInSeconds) {
		return getElements(locator, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS, Globals.DEFAULT_AT_LEAST);
	}
	
	@Override
	public List<Element> getElements(By locator, long timeOutInSeconds, long sleepInMillis) {
		return getElements(locator, timeOutInSeconds, sleepInMillis, Globals.DEFAULT_AT_LEAST);
	}
	
	@Override
	public List<Element> getElements(final By locator, long timeOutInSeconds, long sleepInMillis, final int atLeast) {
		FluentWait<WebDriver> wait = new FluentWait<>(_webDriver);
		wait.withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
		wait.pollingEvery(sleepInMillis, TimeUnit.MILLISECONDS);
		
		List<WebElement> webElements = wait.until(new ExpectedCondition<List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver webDriver) {
				List<WebElement> webElements = webDriver.findElements(locator);
				
				if (webElements.size() >= atLeast) {
					return webElements;
				}
				return null;
			}
		});
		
		List<Element> elements = new ArrayList<>();
		for (int index = 0; index < webElements.size(); index++) {
			elements.add(new Element(webElements.get(index)));
		}
		return elements;
	}
	
	/**
	 * isElementPresent
	 */
	@Override
	public boolean isElementPresent(By locator) {
		return isElementPresent(locator, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	@Override
	public boolean isElementPresent(By locator, long timeOutInSeconds) {
		return isElementPresent(locator, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	@Override
	public boolean isElementPresent(By locator, long timeOutInSeconds, long sleepInMillis) {
		return areElementsPresents(locator, timeOutInSeconds, sleepInMillis, Constants.AT_LEAST_ONE);
	}
	
	/**
	 * areElementsPresents
	 */
	@Override
	public boolean areElementsPresents(By locator) {
		return areElementsPresents(locator, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS, Globals.DEFAULT_AT_LEAST);
	}
	
	@Override
	public boolean areElementsPresents(By locator, long timeOutInSeconds) {
		return areElementsPresents(locator, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS, Globals.DEFAULT_AT_LEAST);
	}
	
	@Override
	public boolean areElementsPresents(By locator, long timeOutInSeconds, long sleepInMillis) {
		return areElementsPresents(locator, timeOutInSeconds, sleepInMillis, Globals.DEFAULT_AT_LEAST);
	}
	
	@Override
	public boolean areElementsPresents(By locator, long timeOutInSeconds, long sleepInMillis, int atLeast) {
		try {
			getElements(locator, timeOutInSeconds, sleepInMillis, atLeast);
		} catch (TimeoutException err) {
			return false;
		}
		return true;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		try {
			return ((TakesScreenshot)_webDriver).getScreenshotAs(target);
		} catch (UnhandledAlertException err) {
			BufferedImage screenshot;
			
			try {
				screenshot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			} catch (HeadlessException | AWTException suberr) {
				throw new WebDriverException(suberr);
			}
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			try {
				ImageIO.write(screenshot, "png", outputStream);
			} catch (IOException suberr2) {
				throw new WebDriverException(suberr2);
			}
			
			byte[] screenshotPngBytes = outputStream.toByteArray();
			return target.convertFromPngBytes(screenshotPngBytes);
		}
	}
}

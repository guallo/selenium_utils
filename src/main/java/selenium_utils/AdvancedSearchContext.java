package selenium_utils;

import java.util.List;

import org.openqa.selenium.By;

public interface AdvancedSearchContext {
	/**
	 * getElement
	 */
	Element getElement(By locator);
	Element getElement(By locator, long timeOutInSeconds);
	Element getElement(By locator, long timeOutInSeconds, long sleepInMillis);
	
	/**
	 * getElements
	 */
	List<Element> getElements(By locator);
	List<Element> getElements(By locator, long timeOutInSeconds);
	List<Element> getElements(By locator, long timeOutInSeconds, long sleepInMillis);
	List<Element> getElements(final By locator, long timeOutInSeconds, long sleepInMillis, final int atLeast);
	
	/**
	 * isElementPresent
	 */
	boolean isElementPresent(By locator);
	boolean isElementPresent(By locator, long timeOutInSeconds);
	boolean isElementPresent(By locator, long timeOutInSeconds, long sleepInMillis);
	
	/**
	 * areElementsPresents
	 */
	boolean areElementsPresents(By locator);
	boolean areElementsPresents(By locator, long timeOutInSeconds);
	boolean areElementsPresents(By locator, long timeOutInSeconds, long sleepInMillis);
	boolean areElementsPresents(By locator, long timeOutInSeconds, long sleepInMillis, int atLeast);
}

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
	
	/**
	 * waitUntilIsElementPresent
	 */
	public AdvancedSearchContext waitUntilIsElementPresent(By locator);
	public AdvancedSearchContext waitUntilIsElementPresent(By locator, long timeOutInSeconds);
	public AdvancedSearchContext waitUntilIsElementPresent(By locator, long timeOutInSeconds, long sleepInMillis);
	
	/**
	 * waitUntilAreElementsPresents
	 */
	public AdvancedSearchContext waitUntilAreElementsPresents(By locator);
	public AdvancedSearchContext waitUntilAreElementsPresents(By locator, long timeOutInSeconds);
	public AdvancedSearchContext waitUntilAreElementsPresents(By locator, long timeOutInSeconds, long sleepInMillis);
	public AdvancedSearchContext waitUntilAreElementsPresents(By locator, long timeOutInSeconds, long sleepInMillis, int atLeast);
}

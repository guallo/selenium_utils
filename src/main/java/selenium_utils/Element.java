package selenium_utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

import com.google.common.base.Function;

public class Element implements WrapsElement, AdvancedSearchContext {
	private WebElement _webElement;  // wrapped WebElement
	
	public Element(WebElement element) {
		assert element instanceof WrapsDriver;
		_webElement = element;
	}
	
	/**
	 * getWrappedElement
	 */
	@Override
	public WebElement getWrappedElement() {
		return _webElement;
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
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		
		List<WebElement> webElements = wait.until(new Function<Element, List<WebElement>>() {
			@Override
			public List<WebElement> apply(Element element) {
				List<WebElement> webElements = element.getWrappedElement().findElements(locator);
				
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
	
	/**
	 * isDisplayed
	 */
	
	public boolean isDisplayed() {
		return isDisplayed(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isDisplayed(long timeOutInSeconds) {
		return isDisplayed(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isDisplayed(long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			return _webElement.isDisplayed();			
		}
		
		try {
			waitUntilIsDisplayed(timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * isEnabled
	 */
	
	public boolean isEnabled() {
		return isEnabled(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isEnabled(long timeOutInSeconds) {
		return isEnabled(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isEnabled(long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			return _webElement.isEnabled();			
		}
		
		try {
			waitUntilIsEnabled(timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * isSelected
	 */
	
	public boolean isSelected() {
		return isSelected(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isSelected(long timeOutInSeconds) {
		return isSelected(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isSelected(long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			return _webElement.isSelected();			
		}
		
		try {
			waitUntilIsSelected(timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * isDisplayedAndIsEnabled
	 */
	
	public boolean isDisplayedAndIsEnabled() {
		return isDisplayedAndIsEnabled(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isDisplayedAndIsEnabled(long timeOutInSeconds) {
		return isDisplayedAndIsEnabled(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isDisplayedAndIsEnabled(long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			return _webElement.isDisplayed() && _webElement.isEnabled();			
		}
		
		try {
			waitUntilIsDisplayedAndIsEnabled(timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * isStale
	 */
	
	public boolean isStale() {
		return isStale(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isStale(long timeOutInSeconds) {
		return isStale(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean isStale(long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			try {
				isEnabled();
			} catch (StaleElementReferenceException exception) {
				return true;
			}
			return false;			
		}
		
		try {
			waitUntilIsStale(timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * containsTheText
	 */
	
	public boolean containsTheText(String text) {
		return containsTheText(text, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean containsTheText(String text, long timeOutInSeconds) {
		return containsTheText(text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean containsTheText(String text, long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			return _webElement.getText().contains(text);
		}
		
		try {
			waitUntilContainsTheText(text, timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * containsTheRegex
	 */
	
	public boolean containsTheRegex(String regex) {
		return containsTheRegex(regex, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean containsTheRegex(String regex, long timeOutInSeconds) {
		return containsTheRegex(regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean containsTheRegex(String regex, long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			String text = _webElement.getText();
			return Pattern.compile(regex).matcher(text).find();
		}
		
		try {
			waitUntilContainsTheRegex(regex, timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * attributeContainsTheText
	 */
	
	public boolean attributeContainsTheText(String attribute, String text) {
		return attributeContainsTheText(attribute, text, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean attributeContainsTheText(String attribute, String text, long timeOutInSeconds) {
		return attributeContainsTheText(attribute, text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean attributeContainsTheText(String attribute, String text, long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			String attributeValue = _webElement.getAttribute(attribute);
			
			if (attributeValue == null) {
				return false;
			}
			return attributeValue.contains(text);
		}
		
		try {
			waitUntilAttributeContainsTheText(attribute, text, timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * attributeContainsTheRegex
	 */
	
	public boolean attributeContainsTheRegex(String attribute, String regex) {
		return attributeContainsTheRegex(attribute, regex, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean attributeContainsTheRegex(String attribute, String regex, long timeOutInSeconds) {
		return attributeContainsTheRegex(attribute, regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean attributeContainsTheRegex(String attribute, String regex, long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			String attributeValue = _webElement.getAttribute(attribute);
			
			if (attributeValue == null) {
				return false;
			}
			return Pattern.compile(regex).matcher(attributeValue).find();
		}
		
		try {
			waitUntilAttributeContainsTheRegex(attribute, regex, timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * valueContainsTheText
	 */
	
	public boolean valueContainsTheText(String text) {
		return valueContainsTheText(text, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean valueContainsTheText(String text, long timeOutInSeconds) {
		return valueContainsTheText(text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean valueContainsTheText(String text, long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			return attributeContainsTheText("value", text);
		}
		
		try {
			waitUntilValueContainsTheText(text, timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * valueContainsTheRegex
	 */
	
	public boolean valueContainsTheRegex(String regex) {
		return valueContainsTheRegex(regex, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean valueContainsTheRegex(String regex, long timeOutInSeconds) {
		return valueContainsTheRegex(regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean valueContainsTheRegex(String regex, long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			return attributeContainsTheRegex("value", regex);
		}
		
		try {
			waitUntilValueContainsTheRegex(regex, timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * cssPropertyContainsTheText
	 */
	
	public boolean cssPropertyContainsTheText(String property, String text) {
		return cssPropertyContainsTheText(property, text, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean cssPropertyContainsTheText(String property, String text, long timeOutInSeconds) {
		return cssPropertyContainsTheText(property, text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean cssPropertyContainsTheText(String property, String text, long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			String propertyValue = _webElement.getCssValue(property);
			
			if (propertyValue == null) {
				return false;
			}
			return propertyValue.contains(text);
		}
		
		try {
			waitUntilCSSPropertyContainsTheText(property, text, timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * cssPropertyContainsTheRegex
	 */
	
	public boolean cssPropertyContainsTheRegex(String property, String regex) {
		return cssPropertyContainsTheRegex(property, regex, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean cssPropertyContainsTheRegex(String property, String regex, long timeOutInSeconds) {
		return cssPropertyContainsTheRegex(property, regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public boolean cssPropertyContainsTheRegex(String property, String regex, long timeOutInSeconds, long sleepInMillis) {
		if (timeOutInSeconds == 0) {
			String propertyValue = _webElement.getCssValue(property);
			
			if (propertyValue == null) {
				return false;
			}
			return Pattern.compile(regex).matcher(propertyValue).find();
		}
		
		try {
			waitUntilCSSPropertyContainsTheRegex(property, regex, timeOutInSeconds, sleepInMillis);
		} catch (TimeoutException exception) {
			return false;
		}
		return true;
	}
	
	/**
	 * waitUntilIsDisplayed
	 */
	
	public Element waitUntilIsDisplayed() {
		return waitUntilIsDisplayed(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsDisplayed(long timeOutInSeconds) {
		return waitUntilIsDisplayed(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsDisplayed(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.isDisplayed());
		return this;
	}
	
	/**
	 * waitUntilIsNotDisplayed
	 */
	
	public Element waitUntilIsNotDisplayed() {
		return waitUntilIsNotDisplayed(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsNotDisplayed(long timeOutInSeconds) {
		return waitUntilIsNotDisplayed(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsNotDisplayed(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.isDisplayed()));
		return this;
	}
	
	/**
	 * waitUntilIsEnabled
	 */
	
	public Element waitUntilIsEnabled() {
		return waitUntilIsEnabled(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsEnabled(long timeOutInSeconds) {
		return waitUntilIsEnabled(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsEnabled(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.isEnabled());
		return this;
	}
	
	/**
	 * waitUntilIsNotEnabled
	 */
	
	public Element waitUntilIsNotEnabled() {
		return waitUntilIsNotEnabled(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsNotEnabled(long timeOutInSeconds) {
		return waitUntilIsNotEnabled(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsNotEnabled(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.isEnabled()));
		return this;
	}
	
	/**
	 * waitUntilIsSelected
	 */
	
	public Element waitUntilIsSelected() {
		return waitUntilIsSelected(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsSelected(long timeOutInSeconds) {
		return waitUntilIsSelected(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsSelected(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.isSelected());
		return this;
	}
	
	/**
	 * waitUntilIsNotSelected
	 */
	
	public Element waitUntilIsNotSelected() {
		return waitUntilIsNotSelected(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsNotSelected(long timeOutInSeconds) {
		return waitUntilIsNotSelected(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsNotSelected(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.isSelected()));
		return this;
	}
	
	/**
	 * waitUntilIsDisplayedAndIsEnabled
	 */
	
	public Element waitUntilIsDisplayedAndIsEnabled() {
		return waitUntilIsDisplayedAndIsEnabled(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsDisplayedAndIsEnabled(long timeOutInSeconds) {
		return waitUntilIsDisplayedAndIsEnabled(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsDisplayedAndIsEnabled(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.isDisplayedAndIsEnabled());
		return this;
	}
	
	/**
	 * waitUntilIsNotDisplayedOrIsNotEnabled
	 */
	
	public Element waitUntilIsNotDisplayedOrIsNotEnabled() {
		return waitUntilIsNotDisplayedOrIsNotEnabled(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsNotDisplayedOrIsNotEnabled(long timeOutInSeconds) {
		return waitUntilIsNotDisplayedOrIsNotEnabled(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsNotDisplayedOrIsNotEnabled(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.isDisplayedAndIsEnabled()));
		return this;
	}
	
	/**
	 * waitUntilIsStale
	 */
	
	public Element waitUntilIsStale() {
		return waitUntilIsStale(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsStale(long timeOutInSeconds) {
		return waitUntilIsStale(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilIsStale(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.isStale());
		return this;
	}
	
	/**
	 * waitUntilContainsTheText
	 */
	
	public Element waitUntilContainsTheText(String text) {
		return waitUntilContainsTheText(text, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilContainsTheText(String text, long timeOutInSeconds) {
		return waitUntilContainsTheText(text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilContainsTheText(String text, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.containsTheText(text));
		return this;
	}
	
	/**
	 * waitUntilNotContainsTheText
	 */
	
	public Element waitUntilNotContainsTheText(String text) {
		return waitUntilNotContainsTheText(text, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilNotContainsTheText(String text, long timeOutInSeconds) {
		return waitUntilNotContainsTheText(text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilNotContainsTheText(String text, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.containsTheText(text)));
		return this;
	}
	
	/**
	 * waitUntilContainsTheRegex
	 */
	
	public Element waitUntilContainsTheRegex(String regex) {
		return waitUntilContainsTheRegex(regex, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilContainsTheRegex(String regex, long timeOutInSeconds) {
		return waitUntilContainsTheRegex(regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilContainsTheRegex(String regex, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.containsTheRegex(regex));
		return this;
	}
	
	/**
	 * waitUntilNotContainsTheRegex
	 */
	
	public Element waitUntilNotContainsTheRegex(String regex) {
		return waitUntilNotContainsTheRegex(regex, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilNotContainsTheRegex(String regex, long timeOutInSeconds) {
		return waitUntilNotContainsTheRegex(regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilNotContainsTheRegex(String regex, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.containsTheRegex(regex)));
		return this;
	}
	
	/**
	 * waitUntilAttributeContainsTheText
	 */
	
	public Element waitUntilAttributeContainsTheText(String attribute, String text) {
		return waitUntilAttributeContainsTheText(attribute, text, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilAttributeContainsTheText(String attribute, String text, long timeOutInSeconds) {
		return waitUntilAttributeContainsTheText(attribute, text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilAttributeContainsTheText(String attribute, String text, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.attributeContainsTheText(attribute, text));
		return this;
	}
	
	/**
	 * waitUntilAttributeNotContainsTheText
	 */
	
	public Element waitUntilAttributeNotContainsTheText(String attribute, String text) {
		return waitUntilAttributeNotContainsTheText(attribute, text, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilAttributeNotContainsTheText(String attribute, String text, long timeOutInSeconds) {
		return waitUntilAttributeNotContainsTheText(attribute, text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilAttributeNotContainsTheText(String attribute, String text, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.attributeContainsTheText(attribute, text)));
		return this;
	}
	
	/**
	 * waitUntilAttributeContainsTheRegex
	 */
	
	public Element waitUntilAttributeContainsTheRegex(String attribute, String regex) {
		return waitUntilAttributeContainsTheRegex(attribute, regex, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilAttributeContainsTheRegex(String attribute, String regex, long timeOutInSeconds) {
		return waitUntilAttributeContainsTheRegex(attribute, regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilAttributeContainsTheRegex(String attribute, String regex, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.attributeContainsTheRegex(attribute, regex));
		return this;
	}
	
	/**
	 * waitUntilAttributeNotContainsTheRegex
	 */
	
	public Element waitUntilAttributeNotContainsTheRegex(String attribute, String regex) {
		return waitUntilAttributeNotContainsTheRegex(attribute, regex, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilAttributeNotContainsTheRegex(String attribute, String regex, long timeOutInSeconds) {
		return waitUntilAttributeNotContainsTheRegex(attribute, regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilAttributeNotContainsTheRegex(String attribute, String regex, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.attributeContainsTheRegex(attribute, regex)));
		return this;
	}
	
	/**
	 * waitUntilValueContainsTheText
	 */
	
	public Element waitUntilValueContainsTheText(String text) {
		return waitUntilValueContainsTheText(text, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilValueContainsTheText(String text, long timeOutInSeconds) {
		return waitUntilValueContainsTheText(text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilValueContainsTheText(String text, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.valueContainsTheText(text));
		return this;
	}
	
	/**
	 * waitUntilValueNotContainsTheText
	 */
	
	public Element waitUntilValueNotContainsTheText(String text) {
		return waitUntilValueNotContainsTheText(text, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilValueNotContainsTheText(String text, long timeOutInSeconds) {
		return waitUntilValueNotContainsTheText(text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilValueNotContainsTheText(String text, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.valueContainsTheText(text)));
		return this;
	}
	
	/**
	 * waitUntilValueContainsTheRegex
	 */
	
	public Element waitUntilValueContainsTheRegex(String regex) {
		return waitUntilValueContainsTheRegex(regex, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilValueContainsTheRegex(String regex, long timeOutInSeconds) {
		return waitUntilValueContainsTheRegex(regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilValueContainsTheRegex(String regex, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.valueContainsTheRegex(regex));
		return this;
	}
	
	/**
	 * waitUntilValueNotContainsTheRegex
	 */
	
	public Element waitUntilValueNotContainsTheRegex(String regex) {
		return waitUntilValueNotContainsTheRegex(regex, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilValueNotContainsTheRegex(String regex, long timeOutInSeconds) {
		return waitUntilValueNotContainsTheRegex(regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilValueNotContainsTheRegex(String regex, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.valueContainsTheRegex(regex)));
		return this;
	}
	
	/**
	 * waitUntilCSSPropertyContainsTheText
	 */
	
	public Element waitUntilCSSPropertyContainsTheText(String property, String text) {
		return waitUntilCSSPropertyContainsTheText(property, text, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilCSSPropertyContainsTheText(String property, String text, long timeOutInSeconds) {
		return waitUntilCSSPropertyContainsTheText(property, text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilCSSPropertyContainsTheText(String property, String text, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.cssPropertyContainsTheText(property, text));
		return this;
	}
	
	/**
	 * waitUntilCSSPropertyNotContainsTheText
	 */
	
	public Element waitUntilCSSPropertyNotContainsTheText(String property, String text) {
		return waitUntilCSSPropertyNotContainsTheText(property, text, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilCSSPropertyNotContainsTheText(String property, String text, long timeOutInSeconds) {
		return waitUntilCSSPropertyNotContainsTheText(property, text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilCSSPropertyNotContainsTheText(String property, String text, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.cssPropertyContainsTheText(property, text)));
		return this;
	}
	
	/**
	 * waitUntilCSSPropertyContainsTheRegex
	 */
	
	public Element waitUntilCSSPropertyContainsTheRegex(String property, String regex) {
		return waitUntilCSSPropertyContainsTheRegex(property, regex, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilCSSPropertyContainsTheRegex(String property, String regex, long timeOutInSeconds) {
		return waitUntilCSSPropertyContainsTheRegex(property, regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilCSSPropertyContainsTheRegex(String property, String regex, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.cssPropertyContainsTheRegex(property, regex));
		return this;
	}
	
	/**
	 * waitUntilCSSPropertyNotContainsTheRegex
	 */
	
	public Element waitUntilCSSPropertyNotContainsTheRegex(String property, String regex) {
		return waitUntilCSSPropertyNotContainsTheRegex(property, regex, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilCSSPropertyNotContainsTheRegex(String property, String regex, long timeOutInSeconds) {
		return waitUntilCSSPropertyNotContainsTheRegex(property, regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilCSSPropertyNotContainsTheRegex(String property, String regex, long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(ExpectedElementConditions.not(ExpectedElementConditions.cssPropertyContainsTheRegex(property, regex)));
		return this;
	}
	
	/**
	 * waitUntilClicked
	 */
	
	public Element waitUntilClicked() {
		return waitUntilClicked(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS, true);
	}
	
	public Element waitUntilClicked(long timeOutInSeconds) {
		return waitUntilClicked(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS, true);
	}
	
	public Element waitUntilClicked(long timeOutInSeconds, long sleepInMillis) {
		return waitUntilClicked(timeOutInSeconds, sleepInMillis, true);
	}
	
	public Element waitUntilClicked(long timeOutInSeconds, long sleepInMillis, final boolean scroll) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(new Function<Element, Integer>() {
			@Override
			public Integer apply(Element element) {
				if (!element.getWrappedElement().isEnabled()) {
					return null;
				}
				
				try {
					element.click(scroll);
				} catch (ElementNotVisibleException except) {
					return null;
				} catch (WebDriverException except) {
					if (except.getMessage() != null && except.getMessage().contains("Element is not clickable at point")) {
						return null;
					}
					throw except;
				}
				return 1;
			}
		});
		return this;
	}
	
	/**
	 * waitUntilDoubleClicked
	 */
	
	public Element waitUntilDoubleClicked() {
		return waitUntilDoubleClicked(Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilDoubleClicked(long timeOutInSeconds) {
		return waitUntilDoubleClicked(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public Element waitUntilDoubleClicked(long timeOutInSeconds, long sleepInMillis) {
		ElementWait wait = new ElementWait(this, timeOutInSeconds, sleepInMillis);
		wait.until(new Function<Element, Integer>() {
			@Override
			public Integer apply(Element element) {
				if (!element.getWrappedElement().isEnabled()) {
					return null;
				}
				
				try {
					element.doubleClick();
				} catch (ElementNotVisibleException except) {
					return null;
				} catch (WebDriverException except) {
					if (except.getMessage() != null && except.getMessage().contains("Element is not clickable at point")) {
						return null;
					}
					throw except;
				}
				return 1;
			}
		});
		return this;
	}
	
	/**
	 * scrollIntoView
	 * 
	 * @SideEffects Moves the mouse to the middle of the element.
	 */
	public void scrollIntoView() {
		WebDriver webDriver = ((WrapsDriver) _webElement).getWrappedDriver();
		(new Actions(webDriver)).moveToElement(_webElement).perform();
	}
	
	/**
	 * click
	 */
	public void click() {
		click(true);
	}
	
	public void click(boolean scroll) {
		if (scroll) {
			scrollIntoView();
		}
		_webElement.click();
	}
	
	/**
	 * doubleClick
	 */
	
	public void doubleClick() {
		WebDriver webDriver = ((WrapsDriver) _webElement).getWrappedDriver();
		(new Actions(webDriver)).doubleClick(_webElement).perform();
	}
}

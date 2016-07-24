package selenium_utils;

import org.openqa.selenium.By;

public final class ElementConditions {
	
	public static ElementCondition not(final ElementCondition condition) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return !condition.apply(element);
			}
		};
	}
	
	/**
	 * isDisplayed
	 */
	
	public static ElementCondition isDisplayed() {
		return ElementConditions.isDisplayed(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isDisplayed(long timeOutInSeconds) {
		return ElementConditions.isDisplayed(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isDisplayed(final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.isDisplayed(timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * isEnabled
	 */
	
	public static ElementCondition isEnabled() {
		return ElementConditions.isEnabled(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isEnabled(long timeOutInSeconds) {
		return ElementConditions.isEnabled(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isEnabled(final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.isEnabled(timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * isSelected
	 */
	
	public static ElementCondition isSelected() {
		return ElementConditions.isSelected(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isSelected(long timeOutInSeconds) {
		return ElementConditions.isSelected(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isSelected(final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.isSelected(timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * isDisplayedAndIsEnabled
	 */
	
	public static ElementCondition isDisplayedAndIsEnabled() {
		return ElementConditions.isDisplayedAndIsEnabled(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isDisplayedAndIsEnabled(long timeOutInSeconds) {
		return ElementConditions.isDisplayedAndIsEnabled(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isDisplayedAndIsEnabled(final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.isDisplayedAndIsEnabled(timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * isStale
	 */
	
	public static ElementCondition isStale() {
		return ElementConditions.isStale(Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isStale(long timeOutInSeconds) {
		return ElementConditions.isStale(timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isStale(final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.isStale(timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * isElementPresent
	 */
	
	public static ElementCondition isElementPresent(By locator) {
		return ElementConditions.isElementPresent(locator, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isElementPresent(By locator, long timeOutInSeconds) {
		return ElementConditions.isElementPresent(locator, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition isElementPresent(final By locator, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.isElementPresent(locator, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * areElementsPresents
	 */
	
	public static ElementCondition areElementsPresents(By locator) {
		return ElementConditions.areElementsPresents(locator, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS, Globals.DEFAULT_AT_LEAST);
	}
	
	public static ElementCondition areElementsPresents(By locator, long timeOutInSeconds) {
		return ElementConditions.areElementsPresents(locator, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS, Globals.DEFAULT_AT_LEAST);
	}
	
	public static ElementCondition areElementsPresents(By locator, long timeOutInSeconds, long sleepInMillis) {
		return ElementConditions.areElementsPresents(locator, timeOutInSeconds, sleepInMillis, Globals.DEFAULT_AT_LEAST);
	}
	
	public static ElementCondition areElementsPresents(final By locator, final long timeOutInSeconds, final long sleepInMillis, final int atLeast) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.areElementsPresents(locator, timeOutInSeconds, sleepInMillis, atLeast);
			}
		};
	}
	
	/**
	 * containsTheText
	 */
	
	public static ElementCondition containsTheText(String text) {
		return ElementConditions.containsTheText(text, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition containsTheText(String text, long timeOutInSeconds) {
		return ElementConditions.containsTheText(text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition containsTheText(final String text, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.containsTheText(text, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * containsTheRegex
	 */
	
	public static ElementCondition containsTheRegex(String regex) {
		return ElementConditions.containsTheRegex(regex, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition containsTheRegex(String regex, long timeOutInSeconds) {
		return ElementConditions.containsTheRegex(regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition containsTheRegex(final String regex, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.containsTheRegex(regex, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * attributeContainsTheText
	 */
	
	public static ElementCondition attributeContainsTheText(String attribute, String text) {
		return ElementConditions.attributeContainsTheText(attribute, text, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition attributeContainsTheText(String attribute, String text, long timeOutInSeconds) {
		return ElementConditions.attributeContainsTheText(attribute, text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition attributeContainsTheText(final String attribute, final String text, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.attributeContainsTheText(attribute, text, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * attributeContainsTheRegex
	 */
	
	public static ElementCondition attributeContainsTheRegex(String attribute, String regex) {
		return ElementConditions.attributeContainsTheRegex(attribute, regex, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition attributeContainsTheRegex(String attribute, String regex, long timeOutInSeconds) {
		return ElementConditions.attributeContainsTheRegex(attribute, regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition attributeContainsTheRegex(final String attribute, final String regex, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.attributeContainsTheRegex(attribute, regex, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * valueContainsTheText
	 */
	
	public static ElementCondition valueContainsTheText(String text) {
		return ElementConditions.valueContainsTheText(text, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition valueContainsTheText(String text, long timeOutInSeconds) {
		return ElementConditions.valueContainsTheText(text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition valueContainsTheText(final String text, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.valueContainsTheText(text, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * valueContainsTheRegex
	 */
	
	public static ElementCondition valueContainsTheRegex(String regex) {
		return ElementConditions.valueContainsTheRegex(regex, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition valueContainsTheRegex(String regex, long timeOutInSeconds) {
		return ElementConditions.valueContainsTheRegex(regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition valueContainsTheRegex(final String regex, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.valueContainsTheRegex(regex, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * cssPropertyContainsTheText
	 */
	
	public static ElementCondition cssPropertyContainsTheText(String property, String text) {
		return ElementConditions.cssPropertyContainsTheText(property, text, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition cssPropertyContainsTheText(String property, String text, long timeOutInSeconds) {
		return ElementConditions.cssPropertyContainsTheText(property, text, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition cssPropertyContainsTheText(final String property, final String text, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.cssPropertyContainsTheText(property, text, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * cssPropertyContainsTheRegex
	 */
	
	public static ElementCondition cssPropertyContainsTheRegex(String property, String regex) {
		return ElementConditions.cssPropertyContainsTheRegex(property, regex, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition cssPropertyContainsTheRegex(String property, String regex, long timeOutInSeconds) {
		return ElementConditions.cssPropertyContainsTheRegex(property, regex, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static ElementCondition cssPropertyContainsTheRegex(final String property, final String regex, final long timeOutInSeconds, final long sleepInMillis) {
		return new ElementCondition() {
			
			@Override
			public Boolean apply(Element element) {
				return element.cssPropertyContainsTheRegex(property, regex, timeOutInSeconds, sleepInMillis);
			}
		};
	}
}

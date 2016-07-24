package selenium_utils;

import org.openqa.selenium.By;

public final class DriverConditions {
	
	public static DriverCondition not(final DriverCondition condition) {
		return new DriverCondition() {
			
			@Override
			public Boolean apply(Driver driver) {
				return !condition.apply(driver);
			}
		};
	}
	
	/**
	 * isElementPresent
	 */
	
	public static DriverCondition isElementPresent(By locator) {
		return DriverConditions.isElementPresent(locator, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static DriverCondition isElementPresent(By locator, long timeOutInSeconds) {
		return DriverConditions.isElementPresent(locator, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public static DriverCondition isElementPresent(final By locator, final long timeOutInSeconds, final long sleepInMillis) {
		return new DriverCondition() {
			
			@Override
			public Boolean apply(Driver driver) {
				return driver.isElementPresent(locator, timeOutInSeconds, sleepInMillis);
			}
		};
	}
	
	/**
	 * areElementsPresents
	 */
	
	public static DriverCondition areElementsPresents(By locator) {
		return DriverConditions.areElementsPresents(locator, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS, Globals.DEFAULT_AT_LEAST);
	}
	
	public static DriverCondition areElementsPresents(By locator, long timeOutInSeconds) {
		return DriverConditions.areElementsPresents(locator, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS, Globals.DEFAULT_AT_LEAST);
	}
	
	public static DriverCondition areElementsPresents(By locator, long timeOutInSeconds, long sleepInMillis) {
		return DriverConditions.areElementsPresents(locator, timeOutInSeconds, sleepInMillis, Globals.DEFAULT_AT_LEAST);
	}
	
	public static DriverCondition areElementsPresents(final By locator, final long timeOutInSeconds, final long sleepInMillis, final int atLeast) {
		return new DriverCondition() {
			
			@Override
			public Boolean apply(Driver driver) {
				return driver.areElementsPresents(locator, timeOutInSeconds, sleepInMillis, atLeast);
			}
		};
	}
}

package selenium_utils;

import org.openqa.selenium.By;

public final class ExpectedDriverConditions {
	
	public static ExpectedDriverCondition not(final ExpectedDriverCondition condition) {
		return new ExpectedDriverCondition() {
			
			@Override
			public None apply(Driver driver) {
				if (condition.apply(driver) == null) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedDriverCondition isElementPresent(final By locator) {
		return new ExpectedDriverCondition() {
			
			@Override
			public None apply(Driver driver) {
				if (DriverConditions.isElementPresent(locator).apply(driver).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedDriverCondition areElementsPresents(By locator) {
		return areElementsPresents(locator, Globals.DEFAULT_AT_LEAST);
	}
	
	public static ExpectedDriverCondition areElementsPresents(final By locator, final int atLeast) {
		return new ExpectedDriverCondition() {
			
			@Override
			public None apply(Driver driver) {
				if (DriverConditions.areElementsPresents(locator, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS, atLeast).apply(driver).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
}

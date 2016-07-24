package selenium_utils;

import org.openqa.selenium.By;

public final class ExpectedElementConditions {
	
	public static ExpectedElementCondition not(final ExpectedElementCondition condition) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (condition.apply(element) == null) {
					return None.getInstance();
				}
				return null;
			}
		};
	}

	public static ExpectedElementCondition isDisplayed() {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.isDisplayed().apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition isEnabled() {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.isEnabled().apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition isSelected() {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.isSelected().apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition isDisplayedAndIsEnabled() {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.isDisplayedAndIsEnabled().apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition isStale() {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.isStale().apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition isElementPresent(final By locator) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.isElementPresent(locator).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition areElementsPresents(By locator) {
		return areElementsPresents(locator, Globals.DEFAULT_AT_LEAST);
	}
	
	public static ExpectedElementCondition areElementsPresents(final By locator, final int atLeast) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.areElementsPresents(locator, Constants.TIME_OUT_0, Globals.DEFAULT_SLEEP_IN_MILLIS, atLeast).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition containsTheText(final String text) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.containsTheText(text).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition containsTheRegex(final String regex) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.containsTheRegex(regex).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition attributeContainsTheText(final String attribute, final String text) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.attributeContainsTheText(attribute, text).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition attributeContainsTheRegex(final String attribute, final String regex) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.attributeContainsTheRegex(attribute, regex).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition valueContainsTheText(final String text) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.valueContainsTheText(text).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition valueContainsTheRegex(final String regex) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.valueContainsTheRegex(regex).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition cssPropertyContainsTheText(final String property, final String text) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.cssPropertyContainsTheText(property, text).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
	
	public static ExpectedElementCondition cssPropertyContainsTheRegex(final String property, final String regex) {
		return new ExpectedElementCondition() {
			
			@Override
			public None apply(Element element) {
				if (ElementConditions.cssPropertyContainsTheRegex(property, regex).apply(element).booleanValue()) {
					return None.getInstance();
				}
				return null;
			}
		};
	}
}

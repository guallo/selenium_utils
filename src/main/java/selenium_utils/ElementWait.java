package selenium_utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

public class ElementWait extends FluentWait<Element> {

	public ElementWait(Element element) {
		this(element, new SystemClock(), Sleeper.SYSTEM_SLEEPER, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public ElementWait(Element element, long timeOutInSeconds) {
		this(element, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public ElementWait(Element element, long timeOutInSeconds, long sleepInMillis) {
		this(element, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis);
	}
	
	public ElementWait(Element element, Clock clock, Sleeper sleeper, long timeOutInSeconds, long sleepInMillis) {
		super(element, clock, sleeper);
		withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
	    pollingEvery(sleepInMillis, TimeUnit.MILLISECONDS);
	}
}

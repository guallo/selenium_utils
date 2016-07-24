package selenium_utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

public class DriverWait extends FluentWait<Driver> {

	public DriverWait(Driver driver) {
		this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, Globals.DEFAULT_TIME_OUT_IN_SECONDS, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public DriverWait(Driver driver, long timeOutInSeconds) {
		this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, Globals.DEFAULT_SLEEP_IN_MILLIS);
	}
	
	public DriverWait(Driver driver, long timeOutInSeconds, long sleepInMillis) {
		this(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis);
	}
	
	public DriverWait(Driver driver, Clock clock, Sleeper sleeper, long timeOutInSeconds, long sleepInMillis) {
		super(driver, clock, sleeper);
		withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
	    pollingEvery(sleepInMillis, TimeUnit.MILLISECONDS);
	}
}

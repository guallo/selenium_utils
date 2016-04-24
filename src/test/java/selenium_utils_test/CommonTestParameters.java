package selenium_utils_test;

import java.io.File;

import org.openqa.selenium.WebDriver;

import com.google.common.base.Function;

import selenium_utils.None;

public final class CommonTestParameters {
	public static Function<None, WebDriver> webDriverFactory;
	public static File webPagesDirectory;
}

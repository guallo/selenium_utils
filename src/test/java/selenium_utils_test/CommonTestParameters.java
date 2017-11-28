package selenium_utils_test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.google.common.base.Function;

import selenium_utils.None;

public final class CommonTestParameters {
	public static Function<None, WebDriver> webDriverFactory;
	public static File webPagesDirectory;
	public static Properties testProperties = CommonTestParameters.getTestProperties();
	
	public static Properties getTestProperties() {
		Properties props = new Properties();
		InputStream is = CommonTestParameters.class.getResourceAsStream("/test.properties");
		
		if (is == null) {
			return null;
		}
		
		try {
			props.load(is);
		} catch (IOException e) {
			return null;
		}
		
		return props;
	}
}

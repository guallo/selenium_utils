package selenium_utils_test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.google.common.io.Files;

@RunWith(Suite.class)
@SuiteClasses({ FirefoxTestSuite.class })
public class AllTests {
	private static File _tmpDir;
	
	@BeforeClass
	public static void setup() throws IOException {
		AllTests._installWebPages();
		CommonTestParameters.webPagesDirectory = AllTests._tmpDir;
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		CommonTestParameters.webPagesDirectory = null;
		AllTests._removeWebPages();
	}
	
	private static void _installWebPages() throws IOException {
		AllTests._tmpDir = Files.createTempDir();
		
		_installResourceFile("/web_pages/menu.html", AllTests._tmpDir);
		_installResourceFile("/web_pages/alert.html", AllTests._tmpDir);
	}
	
	private static void _removeWebPages() throws IOException {
		FileUtils.deleteDirectory(AllTests._tmpDir);
	}
	
	private static void _installResourceFile(String resourceFilename, File directory) throws IOException {
		String resourceBasename = resourceFilename.substring(resourceFilename.lastIndexOf("/") + 1);
		InputStream resourceInputStream = AllTests.class.getResourceAsStream(resourceFilename);
		FileOutputStream installFileOutputStream = new FileOutputStream(new File(directory, resourceBasename));
		IOUtils.copy(resourceInputStream, installFileOutputStream);
		installFileOutputStream.close();
		resourceInputStream.close();
	}
}

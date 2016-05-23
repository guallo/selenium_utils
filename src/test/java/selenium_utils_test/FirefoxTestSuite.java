package selenium_utils_test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.base.Function;
import com.google.common.io.Files;

import selenium_utils.None;

@RunWith(Suite.class)
@SuiteClasses({ MenuTest.class, ScreenshotTest.class })
public class FirefoxTestSuite {
	private static File _tmpDir;
	private static File _extractDir;
	private static File _tarBz2File;
	private static File _tarFile;
	private static final String _tarBz2Filename = "firefox-44.0.2.tar.bz2";
	private static final String _tarFilename = "firefox-44.0.2.tar";
	private static final String _extractDirname = "firefox-44.0.2";
	
	@BeforeClass
	public static void setup() throws IOException, ArchiveException {
		FirefoxTestSuite._installFirefox();
		CommonTestParameters.webDriverFactory = new Function<None, WebDriver>() {
			
			@Override
			public WebDriver apply(None input) {
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
				
				return new FirefoxDriver(new FirefoxBinary(new File(FirefoxTestSuite._extractDir.getAbsolutePath() + File.separator + "firefox" + File.separator + "firefox")), new FirefoxProfile(), dc);
			}
		};
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		CommonTestParameters.webDriverFactory = null;
		FirefoxTestSuite._removeFirefox();
	}
	
	private static void _installFirefox() throws IOException, ArchiveException {
		// Copy to temporary directory
		
		FirefoxTestSuite._tmpDir = Files.createTempDir();
		FirefoxTestSuite._tarBz2File = new File(FirefoxTestSuite._tmpDir, FirefoxTestSuite._tarBz2Filename);
		FirefoxTestSuite._tarFile = new File(FirefoxTestSuite._tmpDir, FirefoxTestSuite._tarFilename);
		
		InputStream tarBz2ResourceInputStream = FirefoxTestSuite.class.getResourceAsStream("/" + FirefoxTestSuite._tarBz2Filename);
		FileOutputStream tarBz2FileOutputStream = new FileOutputStream(FirefoxTestSuite._tarBz2File);
		IOUtils.copy(tarBz2ResourceInputStream, tarBz2FileOutputStream);
		tarBz2FileOutputStream.close();
		tarBz2ResourceInputStream.close();
		
		// decompress
		
		FileInputStream tarBz2FileInputStream = new FileInputStream(FirefoxTestSuite._tarBz2File);
		BufferedInputStream tarBz2BufferedInputStream = new BufferedInputStream(tarBz2FileInputStream);
		FileOutputStream tarFileOutputStream = new FileOutputStream(FirefoxTestSuite._tarFile);
		BZip2CompressorInputStream bZip2CompressorInputStream = new BZip2CompressorInputStream(tarBz2BufferedInputStream);
		final byte[] buffer = new byte[1024 * 4];
		int bytesReaded = 0;
		
		while (-1 != (bytesReaded = bZip2CompressorInputStream.read(buffer))) {
			tarFileOutputStream.write(buffer, 0, bytesReaded);
		}
		
		tarFileOutputStream.close();
		bZip2CompressorInputStream.close();
		tarBz2BufferedInputStream.close();
		tarBz2FileInputStream.close();
		
		// extract
		
		FirefoxTestSuite._extractDir = new File(FirefoxTestSuite._tmpDir, FirefoxTestSuite._extractDirname);
		FirefoxTestSuite._extractDir.mkdir();
		final InputStream tarFileInputStream = new FileInputStream(FirefoxTestSuite._tarFile);
		final TarArchiveInputStream tarArchiveInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", tarFileInputStream);
		TarArchiveEntry entry = null;
		
		while (null != (entry = (TarArchiveEntry) tarArchiveInputStream.getNextEntry())) {
			File extractFile = new File(FirefoxTestSuite._extractDir, entry.getName());
			
			if (entry.isDirectory()) {
				if (!extractFile.exists()) {
					if (!extractFile.mkdirs()) {
						throw new IllegalStateException(String.format("Couldn't create directory `%s'.", extractFile.getAbsolutePath()));
					}
				}
			} else {
				FileOutputStream extractFileOutputStream = new FileOutputStream(extractFile);
				IOUtils.copy(tarArchiveInputStream, extractFileOutputStream);
				extractFileOutputStream.close();
				
				if (Integer.toString(entry.getMode(), 8).matches("^.*[1357]..$")) {
					extractFile.setExecutable(true);
				}
			}
		}
		
		tarArchiveInputStream.close();
	}
	
	private static void _removeFirefox() throws IOException {
		// delete temporary directory
		
		FileUtils.deleteDirectory(FirefoxTestSuite._tmpDir);
	}
}

package selenium_utils_test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.io.Files;

public class CommonTestSetup {
	protected static Logger logger = Logger.getLogger(CommonTestSetup.class.getName());
	
	private static File tempDir;
	private static File driverInstallationDir;
	private static File webPagesInstallationDir;
	
	private WebDriver webDriver;
	
	public File getTempDir() {
		return tempDir;
	}
	
	public static File getWebPage(String name) {
		return new File(webPagesInstallationDir, name);
	}
	
	public WebDriver getWebDriver() {
		return webDriver;
	}
	
	public static void installResourceFile(String resourceFilename, File directory) throws IOException {
		String resourceBasename = resourceFilename.substring(resourceFilename.lastIndexOf("/") + 1);
		InputStream resourceInputStream = CommonTestSetup.class.getResourceAsStream(resourceFilename);
		FileOutputStream installFileOutputStream = new FileOutputStream(new File(directory, resourceBasename));
		IOUtils.copy(resourceInputStream, installFileOutputStream);
		installFileOutputStream.close();
		resourceInputStream.close();
	}
	
	public static void installWebPage(String name) throws IOException {
		installResourceFile("/web_pages/" + name, webPagesInstallationDir);
	}
	
	public static void uninstallWebPage(String name) throws IOException {
		java.nio.file.Files.delete(getWebPage(name).toPath());
	}
	
	@BeforeClass
	public static void CommonTestSetup_setupClass() throws IOException, ArchiveException {
		logger.entering(CommonTestSetup.class.getName(), "CommonTestSetup_setupClass");
		
		createTempDir();
		
		String driverType = TestProperties.getInstance().getProperty("driver.type");
		String driverVersion = TestProperties.getInstance().getProperty("driver.version");
		String driverArch = TestProperties.getInstance().getProperty("driver.arch");
		String driverLang = TestProperties.getInstance().getProperty("driver.lang");
		String driverTarBz2Basename = TestProperties.getInstance().getProperty("driver.tar.bz2.basename");
		
		installDriver(driverType, driverVersion, driverArch, driverLang, driverTarBz2Basename);
		installWebPages();
	}
	
	@AfterClass
	public static void CommonTestSetup_tearDownClass() throws IOException {
		logger.entering(CommonTestSetup.class.getName(), "CommonTestSetup_tearDownClass");
		
		uninstallWebPages();
		uninstallDriver();
		removeTempDir();
	}
	
	@Before
	public void CommonTestSetup_setupMethod() throws IOException {
		logger.entering(CommonTestSetup.class.getName(), "CommonTestSetup_setupMethod");
		
		String driverBinaryFilename = TestProperties.getInstance().getProperty("driver.binary.filename");
		String absDriverBinaryFilename = driverInstallationDir.getAbsolutePath() + File.separator + driverBinaryFilename.replace('/', File.separatorChar);
		
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		
		String driverType = TestProperties.getInstance().getProperty("driver.type");
		
		if (driverType.equals("firefox")) {
			webDriver = new FirefoxDriver(new FirefoxBinary(new File(absDriverBinaryFilename)), new FirefoxProfile(), dc);
		}
		else if (driverType.equals("chromium")) {
			System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, absDriverBinaryFilename);
			
			String chromiumBinaryFilename = TestProperties.getInstance().getProperty("chromium.binary.filename");
			String absChromiumBinaryFilename = driverInstallationDir.getAbsolutePath() + File.separator + chromiumBinaryFilename.replace('/', File.separatorChar);
			
			ChromeOptions co = new ChromeOptions();
			co.setBinary(absChromiumBinaryFilename);
			
			dc.setCapability(ChromeOptions.CAPABILITY, co);
			
			webDriver = new ChromeDriver(dc);
		}
		else {
			throw new TypeNotPresentException(driverType, null);
		}
	}
	
	@After
	public void CommonTestSetup_tearDownMethod() {
		logger.entering(CommonTestSetup.class.getName(), "CommonTestSetup_tearDownMethod");
		
		webDriver.quit();
		webDriver = null;
	}
	
	private static void createTempDir() {
		tempDir = Files.createTempDir();
	}
	
	private static void removeTempDir() throws IOException {
		FileUtils.deleteDirectory(tempDir);
		tempDir = null;
	}
	
	private static void installDriver(String type, String version, String arch, String lang, String tarBz2Basename) throws IOException, ArchiveException {
		// Copy to temporary directory
		
		File tarBz2File = new File(tempDir, tarBz2Basename);
		File tarFile = new File(tempDir, tarBz2Basename.replaceAll("\\.bz2$", ""));
		
		InputStream tarBz2ResourceInputStream = CommonTestSetup.class.getResourceAsStream(
			"/browsers/" + type + "/" + version + "/" + arch + "/" + lang + "/" + tarBz2Basename
		);
		FileOutputStream tarBz2FileOutputStream = new FileOutputStream(tarBz2File);
		IOUtils.copy(tarBz2ResourceInputStream, tarBz2FileOutputStream);
		tarBz2FileOutputStream.close();
		tarBz2ResourceInputStream.close();
		
		// Decompress
		
		FileInputStream tarBz2FileInputStream = new FileInputStream(tarBz2File);
		BufferedInputStream tarBz2BufferedInputStream = new BufferedInputStream(tarBz2FileInputStream);
		FileOutputStream tarFileOutputStream = new FileOutputStream(tarFile);
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
		
		// Extract
		
		driverInstallationDir = new File(tempDir, tarBz2Basename.replaceAll("\\.tar\\.bz2$", ""));
		driverInstallationDir.mkdir();
		final InputStream tarFileInputStream = new FileInputStream(tarFile);
		final TarArchiveInputStream tarArchiveInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", tarFileInputStream);
		TarArchiveEntry entry = null;
		
		while (null != (entry = (TarArchiveEntry) tarArchiveInputStream.getNextEntry())) {
			File extractFile = new File(driverInstallationDir, entry.getName());
			
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
		java.nio.file.Files.delete(tarFile.toPath());
		java.nio.file.Files.delete(tarBz2File.toPath());
	}
	
	private static void uninstallDriver() throws IOException {
		FileUtils.deleteDirectory(driverInstallationDir);
		driverInstallationDir = null;
	}
	
	private static void installWebPages() throws IOException {
		webPagesInstallationDir = new File(tempDir, "web_pages");
		webPagesInstallationDir.mkdir();
	}
	
	private static void uninstallWebPages() throws IOException {
		FileUtils.deleteDirectory(webPagesInstallationDir);
		webPagesInstallationDir = null;
	}
}

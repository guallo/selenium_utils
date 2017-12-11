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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.io.Files;

public class CommonTestSetup {
	private static File tempDir;
	private static File browserInstallationDir;
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
		createTempDir();
		
		String browserType = TestProperties.getInstance().getProperty("browser.type");
		String browserVersion = TestProperties.getInstance().getProperty("browser.version");
		String browserArch = TestProperties.getInstance().getProperty("browser.arch");
		String browserLang = TestProperties.getInstance().getProperty("browser.lang");
		String browserTarBz2Filename = TestProperties.getInstance().getProperty("browser.tar.bz2.filename");
		
		installBrowser(browserType, browserVersion, browserArch, browserLang, browserTarBz2Filename);
		installWebPages();
	}
	
	@AfterClass
	public static void CommonTestSetup_tearDownClass() throws IOException {
		uninstallWebPages();
		uninstallBrowser();
		removeTempDir();
	}
	
	@Before
	public void CommonTestSetup_setupMethod() throws IOException {
		String browserBinaryPath = TestProperties.getInstance().getProperty("browser.binary.path");
		String absBrowserBinaryPath = browserInstallationDir.getAbsolutePath() + File.separator + browserBinaryPath.replace('/', File.separatorChar);
		
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		
		webDriver = new FirefoxDriver(new FirefoxBinary(new File(absBrowserBinaryPath)), new FirefoxProfile(), dc);
	}
	
	@After
	public void CommonTestSetup_tearDownMethod() {
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
	
	private static void installBrowser(String type, String version, String arch, String lang, String tarBz2Filename) throws IOException, ArchiveException {
		// Copy to temporary directory
		
		File tarBz2File = new File(tempDir, tarBz2Filename);
		File tarFile = new File(tempDir, tarBz2Filename.replaceAll("\\.bz2$", ""));
		
		InputStream tarBz2ResourceInputStream = CommonTestSetup.class.getResourceAsStream(
			"/browsers/" + type + "/" + version + "/" + arch + "/" + lang + "/" + tarBz2Filename
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
		
		browserInstallationDir = new File(tempDir, tarBz2Filename.replaceAll("\\.tar\\.bz2$", ""));
		browserInstallationDir.mkdir();
		final InputStream tarFileInputStream = new FileInputStream(tarFile);
		final TarArchiveInputStream tarArchiveInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", tarFileInputStream);
		TarArchiveEntry entry = null;
		
		while (null != (entry = (TarArchiveEntry) tarArchiveInputStream.getNextEntry())) {
			File extractFile = new File(browserInstallationDir, entry.getName());
			
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
	
	private static void uninstallBrowser() throws IOException {
		FileUtils.deleteDirectory(browserInstallationDir);
		browserInstallationDir = null;
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

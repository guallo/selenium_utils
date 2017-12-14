package selenium_utils_test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class TestProperties extends Properties {
	private static final long serialVersionUID = 1L;
	private static TestProperties instance;
	
	private TestProperties() throws IOException {
		super();
		
		InputStream inStream = this.getClass().getResourceAsStream("/test.properties");
		this.load(inStream);
		inStream.close();
	}
	
	public static TestProperties getInstance() throws IOException {
		if (instance == null) {
			instance = new TestProperties();
		}
		
		return instance;
	}
}

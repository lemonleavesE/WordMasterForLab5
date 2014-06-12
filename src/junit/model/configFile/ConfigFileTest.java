package junit.model.configFile;

import static org.junit.Assert.*;
import model.configFile.ConfigFile;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigFileTest {

	private ConfigFile cfig;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cfig = ConfigFile.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWrite() {
		try
		{
			cfig.write(-1, -1, -1, 'i', "");
		}
		catch(Exception e)
		{
			if(e instanceof java.io.IOException)
			{
				fail("No handler for negative number!");
			}
		}
	}

}

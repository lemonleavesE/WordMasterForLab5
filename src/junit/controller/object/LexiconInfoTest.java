package junit.controller.object;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.object.LexiconInfo;

public class LexiconInfoTest {

	private LexiconInfo lx;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		lx = new LexiconInfo(0, 0, 0, 0, 0, "GG");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetName() {
		assertEquals("GG",lx.getName());
	}

	@Test
	public void testSetName() {
		lx.setName("OO");
		assertEquals("OO",lx.getName());
	}

}

package junit.controller.object;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.object.LibraryInfo;

public class LibraryInfoTest {

	private LibraryInfo lib;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		lib = new LibraryInfo();
		lib.setAccuracyAll(0.0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAccuracyAll() {
		assertEquals(0.0,lib.getAccuracyAll(),0.0);
	}

	@Test
	public void testSetAccuracyAll() {
		lib.setAccuracyAll(5.0);
		assertEquals(5.0,lib.getAccuracyAll(),0.0);
	}

}

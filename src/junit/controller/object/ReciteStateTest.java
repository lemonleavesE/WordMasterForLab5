package junit.controller.object;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.object.ReciteState;

public class ReciteStateTest {

	private ReciteState rs;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		rs = new ReciteState(0, 0, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetCount() {
		rs.setCount(5);
		assertEquals(5,rs.getCount());
	}

	@Test
	public void testGetCount() {
		assertEquals(0,rs.getCount());
	}

	@Test
	public void testCountRecitedPlus() {
		rs.CountRecitedPlus();
		assertEquals(1,rs.getCountRecited());
	}

	@Test
	public void testCountCorrectPlus() {
		rs.countCorrectPlus();
		assertEquals(1,rs.getCountCorrect());
	}

}

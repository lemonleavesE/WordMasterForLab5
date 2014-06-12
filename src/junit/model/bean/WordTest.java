package junit.model.bean;

import static org.junit.Assert.*;
import model.bean.Word;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WordTest {

	private Word wTest;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		wTest = Word.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetEntry() {
		assertEquals(-1,wTest.getEntry());
	}

	@Test
	public void testGetEnglish() {
		System.out.println(wTest.getEnglish());
		assertEquals("",wTest.getEnglish());
	}

	/**
	 * SetChineseTest before so the value of Chinese 
	 * should be set correctly in the following GetChineseTest
	 * 
	 * @important singleton test
	 * 
	 * */
	@Test
	public void testGetChinese() {
		assertEquals("บร",wTest.getChinese());
	}

	@Test
	public void testGetState() {
		assertEquals(-1,(int)wTest.getState());
	}

	@Test
	public void testSetEntry() {
		wTest.setEntry(1);
		assertEquals(1,(int)wTest.getEntry());
	}

	@Test
	public void testSetEnglish() {
		wTest.setEnglish("a");
		assertEquals("a",wTest.getEnglish());
	}

	@Test
	public void testSetChinese() {
		wTest.setChinese("บร");
		assertEquals("บร",wTest.getChinese());
	}

	@Test
	public void testSetState() {
		wTest.setState(2);
		assertEquals(2,(int)wTest.getState());
	}

}

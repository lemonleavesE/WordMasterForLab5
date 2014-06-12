package junit.model.bean;

import static org.junit.Assert.*;
import model.bean.Lexicon;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LexiconTest {

	private Lexicon lx; 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		lx = Lexicon.getInstance(); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetCountRecited() {
		lx.setCountRecited(1);
		assertEquals(1,lx.getCountRecited());
	}

	@Test
	public void testSetCountRight() {
		lx.setCountRight(2);
		assertEquals(2,lx.getCountRight());
	}

	@Test
	public void testSetCountTotal() {
		lx.setCountTotal(3);
		assertEquals(3,lx.getCountTotal());
	}

	@Test
	public void testSetEntryLastWord() {
		lx.setEntryLastWord(4);
		assertEquals(4,lx.getEntryLastWord());
	}

	@Test
	public void testSetEntryLexicon() {
		lx.setEntryLexicon(5);
		assertEquals(5,lx.getEntryLexicon());
	}

	@Test
	public void testSetEntryWord() {
		lx.setEntryWord(6);
		assertEquals(6,lx.getEntryWord());
	}

	@Test
	public void testSetType() {
		lx.setType("7");
		assertEquals("7",lx.getType());
	}

	@Test
	public void testGetCountTotal() {
		assertEquals(-1,lx.getCountTotal());
	}

	@Test
	public void testGetCountRecited() {
		assertEquals(-1,lx.getCountRecited());
	}

	@Test
	public void testGetCountRight() {
		assertEquals(-1,lx.getCountRight());
	}

	@Test
	public void testGetEntryLexicon() {
		assertEquals(-1,lx.getEntryLexicon());
	}

	@Test
	public void testGetEntryLastWord() {
		assertEquals(-1,lx.getEntryLastWord());
	}

	@Test
	public void testGetEntryWord() {
		assertEquals(-1,lx.getEntryWord());
	}

	@Test
	public void testGetType() {
		assertEquals("",lx.getType());
	}

	@Test
	public void testSetCountRightPlus() {
		lx.setCountRightPlus();
		assertEquals(0,lx.getCountRight());
	}

	@Test
	public void testSetCountRecitedPlus() {
		lx.setCountRecitedPlus();
		assertEquals(0,lx.getCountRecited());
	}

}

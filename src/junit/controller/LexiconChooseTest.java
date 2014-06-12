package junit.controller;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import model.bean.Lexicon;
import model.bean.Word;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;  

import controller.Action;


@RunWith(Parameterized.class)
public class LexiconChooseTest {

	private String lexiconPrefix;
	private String lexiconWord;
	private Action actionLxTest;
	private Word wordTest;
	
	public LexiconChooseTest(String lexiconPrefix, String lexiconWord)
	{
		this.lexiconPrefix = lexiconPrefix;
		this.lexiconWord = lexiconWord;
	}
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		actionLxTest = Action.getInstance();
		wordTest = Word.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	 @Parameters  
	 public static Collection usernameData() 
	 {  
		 return Arrays.asList(new Object[][] { { "a", "abandon" }, { "b", "baby" },  
		 { "c", "cab" },{"d","d/a"},{"e","each"},{"f","fable"},{"g","gain"},{"h","habit"},{"i","i"},
		 {"j","jack"},{"k","kangaroo"},{"l","lab"},{"m","machine"},{"n","nail"},{"o","oak"},{"p","pace"},{"q","qualification"},
		 {"r","rabbit"},{"s","sack"},{"t","table"},{"u","ugly"},{"v","vacacy"},{"w","wade"},{"x","xerox"},{"y","yacht"},{"z","zeal"}});  
	 }
	
	 
		/**
		 * Fetch all lexicons with the first word and test if 
		 * all the lexicons can be chosen correctly
		 * 
		 * 
		 * 
	     * 
	     */
		
	 
	@Test
	public void testLexiconChoose() {
		actionLxTest.chooseLexicon(this.lexiconPrefix);
		actionLxTest.chooseWord("0");
		assertEquals(this.lexiconWord,wordTest.getEnglish());
	}

}

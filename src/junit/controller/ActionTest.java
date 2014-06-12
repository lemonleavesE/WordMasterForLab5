package junit.controller;



import java.io.IOException;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;
import model.bean.Lexicon;
import model.bean.Word;
import model.configFile.ConfGenerator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized;

import controller.Action;
import controller.object.LexiconInfo;
import controller.object.LibraryInfo;
import controller.object.ReciteState;

@SuppressWarnings("unused")
public class ActionTest extends TestCase{

	private Action actionTest;
	private Word wordTest;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
        @Override
	public void setUp() throws Exception {
		ConfGenerator.generateConfig();
		actionTest = Action.getInstance();
		wordTest = Word.getInstance();
	}

	@After
        @Override
	public void tearDown() throws Exception {
	}

	

	/**
	 * Choose the "b" lexicon with 397 words
	 * 
	 * @SetNumTest_1: check the "b" lexicon with a correct count
	 * @SetNumTest_2: The parameter 15 < 397, the expected return should be 0
	 * @SetNumTest_3: The parameter 1000 > 397, the expected return should be 397
	 * 
	 * @throws IOException 
	 * */

	@Test
	public void testSetNum() throws IOException{
		actionTest.chooseLexicon("b");
		int lexiconTotal = Lexicon.getInstance().getCountTotal();
		
		SetNumTest_1: 
			assertEquals("The result of SetNumTest_1: " + (lexiconTotal == 397),397,lexiconTotal);
		
		int setNum = actionTest.setNum(15);
		
		SetNumTest_2: 
			assertEquals("The result of SetNumTest_2: " + (setNum == 0),0,setNum);
		
		setNum = actionTest.setNum(1000);
		
		SetNumTest_3: 
			assertEquals("The result of SetNumTest_3: " + (lexiconTotal == setNum),lexiconTotal,setNum);
		
	}

	
	/**
	 * Choose a lexicon and pick any word with the head letter as the parameter to cast 
	 * into the chooseWord() function, test whether the returning result match the expected
	 * word given in assertEquals()
	 *
	 * Test the if the word exists or not
	 * 
	 * @ChooseWordTest_1 The word that should be in the lexicon
	 * @ChooseWordTest_2 The word that should not be in the lexicon
	 *
	 *
	 * 
     * @throws java.io.IOException */

	@Test
	public void testChooseWord() throws IOException {
		
		actionTest.chooseLexicon("a");
		
		
		ChooseWordTest_1: 
			actionTest.chooseWord("0");
			assertEquals("The result of ChooseWordTest_1: " + (wordTest.getEnglish().equals("abandon")),"abandon",wordTest.getEnglish());
			actionTest.chooseWord("abbreviation");
			assertEquals("The result of ChooseWordTest_1: " + (wordTest.getEnglish().equals("abbreviation")),"abbreviation",wordTest.getEnglish());
			assertTrue("The result of ChooseWordTest_1: " + actionTest.chooseWord("1"),actionTest.chooseWord("1"));
		
		ChooseWordTest_2: 
			assertFalse("The result of ChooseWordTest_2:" + !actionTest.chooseWord("bad"),actionTest.chooseWord("bad"));
	}

	/**
	 * Check the status to be set correctly
	 * 
	 * @NextWordTest_1 status check 
	 * @NextWordTest_2 check if the next word return correctly
	 * @NextWordTest_3 test the upper bound of one recite task
	 * */
	@Test
	public void testNextWord() throws IOException {
		actionTest.chooseLexicon("a");
		actionTest.chooseWord("0");
		
		int status = actionTest.nextWord(wordTest.getEnglish());
		
		NextWordTest_1:
			assertEquals("The result of NextWordTest_1: " + (status == 1),1, status);
		
		NextWordTest_2:
			assertEquals("The result of NextWordTest_2: " + (wordTest.getEnglish().equals("abandonment")),"abandonment",wordTest.getEnglish());
		
		
		actionTest.chooseWord("abandon");
		actionTest.setNum(1);
		status = actionTest.nextWord(wordTest.getEnglish());
		NextWordTest_3:
			assertEquals("The result of NextWordTest_3: " + (status),(status > 0) ? 2 : -2,status);
	}

	/**
	 * Build a user.conf for test,and load in the file 
	 * which contains that a trivial last-word position,
	 * then check if the position is the same as expected
	 * 
	 *
	 * 
	 * @throws IOException 
	 * */
	@Test
	public void testGetLastWord() throws IOException {
		actionTest.chooseLexicon("a");
		actionTest.chooseWord("abandonment");
		actionTest.setNum(20);
		for(int i = 0;i < 10;i++)
			actionTest.nextWord(wordTest.getEnglish());
		
		assertEquals("abolition",actionTest.getLastWord());
	}
	
	/**
	 * @SearchTest_1 cast prefix "ab" as parameter to test the correctness
	 * 
	 * @throws IOException 
	 * */
	@Test
	public void testSearch() throws IOException
	{
		actionTest.chooseLexicon("a");
		actionTest.chooseWord("0");
		actionTest.setNum(100);
		List<String> sTest = actionTest.search("ab");
		
		SearchTest_1:
			for(int i = 0;i < sTest.size();i++)
			{
				assertTrue("The result of SearchTest_1 group (" + i + ")",sTest.get(i).substring(0, 2).equals("ab"));
			}
	}

	/**
	 * 
	 * @GetLexiconInfoTest_1 The function contains division, pass 0 as bomb to test the exception robustness
	 * @GetLexiconInfoTest_2 To test if we get the right info of present lexicon
	 * 
	 * 
	 * @throws IOException 
	 * */
	@Test
	public void testGetLexiconInfo() throws IOException
	{
		actionTest.chooseLexicon("a");
		actionTest.chooseWord("0");
		actionTest.setNum(100);
		LexiconInfo lx = null;
		
		
		
		GetLexiconInfoTest_1:
			try
			{
				lx = actionTest.getLexiconInfo();
			} 
			catch(Exception e)
			{
				if(e instanceof java.lang.ArithmeticException)
				{
					fail("The divided by zero fault does not resolve!");
				}
			}
			
		GetLexiconInfoTest_2:

			for(int i = 0;i < 10;i++)
			{
				wordTest.setState(1);
								
				actionTest.nextWord(wordTest.getEnglish());
			}
			try
			{
				lx = actionTest.getLexiconInfo();
			} 
			catch(Exception e)
			{
				if(e instanceof java.lang.ArithmeticException)
				{
					fail("The divided by zero fault does not resolve!");
				}
			}
			
			GetLexiconInfoTest_2_1:
				assertEquals("GetLexiconInfoTest_2_1","a",lx.getName());
		
			GetLexiconInfoTest_2_2:
				assertEquals("GetLexiconInfoTest_2_2",562,lx.getCountTotal());
			
			double accu = 10.0/562;
			String acc = accu + "";
			String accGet = lx.getAccuracy()+"";
			
			
			GetLexiconInfoTest_2_3:
				if(accGet.length() > 0)
				{
					assertTrue("GetLexiconInfoTest_2_3",acc.substring(0, 3).equals(accGet.subSequence(0, 3)));
				}
				else
					fail("Wrong with accuracy");
			
	}
	
	/**
	 * @throws IOException 
	 * */
	@Test
	public void testGetLexiconsInfo() throws IOException
	{
		LibraryInfo lInfo = null;
		List<LexiconInfo> lxList = null;
		
		try 
		{
			lInfo = actionTest.getLexiconsInfo();
			lxList = lInfo.getLexiconsInfo();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			if(e instanceof java.lang.ArithmeticException)
			{
				fail("The divided by zero fault does not resolve!");
			}
		}
	}
	
	/**
	 * @GetRecitedInfoTest_1 Check the zero division exception handle
	 * @GetRecitedInfoTest_2 Check the accuracy
	 * 
	 * */
	
	@Test
	public void testGetRecitedInfo()
	{
		String[] sRecited = null;
		
		GetRecitedInfoTest_1:
			try
			{
				for(int i = 0;i < 10;i++)
				{
					actionTest.nextWord("0");
				}
			
				for(int i = 0;i < 10;i++)
				{
					actionTest.nextWord(Word.getInstance().getEnglish());
				}
				sRecited = actionTest.getRecitedInfo();
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				if(e instanceof java.lang.ArithmeticException)
				{
					fail("The divided by zero fault does not resolve!");
				}
			}	
		
		GetRecitedInfoTest_2:
			if(sRecited.length > 0)
				assertTrue("GetRecitedInfoTest_2",sRecited[4].equals("50.0"));
			else
				fail("Wrong accuracy!");
	}
}

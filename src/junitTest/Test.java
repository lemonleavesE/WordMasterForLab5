package junitTest;

import static org.junit.Assert.assertEquals;
import model.bean.Word;
import controller.Action;
import controller.object.ReciteState;

public class Test {

	@org.junit.Test
	public void test() throws InterruptedException {
		Action.getInstance().chooseLexicon("a");
		Action.getInstance().setNum(3);
		Action.getInstance().chooseWord("1");		
		
		//获取乱序英文
		assertEquals(Word.getInstance().getEnglish(),Word.getInstance().getEnglishMuddled());
		
		//获取时间
		Action.getInstance().startTimer();
		Thread.sleep(3000);
		assertEquals("0:3",ReciteState.getInstance().getTime());
	}

}

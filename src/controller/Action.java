package controller;

import java.io.IOException;
import java.util.List;

import controller.object.LexiconInfo;
import controller.object.LibraryInfo;
import controller.object.ReciteState;
import model.bean.Lexicon;
import model.bean.Word;
import model.handler.LexiconHandler;
import model.handler.WordHandler;


/*
 * 主要是一些前台和后台调用的函数
 *public boolean chooseLexicon(String type)：
 *-选择此词库，type为输入的词库的类别（名称）
 *-返回是否选择成功。
 *
 
 *public int setNum(int num)
 *-选择背诵的单词个数,num 为输入的数目
 *-ret: 输入数目不超过词库剩余单词 return 0,
 *		else return 词库剩余单词数
 

 *public boolean chooseWord(String type)
 *-选择起始背诵的单词，type为类别：0为默认第一个，1为上次背诵的，type也可以是单词的英文
 *	0	-默认起始单词
 *	1	-上次背到的地方
 *	单词的英文	-特定单词，type为单词
 *-返回是否成功。
 * 
 * public int nextWord(String english)
 * -选择下一个单词,english为当前单词的背诵结果
 * -ret: 	1	-背对了，且选择好了另一个单词
 * 			2	-背对了，但是上限满了
 *			3 	-背对了，但是没有选好下一个单词
 *			-1 	-背错了，且选好另一个单词
 *			-2	-背错了，上限满了
 *			-3	-背错了，而且没有选好下一个单词
 * 
 * public String getLastWord()
 * -获取上次背诵的单词的信息
 * -ret: 上次的单词的英文
 * 
 * 	public LexiconInfo getLexiconInfo()
 * -获取当前词库的统计信息
 * -返回LexiconInfo
 * 
 * 	public LibraryInfo getLexiconsInfo()
 * -获取全部词库的统计信息
 * -返回LibraryInformation
 * 
 * 
 * 	public List<String> search(String prefix)
 * -prefix 为模糊查询的单词前缀
 * -返回模糊查询的单词英文list，最多为5个。
 * 
 * 
 * */
public class Action
{
	/*java的单例模式，确保整个应用程序中只有一个lexicon实例*/
	private volatile static Action instance = null; 

	  public static Action getInstance() { 
	    if (instance == null) { 
	      synchronized (Action.class) { 
	        if(instance == null) { 
	          instance = new Action(); 
	        } 
	      } 
	    } 
	    return instance; 
	} 
	
	public Action()
	{
		Word.getInstance().addObserver(WordHandler.getInstance());
	}
	
	/*供使用的方法*/
	//选择此词库，type为输入的词库的类别（名称）
	public boolean chooseLexicon(String type)
	{
		try {
			return LexiconHandler.getInstance().chooseLexicon(type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//选择背诵的单词个数,num 为输入的数目
	public int setNum(int num)
	{
		int countTemp = Lexicon.getInstance().getCountTotal();
		if(countTemp>=num)
		{
			ReciteState.getInstance().setReciteState(num, 0,0);
			return 0;
		}
		else
		{
			ReciteState.getInstance().setReciteState(countTemp, 0,0);
			return countTemp;
		}
	}
	
	//选择起始背诵的单词
	//	0	-默认起始单词
	//	1	-上次背到的地方
	//	单词的英文	-特定单词，type为单词
	public boolean chooseWord(String type)
	{
		try {
			return WordHandler.getInstance().chooseWord(type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//选择下一个单词
	//ret: 	1	-背对了，且选择好了另一个单词
	//		2	-背对了，但是上限满了
	//		3 	-背对了，但是没有选好下一个单词
	//		-1 	-背错了，且选好另一个单词
	//		-2	-背错了，上限满了
	//		-3	-背错了，而且没有选好下一个单词
	public int nextWord(String english)
	{
		int ret = 0;
		ReciteState.getInstance().CountRecitedPlus();
		
		if(Word.getInstance().getEnglish().equals(english))
		{
			if(Word.getInstance().getState()==0)
			{
				Word.getInstance().setState(2);
				Lexicon.getInstance().setCountRightPlus();
				Lexicon.getInstance().setCountRecitedPlus();
			}
			else if(Word.getInstance().getState()==1)
			{
				Word.getInstance().setState(2);
				Lexicon.getInstance().setCountRightPlus();
			}
			
			ReciteState.getInstance().countCorrectPlus();
			
			ret = 1;
		}
		else
		{
			if(Word.getInstance().getState()==0)
			{
				Word.getInstance().setState(1);
				Lexicon.getInstance().setCountRecitedPlus();
			}
						
			ret = -1;
		}
		
		if(ReciteState.getInstance().getCountRecited()>=ReciteState.getInstance().getCount())
		{
			ret *= 2;
			return ret;
		}
		
		//System.out.println("nextWord"+Word.getInstance().getEntry());
		if(Word.getInstance().getEntry()>8)
		{
			Lexicon.getInstance().setEntryLastWord(Word.getInstance().getEntry());
		}
		
		try {
			ret = WordHandler.getInstance().chooseWord("3")?ret:ret*3;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	//获取当前词库的统计信息
	//返回LexiconInfo
	public LexiconInfo getLexiconInfo()
	{
		float temp =  ((Lexicon.getInstance().getCountRecited()==0?0:(float)(Math.round(Lexicon.getInstance().getCountRight()*10000/Lexicon.getInstance().getCountRecited())/100.0)));
		
		return new LexiconInfo(Lexicon.getInstance().getCountTotal(), Lexicon.getInstance().getCountRecited(),Lexicon.getInstance().getCountRight(),Lexicon.getInstance().getCountRecited()-Lexicon.getInstance().getCountRight(), temp, Lexicon.getInstance().getType());
	}
	
	//获取全部词库的统计信息
	//返回LibraryInformation
	public LibraryInfo getLexiconsInfo()
	{		
		//for(int i=0; i<LexiconHandler.getInstance().getLibraryInfo().getLexiconsInfo().size();i++)
		//{
			//System.out.println(LexiconHandler.getInstance().getLibraryInfo().getLexiconsInfo().get(i).getName());
		//}
		try {
			return LexiconHandler.getInstance().getLibraryInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//获取上次背诵的单词的信息
	public String getLastWord()
	{
		try {
			return WordHandler.getInstance().getLastWord();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	//返回模糊查询的单词英文list，最多为5个。
	public List<String> search(String prefix)
	{
		try {
			return WordHandler.getInstance().search(prefix);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//返回此次背诵的情况-词库-背诵个数-正确数目-错误数目-正确率
	public String[] getRecitedInfo()
	{
		String[] info = new String[5];
		info[0] = Lexicon.getInstance().getType();
		info[1] = ReciteState.getInstance().getCount()+"";
		info[2] = ReciteState.getInstance().getCountCorrect()+"";
		info[3] = ReciteState.getInstance().getCount()-ReciteState.getInstance().getCountCorrect()+"";
		info[4] = ReciteState.getInstance().getCountRecited()==0?"0.00":(float)(Math.round(ReciteState.getInstance().getCountCorrect()*10000.00/ReciteState.getInstance().getCountRecited())/100.00)+"";				
		return info;
		
	}
	
	public void startTimer()
	{
		ReciteState.getInstance().startTimer();
	}
}

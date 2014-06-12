package model.bean;

import java.util.Observable;

import controller.object.LibraryInfo;
import model.handler.LexiconHandler;

/*Lexicon object的变量，只要有
 * 
 * entryLexicon：词库入口
 * entryWord:词库中第一个单词入口
 * entryLastWord:上一次背到的单词的入口（默认为第一个单词）
 * countTotal:词库的单词个数
 * countRecited:已经背过的单词的个数
 * countRight：已经背对的单词的个数
 * type:词库类别
 * 
 * */

public class Lexicon extends Observable
{
	private long entryLexicon;
	private long entryWord;
	private long entryLastWord;
	private Integer countTotal;	
	private Integer countRecited;
	private Integer countRight;
	private String type;
	
	/*java的单例模式，确保整个应用程序中只有一个lexicon实例*/
	private volatile static Lexicon instance = null; 

	  public static Lexicon getInstance() { 
	    if (instance == null) { 
	      synchronized (Lexicon.class) { 
	        if(instance == null) { 
	          instance = new Lexicon(-1,-1, -1,-1,-1,-1, ""); 
	        } 
	      } 
	    } 
	    return instance; 
	} 
	  
	public Lexicon(long entryLexicon, long entryWord, long entryLastWord, int countTotal, int countRecited, int countRight,String type)
	{
		this.countRecited = countRecited;
		this.countRight =  countRight;
		this.countTotal = countTotal;
		this.entryLastWord = entryLastWord;
		this.entryLexicon = entryLexicon;
		this.entryWord = entryWord;
		this.type = type;
		
		this.addObserver(LexiconHandler.getInstance());
	}
	
	public void setLexicon(long entryLexicon, long entryWord, long entryLastWord, int countTotal, int countRecited, int countRight,String type)
	{
		this.countRecited = countRecited;
		this.countRight =  countRight;
		this.countTotal = countTotal;
		this.entryLastWord = entryLastWord;
		this.entryLexicon = entryLexicon;
		this.entryWord = entryWord;
		this.type = type;
	}
	
	public void setCountRecited(int count)
	{
		this.countRecited = count;
		this.setChanged();
		this.notifyObservers("countRecited");
	}
	
	public void setCountRight(int count)
	{
		this.countRight = count;
		this.setChanged();
		this.notifyObservers("countRight");
	}
	
	public void setCountTotal(int count)
	{
		this.countTotal = count;
	}
	
	public void setEntryLastWord(long entry)
	{
		//System.out.println("Lexicon"+entry);
		this.entryLastWord = entry;
		this.setChanged();
		this.notifyObservers("entryLastWord");
	}
	
	public void setEntryLexicon(long entry)
	{
		this.entryLexicon = entry;
	}
	
	public void setEntryWord(long entry)
	{
		this.entryWord = entry;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public int getCountTotal()
	{
		return this.countTotal;	
	}
	
	public int getCountRecited()
	{
		return this.countRecited;	
	}
	
	public int getCountRight()
	{
		return this.countRight;	
	}
	
	public long getEntryLexicon()
	{
		return this.entryLexicon;
	}
	
	public long getEntryLastWord()
	{
		return this.entryLastWord;
	}
	
	public long getEntryWord()
	{
		return this.entryWord;
	}
	
	public String getType()
	{
		return this.type;
	}

	public void setCountRightPlus() 
	{
		// TODO Auto-generated method stub
		this.countRight++;
		this.setChanged();
		this.notifyObservers("countRight");
	}

	public void setCountRecitedPlus() {
		// TODO Auto-generated method stub
		this.countRecited++;
		this.setChanged();
		this.notifyObservers("countRecited");
	}
}
package model.bean;

import java.util.Observable;

import controller.object.LibraryInfo;
import model.handler.LexiconHandler;

/*Lexicon object�ı�����ֻҪ��
 * 
 * entryLexicon���ʿ����
 * entryWord:�ʿ��е�һ���������
 * entryLastWord:��һ�α����ĵ��ʵ���ڣ�Ĭ��Ϊ��һ�����ʣ�
 * countTotal:�ʿ�ĵ��ʸ���
 * countRecited:�Ѿ������ĵ��ʵĸ���
 * countRight���Ѿ����Եĵ��ʵĸ���
 * type:�ʿ����
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
	
	/*java�ĵ���ģʽ��ȷ������Ӧ�ó�����ֻ��һ��lexiconʵ��*/
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
package model.bean;

import java.util.HashMap;
import java.util.Observable;

import model.handler.WordHandler;

/*Word object的变量，只要有
 * 
 * entry: 当前处理的word的entry 
 * state: 当前处理的word的状态
 * english：word的英文释义
 * chinese：word的中文释义
 * 
 * */

public class Word extends Observable
{
	private long entry;
	private String english;    
	private String chinese;
	private Integer state;
	
	/*java的单例模式，确保整个应用中只有一个实例*/
	private volatile static Word instance = null; 

	public static Word getInstance() { 
	   if (instance == null) { 
	     synchronized (Word.class) { 
	        if(instance == null) { 
	          instance = new Word(-1,-1,"",""); 
	        } 
	      } 
	    } 
	    return instance; 
	 } 
	 
	
	public Word(long entry, int state, String english, String chinese) 
	{
		this.chinese = chinese;
		this.english = english;
		this.entry = entry;
		this.state = state;  
		 
		this.addObserver(WordHandler.getInstance());
	}    
	
	 public void setWord(long entry, int state, String english, String chinese) 
	{
		this.chinese = chinese;
		this.english = english;
		this.entry = entry;
		this.state = state;  
	}
	 
	 public long getEntry() 
	 {    
	     return this.entry;    
	 }
	 
	 public String getEnglish() 
	 {    
	     return this.english;    
	 }
	 
	 public String getChinese()
	 {
		 return this.chinese;
	 }
	 
	 public Integer getState()
	 {
		 this.notifyObservers("state");
		 return this.state;
	 }
	 
	 public void setEntry(long entry)
	 {
		 this.entry = entry;
	 }
	 
	 public void setEnglish(String english)
	 {
		 this.english = english;
	 }
	 
	 public void setChinese(String chinese)
	 {
		 this.chinese = chinese;
	 }
	 
	 public void setState(Integer state)
	 {
		 
		 this.state = state;
		 
		 /*设置observable的状态为已经改变*/
		 this.setChanged();
		 this.notifyObservers("state");
	 }
	 
	 public String getEnglishMuddled()
	 {
		 int length = (int) Math.ceil(english.length()*1.5);
		 
		 String ret = "";
		 
		 char[] temp = new char[length];
		 
		 int i;
		 for( i=0; i<this.english.length(); i++)
		 {
			 temp[i] = this.english.charAt(i);
		 }
		 for(; i < length ; i++)
		 {
			 temp[i] = (char)((int)(Math.random()*26+97));
		 }
		 for(i-- ; i>-1; i--)
		 {
			 int random = (int)(Math.random()*i);
			 ret = temp[random]+ret;
			 temp[random] = temp[i];
		 }
		 return ret;
	 }
}

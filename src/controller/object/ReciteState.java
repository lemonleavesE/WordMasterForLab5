package controller.object;

import java.util.Date;

/*记录此次背诵状态的object
 * 应该只包含以下几个：
 * 
 * count:此次设置的背诵总数
 * countRecited:此次已经背诵的单词的个数
 * countCorrect:此次背诵正确的单词的个数
 * 
 * */
public class ReciteState 
{
	/*java的单例模式，确保整个应用中只有一个实例*/
	private volatile static ReciteState instance = null; 

	public static ReciteState getInstance() { 
	   if (instance == null) { 
	     synchronized (ReciteState.class) { 
	        if(instance == null) { 
	          instance = new ReciteState(0,0,0); 
	        } 
	      } 
	    } 
	    return instance; 
	 } 
	
	private int count;
	private int countRecited;
	private int countCorrect;
	private long time;

	public ReciteState(int count, int countRecited, int countCorrect) 
	{
		// TODO Auto-generated constructor stub
		this.count = count;
		this.countCorrect = countRecited;
		this.countRecited = countRecited;
		this.time = 0;
	}
	
	public void setReciteState (int count, int countRecited, int countCorrect) 
	{
		this.count = count;
		this.countCorrect = countRecited;
		this.countRecited = countRecited;
		this.time = 0;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
	
	public void setCountRecited(int countRecited)
	{
		this.countRecited = countRecited;
	}
	
	public void setCountCorrect(int countCorrect)
	{
		this.countCorrect = countCorrect;
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	public int getCountRecited()
	{
		return this.countRecited;
	}
	
	public int getCountCorrect()
	{
		return this.countCorrect;
	}

	public void CountRecitedPlus() 
	{
		// TODO Auto-generated method stub
		this.countRecited++;
	}

	public void countCorrectPlus() {
		// TODO Auto-generated method stub
		this.countCorrect++;
	}
	
	//Added for lab5
	//
	public void startTimer()
	{
		this.time = new Date().getTime();
	}
	
	public String getTime()
	{
		if(this.time==0)
		{
			return "00:00";
		}
		long temp = (new Date().getTime()-this.time)/1000;
		return temp/60+":"+(int)temp%60;
	}
}

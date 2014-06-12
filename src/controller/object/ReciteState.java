package controller.object;

import java.util.Date;

/*��¼�˴α���״̬��object
 * Ӧ��ֻ�������¼�����
 * 
 * count:�˴����õı�������
 * countRecited:�˴��Ѿ����еĵ��ʵĸ���
 * countCorrect:�˴α�����ȷ�ĵ��ʵĸ���
 * 
 * */
public class ReciteState 
{
	/*java�ĵ���ģʽ��ȷ������Ӧ����ֻ��һ��ʵ��*/
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

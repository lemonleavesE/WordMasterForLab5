package controller.object;

public class LexiconInfo
{
	private String name;
	private int countTotal;
	private int countRecited;
	private int countRight;
	private int countWrong;
	private float accuracy;
	
	public LexiconInfo(int countTotal,int countRecited, int countRight, int countWrong, float accuracy, String name)
	{
		this.name = name;
		this.countTotal = countTotal;
		this.countRecited = countRecited;
		this.countRight = countRight;
		this.countWrong = countWrong;
		this.accuracy = accuracy;
	}
	
	public String getName()
	{
		return this.name;
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
	public int getCountWrong()
	{
		return this.countWrong;
	}
	
	public float getAccuracy()
	{
		return this.accuracy;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public void setCountTotal(int countTotal)
	{
		this.countTotal = countTotal;
	}
	public void setCountRecited(int countRecited)
	{
		this.countRecited = countRecited;
	}
	public void setCountRight(int countRight)
	{
		this.countRight = countRight;
	}
	public void setCountWrong(int countWrong)
	{
		this.countWrong = countWrong;
	}
	public void setAccuracy(float accuracy)
	{
		this.accuracy = accuracy;
	}
	
}

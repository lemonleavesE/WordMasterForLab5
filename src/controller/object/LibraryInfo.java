package controller.object;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LibraryInfo
{
	private double completionRateAll;
	private double accuracyAll;
	private int countTotal;
	private int countRecited;
	private int countCorrect;
	
	List<LexiconInfo> lexiconsInfo;
	
	public List<LexiconInfo> getLexiconsInfo()
	{
		Collections.sort(lexiconsInfo,new Comparator<LexiconInfo>(){

			@Override
			public int compare(LexiconInfo arg0, LexiconInfo arg1) {
				// TODO Auto-generated method stub
				return arg0.getName().compareTo(arg1.getName());
			}});
		return this.lexiconsInfo;
	}	
	public double getCompletionRateAll()
	{
		return this.completionRateAll;
	}
	public double getAccuracyAll()
	{
		return this.accuracyAll;
	}
	public int getCountTotal()
	{
		return this.countTotal;
	}
	public int getCountRecited()
	{
		return this.countRecited;
	}
	public int getCountCorrect()
	{
		return this.countCorrect;
	}
	public void setCountTotal(int countTotal)
	{
		this.countTotal = countTotal;
	}
	public void setCountRecited(int val)
	{
		this.countRecited = val;
	}
	public void setCountCorrect(int val)
	{
		this.countCorrect = val;
	}
	public void setLexiconsInfo(List<LexiconInfo> val)
	{
		this.lexiconsInfo = val;
	}
	public void setCompletionRateAll(double val)
	{
		this.completionRateAll = val;
	}
	public void setAccuracyAll(double val)
	{
		this.accuracyAll = val;
	}
}

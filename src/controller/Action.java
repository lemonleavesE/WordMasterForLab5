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
 * ��Ҫ��һЩǰ̨�ͺ�̨���õĺ���
 *public boolean chooseLexicon(String type)��
 *-ѡ��˴ʿ⣬typeΪ����Ĵʿ��������ƣ�
 *-�����Ƿ�ѡ��ɹ���
 *
 
 *public int setNum(int num)
 *-ѡ���еĵ��ʸ���,num Ϊ�������Ŀ
 *-ret: ������Ŀ�������ʿ�ʣ�൥�� return 0,
 *		else return �ʿ�ʣ�൥����
 

 *public boolean chooseWord(String type)
 *-ѡ����ʼ���еĵ��ʣ�typeΪ���0ΪĬ�ϵ�һ����1Ϊ�ϴα��еģ�typeҲ�����ǵ��ʵ�Ӣ��
 *	0	-Ĭ����ʼ����
 *	1	-�ϴα����ĵط�
 *	���ʵ�Ӣ��	-�ض����ʣ�typeΪ����
 *-�����Ƿ�ɹ���
 * 
 * public int nextWord(String english)
 * -ѡ����һ������,englishΪ��ǰ���ʵı��н��
 * -ret: 	1	-�����ˣ���ѡ�������һ������
 * 			2	-�����ˣ�������������
 *			3 	-�����ˣ�����û��ѡ����һ������
 *			-1 	-�����ˣ���ѡ����һ������
 *			-2	-�����ˣ���������
 *			-3	-�����ˣ�����û��ѡ����һ������
 * 
 * public String getLastWord()
 * -��ȡ�ϴα��еĵ��ʵ���Ϣ
 * -ret: �ϴεĵ��ʵ�Ӣ��
 * 
 * 	public LexiconInfo getLexiconInfo()
 * -��ȡ��ǰ�ʿ��ͳ����Ϣ
 * -����LexiconInfo
 * 
 * 	public LibraryInfo getLexiconsInfo()
 * -��ȡȫ���ʿ��ͳ����Ϣ
 * -����LibraryInformation
 * 
 * 
 * 	public List<String> search(String prefix)
 * -prefix Ϊģ����ѯ�ĵ���ǰ׺
 * -����ģ����ѯ�ĵ���Ӣ��list�����Ϊ5����
 * 
 * 
 * */
public class Action
{
	/*java�ĵ���ģʽ��ȷ������Ӧ�ó�����ֻ��һ��lexiconʵ��*/
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
	
	/*��ʹ�õķ���*/
	//ѡ��˴ʿ⣬typeΪ����Ĵʿ��������ƣ�
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
	
	//ѡ���еĵ��ʸ���,num Ϊ�������Ŀ
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
	
	//ѡ����ʼ���еĵ���
	//	0	-Ĭ����ʼ����
	//	1	-�ϴα����ĵط�
	//	���ʵ�Ӣ��	-�ض����ʣ�typeΪ����
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
	
	//ѡ����һ������
	//ret: 	1	-�����ˣ���ѡ�������һ������
	//		2	-�����ˣ�������������
	//		3 	-�����ˣ�����û��ѡ����һ������
	//		-1 	-�����ˣ���ѡ����һ������
	//		-2	-�����ˣ���������
	//		-3	-�����ˣ�����û��ѡ����һ������
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
	
	//��ȡ��ǰ�ʿ��ͳ����Ϣ
	//����LexiconInfo
	public LexiconInfo getLexiconInfo()
	{
		float temp =  ((Lexicon.getInstance().getCountRecited()==0?0:(float)(Math.round(Lexicon.getInstance().getCountRight()*10000/Lexicon.getInstance().getCountRecited())/100.0)));
		
		return new LexiconInfo(Lexicon.getInstance().getCountTotal(), Lexicon.getInstance().getCountRecited(),Lexicon.getInstance().getCountRight(),Lexicon.getInstance().getCountRecited()-Lexicon.getInstance().getCountRight(), temp, Lexicon.getInstance().getType());
	}
	
	//��ȡȫ���ʿ��ͳ����Ϣ
	//����LibraryInformation
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
	
	//��ȡ�ϴα��еĵ��ʵ���Ϣ
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
	
	//����ģ����ѯ�ĵ���Ӣ��list�����Ϊ5����
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
	
	//���ش˴α��е����-�ʿ�-���и���-��ȷ��Ŀ-������Ŀ-��ȷ��
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

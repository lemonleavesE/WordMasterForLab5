package model.handler;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.bean.Lexicon;
import model.bean.Word;
import model.configFile.ConfigFile;

/*每当被监听的Word发生改变时调用，主要用来修改用户的user.conf文件*/

public class WordHandler implements Observer
{
	/*java的单例模式，确保整个应用程序中只有一个lexicon实例*/
	private volatile static WordHandler instance = null; 

	  public static WordHandler getInstance() { 
	    if (instance == null) { 
	      synchronized (WordHandler.class) { 
	        if(instance == null) { 
	          instance = new WordHandler(); 
	        } 
	      } 
	    } 
	    return instance; 
	} 
	  
	public void update(Observable word, Object arg1) 
	{
		// TODO Auto-generated method stub
		/*TO　BE　DONE　
		 * 根据Word修改txt文件，使用randomAccess.
		 * */
		if(word instanceof Word)
		{    
			if(arg1.equals("state"))
			{
				try {
					ConfigFile.getInstance().write(((Word) word).getEntry(), 8, 0, 'i', ((Word) word).getState());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        } 
	}
	
	public boolean chooseWord(String type) throws IOException
	{
		RandomAccessFile readerConf = ConfigFile.getInstance().getConf();
		boolean ret = false;
		long wordEntry = 0;
		
		//默认的初始单词，直接从词库中读取单词入口，然后读取第一个单词
		if(type.equals("0"))
		{
			readerConf.seek(Lexicon.getInstance().getEntryWord());
			
			//设置Word
			wordEntry = readerConf.readLong();
			Word.getInstance().setWord(wordEntry, readerConf.readInt(), readerConf.readUTF(), readerConf.readUTF());
			//Word.getInstance().setChinese(Word.getInstance().getChinese());
			
			ret = true;
		}
		
		//
		else if(type.equals("1"))
		{
			readerConf.seek(Lexicon.getInstance().getEntryLastWord());
			
			//设置Word
			wordEntry = readerConf.readLong();
			Word.getInstance().setWord(wordEntry, readerConf.readInt(), readerConf.readUTF(), readerConf.readUTF());
			//Word.getInstance().setChinese(Word.getInstance().getChinese());
			
			ret = true;
		}
		
		else if(type.equals("3"))
		{
			readerConf.seek(Word.getInstance().getEntry());
			readerConf.skipBytes(12);
			readerConf.readUTF();
			readerConf.readUTF();
			
			long tempEntry = readerConf.readLong();
			int tempState = readerConf.readInt();
			String tempEnglish = readerConf.readUTF();
			String tempChinese = readerConf.readUTF();
			if(tempEnglish.startsWith("x"))
				System.out.println(tempEnglish);
			if(tempEnglish.startsWith(Lexicon.getInstance().getType()))
			{
				
				Word.getInstance().setWord(tempEntry, tempState, tempEnglish, tempChinese);
				//Word.getInstance().setChinese(tempChinese);
			}
			else
			{
				readerConf.seek(Lexicon.getInstance().getEntryWord());
				Word.getInstance().setWord(readerConf.readLong(), readerConf.readInt(), readerConf.readUTF(), readerConf.readUTF());
				//Word.getInstance().setChinese(tempChinese);
			}
			
			ret = true;
		}
		
		else
		{
			readerConf.seek(Lexicon.getInstance().getEntryWord());
			int count = Lexicon.getInstance().getCountTotal();
			while(count>0)
			{
				long tempEntry = readerConf.readLong();
				int tempState = readerConf.readInt();
				String tempEnglish = readerConf.readUTF();
				String tempChinses  = readerConf.readUTF();
				
				if(tempEnglish.equals(type))
				{
					Word.getInstance().setWord(tempEntry, tempState, tempEnglish, tempChinses);
					//Word.getInstance().setChinese(tempChinses);
					ret =  true;
					break;
				}
				
				count--;
			}
		}
		
		
		return ret;
	}

	public String getLastWord() throws IOException 
	{
		// TODO Auto-generated method stub
		RandomAccessFile readerConf = ConfigFile.getInstance().getConf();
		readerConf.seek(Lexicon.getInstance().getEntryLastWord()+12);
		return readerConf.readUTF();
	}

	public List<String> search(String prefix) throws IOException {
		// TODO Auto-generated method stub
		List<String> temp = new ArrayList<String>();
		RandomAccessFile readerConf = ConfigFile.getInstance().getConf();
		readerConf.seek(Lexicon.getInstance().getEntryWord());
		int count = Lexicon.getInstance().getCountTotal();
		int num  = 0;
		String word = "";
		while(count>0 && num<5)
		{
			readerConf.skipBytes(12);
			word =  readerConf.readUTF();
			readerConf.readUTF();
			if(word.startsWith(prefix))
			{
				temp.add(word);
				num++;
			}
			count--;
		}
		return temp;
	}
}

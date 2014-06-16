package model.configFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConfGeneratorForXML {
	
	private static DicXMLHandler dxHandler;
	
	public static void main(String []arg) throws Exception
	{
		generateConfig();
	}
	
	private static void initConfig()
	{
		Map<String, String> tagMap = new HashMap<String, String>();
		tagMap.put("list", "word");
		tagMap.put("english", "english");
		tagMap.put("chinese", "chinese");
		dxHandler = new DicXMLHandler("dictionary.xml",tagMap);
	}
	
	public static void generateConfig() throws Exception
	{
		initConfig();
		
		Map<String, LexiconTemp> lexMap = dxHandler.HandleXMLFlow();
		
		
		
		File confFile = new File("user.conf");
		
		RandomAccessFile readerConf = null;
		
		Map<String,Long> lexiconPointerMap = new HashMap<String,Long>(); 
		
		if(!confFile.exists())
		{
			confFile.createNewFile();
		}
		
		
		readerConf = new RandomAccessFile(confFile,"rws");
		readerConf.seek(0);
		
		readerConf.writeLong(0);/** The entry of dictionary */
		
		Iterator<Entry<String, LexiconTemp>>  it1 = lexMap.entrySet().iterator();
		while (it1.hasNext())
		{
			Entry<String, LexiconTemp> entry = (Entry<String, LexiconTemp>) it1.next();
			String key = entry.getKey();
			LexiconTemp lexicon = entry.getValue();
			Integer size = lexicon.wordList.size();
			
			long lexiconPoint = readerConf.getFilePointer();
			
			lexiconPointerMap.put((String) key,lexiconPoint);
			
			
			readerConf.writeLong(lexiconPoint);		/**词库入口*/
			readerConf.writeLong(0);				/**词库第一个单词入口*/
			readerConf.writeLong(0);				/**词库上次背到单词的入口*/
			readerConf.writeInt(size);				/**词库含有的单词个数*/
			readerConf.writeInt(0);					/**词库已经背的单词个数*/
			readerConf.writeInt(0);					/**词库已经背对的单词个数*/
			readerConf.writeUTF((String) key);		/**词库类别*/
			
		}
		
		long tempPoint = readerConf.getFilePointer(); 
		readerConf.seek(0);
		readerConf.writeLong(tempPoint);
		
		//开始写词语信息
		readerConf.seek(tempPoint);
		
		
		Iterator<Entry<String, LexiconTemp>>  it2 = lexMap.entrySet().iterator();
		while (it2.hasNext())
		{
			Entry<String, LexiconTemp> entry = (Entry<String, LexiconTemp>) it2.next();
			String key = entry.getKey();
			LexiconTemp lexicon = entry.getValue();
			
			tempPoint = readerConf.getFilePointer();
			
			readerConf.seek(lexiconPointerMap.get(key)+8);
				
			readerConf.writeLong(tempPoint);	/**词库第一个单词入口写入*/
			readerConf.writeLong(tempPoint);	/**词库最后一次背诵的单词入口写入*/
				
				
			readerConf.seek(tempPoint);			/**返回当前单词的入口，继续操作*/
			lexiconPointerMap.keySet().remove(lexiconPointerMap.get(key));
			
			System.out.println(key);
			
			List<WordTemp> wordList = lexicon.wordList;
			
			int wListLength = wordList.size();
			
			for(int i = 0;i < wListLength;i++)
			{
				WordTemp wt = wordList.get(i);
				readerConf.writeLong(readerConf.getFilePointer());	/**写入entry*/
				readerConf.writeInt(0);								/**写入状态*/
				readerConf.writeUTF(wt.english);					/**写入英文释义*/
				readerConf.writeUTF(wt.chinese);					/**写入中文释义*/
			}						
		} 
		
		
		
		
		/**
		 * @param testing_code
		 * 	
		 * 	readerConf.seek(0);
		 * 	long temp = readerConf.readLong();
		 *	while(readerConf.getFilePointer()<temp)
		 *	{
		 *		System.out.print(readerConf.readLong()+"-");
		 *		System.out.print(readerConf.readLong()+"-");
		 *		System.out.print(readerConf.readLong()+"-");
		 *		System.out.print(readerConf.readInt()+"-");
		 *		System.out.print(readerConf.readInt()+"-");
		 *		System.out.print(readerConf.readInt()+"-");
		 *		System.out.println(readerConf.readUTF());
		 *	}
		 *	while(readerConf.getFilePointer()<readerConf.length())
		 *	{
		 *		System.out.print(readerConf.readLong()+"-");
		 *		System.out.print(readerConf.readInt()+"-");
		 *		System.out.print(readerConf.readUTF()+"-");
		 *		System.out.println(readerConf.readUTF());
		 *	}
		 *	
		 *
		 *
		 *	@param lexiconConfig
		 *	8-623-623-1-0-0-nn
			48-654-654-26-0-0-other
			91-1442-1442-1-0-0-ad
			131-1493-1493-1646-0-0-adj
			172-73891-73891-15-0-0-conj
			214-74504-74504-42-0-0-num
			255-75972-75972-4281-0-0-n
			294-239112-239112-9-0-0-int
			335-239403-239403-1-0-0-v,,n
			377-239435-239435-3-0-0-]n
			417-239541-239541-329-0-0-adv
			458-253621-253621-1-0-0-null
			500-253642-253642-46-0-0-pron
			542-255393-255393-1532-0-0-v
			581-316091-316091-56-0-0-prep

		 *	
		 *
		 */
		readerConf.close();
		
	}
}

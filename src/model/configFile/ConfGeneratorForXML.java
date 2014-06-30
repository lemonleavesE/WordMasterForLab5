package model.configFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConfGeneratorForXML {
	
	private DicXMLHandler dxHandler;
	
	public static void main(String []arg) throws Exception
	{
		String [] list = new ConfGeneratorForXML().getLexiconList();
		for(int i = 0;i < list.length;i++)
		{
			System.out.println(list[i]);
		}
		new ConfGeneratorForXML().generateConfig();
	}
	
	public ConfGeneratorForXML()
	{
		Map<String, String> tagMap = new HashMap<String, String>();
		tagMap.put("list", "word");
		tagMap.put("english", "english");
		tagMap.put("chinese", "chinese");
		dxHandler = new DicXMLHandler("dictionary.xml",tagMap);
	}
	
	public void generateConfig() throws Exception
	{
		
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
			
			
			readerConf.writeLong(lexiconPoint);		/**�ʿ����*/
			readerConf.writeLong(0);				/**�ʿ��һ���������*/
			readerConf.writeLong(0);				/**�ʿ��ϴα������ʵ����*/
			readerConf.writeInt(size);				/**�ʿ⺬�еĵ��ʸ���*/
			readerConf.writeInt(0);					/**�ʿ��Ѿ����ĵ��ʸ���*/
			readerConf.writeInt(0);					/**�ʿ��Ѿ����Եĵ��ʸ���*/
			readerConf.writeUTF((String) key);		/**�ʿ����*/
			
		}
		
		long tempPoint = readerConf.getFilePointer(); 
		readerConf.seek(0);
		readerConf.writeLong(tempPoint);
		
		//��ʼд������Ϣ
		readerConf.seek(tempPoint);
		
		
		Iterator<Entry<String, LexiconTemp>>  it2 = lexMap.entrySet().iterator();
		while (it2.hasNext())
		{
			Entry<String, LexiconTemp> entry = (Entry<String, LexiconTemp>) it2.next();
			String key = entry.getKey();
			LexiconTemp lexicon = entry.getValue();
			
			tempPoint = readerConf.getFilePointer();
			
			readerConf.seek(lexiconPointerMap.get(key)+8);
				
			readerConf.writeLong(tempPoint);	/**�ʿ��һ���������д��*/
			readerConf.writeLong(tempPoint);	/**�ʿ����һ�α��еĵ������д��*/
				
				
			readerConf.seek(tempPoint);			/**���ص�ǰ���ʵ���ڣ���������*/
			lexiconPointerMap.keySet().remove(lexiconPointerMap.get(key));
			
			List<WordTemp> wordList = lexicon.wordList;
			
			int wListLength = wordList.size();
			
			for(int i = 0;i < wListLength;i++)
			{
				WordTemp wt = wordList.get(i);
				readerConf.writeLong(readerConf.getFilePointer());	/**д��entry*/
				readerConf.writeInt(0);								/**д��״̬*/
				readerConf.writeUTF(wt.english);					/**д��Ӣ������*/
				readerConf.writeUTF(wt.chinese);					/**д����������*/
			}						
		} 
		
		
		readerConf.seek(0);
		long temp = readerConf.readLong();
		while(readerConf.getFilePointer()<temp)
		{
				System.out.print(readerConf.readLong()+"-");
		 		System.out.print(readerConf.readLong()+"-");
		 		System.out.print(readerConf.readLong()+"-");
		 		System.out.print(readerConf.readInt()+"-");
		 		System.out.print(readerConf.readInt()+"-");
		 		System.out.print(readerConf.readInt()+"-");
		 		System.out.println(readerConf.readUTF());
		 }
		int i = 0;
		while(i < 10)
			 {
			 		System.out.print(readerConf.readLong()+"-");
			 		System.out.print(readerConf.readInt()+"-");
			 		System.out.print(readerConf.readUTF()+"-");
			 		System.out.println(readerConf.readUTF());
			 		i++;
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
		 *	8-334-334-342-0-0-adv
			49-14981-14981-47-0-0-pron
			91-16774-16774-1-0-0-aux
			132-16818-16818-1605-0-0-v
			171-80432-80432-1679-0-0-adj
			212-154255-154255-42-0-0-num
			253-155723-155723-58-0-0-prep
			295-158008-158008-4445-0-0-n
			
			334-0-aboard-adv.��(��)��
			371-0-abreast-adv.���磬����
			413-0-abroad-adv.���⣬����
			454-0-abruptly-adv.ͻȻ��
			491-0-absolutely-adv.���ԣ���ȫ
			536-0-accidentally-adv.ż��������
			583-0-actively-adv.�����أ���Ծ��
			632-0-actually-adv.ʵ���ϣ���Ȼ
			678-0-adequately-adv.ǡ����
			717-0-admittedly-adv.���׵�
		 *	
		 *
		 */
		readerConf.close();
		
	}
	
	public String[] getLexiconList()
	{
		return dxHandler.getLexiconList();
	}
}

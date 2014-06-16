package model.configFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ConfGenerator 
{
	
	public static void main(String [] a) throws IOException
	{
		generateConfig();
	}
	
	@SuppressWarnings("rawtypes")
	public static void generateConfig() throws IOException
	{
		File confFile = new File("user.conf");
		
		if(!confFile.exists())
		{
			confFile.createNewFile();
		}
		
		BufferedReader readerDict = new BufferedReader(new FileReader("dictionary.txt"));
		RandomAccessFile readerConf = new RandomAccessFile(confFile,"rw");
		
		String lineTemp="";
	
		//��ȡdictionary.txt��Ϊ���ôʿ���׼��
		Map<String,Integer> lexiconConf = new HashMap<String,Integer>(); 
		Map<String,Long> lexiconTemp = new HashMap<String,Long>(); 
		
		String type = "";
		int count = 0;
		
		while((lineTemp = readerDict.readLine())!=null)
		{
			if(lineTemp.startsWith(type)&&type.length()>0)
			{
				count++;
			}
			else
			{
				if(type.length()>0)
				{
					lexiconConf.put(type,count);
				}
				type = lineTemp.substring(0, 1);
				count=1;
			}
		} 
		if(type.length()>0)
		{
			lexiconConf.put(type,count);
		}
		readerDict.close();
		
		
		readerConf.seek(0);
		
		//д�ʿ�ʹ���������Ϣ�ķָ��
		readerConf.writeLong(0);
		//д��ʿ���Ϣ
		Iterator iter = lexiconConf.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			
			
			long lexiconPoint = readerConf.getFilePointer();
			lexiconTemp.put((String) key,lexiconPoint);
			
			
			readerConf.writeLong(lexiconPoint);		//�ʿ����
			readerConf.writeLong(0);				//�ʿ��һ���������
			readerConf.writeLong(0);				//�ʿ��ϴα������ʵ����
			readerConf.writeInt((Integer) val);		//�ʿ⺬�еĵ��ʸ���
			readerConf.writeInt(0);					//�ʿ��Ѿ����ĵ��ʸ���
			readerConf.writeInt(0);					//�ʿ��Ѿ����Եĵ��ʸ���
			readerConf.writeUTF((String) key);		//�ʿ����
		}
		
		long tempPoint = readerConf.getFilePointer(); 
		readerConf.seek(0);
		readerConf.writeLong(tempPoint);
		
		//��ʼд������Ϣ
		readerConf.seek(tempPoint);
		readerDict.close();
		readerDict = new BufferedReader(new FileReader("dictionary.txt"));
		while((lineTemp = readerDict.readLine())!=null)
		{
			String[] info = lineTemp.split("   ");
			
			if(info.length!=2)
			{
				continue;
			}
			
			if(lexiconTemp.keySet().contains(info[0].substring(0,1)))
			{
				tempPoint = readerConf.getFilePointer();
				readerConf.seek(lexiconTemp.get(info[0].substring(0,1))+8);
				
				readerConf.writeLong(tempPoint);	//�ʿ��һ���������д��
				readerConf.writeLong(tempPoint);	//�ʿ����һ�α��еĵ������д��
				
				
				readerConf.seek(tempPoint);			//���ص�ǰ���ʵ���ڣ���������
				lexiconTemp.keySet().remove(info[0].substring(0,1));
			}
			
			
			readerConf.writeLong(readerConf.getFilePointer());	//д��entry
			readerConf.writeInt(0);								//д��״̬
			readerConf.writeUTF(info[0]);						//д��Ӣ������
			readerConf.writeUTF(info[1]);						//д����������
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
		while(readerConf.getFilePointer()<readerConf.length())
		{
			System.out.print(readerConf.readLong()+"-");
			System.out.print(readerConf.readInt()+"-");
			System.out.print(readerConf.readUTF()+"-");
			System.out.println(readerConf.readUTF());
		}
		
		readerConf.close();
		readerDict.close();
	}
}
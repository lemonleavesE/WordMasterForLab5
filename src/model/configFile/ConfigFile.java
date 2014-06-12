package model.configFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ConfigFile
{
	/*java的单例模式，确保整个应用程序中只有一个lexicon实例*/
	private volatile static ConfigFile instance = null; 

	public static ConfigFile getInstance() throws FileNotFoundException { 
	    if (instance == null) { 
	      synchronized (ConfigFile.class) { 
	        if(instance == null) { 
	          instance = new ConfigFile(); 
	        } 
	      } 
	    } 
	    return instance; 
	}
	
	private File confFile = new File("user.conf");
	private RandomAccessFile readerConf;
	
	public ConfigFile() throws FileNotFoundException
	{
		readerConf = new RandomAccessFile(confFile,"rws");
	}
	
	public RandomAccessFile getConf() throws FileNotFoundException
	{
		return readerConf;
	}
	
	//i-int,l-long,u-utf
	public void write(long seekNum, int skipNum, int skipUtfNum, char type, Object value) throws IOException
	{
		//System.out.print("write: "+type+":");
		//System.out.println(value);
		try
		{
		readerConf.seek(seekNum+skipNum);
		}catch(Exception e){}
		while(skipUtfNum>0)
		{
			readerConf.readUTF();
		}
		
		switch(type)
		{
			case 'i':
				readerConf.writeInt((int)value);
				break;
				
			case 'l':
				readerConf.writeLong((long)value);
				break;
			
			case 'u':
				readerConf.writeUTF((String)value);
				break;
		}
		
		return;
	}
	/*
	 * 8-1022-1022-562-0-0-a
47-23624-23624-397-0-0-b
86-38688-38688-771-0-0-c
125-70428-70428-526-0-0-d
164-91652-91652-416-0-0-e
203-108940-108940-376-0-0-f
242-124342-124342-210-0-0-g
281-132805-132805-273-0-0-h
320-143598-143598-374-0-0-i
359-159693-159693-58-0-0-j
398-161907-161907-45-0-0-k
437-163585-163585-249-0-0-l
476-173635-173635-411-0-0-m
515-190424-190424-154-0-0-n
554-196724-196724-200-0-0-o
593-205006-205006-644-0-0-p
632-231358-231358-35-0-0-q
671-232865-232865-454-0-0-r
710-252044-252044-907-0-0-s
749-288542-288542-404-0-0-t
788-304655-304655-134-0-0-u
827-310445-310445-129-0-0-v
866-315520-315520-225-0-0-w
905-324451-324451-2-0-0-x
944-324526-324526-22-0-0-y
983-325337-232-11-0-0-z
	 * */
}
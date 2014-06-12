package model.handler;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.object.LexiconInfo;
import controller.object.LibraryInfo;
import model.bean.Lexicon;
import model.configFile.ConfigFile;

public class LexiconHandler implements Observer
{
	private volatile static LexiconHandler instance = null; 

	  public static LexiconHandler getInstance() { 
	    if (instance == null) { 
	      synchronized (LexiconHandler.class) { 
	        if(instance == null) { 
	          instance = new LexiconHandler(); 
	        } 
	      } 
	    } 
	    return instance; 
	}
	 
	  @Override
	  public void update(Observable arg0, Object arg1) 
		{
		  //System.out.println("update"+(arg0 instanceof Lexicon)+":"+arg1.equals("entryLastWord")+Lexicon.getInstance().getEntryLastWord());
			// TODO Auto-generated method stub
			if(arg0 instanceof Lexicon)
			{    
				if(arg1.equals("entryLastWord"))
				{
					try {
						//System.out.println("entryLastWord"+Lexicon.getInstance().getEntryLastWord());
						ConfigFile.getInstance().write(((Lexicon) arg0).getEntryLexicon(), 16, 0, 'l', Lexicon.getInstance().getEntryLastWord());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(arg1.equals("countRight"))
				{
					try {
						ConfigFile.getInstance().write(((Lexicon) arg0).getEntryLexicon(), 32, 0, 'i', (int)((Lexicon) arg0).getCountRight());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(arg1.equals("countRecited"))
				{
					try {
						ConfigFile.getInstance().write(((Lexicon) arg0).getEntryLexicon(), 28, 0, 'i',(int) ((Lexicon) arg0).getCountRecited());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        }
		}
	  
	public boolean chooseLexicon(String type) throws IOException
	{
		RandomAccessFile readerConf = ConfigFile.getInstance().getConf();
		readerConf.seek(0);
		
		long boundry = readerConf.readLong();
		long tempPoint = 0;
		String tempType = "";
		
		while(readerConf.getFilePointer()<boundry)
		{
				tempPoint = readerConf.readLong();
				readerConf.skipBytes(28);
				tempType = readerConf.readUTF();
				
				if(tempType.equals(type))
				{
					readerConf.seek(tempPoint);
					Lexicon.getInstance().setLexicon(readerConf.readLong(), readerConf.readLong(), readerConf.readLong(), readerConf.readInt(), readerConf.readInt(), readerConf.readInt(), readerConf.readUTF());
					return true;
				}
		}
		
		return false;
	}

	public LibraryInfo getLibraryInfo() throws IOException{
		// TODO Auto-generated method stub
		LibraryInfo temp = new LibraryInfo();
		List<LexiconInfo> infos = new ArrayList<LexiconInfo>();
		
		RandomAccessFile readerConf = ConfigFile.getInstance().getConf();
		
		int countTotal = 0;
		int countRecited = 0;
		int countCorrect = 0;
		
		readerConf.seek(0);
		long boundry = readerConf.readLong();
		while(readerConf.getFilePointer()<boundry)
		{
			readerConf.skipBytes(24);
			int total = readerConf.readInt();
			int recited = readerConf.readInt();
			int correct = readerConf.readInt();
			String name = readerConf.readUTF();
			countTotal += total;
			countRecited += recited;
			countCorrect += correct;
			LexiconInfo infoTemp = new LexiconInfo(total, recited, correct, recited-correct, (float) (recited==0?0.00:(float)(Math.round(correct*10000/recited)/100.0)), name);
			infos.add(infoTemp);
		}
		temp.setAccuracyAll(countRecited==0?0.00:Math.round(countCorrect*10000/countRecited)/100.0);
		temp.setCompletionRateAll(countTotal==0?0.00:Math.round(countRecited*10000/countTotal)/100.0);
		temp.setLexiconsInfo(infos);
		temp.setCountCorrect(countCorrect);
		temp.setCountRecited(countRecited);
		temp.setCountTotal(countTotal);
		return temp;
	}
}
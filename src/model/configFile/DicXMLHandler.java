package model.configFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DicXMLHandler {

	private String dicXMLPath;
	private Map<String, String> tagMap;
	
	public static void main(String []arg)
	{
		Map<String, String> tagMap = new HashMap<String, String>();
		tagMap.put("list", "word");
		tagMap.put("english", "english");
		tagMap.put("chinese", "chinese");
		
		//new DicXMLHandler("dictionary.xml",tagMap).HandleXMLFlow();
		
		new DicXMLHandler("dictionary.xml",tagMap).splitCharacteristic("n.,adj.,v.平面图,好图");
	}
	
	public DicXMLHandler(String dicXMLPath, Map<String, String> tagMap)
	{
		this.dicXMLPath = dicXMLPath;
		this.tagMap = tagMap;
	}
	
	public Map<String, LexiconTemp> HandleXMLFlow()
	{
		// TODO Auto-generated method stub
        /**
         * 从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个Document
         * */
        DocumentBuilder db = null;
        Document document = null;
        NodeList wordList = null;
        FileInputStream in = null;
        
		Map<String, LexiconTemp> lexMap = new HashMap<String, LexiconTemp>();
        
        try {
        	db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			in = new FileInputStream(dicXMLPath);
			document = db.parse(in);
			wordList = document.getElementsByTagName(tagMap.get("list"));   
	        in.close();
	        
	        int list_length = wordList.getLength();
	        
	        String englishtag = tagMap.get("english");
	        String chinesetag = tagMap.get("chinese");
	        
	        for(int node_number = 0;node_number < list_length;node_number++)
	        {
	        	
	        	String english = null;
	        	String chinese = null;
	        	String lexName = null;
	        	NodeList wordContent = wordList.item(node_number).getChildNodes();
	        	
	        	for(int content_list = 0;content_list < wordContent.getLength();content_list++)
	        	{
	        		Node contentNode = wordContent.item(content_list);
	        		
	        		/**
	        		 * Fetch the english part
	        		 * */
	        		if(contentNode.getNodeType() == Node.ELEMENT_NODE && contentNode.getNodeName().equals(englishtag))
	        		{
	        			english = contentNode.getTextContent();
	        		}
	        		/**
	        		 * Fetch the chinese part
	        		 * */
	        		else if(contentNode.getNodeType() == Node.ELEMENT_NODE && contentNode.getNodeName().equals(chinesetag))
	        		{
	        			chinese = contentNode.getTextContent();
	        		}
	        	}
	        	
	        	List<String> charList = splitCharacteristic(chinese);
	        	
	        	for(int i = 0;i < charList.size();i++){
	        	
	        	lexName = charList.get(i);	
	        		
	        	if(lexMap.containsKey(lexName))
	        	{
	        		LexiconTemp temp = lexMap.get(lexName);
	        		WordTemp tempW = new WordTemp();
	        		tempW.chinese = chinese;
	        		tempW.english = english;
	        		temp.wordList.add(tempW);
	        		lexMap.put(lexName, temp);
	        	}
	        	else
	        	{
	        		LexiconTemp temp = new LexiconTemp();
	        		temp.wordList = new ArrayList<WordTemp>();
	        		WordTemp tempW = new WordTemp();
	        		tempW.chinese = chinese;
	        		tempW.english = english;
	        		temp.wordList.add(tempW);
	        		temp.lexName = lexName;
	        		lexMap.put(lexName, temp);
	        	}
	        	}
	        }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(e instanceof java.io.IOException)
			{
				System.out.println("File not found!");
			}
			else if(e instanceof NullPointerException)
			{
				System.out.println("The Map didn't be initialized!");
			}
			else
				e.printStackTrace();
		}
        return lexMap;
	}
	
	private List<String> splitCharacteristic(String chinese)
	{
		List<String> charList = new ArrayList<String>();
		
		String []tempArray = chinese.split("\\.,");
		
		int length = tempArray.length;
		if(length > 1)
		for(int i = 0;i < length-1;i++)
		{
			charList.add(tempArray[i]);
		}
		
		String []tempArray1 = tempArray[length-1].split("\\.");
		
		charList.add(tempArray1[0]);
		
		//for(int i = 0;i < charList.size();i++)
		//{
		//	System.out.println(charList.get(i));
		//}
		
		return charList;
	}
	
}

class LexiconTemp
{
	public String lexName;
	List<WordTemp> wordList;
}

class WordTemp
{
	public String english;
	public String chinese;
}
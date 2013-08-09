package com.iitb.clicker;


import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class XmlResultReader {
	
	String TotalQues;
	String CorrectCount;
	String TotalMarks;
	String MarksObtained;
	String [] AnsMarked; 
	String [] CorrectAns;
	public void xmlToData(String xmlData)
	{
		
		try {			
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlData));
			Document doc = db.parse(is);
			NodeList e1= (NodeList)doc.getElementsByTagName("ques");
			AnsMarked=new String[e1.getLength()];
			CorrectAns=new String[e1.getLength()];
			for(int i=0;i<e1.getLength();i++)
			{
				NodeList nl=e1.item(i).getChildNodes();
				AnsMarked[i]=nl.item(0).getTextContent();
				CorrectAns[i]=nl.item(1).getTextContent();
			}
			NodeList e2=(NodeList)doc.getElementsByTagName("total_ques");
			TotalQues=e2.item(0).getTextContent();
			NodeList e3=(NodeList)doc.getElementsByTagName("correct_count");
			CorrectCount=e3.item(0).getTextContent();
			NodeList e4=(NodeList)doc.getElementsByTagName("total_marks");
			TotalMarks=e4.item(0).getTextContent();
			NodeList e5=(NodeList)doc.getElementsByTagName("marks_obtained");
			MarksObtained=e5.item(0).getTextContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e);
		}	
		
}
	public static String AnsToXml(int qid,String ... answers)
	{
		StringBuilder data=new StringBuilder();
		data.append("<answer id='"+qid+"'>");
		int i=1;
		for(String opt:answers)
		{
			data.append("<option"+i+">"+opt+"</option"+i+">");
			i++;
		}
		data.append("</answers>");
		return data.toString();
		
	}
}

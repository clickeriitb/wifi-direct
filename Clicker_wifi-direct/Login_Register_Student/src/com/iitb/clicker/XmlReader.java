package com.iitb.clicker;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;


public class XmlReader {
	String qid;
	String Type;
	String question;
	String [] options;
	String time,quiz_id;
	int c;
	
	public void xmlToData(String xmlData,int no)
	{
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlData));
			Document doc = db.parse(is);
			NodeList data= (NodeList)doc.getElementsByTagName("data");
			NamedNodeMap attrs1 = data.item(0).getAttributes(); 
		    quiz_id=((Attr) attrs1.item(0)).getValue();
		    time=((Attr) attrs1.item(1)).getValue();
		    
			NodeList e= (NodeList)doc.getElementsByTagName("question");
			NodeList nl=e.item(no).getChildNodes();
			
			NamedNodeMap attrs = e.item(no).getAttributes(); 
		    qid=((Attr) attrs.item(0)).getValue();
		    Type=((Attr) attrs.item(1)).getValue();
		    
		    question=nl.item(0).getTextContent();
		    options=new String[nl.getLength()-1];
			for(int i=0;i<nl.getLength()-1;i++){
			   options[i]=nl.item(i+1).getTextContent();
			
			 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e);
		}	
   }
	public void count(String xmlData)
	{  
		try{
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xmlData));
		Document doc = db.parse(is);
		NodeList e= (NodeList)doc.getElementsByTagName("question");
		c=e.getLength();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		
		
	}
}

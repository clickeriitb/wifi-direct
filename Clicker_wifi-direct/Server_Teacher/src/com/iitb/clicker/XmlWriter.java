package com.iitb.clicker;
public class XmlWriter {
	public static String InitialiseData(int quiz_id,String time)
	{
		String data="<data quiz_id='"+quiz_id+"' time='"+time+"'>";
		return data;
	}
	public static String DataToXml(int id,char type,String Ques,String ... options)
	{
		StringBuilder data=new StringBuilder();
		data.append("<question id='"+id+"' type='"+type+"'>");
		data.append("<ques>"+Ques+"</ques>");
		int i=1;
		for(String opt:options)
		{
			data.append("<option"+i+">"+opt+"</option"+i+">");
			i++;
		}
		data.append("</question>");
		return data.toString();
	}
	public static String FinalData()
	{
		return "</data>";
	}
	public static void InitializeResultXml(StringBuilder s)
	{
		s.append("~<result><report>");
	}
	public static void addQuesReport(StringBuilder s,String Marked, String Correct)
	{
		s.append("<ques><marked>");
		s.append(Marked);
		s.append("</marked><correct>");
		s.append(Correct);
		s.append("</correct></ques>");
	}
	public static void addResultSummary(StringBuilder s,int totelQues,int correctCount,int totelMarks,int marksObtained)
	{
		s.append("</report>");
		s.append("<total_ques>");
		s.append(totelQues);
		s.append("</total_ques>");
		s.append("<correct_count>");
		s.append(correctCount);
		s.append("</correct_count>");
		s.append("<total_marks>");
		s.append(totelMarks);
		s.append("</total_marks>");
		s.append("<marks_obtained>");
		s.append(marksObtained);
		s.append("</marks_obtained>");
	}
	public static void endResultXml(StringBuilder s)
	{
		s.append("</result>");
	}
	
	
}

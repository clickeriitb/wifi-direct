package com.iitb.clicker;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {

	private static final String DATABASE_NAME="clicker_db";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_TABLE1 = "STUDENT";
    private static final String DATABASE_TABLE2 = "LOGIN";
    private static final String DATABASE_TABLE3 = "SECURITY";
    String[] RollList,NameList;
    private Cursor mCursor1,mCursor2,mCursor3;
	
	private DbHelper ourHelper;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context,DATABASE_NAME , null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public Database(Context c)
	{
		ourHelper=new DbHelper(c);
		ourDatabase=ourHelper.getWritableDatabase();
	}
	
	public void close()
	{
		ourHelper.close();
	}
	
	public void getResultData(int QUIZ_ID,String Class,String Section)
	{
		String [] studentColumn={"STUD_ID","IP_ADDR"};
		String [] data1={Class,Section};
		Cursor c1=ourDatabase.query("STUDENT",studentColumn, "CLASS=? and SECTION=? and IP_ADDR!=0",data1,null,null,null);
		int Sindex=c1.getColumnIndex(studentColumn[0]);
		int Ipindex=c1.getColumnIndex(studentColumn[1]);
		
		while(c1.moveToNext())
		{
			int STUD_ID=c1.getInt(Sindex);
			String IP_ADDR=c1.getString(Ipindex);
			StringBuilder resultData=new StringBuilder();
			XmlWriter.InitializeResultXml(resultData);
			int CorrectCount=0;
			int MarksObtained=0;
			int totalQuesCount=0;
			int totelMarks=0;
			
			String [] QQColumn={"Q_ID","MARKS"};
			String [] data4={String.valueOf(QUIZ_ID)};
			Cursor c4=ourDatabase.query("QQ_MAP",QQColumn, "QUIZ_ID=?",data4,null,null,"Q_ID");
			totalQuesCount=c4.getCount();
			Log.i("Question Count",totalQuesCount+"" );
			while(c4.moveToNext())
			{
			int Q_ID=c4.getInt(c4.getColumnIndex(QQColumn[0]));
			int Marks=c4.getInt(c4.getColumnIndex(QQColumn[1]));
			Log.i("Q_ID",Q_ID+"" );
			totelMarks+=Marks;
			
			String [] answerColumn={"A_ID"};
			String [] data3={String.valueOf(Q_ID)};
			Cursor c3=ourDatabase.query("ANSWER",answerColumn, "Q_ID=? and CORRECTNESS=1",data3,null,null,"A_ID");
			int AnsIndex=c3.getColumnIndex(answerColumn[0]);
			String CorrectAns=new String();
			while(c3.moveToNext())
			{
				CorrectAns+=String.valueOf(c3.getInt(AnsIndex));
			}
			
			String [] responseColumn={"A_ID"};
			String [] data2={String.valueOf(STUD_ID),String.valueOf(QUIZ_ID),String.valueOf(Q_ID)};
			Cursor c2=ourDatabase.query("QUIZ_SCORE",responseColumn, "STUD_ID=? and QUIZ_ID=? and Q_ID=?",data2,null,null,"Q_ID");
			
			int Aindex=c2.getColumnIndex(responseColumn[0]);
			if(c2.moveToFirst())
			{
				
				String MarkedAnswers=String.valueOf(c2.getInt(Aindex));
				
				
				if(MarkedAnswers.equals(CorrectAns))
				{
					MarksObtained+=Marks;
					CorrectCount++;
					
				}
				
				XmlWriter.addQuesReport(resultData, MarkedAnswers, CorrectAns);
					
			}
			else
			{
				XmlWriter.addQuesReport(resultData, "N.A.",CorrectAns );
			}
			}
			XmlWriter.addResultSummary(resultData,totalQuesCount , CorrectCount, totelMarks, MarksObtained);
			XmlWriter.endResultXml(resultData);
			Log.i("xml", resultData.toString());
			HomePage.sendResultData(IP_ADDR,resultData.toString());
			Log.i("outtttttttttttttttttt", "ffffffff");
		}
		}
	public void insertResponse(int STUD_ID,int QUIZ_ID,int Q_ID,int A_ID)
	{
		String [] columns={"RESPONSE_ID"};
		String [] data={String.valueOf(STUD_ID),String.valueOf(QUIZ_ID),String.valueOf(Q_ID)};
		Cursor c=ourDatabase.query("QUIZ_SCORE",columns, "STUD_ID=? and QUIZ_ID=? and Q_ID=?",data , null, null, null);
		int Rid=c.getColumnIndex(columns[0]);
		
		if(!c.moveToFirst())
		{
			
			ContentValues cv1=new ContentValues();
			
			cv1.put("STUD_ID",STUD_ID);
			cv1.put("QUIZ_ID", QUIZ_ID);
			cv1.put("Q_ID",Q_ID);
			cv1.put("A_ID", A_ID);
		    ourDatabase.insert("QUIZ_SCORE", null, cv1);
		}
		else{
			ContentValues cv2=new ContentValues();
			cv2.put("A_ID",A_ID);
			ourDatabase.update("QUIZ_SCORE", cv2, "STUD_ID=? and QUIZ_ID=? and Q_ID=?", data);
		}
		
	}
	
	
	public int getStudentIdByIP(String IP)
	{
		String [] columns={"STUD_ID"};
		String [] data={IP};
		Cursor c=ourDatabase.query("STUDENT",columns, "IP_ADDR=?",data , null, null, null);
		int iId=c.getColumnIndex(columns[0]);
		c.moveToFirst();
		
		int Stud_id=c.getInt(iId); 
		return Stud_id;
	}
	public String getStudent_raised(String IP)
	{
		String [] columns={"NAME"};
		String [] data={IP};
		Cursor c=ourDatabase.query("STUDENT",columns, "IP_ADDR=?",data , null, null, null);
		int iName=c.getColumnIndex(columns[0]);
		c.moveToFirst();
		
		String Name=c.getString(iName); 
		return Name;
	}
	public long addPassword(String pass)
    {
    	ourDatabase.execSQL("UPDATE "+DATABASE_TABLE2+" SET PASSWORD= "+"'"+pass+"'"+" WHERE LOGIN_ID="+"1");
    	return 0;
    }
    public boolean check_password(String password) throws SQLException 
    {
    	Log.i("inside check_password",password);
       	mCursor2 = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE2 + " WHERE PASSWORD=?", new String[]{password});
        if (mCursor2 != null) 
        {           
            if(mCursor2.getCount() > 0)
            {
            	Log.i("inside check_password","true");
            	return true;
            }
            mCursor2.close();
        }
        Log.i("inside check_password","false");
     return false;
    }

   public void addAnswer(int id,String ans)
	{
	   Log.i("Inside","Add Answer");
		String [] columns={"SECURE_QUES_ID"};
		String [] data={String.valueOf(id)};
		Cursor c=ourDatabase.query(DATABASE_TABLE3,columns, "SECURE_QUES_ID=?",data , null, null, null);
		//int Rid=c.getColumnIndex(columns[0]);
		if(!c.moveToFirst())
		{
			Log.i("Error","Inside Null");
			ContentValues cv1=new ContentValues();			
			cv1.put("SECURE_QUES_ID",id);
			cv1.put("SECURE_ANS",ans);
		    ourDatabase.insert(DATABASE_TABLE3, null, cv1);
		}
		else{
			ContentValues cv2=new ContentValues();
			cv2.put("SECURE_ANS",ans);
			Log.i("ans",ans);
			ourDatabase.update(DATABASE_TABLE3, cv2, "SECURE_QUES_ID=?", data);
			Log.i("Inside","update");
		}
		
	}
   
    public boolean check_security_answer(int id,String answer) throws SQLException 
    {
    	Log.i("inside check_security_answer",answer);
       	mCursor3 = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE3 + " WHERE SECURE_QUES_ID=? AND SECURE_ANS=?", new String[]{Integer.toString(id),answer});
        if (mCursor3 != null) 
        {           
            if(mCursor3.getCount() > 0)
            {
            	Log.i("inside check_security_answer","true");
            	return true;
            }
            mCursor3.close();
        }
        Log.i("inside check_security_answer","false");
     return false;
    }
    
    
    public long AddUser(String name, String roll, String clas, String sec, String mac ,String ip)
    {
    	 ContentValues initialValues = new ContentValues();
         initialValues.put("NAME", name);
         initialValues.put("ROLL_NO", roll);
         initialValues.put("CLASS", clas); 
         initialValues.put("SECTION", sec); 
         initialValues.put("MAC_ADDR", mac); 
         initialValues.put("IP_ADDR", ip); 
         return ourDatabase.insert(DATABASE_TABLE1, null, initialValues);
    }

    public boolean Login(String mac) throws SQLException 
    {
    	mac=mac.substring(0, 17);
    	Log.i("inside login",mac);
    	
    	mCursor1 = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE1 + " WHERE MAC_ADDR=?", new String[]{mac});
        if (mCursor1 != null) {           
            if(mCursor1.getCount() > 0)
            {
            	Log.i("inside true","true");
            	return true;
            }
            mCursor1.close();
        }
        Log.i("inside false","false");
     return false;
    }
    public void addIP(String mac,String IP)
    {
    	Log.i("Inside","Add IP");
		String [] data={String.valueOf(mac)};		
			Log.i("","Inside Null");
			ContentValues cv1=new ContentValues();
			Log.i("mac",mac);
			cv1.put("IP_ADDR",IP);
			ourDatabase.update(DATABASE_TABLE1, cv1, "MAC_ADDR=?", data);
		
    	
    }
    public void SET_IP_ZERO()
    {
    	ourDatabase.execSQL("UPDATE "+DATABASE_TABLE1+" SET IP_ADDR= '0' WHERE 1");
			Log.i("Inside","update");
		
    	
    }

    public void getAttendance(String Dt)
	{
		String [] Date={Dt};
		Cursor c=ourDatabase.rawQuery("select ROLL_NO, NAME from STUDENT, ATTENDANCE where STUDENT.STUD_ID=ATTENDANCE.STUD_ID and ATTENDANCE.DATE=? and STUDENT.IP_ADDR!=0",Date);
		int RollIndex=c.getColumnIndex("ROLL_NO");
		int NameIndex=c.getColumnIndex("NAME");
		
		RollList=new String[c.getCount()];
	    NameList=new String[c.getCount()];
	   
	    int i=0;
		while(c.moveToNext())
		{
			RollList[i]=c.getString(RollIndex);
			NameList[i]=c.getString(NameIndex);
			i++;
		}
	}
    public void add_Attendance(String mac)
    {
    	Log.i("mac",mac);
    	String q="SELECT STUD_ID FROM "+DATABASE_TABLE1+" WHERE MAC_ADDR= " + "'"+mac+"'";
    	Cursor  cursor = ourDatabase.rawQuery(q,null);
    	cursor.moveToFirst();
    	while (!cursor.isAfterLast()) 
    	{
    		if (cursor != null) 
	        {
	           int id=cursor.getInt(0);
	           Log.i("id",Integer.toString(id));
	           Date date=new Date();
	           SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	           String d=sdf.format(date);
	           Log.i("date",d);
	           ContentValues c = new ContentValues();
	           c.put("STUD_ID", id); 
	           c.put("DATE", d); 
	           ourDatabase.insert("ATTENDANCE", null, c);    	  
	           Log.i("Attendance","Added");
	        }		
    	    cursor.moveToNext();
    	}

    	        
    	
    }
    public boolean check_Attendance(String mac)
    {
    	Log.i("mac",mac);
    	String q="SELECT STUD_ID FROM "+DATABASE_TABLE1+" WHERE MAC_ADDR= " + "'"+mac+"'";
    	Cursor  cursor = ourDatabase.rawQuery(q,null);
    	if(cursor.moveToNext())
    		return true;
    	else
		return false;
    }



	
}
	

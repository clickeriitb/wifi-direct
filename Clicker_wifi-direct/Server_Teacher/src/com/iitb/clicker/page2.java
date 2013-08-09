package com.iitb.clicker;
//After create a quiz is clicked, enter title n 2 options:create a new ques,import from quesbank
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Date;
//import android.widget.Toast;

public class page2 extends Activity{
	Button b2,b1;
	static int i=0;
	SQLiteDatabase db;
	EditText e1;
	static String title;
	static int max_quesid;
	Cursor c;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main2);
        
        b2=(Button)findViewById(R.id.button2); //"Import from question bank" button
        b1=(Button)findViewById(R.id.button1); //"Create a new question" button
        try{	
            db=openOrCreateDatabase("clicker_db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
           
        }
        catch(SQLException et)
            {
        
            }
        
       
		
		e1=(EditText)findViewById(R.id.editText1); //Edit text to enter the title for quiz
		title=e1.getText().toString();
		try{
		if(i!=0)
		{
			c=db.rawQuery("select TITLE,MAX(QUIZ_ID) from QUIZ", null);
			c.moveToFirst();
			e1.setText(c.getString(0));
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
        b1.setOnClickListener(new OnClickListener(){

			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(e1.getText().toString().length()==0)
				{
					Toast.makeText(page2.this,"Enter title", 2000).show();
				}
				else
				{
				if(i==0){
				Date date=new Date();
				ContentValues values=new ContentValues();
				values.put("TITLE", e1.getText().toString().toUpperCase());
				values.put("DATE",date.toString());
				db.insert("QUIZ", null, values);
				}
				
			
				i++;
				 String query = "SELECT MAX(Q_ID) FROM QUESTION";
			      Cursor cursor = db.rawQuery(query, null);	//Storing the max question id so that on import_from_quesbank page, the questions whose q_id is more than this max q_id are checked already
			      max_quesid = 0;     
			      if (cursor.moveToFirst())
			      {
			          do
			          {           
			              max_quesid = cursor.getInt(0);                  
			          } while(cursor.moveToNext());       
			        
			      }				      
			      
				Intent j=new Intent(page2.this,newques_inquiz.class);
				startActivity(j);
				}
				}
				
        });
        b2.setOnClickListener(new OnClickListener(){

			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			if(e1.getText().toString().length()==0)
			{
				Toast.makeText(page2.this,"Enter title", 2000).show();
			}
			else
			{
				if(i==0)
				{
				Date date=new Date(); 
				ContentValues values=new ContentValues();
				values.put("TITLE", e1.getText().toString().toUpperCase());
				values.put("DATE",date.toString());
				db.insert("QUIZ", null, values);
				}
				i++;
				Intent i=new Intent(page2.this,import_from_quesbank.class);
				startActivity(i);
			}
			}
        });
       Button back=(Button)findViewById(R.id.button3);
       back.setOnClickListener(new OnClickListener(){

			
			public void onClick(View arg0) {
				Intent i=new Intent(page2.this,QuizActivity.class);
				startActivity(i);
			}
       });
	}
}
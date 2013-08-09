package com.iitb.clicker;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class QuizActivity extends Activity {
    /** Called when the activity is first created. */
	Button newques,newquiz,importquiz,exit;
	static int max_quesid;
	SQLiteDatabase db;
	TextView t;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        t=(TextView)findViewById(R.id.textView1);
        newques=(Button)findViewById(R.id.button1);
        newquiz=(Button)findViewById(R.id.button2);
        importquiz=(Button)findViewById(R.id.button3);
       
        exit=(Button)findViewById(R.id.Button02);
        page2.i=0;
        max_quesid=0;
        try{
            db=openOrCreateDatabase("clicker_db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
          
        }
            catch(Exception e)
            {
            	e.printStackTrace();
            }

              newques.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 try{
				     
				      String query = "SELECT MAX(Q_ID) FROM QUESTION";
				      Cursor cursor = db.rawQuery(query, null);
				      max_quesid = 0;     
				      if (cursor.moveToFirst())
				      {
				          do
				          {           
				              max_quesid = cursor.getInt(0);                  
				          } while(cursor.moveToNext());       
				          //t.setText(Integer.toString(max_quesid));
				      }				      
				      }catch(SQLException e)
				      {
				    	  //t.setText(e.getMessage());
				    	  
				      }
				Intent i=new Intent(QuizActivity.this,QuizCreationActivity.class);
				startActivity(i);
				
			}
        	
        });
        newquiz.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(QuizActivity.this,page2.class);
				startActivity(i);
			}
        	
        });
       importquiz.setOnClickListener(new OnClickListener(){

			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(QuizActivity.this,Import_a_quiz.class);
				startActivity(i);
							}
        	
        });
      
      exit.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
      	
      });

    }

}
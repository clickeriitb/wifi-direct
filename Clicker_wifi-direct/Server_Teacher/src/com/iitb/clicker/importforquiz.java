package com.iitb.clicker;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;


	public class importforquiz extends Activity{
		
		int n,current_quizid;
		static int count;
		static int[]ques_to_edit;
		SQLiteDatabase db;
		TableLayout r;
		
		String st,sub,cl;
		EditText subj,clas;
		Button edit,save,send,back,sear;
		Cursor d;
		static TextView []a;
	    static CheckBox []b;
	    static int []quesid;
	        
		public void onCreate(Bundle savedInstanceState) {  
	        count=0;       
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.main5);
	        subj=(EditText)findViewById(R.id.editText2);
	        clas=(EditText)findViewById(R.id.editText1);
	        r=(TableLayout)findViewById(R.id.tableLayout1);
	        edit=(Button)findViewById(R.id.button1);
	        save=(Button)findViewById(R.id.button2);
	        send=(Button)findViewById(R.id.button3);
	        back=(Button)findViewById(R.id.button4);
	        sear=(Button)findViewById(R.id.button5);
	        send.setVisibility(View.INVISIBLE);
	       try
	       {
	        db=openOrCreateDatabase("clicker_db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
	      
	       }
	       catch(SQLException e)
	        {
	        e.printStackTrace();
	        }
	       try{
	       int i=page2.max_quesid;
	        String s="SELECT COUNT(*) FROM QUESTION";
	        Cursor c=db.rawQuery(s, null);
	        c.moveToFirst();
	        count= c.getInt(0);
	        c.close();
	        ques_to_edit=new int[count];
	        a=new TextView[count];
	        b=new CheckBox[count];
	        quesid=new int[count];
	       
	    /*THE SEARCH BUTTON*/           
	        sear.setOnClickListener(new OnClickListener(){
	       	        
			    Cursor e;
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					r.removeAllViews();
					sub=subj.getText().toString().toUpperCase();
			        cl=clas.getText().toString().toUpperCase();
					if(sub.length()==0 && cl.length()>0)				     
			        {				
			        e=db.rawQuery("select QUESTION,Q_ID from QUESTION where CLASS='"+cl+"'",null);        
			        }			
				
			        else if(sub.length()>0 && cl.length()==0)
			        {
			         e=db.rawQuery("select QUESTION,Q_ID from QUESTION where SUBJECT='"+sub+"'",null);
			        }
			        else if(sub.length()>0 && cl.length()>0)
			        {
				         e=db.rawQuery("select QUESTION,Q_ID from QUESTION where SUBJECT='"+sub+"' AND CLASS='"+cl+"'",null);
				    }
			        else 
			        {
			        	Intent i=new Intent(importforquiz.this,importforquiz.class);
			        	startActivity(i);
			        }
					e.moveToFirst();
					try{
					
					   for (int j = 0; j < count; j++) 
				         {
				            TextView rowTextView = new TextView(importforquiz.this);
				            rowTextView.setTextColor(R.color.black);
				            CheckBox cb=new CheckBox(importforquiz.this);
				            st=e.getString(0);
				            rowTextView.setText(st);
				            TableRow tr = new TableRow(importforquiz.this);
				            tr.setLayoutParams(new LayoutParams(
				                    LayoutParams.FILL_PARENT,
				                    LayoutParams.WRAP_CONTENT));
				           
				            tr.addView(cb);
				            tr.addView(rowTextView);
				            
				            a[j] = rowTextView;
				            b[j] = cb;
				            quesid[j]=e.getInt(1);
				            r.addView(tr,new TableLayout.LayoutParams(
				                    LayoutParams.FILL_PARENT,
				                    LayoutParams.WRAP_CONTENT));
				            e.moveToNext();
				        }
				         	e.close();
					}
					catch(Exception ett)
					{
						ett.printStackTrace();
					}
				}	       	
	        });
	        d=db.rawQuery("select QUESTION,Q_ID from QUESTION order by Q_ID desc",null);
	        d.moveToFirst();
	        
	        for (int j = 0; j < count; j++) 
	         {
	            TextView rowTextView = new TextView(this);
	            rowTextView.setTextColor(R.color.black);
	            CheckBox cb=new CheckBox(this);
	            st=d.getString(0);
	            
	            rowTextView.setText(st);
	            TableRow tr = new TableRow(this);
	            tr.setLayoutParams(new LayoutParams(
	                    LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT));
	            if(i!=0){
	            if(d.getInt(1)>i)
	            {
	            	cb.setChecked(true);
	            }}
	            tr.addView(cb);
	            tr.addView(rowTextView);
	            
	            a[j] = rowTextView;
	            b[j] = cb;
	            quesid[j]=d.getInt(1);
	            r.addView(tr,new TableLayout.LayoutParams(
	                    LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT));
	            d.moveToNext();
	        }
	         d.close();
	         
	         edit.setOnClickListener(new OnClickListener(){

				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				
					Intent i=new Intent(importforquiz.this,edit3.class);
					startActivity(i);
				}
	        	 
	         });
	         Cursor e=db.rawQuery("select max(QUIZ_ID) from QUIZ", null);
	         e.moveToFirst();
	         e.close();
	         save.setOnClickListener(new OnClickListener(){

					
					public void onClick(View arg0) {
						for(int n=0;n<count;n++)
						{
							try{
							if(b[n].isChecked())
							{
							db.execSQL("insert into QQ_MAP(QUIZ_ID,Q_ID,MARKS) values ("+Import_a_quiz.Quiz_id+","+quesid[n]+",2)");
							}
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
					});
	         	         back.setOnClickListener(new OnClickListener(){

					
					public void onClick(View arg0) {
						Intent i=new Intent(importforquiz.this,preview.class);
						startActivity(i);
					}
	         });}
	       catch(Exception e)
	       {
	    	   e.printStackTrace();
	       }
		}
		
	}



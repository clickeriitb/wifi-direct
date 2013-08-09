package com.iitb.clicker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class new_ques extends Activity implements OnClickListener, OnItemSelectedListener {
    /** Called when the activity is first created. */
	Button bnext,bend;
	 SQLiteDatabase db;
	 Spinner spinner,spinner2;
	 int max;
	 String sql;
	 EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,et1,et2,et3,et4,et5,et6,e33;
		Integer a1=0,a2=0,a3=0,a4=0,a5=0,a6=0;
		CheckBox c1,c2,c3,c4,c5,c6;
int j=0;	 
int count=0;
int flag=0;
int qid=0;
String s=null;
Cursor cursor,cursor2,tempcursor;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newques);
     
        e1=(EditText)findViewById(R.id.editText1);
        e33=(EditText)findViewById(R.id.editText13);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        e7=(EditText)findViewById(R.id.editText7);
        e8=(EditText)findViewById(R.id.editText8);
        e9=(EditText)findViewById(R.id.editText9);
        e10=(EditText)findViewById(R.id.editText10);
        e11=(EditText)findViewById(R.id.editText11);
        e12=(EditText)findViewById(R.id.editText12);
        c1=(CheckBox)findViewById(R.id.checkBox1);
        c2=(CheckBox)findViewById(R.id.checkBox2);
        c3=(CheckBox)findViewById(R.id.checkBox3);
        c4=(CheckBox)findViewById(R.id.checkBox4);
        c5=(CheckBox)findViewById(R.id.checkBox5);
        c6=(CheckBox)findViewById(R.id.checkBox6);
        et1=(EditText)findViewById(R.id.editText01);
        et2=(EditText)findViewById(R.id.editText02);
        et3=(EditText)findViewById(R.id.editText03);
        et4=(EditText)findViewById(R.id.editText04);
        et5=(EditText)findViewById(R.id.editText05);
        et6=(EditText)findViewById(R.id.editText06);
        et1.setVisibility(View.INVISIBLE);
        et2.setVisibility(View.INVISIBLE);
        et3.setVisibility(View.INVISIBLE);
        et4.setVisibility(View.INVISIBLE);
        et5.setVisibility(View.INVISIBLE);
        et6.setVisibility(View.INVISIBLE);
        bnext=(Button)findViewById(R.id.button1);
        bend=(Button)findViewById(R.id.button2);
        
        spinner2=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.question_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(this);

        try
        {
        
        	db=openOrCreateDatabase("clicker_db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
        
        }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
       
            bend.setOnClickListener(new OnClickListener() {
        		
        		
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			String p=e4.getText().toString();
        			if(p.length()>0)
        			{
        				
        			
        			try
    				{
        				if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
    					{
    						 sql = "INSERT INTO QUESTION ( CLASS, SUBJECT, QUESTION, TYPE_ID) VALUES('"+e33.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"','a');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Multiple choice, single correct answer"))
    					{
    						 sql = "INSERT INTO QUESTION ( CLASS, SUBJECT, QUESTION, TYPE_ID) VALUES('"+e33.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"','s');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Multiple choice, multiple correct answer"))
    					{
    						 sql = "INSERT INTO QUESTION ( CLASS, SUBJECT, QUESTION, TYPE_ID) VALUES('"+e33.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"','m');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("True/False"))
    					{
    						
    						 sql = "INSERT INTO QUESTION ( CLASS, SUBJECT, QUESTION, TYPE_ID) VALUES('"+e33.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"','t');";       
    						
    					}	
    					db.execSQL(sql);
    					
    				}
    				catch(Exception e)
    		        {
    		        e.printStackTrace();	
    		        }
    				try
    				{
    					
    					tempcursor=db.rawQuery("SELECT MAX(Q_ID) FROM QUESTION", null);
    					
    					tempcursor.moveToFirst();
    					max=tempcursor.getInt(tempcursor.getColumnIndex("MAX(Q_ID)"));
    					db.execSQL("INSERT INTO QQ_MAP(Q_ID,QUIZ_ID,MARKS) VALUES ("+max+","+Import_a_quiz.Quiz_id+",2)");
    					
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    				}
    				a1=0;
    		           a2=0;
    		           a3=0;
    		           a4=0;
    		           a5=0;
    		           a6=0;
    				if(c1.isChecked())a1=1;
    		           if(c2.isChecked())a2=1;
    		           if(c3.isChecked())a3=1;
    		           if(c4.isChecked())a4=1;
    		           if(c5.isChecked())a5=1;
    		           if(c6.isChecked())a6=1;
    				String sql1;
    				
    			int opt=0;
    				try
    				{
    					 opt=0;
    	  		           if(e7.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e8.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e9.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e10.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e11.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e12.getText().toString().length()>0)
    	  		        	   opt++;
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    				}
    				try
    				{
    	        	    if(!spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
		                {
		                switch(opt)
		                 {
		                 case 6:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','6','"+e12.getText().toString()+"','"+a6.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 5:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','5','"+e11.getText().toString()+"','"+a5.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 4:
		                     sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','4','"+e10.getText().toString()+"','"+a4.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 3:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','3','"+e9.getText().toString()+"','"+a3.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 2:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','2','"+e8.getText().toString()+"','"+a2.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 1:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','1','"+e7.getText().toString()+"','"+a1.toString()+"');";
		                 	db.execSQL(sql1);
		                 	break;
		                 default:
		                 		break;
		                 }
		                }
		                else
		                {
		             	   switch(opt)
		                    {
		                    case 6:
		                      sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','6','"+e12.getText().toString()+"','"+et6.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 5:
		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','5','"+e11.getText().toString()+"','"+et5.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 4:
		                        sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','4','"+e10.getText().toString()+"','"+et4.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 3:
		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','3','"+e9.getText().toString()+"','"+et3.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 2:
		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','2','"+e8.getText().toString()+"','"+et2.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 1:
		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','1','"+e7.getText().toString()+"','"+et1.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    	break;
		                    default:
		                    		break;
		                    }
		                     
		                }	
		
    				e1.setText("");
    				e3.setText("");
    			e4.setText("");
    				e7.setText("");
    				e8.setText("");
    				e9.setText("");
    				e10.setText("");
    				e11.setText("");
    				e12.setText("");
    				et1.setText("");
    				et2.setText("");
    				et3.setText("");
    				
    				
    				et4.setText("");
    				et5.setText("");
    				et6.setText("");
    			   	 c1.setChecked(false);
    		         c2.setChecked(false);
    		         c3.setChecked(false);
    		         c4.setChecked(false);
    		         c5.setChecked(false);
    		         c6.setChecked(false);
    				
    				c1.setVisibility(View.VISIBLE);
    				c2.setVisibility(View.VISIBLE);
    				c3.setVisibility(View.VISIBLE);
    				c4.setVisibility(View.VISIBLE);
    				c5.setVisibility(View.VISIBLE);
    				c6.setVisibility(View.VISIBLE);
    				et1.setVisibility(View.INVISIBLE);
    		        et2.setVisibility(View.INVISIBLE);
    		        et3.setVisibility(View.INVISIBLE);
    		        et4.setVisibility(View.INVISIBLE);
    		        et5.setVisibility(View.INVISIBLE);
    		        et6.setVisibility(View.INVISIBLE);

    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    				}
        			}
        			
        			Intent i=new Intent(new_ques.this,preview.class);
        			startActivity(i);
        			
        		}
        	});
            bnext.setOnClickListener(new OnClickListener() {
        		
        		
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        		
        			String p,q,r;
        			p=e3.getText().toString();
        			q=e33.getText().toString();
        			r=e4.getText().toString();
        			    					if(p.length()==0 || q.length()==0 || r.length()==0)
        			    					{
        			    					if(p.length()==0)
        			    					{    						Toast.makeText(new_ques.this,"Enter Subject", 2000).show();
        			    					}
        			    					else if(q.length()==0)
        			    					{
        			    						Toast.makeText(new_ques.this,"Enter Class", 2000).show();
        			    					}
        			    					else
        			    					{
        			    						Toast.makeText(new_ques.this,"Enter question", 2000).show();	
        			    					}
        		}
        			    					else
        			    					{
        			    					
    				try
    				{
    					
    					if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
    					{
    						 sql = "INSERT INTO QUESTION ( CLASS, SUBJECT, QUESTION, TYPE_ID) VALUES('"+e33.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"','a');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Multiple choice, single correct answer"))
    					{
    						 sql = "INSERT INTO QUESTION ( CLASS, SUBJECT, QUESTION, TYPE_ID) VALUES('"+e33.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"','s');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Multiple choice, multiple correct answer"))
    					{
    						 sql = "INSERT INTO QUESTION ( CLASS, SUBJECT, QUESTION, TYPE_ID) VALUES('"+e33.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"','m');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("True/False"))
    					{
    						
    						 sql = "INSERT INTO QUESTION ( CLASS, SUBJECT, QUESTION, TYPE_ID) VALUES('"+e33.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"','t');";       
    						
    					}	
    					db.execSQL(sql);
    			
    					
    				}
    				catch(Exception e)
    		        {
    		        e.printStackTrace();	
    		        }
    				try
    				{
    					
    					tempcursor=db.rawQuery("SELECT MAX(Q_ID) FROM QUESTION", null);
    					
    					tempcursor.moveToFirst();
    					max=tempcursor.getInt(tempcursor.getColumnIndex("MAX(Q_ID)"));
    					db.execSQL("INSERT INTO QQ_MAP(Q_ID,QUIZ_ID,MARKS) VALUES ("+max+","+Import_a_quiz.Quiz_id+",2)");
    					
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    				}
    				a1=0;
    		           a2=0;
    		           a3=0;
    		           a4=0;
    		           a5=0;
    		           a6=0;
    				if(c1.isChecked())a1=1;
    		           if(c2.isChecked())a2=1;
    		           if(c3.isChecked())a3=1;
    		           if(c4.isChecked())a4=1;
    		           if(c5.isChecked())a5=1;
    		           if(c6.isChecked())a6=1;
    				String sql1;
    				
    			int opt=0;
    				try
    				{
    					 opt=0;
    	  		           if(e7.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e8.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e9.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e10.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e11.getText().toString().length()>0)
    	  		        	   opt++;
    	  		           if(e12.getText().toString().length()>0)
    	  		        	   opt++;
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    				}
    				try
    				{
    	        	    if(!spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
		                {
		                switch(opt)
		                 {
		                 case 6:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','6','"+e12.getText().toString()+"','"+a6.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 5:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','5','"+e11.getText().toString()+"','"+a5.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 4:
		                     sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','4','"+e10.getText().toString()+"','"+a4.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 3:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','3','"+e9.getText().toString()+"','"+a3.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 2:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','2','"+e8.getText().toString()+"','"+a2.toString()+"');";
		                 	db.execSQL(sql1);
		                 case 1:
		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','1','"+e7.getText().toString()+"','"+a1.toString()+"');";
		                 	db.execSQL(sql1);
		                 	break;
		                 default:
		                 		break;
		                 }
		                }
		                else
		                {
		             	   switch(opt)
		                    {
		                    case 6:
		                      sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','6','"+e12.getText().toString()+"','"+et6.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 5:
		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','5','"+e11.getText().toString()+"','"+et5.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 4:
		                        sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','4','"+e10.getText().toString()+"','"+et4.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 3:
		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','3','"+e9.getText().toString()+"','"+et3.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 2:
		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','2','"+e8.getText().toString()+"','"+et2.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    case 1:
		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','1','"+e7.getText().toString()+"','"+et1.getText().toString()+"');";
		                    	db.execSQL(sql1);
		                    	break;
		                    default:
		                    		break;
		                    }
		                     
		                }
			e1.setText("");
    			e4.setText("");
    				e7.setText("");
    				e8.setText("");
    				e9.setText("");
    				e10.setText("");
    				e11.setText("");
    				e12.setText("");
    				et1.setText("");
    				et2.setText("");
    				et3.setText("");
    				
    				
    				et4.setText("");
    				et5.setText("");
    				et6.setText("");
    			   	 c1.setChecked(false);
    		         c2.setChecked(false);
    		         c3.setChecked(false);
    		         c4.setChecked(false);
    		         c5.setChecked(false);
    		         c6.setChecked(false);
    				
    				c1.setVisibility(View.VISIBLE);
    				c2.setVisibility(View.VISIBLE);
    				c3.setVisibility(View.VISIBLE);
    				c4.setVisibility(View.VISIBLE);
    				c5.setVisibility(View.VISIBLE);
    				c6.setVisibility(View.VISIBLE);
    				et1.setVisibility(View.INVISIBLE);
    		        et2.setVisibility(View.INVISIBLE);
    		        et3.setVisibility(View.INVISIBLE);
    		        et4.setVisibility(View.INVISIBLE);
    		        et5.setVisibility(View.INVISIBLE);
    		        et6.setVisibility(View.INVISIBLE);

    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    				}
        			    					}
    }      
    
        		});
 }
   	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		// TODO Auto-generated method stub
		
		c1.setVisibility(View.VISIBLE);
		c2.setVisibility(View.VISIBLE);
		c3.setVisibility(View.VISIBLE);
		c4.setVisibility(View.VISIBLE);
		c5.setVisibility(View.VISIBLE);
		c6.setVisibility(View.VISIBLE);
		et1.setVisibility(View.INVISIBLE);
        et2.setVisibility(View.INVISIBLE);
        et3.setVisibility(View.INVISIBLE);
        et4.setVisibility(View.INVISIBLE);
        et5.setVisibility(View.INVISIBLE);
        et6.setVisibility(View.INVISIBLE);
	 if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("True/False"))
		{
			e7.setText("True");
			e8.setText("False");
		}
		else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
		{
			c1.setVisibility(View.INVISIBLE);
			c2.setVisibility(View.INVISIBLE);
			c3.setVisibility(View.INVISIBLE);
			c4.setVisibility(View.INVISIBLE);
			c5.setVisibility(View.INVISIBLE);
			c6.setVisibility(View.INVISIBLE);
			et1.setVisibility(View.VISIBLE);
	        et2.setVisibility(View.VISIBLE);
	        et3.setVisibility(View.VISIBLE);
	        et4.setVisibility(View.VISIBLE);
	        et5.setVisibility(View.VISIBLE);
	        et6.setVisibility(View.VISIBLE);
			
		}
	}

	
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}


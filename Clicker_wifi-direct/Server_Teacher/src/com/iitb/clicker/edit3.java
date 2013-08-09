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
import android.widget.AdapterView.OnItemSelectedListener;

public class edit3 extends Activity implements OnClickListener, OnItemSelectedListener {
    /** Called when the activity is first created. */
	
	Button bnext,bend;
	 SQLiteDatabase db;
	 String sql;
	 Spinner spinner,spinner2;
	 
	 EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,et1,et2,et3,et4,et5,et6,e33;
		Integer a1=0,a2=0,a3=0,a4=0,a5=0,a6=0;
		CheckBox c1,c2,c3,c4,c5,c6;
int j=0;
int count=0;
int flag=0;
int qid=0;
String s=null;
Cursor cursor,cursor2;

	//Context context=this.context;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newques);
     
        e1=(EditText)findViewById(R.id.editText1);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        e7=(EditText)findViewById(R.id.editText7);
        e8=(EditText)findViewById(R.id.editText8);
        e9=(EditText)findViewById(R.id.editText9);
        e10=(EditText)findViewById(R.id.editText10);
        e11=(EditText)findViewById(R.id.editText11);
        e12=(EditText)findViewById(R.id.editText12);
        e33=(EditText)findViewById(R.id.editText13);
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
           
            j=0;
            fun_cal();
            bend.setOnClickListener(new OnClickListener() {
        		
        		
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        			try
    				{
        				if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
    					{
    						 sql = "UPDATE QUESTION SET CLASS='"+e33.getText().toString()+"', SUBJECT='"+e3.getText().toString()+"', QUESTION='"+e4.getText().toString()+"', TYPE_ID='a');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Multiple choice, single correct answer"))
    					{
    						sql = "UPDATE QUESTION SET CLASS='"+e33.getText().toString()+"', SUBJECT='"+e3.getText().toString()+"', QUESTION='"+e4.getText().toString()+"', TYPE_ID='s');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Multiple choice, multiple correct answer"))
    					{
    						sql = "UPDATE QUESTION SET CLASS='"+e33.getText().toString()+"', SUBJECT='"+e3.getText().toString()+"', QUESTION='"+e4.getText().toString()+"', TYPE_ID='m');";      
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("True/False"))
    					{
    						
    						sql = "UPDATE QUESTION SET CLASS='"+e33.getText().toString()+"', SUBJECT='"+e3.getText().toString()+"', QUESTION='"+e4.getText().toString()+"', TYPE_ID='t');";       
    						
    					}	
    		
    					
    		
    					
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
    				int opt;
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
    				
    		        	    if(!spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
       		                {
       		                switch(opt)
       		                 {
       		                 case 6:
       		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','6','"+e12.getText().toString()+"','"+a6.toString()+"');";
       		                 	db.execSQL(sql1);
       		                 	//Toast.makeText(basic_ui.this, "inserted option 6!!! :)", Toast.LENGTH_LONG).show();
       		                 case 5:
       		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','5','"+e11.getText().toString()+"','"+a5.toString()+"');";
       		                 	db.execSQL(sql1);
       		                 	//Toast.makeText(basic_ui.this, "inserted option 5!!! :)", Toast.LENGTH_LONG).show();
       		                 case 4:
       		                     sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','4','"+e10.getText().toString()+"','"+a4.toString()+"');";
       		                 	db.execSQL(sql1);
       		                 	//Toast.makeText(basic_ui.this, "inserted option 4!!! :)", Toast.LENGTH_LONG).show();
       		                 case 3:
       		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','3','"+e9.getText().toString()+"','"+a3.toString()+"');";
       		                 	db.execSQL(sql1);
       		                 	//Toast.makeText(basic_ui.this, "inserted option 3!!! :)", Toast.LENGTH_LONG).show();
       		                 case 2:
       		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','2','"+e8.getText().toString()+"','"+a2.toString()+"');";
       		                 	db.execSQL(sql1);
       		                 	//Toast.makeText(basic_ui.this, "inserted option 2!!! :)", Toast.LENGTH_LONG).show();
       		                 case 1:
       		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','1','"+e7.getText().toString()+"','"+a1.toString()+"');";
       		                 	db.execSQL(sql1);
       		                 	//Toast.makeText(basic_ui.this, "inserted option 1!!! :)", Toast.LENGTH_LONG).show();
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
       		                    	//Toast.makeText(basic_ui.this, "inserted option 6!!! :)", Toast.LENGTH_LONG).show();
       		                    case 5:
       		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','5','"+e11.getText().toString()+"','"+et5.getText().toString()+"');";
       		                    	db.execSQL(sql1);
       		                    	//Toast.makeText(basic_ui.this, "inserted option 5!!! :)", Toast.LENGTH_LONG).show();
       		                    case 4:
       		                        sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','4','"+e10.getText().toString()+"','"+et4.getText().toString()+"');";
       		                    	db.execSQL(sql1);
       		                    	//Toast.makeText(basic_ui.this, "inserted option 4!!! :)", Toast.LENGTH_LONG).show();
       		                    case 3:
       		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','3','"+e9.getText().toString()+"','"+et3.getText().toString()+"');";
       		                    	db.execSQL(sql1);
       		                    	//Toast.makeText(basic_ui.this, "inserted option 3!!! :)", Toast.LENGTH_LONG).show();
       		                    case 2:
       		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','2','"+e8.getText().toString()+"','"+et2.getText().toString()+"');";
       		                    	db.execSQL(sql1);
       		                    	//Toast.makeText(basic_ui.this, "inserted option 2!!! :)", Toast.LENGTH_LONG).show();
       		                    case 1:
       		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','1','"+e7.getText().toString()+"','"+et1.getText().toString()+"');";
       		                    	db.execSQL(sql1);
       		                    	//Toast.makeText(basic_ui.this, "inserted option 1!!! :)", Toast.LENGTH_LONG).show();
       		                    	break;
       		                    default:
       		                    		break;
       		                    }
       		                     
       		                }
    				
        			Intent i=new Intent(edit3.this,import_from_quesbank.class);
        			startActivity(i);
        			
        		}
        	});
            bnext.setOnClickListener(new OnClickListener() {
        		
        		
        		public void onClick(View v) {
        			// TODO Auto-generated method stub
        		
    				try
    				{
    					if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
    					{
    						 sql = "UPDATE QUESTION SET CLASS='"+e33.getText().toString()+"', SUBJECT='"+e3.getText().toString()+"', QUESTION='"+e4.getText().toString()+"', TYPE_ID='a');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Multiple choice, single correct answer"))
    					{
    						sql = "UPDATE QUESTION SET CLASS='"+e33.getText().toString()+"', SUBJECT='"+e3.getText().toString()+"', QUESTION='"+e4.getText().toString()+"', TYPE_ID='s');";       
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Multiple choice, multiple correct answer"))
    					{
    						sql = "UPDATE QUESTION SET CLASS='"+e33.getText().toString()+"', SUBJECT='"+e3.getText().toString()+"', QUESTION='"+e4.getText().toString()+"', TYPE_ID='m');";      
    		           
    					}
    					else if(spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("True/False"))
    					{
    						
    						sql = "UPDATE QUESTION SET CLASS='"+e33.getText().toString()+"', SUBJECT='"+e3.getText().toString()+"', QUESTION='"+e4.getText().toString()+"', TYPE_ID='t');";       
    						
    					}	
    		
    					
    					db.execSQL(sql);
    					
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
    				int opt;
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
    				
    		           try
    				{
    		        	    if(!spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString().equals("Arrange in correct order"))
    		                {
    		                switch(opt)
    		                 {
    		                 case 6:
    		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','6','"+e12.getText().toString()+"','"+a6.toString()+"');";
    		                 	db.execSQL(sql1);
    		                 	//Toast.makeText(basic_ui.this, "inserted option 6!!! :)", Toast.LENGTH_LONG).show();
    		                 case 5:
    		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','5','"+e11.getText().toString()+"','"+a5.toString()+"');";
    		                 	db.execSQL(sql1);
    		                 	//Toast.makeText(basic_ui.this, "inserted option 5!!! :)", Toast.LENGTH_LONG).show();
    		                 case 4:
    		                     sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','4','"+e10.getText().toString()+"','"+a4.toString()+"');";
    		                 	db.execSQL(sql1);
    		                 	//Toast.makeText(basic_ui.this, "inserted option 4!!! :)", Toast.LENGTH_LONG).show();
    		                 case 3:
    		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','3','"+e9.getText().toString()+"','"+a3.toString()+"');";
    		                 	db.execSQL(sql1);
    		                 	//Toast.makeText(basic_ui.this, "inserted option 3!!! :)", Toast.LENGTH_LONG).show();
    		                 case 2:
    		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','2','"+e8.getText().toString()+"','"+a2.toString()+"');";
    		                 	db.execSQL(sql1);
    		                 	//Toast.makeText(basic_ui.this, "inserted option 2!!! :)", Toast.LENGTH_LONG).show();
    		                 case 1:
    		                   	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','1','"+e7.getText().toString()+"','"+a1.toString()+"');";
    		                 	db.execSQL(sql1);
    		                 	//Toast.makeText(basic_ui.this, "inserted option 1!!! :)", Toast.LENGTH_LONG).show();
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
    		                    	//Toast.makeText(basic_ui.this, "inserted option 6!!! :)", Toast.LENGTH_LONG).show();
    		                    case 5:
    		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','5','"+e11.getText().toString()+"','"+et5.getText().toString()+"');";
    		                    	db.execSQL(sql1);
    		                    	//Toast.makeText(basic_ui.this, "inserted option 5!!! :)", Toast.LENGTH_LONG).show();
    		                    case 4:
    		                        sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','4','"+e10.getText().toString()+"','"+et4.getText().toString()+"');";
    		                    	db.execSQL(sql1);
    		                    	//Toast.makeText(basic_ui.this, "inserted option 4!!! :)", Toast.LENGTH_LONG).show();
    		                    case 3:
    		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','3','"+e9.getText().toString()+"','"+et3.getText().toString()+"');";
    		                    	db.execSQL(sql1);
    		                    	//Toast.makeText(basic_ui.this, "inserted option 3!!! :)", Toast.LENGTH_LONG).show();
    		                    case 2:
    		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','2','"+e8.getText().toString()+"','"+et2.getText().toString()+"');";
    		                    	db.execSQL(sql1);
    		                    	//Toast.makeText(basic_ui.this, "inserted option 2!!! :)", Toast.LENGTH_LONG).show();
    		                    case 1:
    		                      	sql1 = "INSERT or replace INTO answer (Q_ID,A_ID,ANSWER,CORRECTNESS) VALUES ('"+Integer.toString(qid)+"','1','"+e7.getText().toString()+"','"+et1.getText().toString()+"');";
    		                    	db.execSQL(sql1);
    		                    	//Toast.makeText(basic_ui.this, "inserted option 1!!! :)", Toast.LENGTH_LONG).show();
    		                    	break;
    		                    default:
    		                    		break;
    		                    }
    		                     
    		                }	//}
    				e1.setText("");
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

    		  
    				j++;
    				if(j<importforquiz.count)
    				{
    					//Toast.makeText(edit3.this, "moving to next", 1000).show();
    					
    					fun_cal();
    				}
    				else
    				{
    				//	Toast.makeText(edit3.this, "moving out", 1000).show();
    					Intent i=new Intent(edit3.this,importforquiz.class);
    					startActivity(i);
    				}
    				}
    				catch(Exception e)
    				{
    					e.printStackTrace();
    				}
    }      
    
        		});
 }
    void fun_cal()
    {
    try{	
			if(importforquiz.b[j].isChecked())
			{
				flag=0;
				count=0;
				qid=importforquiz.quesid[j];	
			
				try
	    		{
					cursor=db.rawQuery("SELECT QUESTION,SUBJECT,CLASS,TYPE_ID FROM QUESTION WHERE Q_ID="+qid, null); 
					cursor.moveToFirst();
					s=cursor.getString(0);
				}
    		catch(Exception e){
    			
    			e.printStackTrace();
    			}
    		
			e4.setText(s);
			e3.setText(cursor.getString(1));
			e33.setText(cursor.getString(2));
			if(cursor.getString(3).contentEquals("a"))			
				spinner2.setSelection(3);
				else if(cursor.getString(3).contentEquals("s"))			
					spinner2.setSelection(0);
				else if(cursor.getString(3).contentEquals("m"))			
					spinner2.setSelection(1);
				else if(cursor.getString(3).contentEquals("t"))			
					spinner2.setSelection(2);
				
			cursor2=db.rawQuery("SELECT ANSWER,CORRECTNESS FROM ANSWER WHERE Q_ID="+qid, null);
			cursor2.moveToFirst();
			String temp;
			if(cursor.getString(3).contentEquals("a"))	
			{
				for(int k=0;k<cursor2.getCount();k++)
				{
				
				if(count==0)
				{
				temp=cursor2.getString(0);
				e7.setText(temp);
				c1.setVisibility(View.INVISIBLE);
				et1.setVisibility(View.VISIBLE);
				et1.setText(Integer.toString(cursor2.getInt(1)));
				}				
				if(count==1)
				{
				e8.setText(cursor2.getString(0));
				c2.setVisibility(View.INVISIBLE);
				et2.setVisibility(View.VISIBLE);
				et2.setText(Integer.toString(cursor2.getInt(1)));
				}
				if(count==2)
				{
				e9.setText(cursor2.getString(0));
				c3.setVisibility(View.INVISIBLE);
				et3.setVisibility(View.VISIBLE);
				et3.setText(Integer.toString(cursor2.getInt(1)));
				}
				if(count==3)
				{
				e10.setText(cursor2.getString(0));
				c4.setVisibility(View.INVISIBLE);
				et4.setVisibility(View.VISIBLE);
				et4.setText(Integer.toString(cursor2.getInt(1)));
				}
				if(count==4)
				{
				e11.setText(cursor2.getString(0));
				c5.setVisibility(View.INVISIBLE);
				et5.setVisibility(View.VISIBLE);
				et5.setText(Integer.toString(cursor2.getInt(1)));
				}
				if(count==5)
				{
				e12.setText(cursor2.getString(0));
				c6.setVisibility(View.INVISIBLE);
				et6.setVisibility(View.VISIBLE);
				et6.setText(Integer.toString(cursor2.getInt(1)));
				}
				count++;
				cursor2.moveToNext();
				}	
			}
			else
			{
		
			for(int k=0;k<cursor2.getCount();k++)
			{
			
			if(count==0)
			{
			temp=cursor2.getString(0);
			e7.setText(temp);
			if(cursor2.getString(1).contentEquals("1"))
			{
			c1.setChecked(true);	
			}
			}
			if(count==1)
			{
			e8.setText(cursor2.getString(0));
			if(cursor2.getString(1).contentEquals("1"))
			{
			c2.setChecked(true);	
			}
			}
			if(count==2)
			{
			e9.setText(cursor2.getString(0));
			if(cursor2.getInt(1)==1)
			{
			c3.setChecked(true);	
			}
			}
			if(count==3)
			{
			e10.setText(cursor2.getString(0));
			if(cursor2.getInt(1)==1)
			{
			c4.setChecked(true);	
			}
			}
			if(count==4)
			{
			e11.setText(cursor2.getString(0));
			if(cursor2.getInt(1)==1)
			{
			c5.setChecked(true);	
			}
			}
			if(count==5)
			{
			e12.setText(cursor2.getString(0));
			if(cursor2.getInt(1)==1)
			{
			c6.setChecked(true);	
			}
			}
			count++;
			cursor2.moveToNext();
			}
			}
			}
			else
			{
				j++;
				if(j<importforquiz.count)
				{
					fun_cal();
				}
				else
				{
					Intent i=new Intent(edit3.this,importforquiz.class);
					startActivity(i);
				}
			}}
    catch(Exception e)
    {
e.printStackTrace();
    }
    }
       
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		// TODO Auto-generated method stub
		
		e1.setText("");
		
		//e3.setText("");

/*	   	 c1.setChecked(false);
         c2.setChecked(false);
         c3.setChecked(false);
         c4.setChecked(false);
         c5.setChecked(false);
         c6.setChecked(false);
	*/	
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
		//	e13.setText("2");
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
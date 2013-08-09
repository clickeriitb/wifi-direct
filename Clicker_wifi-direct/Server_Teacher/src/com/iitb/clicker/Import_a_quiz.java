package com.iitb.clicker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class Import_a_quiz extends Activity implements OnClickListener,OnCheckedChangeListener  {
    /** Called when the activity is first created. */
	
	Button bimport,bback,bsearch;
	 SQLiteDatabase db;
	 TextView t1;
	 int k=0,flag=0;
	
	 Cursor cursor,cursor2,cursor3=null;
	 int id=0,total=0;
	 static int Quiz_id;
	 TextView tv;
	 CheckBox cb;
	 List<TextView> alltvs;
	 List<CheckBox> allcbs;
	 EditText esub,eclass;
	 TableLayout tl;
	 static int allints[];
    
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.importquiz);
        esub=(EditText)findViewById(R.id.editText1);
        eclass=(EditText)findViewById(R.id.editText2);
        try
        {
        try
        {
            db=openOrCreateDatabase("clicker_db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
         		  tl=(TableLayout)findViewById(R.id.tableLayout1);		
				 t1=(TextView)findViewById(R.id.textView3);
			    
				 bimport=(Button)findViewById(R.id.button4);
			        bback=(Button)findViewById(R.id.button5);
			        bsearch=(Button)findViewById(R.id.button3);
			        
			        alltvs= new ArrayList<TextView>();
			      
			        allcbs = new ArrayList<CheckBox>();
				
			cursor2=db.rawQuery("SELECT COUNT(QUIZ_ID) FROM QUIZ",null);
			cursor2.moveToFirst();
			total=cursor2.getInt(0);
			
		cursor2.close();	
				allints=new int[total];
				
       cursor=db.rawQuery("SELECT TITLE,QUIZ_ID FROM QUIZ",null);
        
try
{
		if(cursor.moveToFirst())
		{
			do
			{
				
				String s;
				s=cursor.getString(0);
				
			int temp;
       


        TableRow tr1 = new TableRow(this);
          tr1.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
         temp=cursor.getInt(cursor.getColumnIndex("QUIZ_ID"));
    
         allints[k]=temp;
         k++;
          tv = new TextView(this);
          alltvs.add(tv);
         
          cb=new CheckBox(this);
          allcbs.add(cb);
          tv.setText(s); 
          tv.setTextColor(R.color.black);

          tr1.addView(cb);
          tr1.addView(tv);
          tr1.setId(id);
          id++;
          tl.addView(tr1, new TableLayout.LayoutParams(
                  LayoutParams.FILL_PARENT,
                 LayoutParams.WRAP_CONTENT));
			}while(cursor.moveToNext());
		}
		cursor.close();
}
catch(Exception e)
{
	e.printStackTrace();
}
	    
	
/*CODE FOR "LOAD THE QUIZ" BUTTON*/		
		bimport.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				total=allcbs.size();
		for(int j=0;j<total;j++)
			{
				if(allcbs.get(j).isChecked())
				{
					Quiz_id=allints[j];
					Intent i=new Intent(Import_a_quiz.this,preview.class);
					startActivity(i);
					flag=1;
				}
							
			}
		if(flag==0)
		{
				Toast.makeText(Import_a_quiz.this, "CHECK SOME QUIZ", 100000).show();
		}		
							
			}
		});
		/*CODE FOR BACK BUTTON*/
bback.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*MOVE TO MENU PAGE*/
				Intent i=new Intent(Import_a_quiz.this,QuizActivity.class);
				startActivity(i);
				
			}
				
				
		});

/*CODE FOR TEHE SEARCH BUTTON*/
bsearch.setOnClickListener(new OnClickListener() {
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*REMOVING ALL THE QUESTIONS PREVIOUSLY*/
		tl.removeAllViews();
		try
		{
		String s1=esub.getText().toString();
		String s2=eclass.getText().toString();
		k=0;
		allcbs.clear();
		alltvs.clear();
		if(s1.length()==0 && s2.length()==0)
		{
/*IN THE CASE TH EFIELDS ARE EMPTY AND SEARCH BUTTON IS PRESSED THE ACTIVITY SHOUL RESTART*/
			cursor3=db.rawQuery("SELECT * FROM QUIZ WHERE QUIZ_ID<"+0, null);
			onCreate(null);
		}
		else if(s1.length()==0 && s2.length()>0)
	{
		cursor3=db.rawQuery("SELECT DISTINCT QUIZ.QUIZ_ID,QUIZ.TITLE FROM QUIZ,QUESTION,QQ_MAP WHERE QUESTION.CLASS='"+s2+"' AND QQ_MAP.Q_ID=QUESTION.Q_ID AND QQ_MAP.QUIZ_ID=QUIZ.QUIZ_ID ", null);
	}
	else if(s2.length()==0 && s1.length()>0)
	{
		cursor3=db.rawQuery("SELECT DISTINCT QUIZ.QUIZ_ID,QUIZ.TITLE FROM QUIZ,QUESTION,QQ_MAP WHERE QUESTION.SUBJECT='"+s1+"' AND QQ_MAP.Q_ID=QUESTION.Q_ID AND QQ_MAP.QUIZ_ID=QUIZ.QUIZ_ID ", null);
	}
	else
	{
		cursor3=db.rawQuery("SELECT DISTINCT QUIZ.QUIZ_ID,QUIZ.TITLE FROM QUIZ,QUESTION,QQ_MAP WHERE QUESTION.SUBJECT='"+s1+"' AND QUESTION.CLASS='"+s2+"' AND QQ_MAP.Q_ID=QUESTION.Q_ID AND QQ_MAP.QUIZ_ID=QUIZ.QUIZ_ID ", null);
	}
		}
		catch (Exception e) {
	e.printStackTrace();		
		}
	if(cursor3.getCount()==0)
	{
		Toast.makeText(Import_a_quiz.this,"No matches found",1000);
	}
		try
		{
				if(cursor3.moveToFirst())
				{
					int j=0;
					id=0;
					do
					{
						String s;
						s=cursor3.getString(1);
					int temp;
				        TableRow tr1 = new TableRow(Import_a_quiz.this);
		          tr1.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		         temp=cursor3.getInt(0);
		         allints[j]=temp;
		         j++;
		          tv = new TextView(Import_a_quiz.this);
		          alltvs.add(tv);
		         
		          cb=new CheckBox(Import_a_quiz.this);
		          allcbs.add(cb);
		          tv.setText(s); 
		          tv.setTextColor(R.color.black);

		          tr1.addView(cb);
		          tr1.addView(tv);
		          tr1.setId(id);
		          id++;
		          tl.addView(tr1, new TableLayout.LayoutParams(
		                  LayoutParams.FILL_PARENT,
		                 LayoutParams.WRAP_CONTENT));
					}while(cursor3.moveToNext());
				}
				cursor3.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
});
		
		
	
			}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		int j = 0;
	for(int i=0;i<allcbs.size();i++)
	{
		if(allcbs.get(i).isChecked())
		{
			j=i;
		}
		
		allcbs.get(i).setChecked(false);
			
	}
	allcbs.get(j).setChecked(true);
	}
}
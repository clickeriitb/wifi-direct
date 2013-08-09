
package com.iitb.clicker;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class preview extends Activity implements OnClickListener  {
    /** Called when the activity is first created. */
	static int qid;
	Button bback,bsave,bedit,bselect,bdeselect,bstart,bnew,bimport;
	 SQLiteDatabase db;
	 TextView t1;
	 EditText et;
	 Cursor cursor,cursor2,cursor3;
	 static int allints[];
	 int id=0,total;
	static int k=0;
	int check=0;
	 TextView tv;
	 CheckBox cb;
	 List<TextView> alltvs;
	 static List<CheckBox> allcbs;

	 
    @SuppressLint("ResourceAsColor")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
     tv=(TextView)findViewById(R.id.textView3);
            try
        {
        
        	db=openOrCreateDatabase("clicker_db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
        
        }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
     			cursor2=db.rawQuery("SELECT COUNT(QUIZ_ID) FROM QUIZ",null);
				cursor2.moveToFirst();
				total=cursor2.getInt(0);
				qid=Import_a_quiz.Quiz_id;
		
       
        bselect=(Button)findViewById(R.id.button3);
        
        bback=(Button)findViewById(R.id.button9);
        bsave=(Button)findViewById(R.id.button7);
        bedit=(Button)findViewById(R.id.button6);
        bimport=(Button)findViewById(R.id.button4);
       
        bstart=(Button)findViewById(R.id.button8);
        bnew=(Button)findViewById(R.id.button5);
        
         alltvs= new ArrayList<TextView>();
         allcbs = new ArrayList<CheckBox>();

     try
     {
         cursor=db.rawQuery("SELECT QUESTION.QUESTION,QUESTION.Q_ID FROM QUESTION,QQ_MAP WHERE QQ_MAP.QUIZ_ID="+qid+" AND QQ_MAP.Q_ID=QUESTION.Q_ID",null);
  
         allints=new int[cursor.getCount()];
         k=0;
         check=0;
     }
    
         catch(Exception e){
        	 e.printStackTrace();
         }
         try
         {
         if(cursor.moveToFirst())
		{
        	do
			{
				try
				{
				String s;
				int temp;
				check++;
				s=cursor.getString(0);
				
				  temp=cursor.getInt(1);
				    
			         allints[k]=temp;
				k++;
				
        TableLayout tl=(TableLayout)findViewById(R.id.tableLayout1);
        

        TableRow tr1 = new TableRow(this);
          tr1.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
          
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
				}
				catch(Exception e)
				{
				e.printStackTrace();
				}
			}while(cursor.moveToNext());
		}
		cursor.close();
		try
		{
bback.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try
				{
				Intent i=new Intent(preview.this,Import_a_quiz.class);
				startActivity(i);
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
bsave.setOnClickListener(new OnClickListener() {
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		db.execSQL("DELETE FROM QQ_MAP WHERE QUIZ_ID="+qid);
		for(int i=0;i<allcbs.size();i++)
		{
			if(allcbs.get(i).isChecked())
			{
				db.execSQL("INSERT INTO QQ_MAP(QUIZ_ID,Q_ID,MARKS) VALUES ("+qid+","+allints[i]+",2)");
			}
		}
		Toast.makeText(preview.this, "Changes Saved", 5000).show();
		
		
	}
		
		
});

bimport.setOnClickListener(new OnClickListener() {
	
	
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	Intent i=new Intent(preview.this,importforquiz.class);	
	startActivity(i);
	}
});

bedit.setOnClickListener(new OnClickListener() {
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent(preview.this,Edit_question.class);
		startActivity(i);
		
	}
		
		
});
bnew.setOnClickListener(new OnClickListener() {
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent(preview.this,new_ques.class);
		startActivity(i);
		
	}
		
});


bselect.setOnClickListener(new OnClickListener() {
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		for(int j=0;j<allcbs.size();j++)
		{
			allcbs.get(j).setChecked(true);
			
				}
			
		}
		
});


bstart.setOnClickListener(new OnClickListener() {
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(preview.this)
		.setTitle("Alert")
		.setMessage("Do you really want to Start the Quiz")
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dlg, int sumthin) {
			
		}
		}).setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				db.execSQL("DELETE FROM QQ_MAP WHERE QUIZ_ID="+qid);
				for(int i=0;i<allcbs.size();i++)
				{
					if(allcbs.get(i).isChecked())
					{
						db.execSQL("INSERT INTO QQ_MAP(QUIZ_ID,Q_ID,MARKS) VALUES ("+qid+","+allints[i]+",2)");
					}
				}
				Intent i=new Intent(preview.this,data_retrieval.class);
				i.putExtra("quiz_id", Integer.toString(qid));
				startActivity(i);
						
				}
				})
		.show();
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
}
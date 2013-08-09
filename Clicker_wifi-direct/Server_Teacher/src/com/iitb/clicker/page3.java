package com.iitb.clicker;
//Displaying questions just created at first page
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class page3 extends Activity{
	
	int n;
	static int count;
	SQLiteDatabase db;
	TableLayout r;
	Button save,edit;
	
	 static TextView []a;
     static CheckBox []b;
     static int []quesid;
	public void onCreate(Bundle savedInstanceState) {  
        count=0;       
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main4);
        save=(Button)findViewById(R.id.button2);
        edit=(Button)findViewById(R.id.button1);
        r=(TableLayout)findViewById(R.id.tableLayout1);
       
       try
       {
        db=openOrCreateDatabase("clicker_db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
       }
       catch(SQLException e)
        {
        
        }
        int i=QuizActivity.max_quesid;
       
        String s="SELECT COUNT(*) FROM QUESTION WHERE Q_ID>"+i;
        Cursor c=db.rawQuery(s, null);
        c.moveToFirst();
        count= c.getInt(0);
        c.close();
        String st;
        
        
        a=new TextView[count];
        b=new CheckBox[count];
        quesid=new int[count];
        Cursor d=db.rawQuery("select QUESTION,Q_ID from QUESTION where Q_ID>"+i,null);
        d.moveToFirst();
       //Loop to display the questions
         for (int j = 0; j < count; j++) 
         {
            final TextView rowTextView = new TextView(this);
            rowTextView.setTextColor(R.color.black);
            final CheckBox cb=new CheckBox(this);
            st=d.getString(0);
            rowTextView.setText(st);
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
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
       
         //Save button simply takes the teacher back to first page
         save.setOnClickListener(new OnClickListener(){

			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(page3.this,QuizActivity.class);
				startActivity(i);
			}
        	 
         });
         //Edit button for making changes to questions selected among created just now
         edit.setOnClickListener(new OnClickListener(){
        	 
 			public void onClick(View arg0) {
        		
					Intent i=new Intent(page3.this,edit2.class);
					startActivity(i);
				
					
         }
         });
        
       
}
}

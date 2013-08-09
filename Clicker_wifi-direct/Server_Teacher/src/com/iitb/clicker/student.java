package com.iitb.clicker;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.util.Log;
import android.view.*;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;


public class student extends Activity implements View.OnClickListener{
	Bundle b_get,b_post;
	Intent i_post,i_get;
	Button back_student;
	
	TableLayout table_student;
	SQLiteDatabase db ;
	
	String query_student,query_student_name;
	Cursor c_student,c_name;
	String name;
	
	int quiz=0,no_st=0,st_id,i=0,j=0;
	
	TableRow t[];
	TextView tv[];
	Button b;
	
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.studentlist);
	        
	        b=(Button)findViewById(R.id.bt_);
	        b.setText("Detailed Report");
	        
	        b_get=getIntent().getExtras();
		    quiz= b_get.getInt("quiz_id");
		    
		    i_post=new Intent(this,student_graph.class);
		   
	        
	        table_student=(TableLayout)findViewById(R.id.table_student);
	        
	        back_student=(Button)findViewById(R.id.back_student);
            back_student.setOnClickListener(this);
            try{
	            InputStream myInput = this.getAssets().open("clicker_db");
	            
	        	// Path to the just created empty db
	        	String outFileName = "/data/data/com.reports/databases/clicker_db";
	     
	        	//Open the empty db as the output stream
	        	OutputStream myOutput = new FileOutputStream(outFileName);
	     
	        	//transfer bytes from the inputfile to the outputfile
	        	byte[] buffer = new byte[1024];
	        	int length;
	        	while ((length = myInput.read(buffer))>0){
	        		myOutput.write(buffer, 0, length);
	        	}
	     
	        	//Close the streams
	        	myOutput.flush();
	        	myOutput.close();
	        	myInput.close();
	            }catch(Exception e)
	            {
	            	
	            }
	        //database fetching data
	        
	        
	        
	            db=openOrCreateDatabase("clicker_db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
	        //db = openOrCreateDatabase("/data/data/com.reports/databases/clicker_db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	        
	        query_student="select distinct STUD_ID from QUIZ_SCORE  where QUIZ_ID='"+quiz+"'";
            c_student=db.rawQuery(query_student, null);
            c_student.moveToFirst();
            no_st=c_student.getCount();
            
            tv=new TextView[(no_st+1)*4];
            t=new TableRow[no_st+1];
           // b=new Button[no_st+1];
            
            for(j=0;j<=3;j++)
            {
            	tv[j]=new TextView(this);
            	tv[j].setTextColor(Color.BLACK);
            
            }
           // b[0]=new Button(this);
            
            t[0]=new TableRow(this);
        	tv[0].setTextSize(20);
        	tv[1].setTextSize(20);
        	tv[2].setTextSize(20);
        	tv[3].setTextSize(20);
            tv[0].setText("Sr.No"+'\t');
            tv[1].setText("Name"+'\t');
            tv[2].setText("Id"+'\n');
           // tv[3].setText("Marks"+'\n');
            
            
            Log.d("abc","hii");
            
            for(j=0;j<=3;j++)
              t[0].addView(tv[j]);
           // t[0].addView(b[0]);
            
            
          table_student.addView(t[0]) ;
          
          for(i=1;i<=(no_st);i++)
          {
            	st_id=c_student.getInt(c_student.getColumnIndex("STUD_ID"));
            	query_student_name="select NAME from STUDENT where STUD_ID='"+st_id+"'";
            	c_name=db.rawQuery(query_student_name, null);
            	c_name.moveToFirst();
            	c_student.moveToNext();
                for(j=0;j<=3;j++)
                {
                	tv[(i*3)+j]=new TextView(this);
                	tv[j+(i*3)].setTextColor(Color.BLUE);
                	tv[j+(i*3)].setTextSize(20);
                	
                }
              //  b[i]=new Button(this);
              //  b[i].setTextColor(Color.BLUE);
                //b[i].setTextSize(6);
                t[i]=new TableRow(this);
                name=c_name.getString(c_name.getColumnIndex("NAME"));
                
                tv[i*3].setText(""+(i)+'\t');
                tv[1+(i*3)].setText(name+'\t'+'\t'+'\t');
                tv[2+i*3].setText(""+st_id+'\t');
               // tv[3+(i*3)].setText("marks"+'\t'+'\t'+'\t');
               // b[i].setText(""+st_id);
               // b[i].setId(st_id);
               // b[i].setOnClickListener(this);
                Log.d("abc","hii");
                
                for(j=0;j<=3;j++)
                {
                	t[i].addView(tv[(i*3)+j]);
                }
             //   t[i].addView(b[i]);
                b.setOnClickListener(this);
                table_student.addView(t[i]) ;
              //  table_student.addView(b);
            	
            }
         
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
          // TODO Auto-generated method stub
          super.onActivityResult(requestCode, resultCode, data);
    }
	
	 public void onClick(View v) 
     {     
		   
		   if(v.getId()==R.id.back_student)
			{
			    db.close();
			    Intent i_back = new Intent(this, ReportsActivity.class);
			    setResult(RESULT_OK, i_back);
			    finish();
			    
			}
		   else
		   {   i_post.putExtras(b_get);
			   startActivity(i_post);
		   }
		  
			
	 }

}

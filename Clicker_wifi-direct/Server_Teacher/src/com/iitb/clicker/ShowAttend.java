package com.iitb.clicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowAttend extends Activity implements OnClickListener {
	
	Button back;
	TableLayout table;
	Database db;
	TextView date;
	String[] RollList,NameList;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showattend);
		/*total_question=(TextView) findViewById(R.id.total_question);
        marks_obtained=(TextView) findViewById(R.id.marks_obtained);
        correct_answers=(TextView) findViewById(R.id.correct_answers);
        total_marks=(TextView) findViewById(R.id.total_marks);*/
		db=new Database(this);
        back=(Button) findViewById(R.id.back_result);
        back.setOnClickListener(this);
        date=(TextView) findViewById(R.id.date);
        table=(TableLayout) findViewById(R.id.attend_table);
        Date d=new Date();
        SimpleDateFormat sdf=new  SimpleDateFormat("dd/MM/yyyy");
        String Current_date=sdf.format(d);
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
       
        date.setText(currentDateTimeString);
      
        db.getAttendance(Current_date);
        NameList=db.NameList;
        RollList=db.RollList;
      /*  Bundle bundle=this.getIntent().getExtras();
        total_question.setText(bundle.getString("total_question"));
        total_marks.setText(bundle.getString("total_marks"));
        marks_obtained.setText(bundle.getString("marks_obtained"));
        correct_answers.setText(bundle.getString("correct_count"));
        AnsMarked=bundle.getStringArray("ans_marked");
        CorrectAns=bundle.getStringArray("corr_marked");*/
        
        
      for(int i=0;i<NameList.length;i++)
       {
    	  Log.i(NameList[i],RollList[i]);
    	  
        Log.i("inside resuls"+i, "iiiiiiiiiiiiii");
        TableRow tr=new TableRow(this);
		
		
		
		tr.setGravity(Gravity.CENTER);
        LayoutParams params = new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);

        tr.setLayoutParams(params);

        

		
		
		TextView roll=new TextView(this);
		roll.setBackgroundResource(R.drawable.cell_shape);
		roll.setText(RollList[i]);
		roll.setGravity(Gravity.CENTER);
		roll.setPadding(5, 5, 5, 5);
		roll.setWidth(100);

		TextView name=new TextView(this);
		name.setBackgroundResource(R.drawable.cell_shape);
		name.setText(NameList[i]);
		name.setGravity(Gravity.CENTER);
		name.setPadding(5, 5, 5, 5);
		name.setWidth(100);
		
		
		tr.addView(roll);
		tr.addView(name);
		
		table.addView(tr,params);
		
       }
		
	
	}
	public void onClick(View v) {
		
		
		HomePage.socket.close();
		HomePage.universe=false;
		Intent i=new Intent(this,HomePage.class);
		startActivity(i);
		finish();
		
	}
	

		
		
	}
	



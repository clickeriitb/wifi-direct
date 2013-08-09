package com.iitb.clicker;

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

public class Results extends Activity implements OnClickListener {
	
	TextView total_question,marks_obtained,correct_answers,total_marks;	
	Button back;
	String [] AnsMarked; 
	String [] CorrectAns;
	TableLayout table;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		total_question=(TextView) findViewById(R.id.total_question);
        marks_obtained=(TextView) findViewById(R.id.marks_obtained);
        correct_answers=(TextView) findViewById(R.id.correct_answers);
        total_marks=(TextView) findViewById(R.id.total_marks);
        back=(Button) findViewById(R.id.back_result);
        back.setOnClickListener(this);
        table=(TableLayout) findViewById(R.id.marks_table);
        
        
        
        Bundle bundle=this.getIntent().getExtras();
        total_question.setText(bundle.getString("total_question"));
        total_marks.setText(bundle.getString("total_marks"));
        marks_obtained.setText(bundle.getString("marks_obtained"));
        correct_answers.setText(bundle.getString("correct_count"));
        AnsMarked=bundle.getStringArray("ans_marked");
        CorrectAns=bundle.getStringArray("corr_marked");
        
        
       for(int i=0;i<AnsMarked.length;i++)
       {
    	   int j=i+1;
        Log.i("inside resuls"+i, "iiiiiiiiiiiiii");
        TableRow tr=new TableRow(this);
		
		
		
		tr.setGravity(Gravity.CENTER);
        LayoutParams params = new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);

        tr.setLayoutParams(params);

        

		
		
		TextView ques_no=new TextView(this);
		ques_no.setBackgroundResource(R.drawable.cell_shape);
		ques_no.setText(new Integer(j).toString());
		ques_no.setGravity(Gravity.CENTER);
		ques_no.setPadding(5, 5, 5, 5);
		ques_no.setWidth(100);

		TextView ans_marked=new TextView(this);
		ans_marked.setBackgroundResource(R.drawable.cell_shape);
		ans_marked.setText(AnsMarked[i]);
		ans_marked.setGravity(Gravity.CENTER);
		ans_marked.setPadding(5, 5, 5, 5);
		ans_marked.setWidth(100);
		
		TextView corr_ans=new TextView(this);
		corr_ans.setBackgroundResource(R.drawable.cell_shape);
		corr_ans.setText(CorrectAns[i]);
		corr_ans.setGravity(Gravity.CENTER);
		corr_ans.setPadding(5, 5, 5, 5);
		corr_ans.setWidth(100);
		tr.addView(ques_no);
		tr.addView(ans_marked);
		tr.addView(corr_ans);
		table.addView(tr,params);
		
       }
		
		
	}
	public void onClick(View v) {
		if((!(HomePage.socket==null))||(!(HomePage.socket.isClosed()))||(HomePage.socket.isBound()))
		{HomePage.socket.close();}// check before closing sockets
		HomePage.universe_home=false;
		Intent i=new Intent(this,HomePage.class);
		startActivity(i);
		finish();
		
	}
	

}

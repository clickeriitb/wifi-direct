package com.iitb.clicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

@SuppressWarnings("unused")
public class ReportsActivity extends Activity implements View.OnClickListener{
	private TextView quiz_wise,st_wise,batch_wise;
	Intent i_post,i_get;
	int quiz;
	Button bc,btn1,btn2;
	
	   public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.report);
		
		    quiz_wise=(TextView)findViewById(R.id.quiz);
		    st_wise=(TextView)findViewById(R.id.student);
		    //batch_wise=(TextView)findViewById(R.id.batch);
		
		    quiz_wise.setOnClickListener(this);
		    st_wise.setOnClickListener(this);
		    //batch_wise.setOnClickListener(this);
		    Bundle b=getIntent().getExtras();
		    quiz= b.getInt("quiz_id");
		    bc=(Button)findViewById(R.id.button_bck);
		    bc.setOnClickListener(this);
		    btn1=(Button)findViewById(R.id.button1);
		    btn1.setOnClickListener(this);
		    btn2=(Button)findViewById(R.id.button2);
		    btn2.setOnClickListener(this);
		    
	}
	   protected void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
	          // TODO Auto-generated method stub
	          super.onActivityResult(requestCode, resultCode, data);
	    }
	   public void onClick(View v)
		{
			if(v.getId()==R.id.button1)
			{i_post=new Intent(this,GraphViewDemo.class); 
			Bundle b=new Bundle();
			b.putInt("quiz_id",quiz);
			i_post.putExtras(b);startActivity(i_post);
			
	   }
			 
			else if(v.getId()==R.id.button_bck) 
				
			{HomePage.universe=false;
			if((!(HomePage.socket==null))||(!(HomePage.socket.isClosed()))||(HomePage.socket.isBound()))
			{HomePage.socket.close();}
				Intent i_back = new Intent(this,HomePage.class);
			setResult(RESULT_OK, i_back);
			finish();}
			
			else if(v.getId()==R.id.button2)
			{ 
				 i_post=new Intent(this,student.class);
			     Bundle b=new Bundle();
			     b.putInt("quiz_id",quiz);
			     i_post.putExtras(b);
			     startActivity(i_post);
			}
	   }
	
		
	
  }


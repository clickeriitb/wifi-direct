package com.iitb.clicker;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableLayout.LayoutParams;
import com.iitb.clicker.GraphView.*;



public class GraphViewDemo extends Activity implements OnTabChangeListener , OnClickListener
{
	         LinearLayout  l;//in which graph is being added
             TabHost tabHost;
             TextView header;
             Button bck;
             TextView t[]=new TextView[12];
             SQLiteDatabase db ;
             BarGraphView graphView;
	         private String query_student,query_question,query_st_attnd,query_insert,query_q_txt,question,query_ans_txt,query_marksquerya,queryb,querya,st_id;
	         Cursor c_student,c_marks,c_q_txt,c_ans_txt,c_question,c_st_attnd,c_insert,c_stud_st;
	         int quiz=1,count_option=0,count_graph=0,count4=0,i,color=0,st=0,st_per=0,no_q,option,present_q_id=0;
	         public String answers[]=null,ans_text[]=null,results[]=null;
             int marks[]=null,ques_map[]=null,correctness[]=null,stu_count[]=null,ans,ans1,len=0,st_count=0;
             Double perArray[]=null;
             Double per=0.0;
             String mapping[]={"A","B","C","D","E"};
	         int flag=0;
	         int ansarr[][]=null;//
	   	    
	   	     String ansss="";
	        @Override
	        public void onCreate(Bundle savedInstanceState)
	        {  
		              super.onCreate(savedInstanceState);
		              setContentView(R.layout.tab);
		              
		              
		              
		              Bundle b=getIntent().getExtras();
		  		      quiz= b.getInt("quiz_id");
		  		      
		  		      header=(TextView)findViewById(R.id.textView_header1);
		              
		              header.append('\t'+"QUIZ"+new Integer(quiz).toString());
		  		      
		              Log.d("quiz",new Integer(quiz).toString());
		              bck=(Button)findViewById(R.id.button_bk);  
		              bck.setOnClickListener(this);
		              
		              l=(LinearLayout)findViewById(R.id.layout);
		              tabHost=(TabHost) findViewById(R.id.tabhost);
		
		              t[0]=(TextView)findViewById(R.id.question);
		              t[6]=(TextView)findViewById(R.id.textView6);
		
		                         
		              t[1]=(TextView)findViewById(R.id.textView1);
		              t[2]=(TextView)findViewById(R.id.textView2);
		              t[3]=(TextView)findViewById(R.id.textView3);
		              t[4]=(TextView)findViewById(R.id.textView4);
		              t[5]=(TextView)findViewById(R.id.textView5);
		
		              t[7]=(TextView)findViewById(R.id.textView7);
		              t[8]=(TextView)findViewById(R.id.textView8);
		              t[9]=(TextView)findViewById(R.id.textView9);
		              t[10]=(TextView)findViewById(R.id.textView10);
		              t[11]=(TextView)findViewById(R.id.textView11);
		             
		
		              //bck.setOnClickListener(this);
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
		              
	                //  db = openOrCreateDatabase("/data/data/com.reports/databases/clicker_db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
                      query_student="select distinct STUD_ID  from QUIZ_SCORE  where QUIZ_ID='"+quiz+"'";
                      c_student =db.rawQuery(query_student, null); 
                      st=c_student.getCount();
        
                      query_question="select distinct Q_ID from QQ_MAP where QUIZ_ID='"+quiz+"'";
                      c_question=db.rawQuery(query_question, null);
                      c_question.moveToFirst();
                      no_q = c_question.getCount();
                                
        
                       ques_map=new int[no_q];
                    
                       tabHost.setup();
                       for(int i=0;i<no_q;i++)
                       {  int j=i+1;
                                    ques_map[i]=c_question.getInt(c_question.getColumnIndex("Q_ID"));
                                    c_question.moveToNext();
        	                        tabHost.addTab(tabHost.newTabSpec(new Integer(i).toString())
                                    .setIndicator(createIndicatorView(tabHost, "QUESTION"+j,null))
                                    .setContent(android.R.id.tabcontent));
                       }
                      
                     
                      tabHost.setOnTabChangedListener(this);
                    
                     tabHost.setCurrentTab(no_q-1);
                     t[6].setText("Q."+no_q);
                     try{
		             all_activity(no_q);
                     }
                     catch(Exception e){}
                     Log.d("mmmmmmmmmmmmmmmmm","hii");
		
	         }
	        
	        
	        
	        
             public void onTabChanged(String tabId) 
             {       for(i=0;i<=11;i++)
         		          t[i].setText("");
            	      present_q_id=ques_map[Integer.parseInt(tabId)];
        	          Log.d("abc",new Integer(present_q_id).toString());
        	          t[6].setText("Q."+(Integer.parseInt(tabId)+1));
        	          try{
        	          all_activity(present_q_id);
        	          }
        	          catch(Exception e)
        	          {
        	        	  
        	          }
        	 }
        

	   private View createIndicatorView(TabHost tabHost, CharSequence label, Drawable icon)
	   {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View tabIndicator = inflater.inflate(R.layout.tab_indicator1,tabHost.getTabWidget(), // tab widget is the parent
	                false); // no inflate params
	        final TextView tv = (TextView) tabIndicator.findViewById(R.id.title);
	        tv.setText(label);
	        return tabIndicator;
	   }

    public void all_activity(int present_q_id) throws Exception
    {
    	
    	
    	query_q_txt="select QUESTION from QUESTION where Q_ID='"+present_q_id+"'";
        c_q_txt=db.rawQuery(query_q_txt, null);
          
        c_q_txt.moveToFirst();
        question=c_q_txt.getString(c_q_txt.getColumnIndex("QUESTION"));

        t[0].setText(question);
        
          
        query_ans_txt="select ANSWER,A_ID,CORRECTNESS from ANSWER where Q_ID='"+present_q_id+"'";
        c_ans_txt=db.rawQuery(query_ans_txt, null);
/*imp**/            
        ans_text=new String[c_ans_txt.getCount()];
        answers=new String[c_ans_txt.getCount()];
        results=new String[c_ans_txt.getCount()];
        perArray=new Double[c_ans_txt.getCount()];
        correctness =new int[c_ans_txt.getCount()];
        stu_count =new int[c_ans_txt.getCount()]; 
        Log.d("no of options",new Integer(c_ans_txt.getCount()).toString());
          
        i=0;//important to set zero variables;
        if(c_ans_txt.moveToFirst())
        do
        {    ans_text[i]=c_ans_txt.getString(c_ans_txt.getColumnIndex("ANSWER"));
	           correctness[i]=c_ans_txt.getInt(c_ans_txt.getColumnIndex("CORRECTNESS"));
             if(correctness[i]==0)
             {
  	            t[i+1].setTextColor(Color.rgb(215, 39, 45));
  	            t[i+7].setTextColor(Color.rgb(215, 39, 45));
             }
             else
             {
  	             t[i+1].setTextColor(Color.rgb(11, 165, 41));
  	             t[i+7].setTextColor(Color.rgb(11, 165, 41));
             }
	           t[i+1].setText(ans_text[i]);
	           answers[i]=mapping[c_ans_txt.getInt(c_ans_txt.getColumnIndex("A_ID"))-1];
	           Log.d("op",answers[i]);
	           Log.d("op",ans_text[i]);
	           t[i+7].setText(answers[i]);
	           i++;
	      }
       while(c_ans_txt.moveToNext());

       

      ans1=0;
      ans=0;
      st_per=0;
      count_option=0;
      len=0;
      st_count=0;
	     
      querya="select distinct STUD_ID from QUIZ_SCORE where QUIZ_ID='"+quiz+"' and Q_ID= '"+present_q_id+"'";
	  c_stud_st=db.rawQuery(querya,null);
	  st=c_stud_st.getCount();
	     
     ansarr=new int[c_stud_st.getCount()][6];
     
	  if(c_stud_st.moveToFirst())
		 do
		 {  
			 ans1=0;
			 st_id="";
			 st_id = c_stud_st.getString(c_stud_st.getColumnIndex("STUD_ID"));
		     queryb="select distinct A_ID from QUIZ_SCORE where QUIZ_ID='"+quiz+"' and Q_ID= '"+present_q_id+"' and STUD_ID='"+st_id+"'"; 
			 Cursor c_stud_ans=db.rawQuery(queryb,null);
		     if(c_stud_ans.moveToFirst())
		      {
		           ans1 =c_stud_ans.getInt(c_stud_ans.getColumnIndex("A_ID"));
		           len=0;
		           while(ans1>0)
		           {
		        	   
		                ansarr[st_count][len]=ans1%10;
		                Log.d("anssppppssssss",new Integer(ans1%10).toString());
		                ans1=ans1/10;
		                len++;
		           }    
		      }
		      st_count++;
		     
		   }while(c_stud_st.moveToNext());
		  Log.d("students",new Integer(st_count).toString());
		  st_per=0; 
		  per=0.0;
		
		 
		 /* for(i=0;i<6;i++)
		     {
		    	 perArray[i]=0.0;
		     }*/
		  
          if(c_ans_txt.moveToFirst()) 
          {
            do 
            {    
          	       ans=c_ans_txt.getInt(c_ans_txt.getColumnIndex("A_ID")) ;
          	       Log.d("ans",new Integer(ans).toString());
          	       
          	        st_per=0;
          	        per=0.0;
                    int k,l1;
       		        for(k=0;k<st_count;k++)
       			    for(l1=0;l1<ansarr[k].length;l1++)
       			    {
       				         if(ans==ansarr[k][l1])
       				          {
       					             st_per++;
       					             break;
       				          }
       				 
       			    }
          	     
                  stu_count[count_option]=st_per;
                  Log.d("stu",new Integer(stu_count[count_option]).toString());
                  if(st>0)
                  per=(double)((st_per*100)/st);
                  else
                  per=0.0;
                  Log.d("per",new Double(per).toString());
                  perArray[count_option]=per;
                  count_option++;
             }while (c_ans_txt.moveToNext());
       }



         if(count_graph>0)
	          l.removeView(graphView);
         graphView = new BarGraphView(this,"",correctness,stu_count,flag);
         GraphViewData []g=new GraphViewData[perArray.length];
         for(int i=0;i<answers.length;i++)
	          g[i]=new GraphViewData(answers[i], perArray[i]);
        
         graphView.addSeries(new GraphViewSeries("Right","Wrong",null,g));
         l.addView(graphView,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
         count_graph++;
    }


	public void onClick(View v) {
		if(v.getId()==R.id.button_bk)
		{   db.close();
			Intent i_back = new Intent(this,ReportsActivity.class);
			setResult(RESULT_OK, i_back);
			finish();
		}
		
	}
}

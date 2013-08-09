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



public class student_graph extends Activity implements OnTabChangeListener , OnClickListener
{
	       LinearLayout  l,l_st;//in which graph is being added
           TabHost tabHost;
           Button bck;
           TextView header;
             
            BarGraphView graphView;
            Intent i_post,i_get;
         	Button back;
         	TableLayout table;
          	SQLiteDatabase db ;
         	String query_question,query_question_st,query_q_txt,query_ans_correct,query_op,query_op_txt,question,ans_marked,query_marks,correctness,query_student,query_student_name;
         	Cursor c_question,c_question_st,c_q_txt,c_ans_correct,c_op,c_op_txt,c_marks,c_student,c_student_name;
         	int quiz=0,st_id,i=0,q_id=0,op_id,j=0,marks,total=0,ans_correct,no_st,no_q=0,mark_array[]=null,mark_arraycorr[]=null,count_question=0,no_q_st;
			int present_st_id,q_map[]=null,count_graph=0,q_st[]=null;
         	TableRow t[];
         	TextView tv[];
	        String stud_map[]=null;
	        int flag=1;

	        @Override
	        public void onCreate(Bundle savedInstanceState)
	        {  
		              super.onCreate(savedInstanceState);
		              setContentView(R.layout.tab_student);
		            
		              Bundle b=getIntent().getExtras();
		  		      quiz= b.getInt("quiz_id");
		             // st_id=b.getInt("st_id");
		              Log.d("quiz",new Integer(quiz).toString());
		              bck=(Button)findViewById(R.id.button_bk_student);  
		              bck.setOnClickListener(this);
		              
		              l=(LinearLayout)findViewById(R.id.layout_student);
		              l_st=(LinearLayout)findViewById(R.id.layout_stud);
		              tabHost=(TabHost) findViewById(R.id.tabhost_student);
		              
		              header=(TextView)findViewById(R.id.textView_header);
		              
		              header.append('\t'+"QUIZ"+new Integer(quiz).toString());
		             
		
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
		  	         
		

			         query_question="select distinct Q_ID from QQ_MAP where QUIZ_ID='"+quiz+"'";
			         c_question=db.rawQuery(query_question, null);
			         
                     no_q=c_question.getCount();
			         
			         q_map=new int[no_q];
			        
		              
			         if( c_question.moveToFirst())
			        	do{
			        		q_id=c_question.getInt(c_question.getColumnIndex("Q_ID"));
			        		q_map[i++]=q_id;
			        	}while(c_question.moveToNext());
			         
			        
			            
			            
			            
			       	
		  	             
		  	         query_student="select distinct STUD_ID from QUIZ_SCORE where QUIZ_ID='"+quiz+"'";
		             c_student=db.rawQuery(query_student, null);
		             c_student.moveToFirst();
		             no_st=c_student.getCount();
		
		  	         
                       stud_map=new String[no_st];
                    
                       tabHost.setup();
                       for(int i=0;i<no_st;i++)
                       {  int j=i+1;
                                    stud_map[i]=c_student.getString(c_student.getColumnIndex("STUD_ID"));
                                    st_id=c_student.getInt(c_student.getColumnIndex("STUD_ID"));
                                	query_student_name="select NAME from STUDENT where STUD_ID='"+st_id+"'";
                                	c_student_name=db.rawQuery(query_student_name, null);
                                	c_student_name.moveToFirst();
                                    c_student.moveToNext();
        	                        tabHost.addTab(tabHost.newTabSpec(new Integer(i).toString())
                                    .setIndicator(createIndicatorView(tabHost, c_student_name.getString(c_student_name.getColumnIndex("NAME")),null))
                                    .setContent(android.R.id.tabcontent));
                        }
                      
                     
                      tabHost.setOnTabChangedListener(this);
                    
                      tabHost.setCurrentTab(no_st-1);
                    
		              all_activity(no_st-1);
                     
		
	         }
	        
	        
	        
	        
             public void onTabChanged(String tabId) 
             { 
            	      present_st_id=Integer.parseInt(tabId);
        	          all_activity(present_st_id);
        	 }
        

	        private View createIndicatorView(TabHost tabHost, CharSequence label, Drawable icon)
	        {
                      LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                      View tabIndicator = inflater.inflate(R.layout.tab_indicator,tabHost.getTabWidget(), // tab widget is the parent
	                  false); // no inflate params
	                  final TextView tv = (TextView) tabIndicator.findViewById(R.id.title);
	                  tv.setText(label);
	                  return tabIndicator;
	        }

           public void all_activity(int present_st_id)
           {          
        	            total=0;
                        tv=new TextView[(no_q+2)*3];
	                    t=new TableRow[no_q+2];
	         
	                      for(j=0;j<=2;j++)
	                       {
	            	                tv[j]=new TextView(this);
	                             	tv[j].setTextColor(Color.BLACK);
	                       }

	                      t[0]=new TableRow(this);
	            
	                      tv[0].setText('\t'+"Q.No"+'\t');
	                      tv[1].setText("Correctness"+'\t'+'\t');
	                      tv[2].setText("Marks"+'\n');
	            
                          Log.d("abc","hii");
	            
	                      for(j=0;j<=2;j++)
	                            t[0].addView(tv[j]);
	            
	                    
	            
        	   
    	              /*  query_q_txt="select QUESTION from QUESTION where Q_ID='"+q_id+"'";
                      c_q_txt=db.rawQuery(query_q_txt, null);
                      c_q_txt.moveToFirst();
    	              question=c_q_txt.getString(c_q_txt.getColumnIndex("QUESTION"));*/
	            
	            
        	          query_question_st="select distinct Q_ID from QUIZ_SCORE where QUIZ_ID='"+quiz+"' and STUD_ID='"+stud_map[present_st_id]+"'";
		              c_question_st=db.rawQuery(query_question_st, null);
		              no_q_st=c_question_st.getCount();
		              
		              q_st=new int[no_q_st];
		              int i=0,set=0;
		              if(c_question_st.moveToFirst())
		              do{
		            	  q_st[i++]=c_question_st.getInt(c_question_st.getColumnIndex("Q_ID"));
		              }while(c_question_st.moveToNext());
		              
		              count_question=0;
        	          ans_correct=0;
        	          op_id=0;
    	              Log.d("mm","hii");
    	              mark_array=new int[no_q];
    	              mark_arraycorr=new int[no_q];
    	              
    	              while(count_question<no_q)
    	              {   
    	            	  
    	            	 for(j=0;j<=3;j++)
 		                 {
 		                 	tv[((count_question+1)*3)+j]=new TextView(this);
 		                 	tv[j+((count_question+1)*3)].setTextColor(Color.BLUE);
 		                 }
 		                 t[count_question+1]=new TableRow(this);
 			               
    	            	  set=0;
    	                   q_id=q_map[count_question];
                        for(i=0;i<no_q_st;i++)
                        {
                        	   if(q_id==q_st[i])
                        	   {  
                        		   Log.d("q_id",new Integer(q_st[i]).toString());
                        		   Log.d("q_id",stud_map[present_st_id]);
                        		   set=1;
                        		   break;
                        	   }
                        }
                        Log.d("stid",stud_map[present_st_id]);
                        Log.d("correct",new Integer(ans_correct).toString());
                        Log.d("opid",new Integer(op_id).toString());
                        Log.d("noqst",new Integer(no_q_st).toString());
                        Log.d("noq",new Integer(no_q).toString());
                        Log.d("count_question",new Integer(count_question).toString());
                        if(set==1)
                        {
	                       String anss="";
                           query_ans_correct="select A_ID from ANSWER where Q_ID='"+q_id+"' and CORRECTNESS = 1";
                           c_ans_correct=db.rawQuery(query_ans_correct, null);
                           if(c_ans_correct.moveToFirst())
                        	   do{
                                     anss=anss+new Integer(c_ans_correct.getInt(c_ans_correct.getColumnIndex("A_ID"))).toString();
                        	   }while(c_ans_correct.moveToNext());
                           ans_correct=Integer.parseInt(anss);
                           query_op="select A_ID from QUIZ_SCORE where STUD_ID='"+stud_map[present_st_id]+"' and Q_ID='"+q_id+"' and QUIZ_ID ='"+quiz+"'";
                           c_op=db.rawQuery(query_op, null);
                           c_op.moveToFirst();
                           op_id=c_op.getInt(c_op.getColumnIndex("A_ID"));
        
                          
                      
                          
                           if(op_id==ans_correct)
                           {
                        	    marks=1;
                        	    mark_array[count_question]=1;
                        	    mark_arraycorr[count_question]=1;
                        	    correctness="Right";
   		                	    tv[1+((count_question+1)*3)].setTextColor(Color.rgb(11, 165, 41));
                        	
                           }
                           else
                           {   marks=0;
                        	   mark_array[count_question]=0;
                        	   mark_arraycorr[count_question]=0;
                        	   correctness="Wrong";
                        	   tv[1+((count_question+1)*3)].setTextColor(Color.rgb(215, 39, 45));
                           }
                        }
                        else 
                        {
                        	marks=0;
                        	mark_array[count_question]=0;
                        	mark_arraycorr[count_question]=-1;
                            correctness="N.A";
                 	        tv[1+((count_question+1)*3)].setTextColor(Color.BLUE);
                 	    }
                        
                        total=total+marks;
                        
                       
				        
		                tv[0+((count_question+1)*3)].setText('\t'+"Q."+(count_question+1)+'\t');
		                tv[1+((count_question+1)*3)].setText(correctness+'\t'+'\t');
		                tv[2+((count_question+1)*3)].setText(""+marks+'\t');
		                
		                Log.d("abc","hii");
		                
		                for(j=0;j<=2;j++)
		                {
		                	t[(count_question+1)].addView(tv[((count_question+1)*3)+j]);
		                }
		                
		               
		            	
                    	count_question++;
                    	  
    	              }
    	              tv[(no_q*3)-3]=new TextView(this);
    		          tv[(no_q*3)-2]=new TextView(this);
    		          tv[(no_q*3)-1]=new TextView(this);
    		       
    		          tv[(no_q*3)-3].setText("Total marks obtained");
    		          tv[(no_q*3)-1].setText(""+total);
    		          
    		          t[no_q+1]=new TableRow(this);
    		          
    		          t[no_q+1].addView(tv[(no_q*3)-3]);
    		          t[no_q+1].addView(tv[(no_q*3)-2]);
    		          t[no_q+1].addView(tv[(no_q*3)-1]);
    		          
    		         
                    	
    	              if(count_graph>0)
    	              {
    	    	          l.removeView(graphView);
    	    	          l_st.removeView(table);
    	              }
    	              table=new TableLayout(this); 
    	              for(int l=0;l<=(no_q+1);l++)
    	              {
    	                      table.addView(t[l]);
    	              }
    	              l_st.addView(table);
    	              header.setTextColor(Color.BLACK);
    	              header.setText("STUDENT WISE REPORT"+'\t'+"QUIZ"+new Integer(quiz).toString()+'\t'+"Student Id "+stud_map[present_st_id]+'\t'+'\t'+"TOTAL MARKS ="+total+"/"+no_q);
    	              graphView = new BarGraphView(this,"",mark_arraycorr,mark_array,flag);
    	              GraphViewData []g=new GraphViewData[no_q];
    	              for( i=0;i<no_q;i++)
    	    	      g[i]=new GraphViewData("q"+(i+1), mark_array[i]*50);
    	            
    	              graphView.addSeries(new GraphViewSeries("Right","Wrong","not attempted",g));
    	              l.addView(graphView,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
    	              count_graph++;
    	

       
         }
           public void onClick(View v) {
       		if(v.getId()==R.id.button_bk_student)
       		{   db.close();
       			Intent i_back = new Intent(this,student.class);
       			setResult(RESULT_OK, i_back);
       			finish();
       		}
       		
       	}

	
}

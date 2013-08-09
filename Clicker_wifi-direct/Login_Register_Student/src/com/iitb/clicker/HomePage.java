package com.iitb.clicker;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;













import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TimePicker;
import android.widget.Toast;



@SuppressWarnings("unused")



public class HomePage extends Activity implements TabHost.TabContentFactory, OnClickListener 
{
    /** Called when the activity is first created. */
    	      volatile static Boolean universe_home;                                                                //declaration
	 
    	      private int mHour;
	 long time,sec,min;
	 private int mMinute;
     private Button start,set,btnLogout,raise_clear,raise_send;
     private TextView textmin,textsec;
	 static final int TIME_DIALOG_ID = 0;
	  TabHost tabHost;
	  EditText raise_text;
	  String quizid,quiz_time,ServerIp;
	  //start
	  TableLayout marks_table;
	  String str=null;
	  static DatagramSocket socket;
	 DatagramSocket socket1;
	  DatagramPacket Rpacket;
	  DatagramPacket Spacket;
	  XmlReader reader;
	  XmlResultReader result_reader;
	  int question_count;
	    
	  
	  //start
	 
	  
	  
    public void onCreate(Bundle savedInstanceState) 
    {                 //activity starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

       tabHost = (TabHost) findViewById(R.id.tabHost);
        textmin=(TextView) findViewById(R.id.textmin);
        textsec=(TextView) findViewById(R.id.textsec);
        
        raise_text=(EditText) findViewById(R.id.editText_raise);
        //start=(Button) findViewById(R.id.buttonstart);
        //set=(Button) findViewById(R.id.buttonset); 
        raise_clear=(Button) findViewById(R.id.raiseclear); 
        raise_send=(Button) findViewById(R.id.raisesend); 
        btnLogout=(Button) findViewById(R.id.btnLogout);
       marks_table=(TableLayout) findViewById(R.id.marks_table);
       // start.setOnClickListener(this);
        //set.setOnClickListener(this);
        raise_clear.setOnClickListener(this);
        raise_send.setOnClickListener(this);
       
      /*  Bundle bundle=this.getIntent().getExtras();
        if(bundle!=null)
        serverip=bundle.getString("ip");
        */
        
        btnLogout.setOnClickListener(new View.OnClickListener() 
    	{
		    public void onClick(View arg0) 
			{
				android.os.Process.killProcess(android.os.Process.myPid());     	
	        	
			}
		});
        
        //start.java receive packets             
       universe_home=true;
    	reader=new XmlReader();
    	result_reader=new XmlResultReader();
    	 
        /*try {
    		socket=new DatagramSocket(2000);
    	} catch (SocketException e) {
    		// TODO Auto-generated catch block
    		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    	}*/
        new Thread(new Runnable() {
    		public void run() {
    			////
    			try {
    	    		socket=new DatagramSocket(2000);
    	    	} catch (SocketException e) {
    	    		// TODO Auto-generated catch block
    	    		Log.d("Check","Error Here");
    	    		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    	    	}
    			
    			////
    			while(universe_home)
    			{
    			try {
    				Log.i("running ", "runningggggggggggggggg");
    				Log.i("running ", "runningggggggggggggggg");
    				
    				if((socket==null)||(socket.isClosed())||(!socket.isConnected())){
    					if((!(socket==null))&&(!(socket.isClosed())))
    					{Log.d("closing", "socket");
    					socket.close();}
    					socket=null;
    					Log.d("restarting","socket");
    					socket=new DatagramSocket(2000);
    				}
    				
    			
    				byte [] Rdata=new byte[3000];
    				Rpacket=new DatagramPacket(Rdata,Rdata.length);
    				socket.receive(Rpacket);
    			
    				Log.i("receive", "receive");
    				str=new String(Rpacket.getData()).trim(); 
    			
    				if(str.charAt(0)=='~') //Quiz symbol
    				{
    					Log.i("data received resluts", "results");
    					String Msg1=str.trim().substring(1);
    					result_reader.xmlToData(str);
    					Bundle b=new Bundle();
    					b.putStringArray("ans_marked", result_reader.AnsMarked);
    					b.putStringArray("corr_marked", result_reader.CorrectAns);
    					b.putString("total_question", result_reader.TotalQues);
    					b.putString("total_marks", result_reader.TotalMarks);
    					b.putString("correct_count", result_reader.CorrectCount);
    					b.putString("marks_obtained", result_reader.MarksObtained);
    					Intent i=new Intent(HomePage.this,Results.class);
    					i.putExtras(b);
    					startActivity(i);
    					Message m=new Message();
    	   				h1.sendMessage(m);
    	   				 
    					//String [] Ids=Msg1.split("@");
    			        //int STUD_ID=d.getStudentIdByIP(ip1);//TODO change IP
    			        //d.insertResponse(STUD_ID, Integer.parseInt(Ids[2]), Integer.parseInt(Ids[1]),Integer.parseInt(Ids[0]));	
    				}
    				else{
   				 reader.count(str);	
   			        Log.i("reader", "" + reader.c);
   			        reader.xmlToData(str,1);	
   			        quizid=reader.quiz_id;
   			        quiz_time=reader.time;
   			        Log.i("quizid",quizid);
   			        
   			        Log.i("xml", str);
   			        question_count=reader.c;
   			     Log.i("Running till here","Contacting handler");
   			        Message m=new Message();
    				h.sendMessage(m);
    				Log.i("Running till here","Contacted handler");	
    				}
   			     
   				}
   				catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			}
    		}
    	}).start();
        
    	
    
        
        
        
        
        
        
        
        //start receive packets end
        //creating tab host and adding tabs
        tabHost.setup();
        Resources res = getResources();
        Configuration cfg = res.getConfiguration();
        boolean hor = cfg.orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (hor) 
        {
            TabWidget tw = tabHost.getTabWidget();
            tw.setOrientation(LinearLayout.VERTICAL);
        }
    
        
        tabHost.addTab(tabHost.newTabSpec("WELCOME")
                .setIndicator(createIndicatorView(tabHost, "WELCOME", null))
                .setContent(R.id.tab4Layout));
        /*tabHost.addTab(tabHost.newTabSpec("ATTEND")
                .setIndicator(createIndicatorView(tabHost, "ATTEND",null))
                .setContent(R.id.tab1Layout));*/
        tabHost.addTab(tabHost.newTabSpec("QUIZ")
                .setIndicator(createIndicatorView(tabHost, "QUIZ",null))
                .setContent(R.id.tab2Layout));
       /* tabHost.addTab(tabHost.newTabSpec("RESULTS")
                .setIndicator(createIndicatorView(tabHost, "RESULTS",null))
                .setContent(R.id.tab3Layout));*/
        tabHost.addTab(tabHost.newTabSpec("RAISE HAND")
                .setIndicator(createIndicatorView(tabHost, "RAISE HAND",null))
                .setContent(R.id.tab5Layout));
        /*tabHost.addTab(tabHost.newTabSpec("REPORTS")
                .setIndicator(createIndicatorView(tabHost, "REPORTS", null))
                .setContent(R.id.tab4Layout));
        tabHost.addTab(tabHost.newTabSpec("POLL")
                .setIndicator(createIndicatorView(tabHost, "POLL", null))
                .setContent(R.id.tab5Layout));
        */
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
        	 tabHost.getTabWidget().getChildAt(i).getLayoutParams().width = 120;
             tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 100;
        }
       /* for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#24b2eb")); //unselected
        }*/
        
        
        
        

    }
    //end of create method
    
   private View createIndicatorView(TabHost tabHost, CharSequence label, Drawable icon) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tabIndicator = inflater.inflate(R.layout.tab_indicator,
                tabHost.getTabWidget(), // tab widget is the parent
                false); // no inflate params

        final TextView tv = (TextView) tabIndicator.findViewById(R.id.title);
        tv.setText(label);
 
        return tabIndicator;
    }

	public View createTabContent(String tag) {
		final TextView tv = new TextView(this);
        tv.setText("Content for tab with tag " + tag);
        return tv;
	}

	public void onClick(View arg0) {
		if(arg0.getId()==R.id.raisesend)
		{
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to raise hand?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id)
			           {try{
			   			String raise="%"+raise_text.getText().toString();
						byte [] Sdata=raise.getBytes();
						//byte [] ip=.getAddress();
						Spacket=new DatagramPacket(Sdata,Sdata.length,InetAddress.getByName(ServerIp),3000);
				   			//socket.send(Spacket);
						new Thread(new packetSender(Spacket)).start();
				   			Log.i("last packet", "last packet sent to teacher");
						
					    }
					catch(Exception e)
					{
						
					}
			        	  
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		if(arg0.getId()==R.id.raiseclear)
		{
			raise_text.setText("");
		}

	
		
		
		
		
		
	
	}
	public void start_test(){
		if(str!=null)
		{
			
			Toast toast=Toast.makeText(this,"Test started", 1000);
			toast.show();
			Bundle bundle=new Bundle();
			bundle.putString("str",str);
			bundle.putString("quizid",quizid);
			bundle.putString("time",quiz_time);
			bundle.putInt("question_count",question_count);
			Log.i("going", "going");
			
			Intent intent = new Intent(this,ChatClientActivity.class);
			intent.putExtras(bundle);
			
			startActivity(intent);
			
			
		}
		else{
			Log.d("received string of quiz","is null");
		}
	}
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
		    new TimePickerDialog.OnTimeSetListener() {
		        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		            mHour = hourOfDay;
		            mMinute = minute;
		           
		            updateDisplay();
		        }
		    };
		    
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case TIME_DIALOG_ID:
	        return new TimePickerDialog(this,mTimeSetListener, mHour, mMinute, true);
	    }
	    return null;
	}
	private void updateDisplay() {
	   textmin.setText(new Integer(mHour).toString());
	   textsec.setText(new Integer(mMinute).toString());
		
		
	}

	
	class mycount extends CountDownTimer
	{
	  
		
		     public mycount(long millisInFuture, long countDownInterval) 
		     {
		    	 super(millisInFuture, countDownInterval);
		    	 // TODO Auto-generated constructor stub
		     }

			public void onTick(long millisUntilFinished) 
			{
					Log.i("dfdffffffffffff", "fdfd");
					sec=millisUntilFinished/1000;
				
					//sec=sec%3600;
					min=sec/60;
					sec=sec%60;
					textsec.setText(Long.toString(sec));
			        textmin.setText(Long.toString(min));
		        
		     }

		     public void onFinish() 
		     {
		    	//after counter finishes do whatever want to do.....here
		    	 textsec.setText("0");
			     textmin.setText("0");
		    	 
		     }
	  }
	
	//start.java handler
	
	
	
	
	Handler h=new Handler()
	{
		public void handleMessage(Message msg)
		{
			Log.i("data receive", "asasa");
			start_test();
		}
	};
	Handler h1=new Handler()
	{
		public void handleMessage(Message msg)
		{
			
			
			
			
			
			
			
		}
	};
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	        ServerIp=pref.getString("ip","null");
	       
	       
	}
	

	/*
	 *   packet sender class for the thread which sends packets to the peer
	 *    after checking the status of the socket
	 *                                      -Anand
	 */
	
	public class packetSender implements Runnable{
		 
		DatagramPacket packet;
		public packetSender(DatagramPacket pack){
			this.packet=pack;
		}
		public void run() {
			try{
				if((socket1==null)||(!socket1.isConnected())||(socket1.isClosed())){
					if((!(socket1==null))&&(!(socket1.isClosed())))
					{Log.d("closing", "socket");
					socket1.close();}
					socket1=null;
					socket1=new DatagramSocket();
				}
				socket1.send(packet);
				socket1.close();
				
			}
			
			catch(SocketException e){
				Log.d("error","socket1 conn error");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("error","Socket1 sending error");
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	
	//start
	
	


		
}
	
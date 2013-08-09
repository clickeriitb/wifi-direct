package com.iitb.clicker;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.StringTokenizer;











import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.GroupInfoListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
public class HomePage extends Activity implements  OnClickListener, OnItemSelectedListener,ConnectionInfoListener
{
    /** Called when the activity is first created. */
    volatile static Boolean universe;	                                                                      //declaration
	 private int mHour;
	 long time,sec,min;
	 private int mMinute;
     private Button start,set,btnLogout,getip,showattend,manage;
     private TextView textmin,textsec;
     Database d;
	TableLayout table;
	String raise_name=null;
	 static final int TIME_DIALOG_ID = 0;
	boolean mycheck=false;
	  TabHost tabHost;
		/////
	 static HomePage my;
	  WifiP2pManager manager;
	     Channel channel;
		  final IntentFilter intentFilter = new IntentFilter();
		  BroadcastReceiver receiver = null;
	  
	  //start
   // Handler h,h1;
	  Button startquiz;
	static ImageButton raisehand_signal;
	  String str=null;
	 static  DatagramSocket socket;
	  DatagramSocket socket1;
	  DatagramPacket Rpacket;
	  DatagramPacket Spacket;
	  //XmlReader reader;
	  int question_count;
	  String ClientMsg=" ";
	    String macAddr;
	    String data;
	    String name,roll,clas,section,mac,ip1;
	    int Quiz_id;
	    
	    ListView listView;
		 int i;
		 Intent i_post,i_get;
		 Button back,proceed;
		 Spinner spin;
		 String spiner[]=null;
		 int quiz_no,present_quiz,quiz_id=1;
	     String query_quiz;
		 Cursor c_quiz;
		 SQLiteDatabase db ;

	  
	  //start
	 
	  
	  
    public void onCreate(Bundle savedInstanceState) 
    {                 //activity starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        d=new Database(this);
        my=this;
        ////////////
        
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

   	///code to start wifi direct automatically
        
   	 manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        try {
	        Class<?> wifiManager = Class
	                .forName("android.net.wifi.p2p.WifiP2pManager");

	        Method method = wifiManager
	                .getMethod(
	                        "enableP2p",
	                        new Class[] { android.net.wifi.p2p.WifiP2pManager.Channel.class });

	        method.invoke(manager, channel);

	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
      /*  Log.d("myself","creating P2p group");
        manager.createGroup(channel, new ActionListener() {
			
			public void onSuccess() {
				// TODO Auto-generated method stub
				  Log.d("creating group","successful");			
			}
			
			public void onFailure(int reason) {
				// TODO Auto-generated method stub
				  Log.d("creating group","unsuccessful");
			}
        });*/
       ////////////// 
        table=(TableLayout) findViewById(R.id.table);
       tabHost = (TabHost) findViewById(R.id.tabHost);
        textmin=(TextView) findViewById(R.id.textmin);
        textsec=(TextView) findViewById(R.id.textsec);
        //start=(Button) findViewById(R.id.buttonstart);
        startquiz=(Button) findViewById(R.id.startquiz);
        //set=(Button) findViewById(R.id.buttonset); 
        btnLogout=(Button) findViewById(R.id.btnLogout);
         raisehand_signal=(ImageButton) findViewById(R.id.raisehand_signal);
        getip=(Button) findViewById(R.id.buttonip);
        manage=(Button)findViewById(R.id.manage_button);
        manage.setOnClickListener(this);
        startquiz.setOnClickListener(this);
        //start.setOnClickListener(this);
        //set.setOnClickListener(this);
        getip.setOnClickListener(this);
       raisehand_signal.setOnClickListener(this);
        
       showattend=(Button) findViewById(R.id.showattend); 	
	    showattend.setOnClickListener(this);
	  
       
        btnLogout.setOnClickListener(new View.OnClickListener() 
    	{
		    public void onClick(View arg0) 
			{
		    	d.SET_IP_ZERO();
		    	android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
        
        
        
        
        
        //start.java receive packets  
        // define handlers for interaction with ui thread
      Bundle bundle=this.getIntent().getExtras();
         if(bundle!=null)
         {
        	 try{
        sec= new Long(bundle.getString("Quiz_sec")); 
		    min= new Long(bundle.getString("Quiz_min"));
		    Quiz_id=bundle.getInt("Quiz_id");
		    time=(sec+(min*60))*1000;
		    
		    
		    
		    
		    final  mycount count=new mycount(time,1000);
		    
			 count.start();
			
			 
        	 }
        	 catch(Exception e)
        	 {
        		 
        	 }
			
         }
         universe=true;
        
        
        ///DElayyyy till group formed and ip is generated
    
    	 /////////////
      
       
    
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
        tabHost.addTab(tabHost.newTabSpec("ATTEND")
             .setIndicator(createIndicatorView(tabHost, "ATTEND",null))
               .setContent(R.id.tab1Layout));
         tabHost.addTab(tabHost.newTabSpec("QUIZ")
                .setIndicator(createIndicatorView(tabHost, "QUIZ",null))
                .setContent(R.id.tab2Layout));
        tabHost.addTab(tabHost.newTabSpec("RESULTS")
                .setIndicator(createIndicatorView(tabHost, "RESULTS",null))
                .setContent(R.id.tab3Layout));
        
        tabHost.addTab(tabHost.newTabSpec("RAISE HAND")
                .setIndicator(createIndicatorView(tabHost, "RAISE HAND", null))
                .setContent(R.id.tab5Layout));
        tabHost.addTab(tabHost.newTabSpec("MANAGE DATABASE")
                .setIndicator(createIndicatorView(tabHost, "MANAGE DATABASE", null))
                .setContent(R.id.tab6layout));
        
       tabHost.setCurrentTab(0);
        
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
        	 tabHost.getTabWidget().getChildAt(i).getLayoutParams().width = 120;
             tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 100;
        }
       /* for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#24b2eb")); //unselected
        }*/
        
        
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			public void onTabChanged(String tabId) {
			 if(tabHost.getCurrentTab()==4)
			 {
				 raisehand_signal.setImageResource(R.drawable.palm1); 
			 }
				
				
				
			}
		});
        
        //monika
        
        proceed=(Button)findViewById(R.id.btn_go);
        proceed.setOnClickListener(this);
        spin=(Spinner)findViewById(R.id.spin);
        spin.setOnItemSelectedListener(this);
      //  et_qi=(EditText)findViewById(R.id.et_qi);
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
        
       // db = openOrCreateDatabase("/data/data/com.reports/databases/clicker_db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
        query_quiz="select distinct QUIZ_ID  from QQ_MAP where 1";
        c_quiz =db.rawQuery(query_quiz, null); 
        quiz_no=c_quiz.getCount();
        spiner=new String[quiz_no];
       // listView =(ListView)findViewById(R.id.list);
	
	    
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spiner);
	    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spin.setAdapter(aa);
        i=0;
	    if(c_quiz.moveToFirst())
	    {
	    	do
	    	{
	    		present_quiz=c_quiz.getInt(c_quiz.getColumnIndex("QUIZ_ID"));
	    		
	    		spiner[i++]=new Integer(present_quiz).toString();
	    		Log.d("spi",spiner[0]);
	    	}while(c_quiz.moveToNext());
	    	
	    }
        
        
        
        //monika

    }
    //end of create method
    
    //handlersss for threads 
   Handler h=new Handler()
    {
    	public void handleMessage(Message msg)
    	{   
    		if(ClientMsg.substring(0,1).equals("("))
			{
				macAddr=ClientMsg.substring(1,ClientMsg.length());
				Toast.makeText(HomePage.this, macAddr, 2000).show();
				
				try{			
    				// Clear all previous data in database
    				Log.i("in","Login");
					
    				if(d.Login(macAddr)) // checking if macaddress is stored in database
    				{
    					try {
    						String reg="Registered";
    						Log.i("Registered",reg);
    						byte [] Sdata=reg.getBytes();
    					//	byte [] ip=InetAddress.getByName(ip1).getAddress();
    						//Toast.makeText(getApplicationContext(), ip1, Toast.LENGTH_LONG).show();
    						Spacket=new DatagramPacket(Sdata,Sdata.length,InetAddress.getByName(ip1),2000);
    						//socket.send(Spacket);
    						new Thread(new packetSender(Spacket)).start();
    						// use packet sender for sending packets
    						d.addIP(macAddr,ip1);
    						if(!d.check_Attendance(macAddr))
    						d.add_Attendance(macAddr);
    					} catch (Exception e) 
    					{
    					    	Log.e("HERE error", "1");
    						Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    					}
    					
    				}
    				else
    				{
    					try {
    						String reg="UnRegistrd";
    						Log.i("UnRegistered",reg);
    						byte [] Sdata=reg.getBytes();
    						Log.d("ip of student",ip1);
    						Log.wtf("socket connected", ""+socket.isConnected());
    						byte [] ip=InetAddress.getByName(ip1).getAddress();
    						Log.d("if error","line 1");
    						Toast.makeText(getApplicationContext(), ip1, Toast.LENGTH_LONG).show();
    						Log.d("if error","line 2");
    						
    						Spacket=new DatagramPacket(Sdata,Sdata.length,InetAddress.getByName(ip1),2000);
    						Log.d("if error","line 3");
    						//socket.send(Spacket);
    						new Thread(new packetSender(Spacket)).start();
    						// use packet sender for sending packets
    						Log.i("packet send", "packet");
    					} catch (Exception e) {
    						Log.e("HERE error", "2");
    						e.printStackTrace();
    						Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    					}
    				}
    				//d.close();
    			
    			
    		}
      		catch(Exception e)
    		{
    			//Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
    		}
			}
			if(ClientMsg.substring(0,1).equals(")"))
			{
				data=ClientMsg.substring(1,ClientMsg.length());
				StringTokenizer st=new StringTokenizer(data,"|",false);
				name = st.nextToken();
				roll = st.nextToken();
				clas = st.nextToken();
				 section = st.nextToken();
				 mac = st.nextToken();
				 
				 
				 try{	
	    				// Clear all previous data in database
	    			
						d.AddUser(name,roll,clas,section,mac,ip1);	
						d.add_Attendance(macAddr);
	    				//d.close();		    			
				 	}
	      		catch(Exception e)
	    		{    Log.e("HERE error", "3");
	    			Toast.makeText(HomePage.this,e.getMessage(), Toast.LENGTH_LONG).show();
	    		}

			}
    		
    		
    	}
    };

   Handler  h1=new Handler()
    {
    	public void handleMessage(Message msg)
    	{  if(tabHost.getCurrentTab()==4)
    		raisehand_signal.setImageResource(R.drawable.palm1);
    	else
    		raisehand_signal.setImageResource(R.drawable.redpalm);
    		
    		String Msg1=ClientMsg.trim().substring(1);
    		
    		TableRow tr=new TableRow(HomePage.this);
    		tr.setGravity(Gravity.CENTER);
            LayoutParams params = new TableRow.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);

            tr.setLayoutParams(params);
    		
    		
    		//tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    		
    		
    		
    		
    		TextView tv=new TextView(HomePage.this);
    		tv.setBackgroundResource(R.drawable.cell_shape);
    		tv.setText(Msg1);
    	    tv.setGravity(Gravity.CENTER);
    		tv.setPadding(5, 5, 5, 5);
    		tv.setWidth(300);
    		
    		
    		
    		TextView tv_name=new TextView(HomePage.this);
    		tv_name.setBackgroundResource(R.drawable.cell_shape);
    		tv_name.setText(raise_name);
    	     tv_name.setGravity(Gravity.CENTER);
    		tv_name.setPadding(5, 5, 5, 5);
    		tv_name.setWidth(100);        		
    		Button cancel_raise=new Button(HomePage.this);
    		cancel_raise.setText("cancel");
    		cancel_raise.setTextColor(Color.WHITE);
    		cancel_raise.setBackgroundResource(R.drawable.cell_shape);
    		cancel_raise.setGravity(Gravity.CENTER);
    		cancel_raise.setPadding(5,5,5,5);
    		cancel_raise.setWidth(100);
    		
    		
    		cancel_raise.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
				  table.removeView((View) v.getParent());
					
				}
			});
    		

    		
    		
    	
    		
    	   
    	    tr.addView(tv_name);
    		tr.addView(tv);
    		tr.addView(cancel_raise);
    		tr.setGravity(Gravity.CENTER);
    		table.addView(tr);
    		
    		
    		
    	}
    };
    
    
    /// handler end
    
   private View createIndicatorView(TabHost tabHost, CharSequence label, Drawable icon) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tabIndicator = inflater.inflate(R.layout.tab_indicator,
                tabHost.getTabWidget(), // tab widget is the parent
                false); // no inflate params

        final TextView tv = (TextView) tabIndicator.findViewById(R.id.title);
        tv.setText(label);
 
        return tabIndicator;
    }



	public void onClick(View arg0) {
		if(arg0.getId()==R.id.startquiz)
		{
			Intent i=new Intent(this,QuizActivity.class);
			startActivity(i);
			
			
			
			
		}
		if(arg0.getId()==R.id.btn_go)
		{   
			Intent i=new Intent(this,ReportsActivity.class);
			Bundle b=new Bundle();
			b.putInt("quiz_id",quiz_id);
			i.putExtras(b);
			db.close();
            startActivity(i);
		}
		
		
		if(arg0.getId()==R.id.manage_button)
		{
			Intent i=new Intent(this,MainFileActivity.class);
			
			startActivity(i);
			
			
			
			
		}
		
		
		if(arg0.getId()==R.id.raisehand_signal)
		{
			
			raisehand_signal.setImageResource(R.drawable.palm1);
			tabHost.setCurrentTab(4);
			
			
			
			
		}
		if(arg0.getId()==R.id.showattend)
		{
			
			Intent i=new Intent(this,ShowAttend.class);
			startActivity(i);
			
		}
		/*if(arg0.getId()==R.id.buttonset)
		{
		
		 showDialog(TIME_DIALOG_ID);
         set.setEnabled(false);
		}
		if(arg0.getId()==R.id.buttonstart)
		{
			    
				 start.setEnabled(false);
		}*/

		
		if(arg0.getId()==R.id.buttonip)
			
		{
			//String ip=getDottedDecimalIP(getLocalIPAddress()); // Works, but ip not required anymore -Anand
			
			 SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		     
			 String ymac=pref.getString("mmac","null");
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("your mac:"+ymac);
			AlertDialog alert = builder.create();
			alert.show();
			Log.i("mac",ymac);
		}		
		if(arg0.getId()==R.id.startquiz)
		{
			if(str!=null)
			{
				
				Toast toast=Toast.makeText(this,"test started", 1000);
				toast.show();
				Bundle bundle=new Bundle();
				bundle.putString("str",str);
				bundle.putInt("question_count",question_count);
				Log.i("going", "going");
				//Intent intent = new Intent(this,ChatClientActivity.class);
				//intent.putExtras(bundle);
				//startActivity(intent);
				
			}
			else
			{
				
				//Toast toast=Toast.makeText(this,"test not started try later!!!", 4000);
				//toast.show();
				
			}
		}
		
		
	
	}
/*
 * Functions to find ip of this device from the interfaces class 
 *                                                  -Anand
 */
	
	  private byte[] getLocalIPAddress() {
	        try {
	            for (Enumeration<NetworkInterface> en = NetworkInterface
	                    .getNetworkInterfaces(); en.hasMoreElements();) {
	                NetworkInterface intf = en.nextElement();
	                for (Enumeration<InetAddress> enumIpAddr = intf
	                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                    InetAddress inetAddress = enumIpAddr.nextElement();
	                    if (!inetAddress.isLoopbackAddress()) {
	                        if (inetAddress instanceof Inet4Address) {
	                            return inetAddress.getAddress();
	                        }
	                    }
	                }
	            }
	        } catch (SocketException ex) {
	            // Log.e("AndroidNetworkAddressFactory", "getLocalIPAddress()", ex);
	        } catch (NullPointerException ex) {
	            // Log.e("AndroidNetworkAddressFactory", "getLocalIPAddress()", ex);
	        }
	        return null;
	    }

	    private String getDottedDecimalIP(byte[] ipAddr) {
	        if (ipAddr != null) {
	            String ipAddrStr = "";
	            for (int i = 0; i < ipAddr.length; i++) {
	                if (i > 0) {
	                    ipAddrStr += ".";
	                }
	                ipAddrStr += ipAddr[i] & 0xFF;
	            }
	            return ipAddrStr;
	        } else {
	            return "null";
	        }
	    }
	
	
	    //// ip finder end
	    
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
			     Log.i("inside finish", Quiz_id+"");
			     //showattend.setVisibility(View.VISIBLE);
			     d.getResultData(Quiz_id, "1","a");
		     }
	  }


	
		//start.java handler
	
	
	
	
	/*Handler h=new Handler()
	{
		public void handleMessage(Message msg)
		{
			Log.i("data receive", "asasa");
		}
	};*/
	
	 public static void sendResultData(String IP, String ResultData)
	    {
	    	byte [] Sdata=ResultData.getBytes();
	    	DatagramPacket Spacket;
	    	
			try {
				//DatagramSocket socket=new DatagramSocket();
				Spacket = new DatagramPacket(Sdata,Sdata.length,InetAddress.getByName(IP),2000);
				
				//socket.send(Spacket);
				new Thread(my.new packetSender(Spacket)).start(); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("data send by teacher ", "results");
	    	//Toast.makeText(HomePage.this,"Result Sent to: "+IP+"  "+ResultData, Toast.LENGTH_SHORT).show();
	    		
	    }
	

	
	
	
	
	
	
	
	//start
	
/*protected void onResume() {
	super.onResume();
	 
    try {
		socket=new DatagramSocket(3000);
	} catch (SocketException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
	
	
}*/
	 public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			// TODO Auto-generated method stub
			quiz_id=Integer.parseInt(spiner[arg2]);
			//Log.d("quiz_idjjjjjjjjjjjj","spiner[0]");
			
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	
		protected void onActivityResult(int requestCode, int resultCode, Intent data)
		{
		          // TODO Auto-generated method stub
		          super.onActivityResult(requestCode, resultCode, data);
		 }

		



protected void onDestroy() 
{
	// TODO Auto-generated method stub
	super.onDestroy();
	d.close();
}
// registering broadcast receiver to inform of changes 
public void onResume(){
	super.onResume();
	  receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
	     registerReceiver(receiver, intentFilter);
	
}
public void onPause(){
	super.onPause();
	unregisterReceiver(receiver);
}


public void onConnectionInfoAvailable(WifiP2pInfo info) {
	// TODO Auto-generated method stub
	if((info.groupFormed)&&(info.isGroupOwner))
	{Toast.makeText(this, "Group formed me owner", Toast.LENGTH_SHORT).show();
	if(mycheck==false){
		 new Thread(new Runnable() {
	    		public void run() {
	    			Log.d("Delay","Started");
	    			double dd=5;
	    			for(int kp=0;kp<15000;kp++){
	    				//time delay
	    				dd=(dd*55)%5;
	    			}
	    			Log.d("Delay","end");
	    			  try {
	    					socket=new DatagramSocket(3000);
	    				} catch (SocketException e1) {
	    					// TODO Auto-generated catch block
	    					e1.printStackTrace();
	    					Log.e("HERE error", "55");
	    				}
	    			
	    			while(universe)
	    			{
	    			try {	
	    				if((socket==null)||(socket.isClosed())||(!socket.isConnected())){
	    					if((!(socket==null))&&(!(socket.isClosed())))
	    					{Log.d("closing", "socket");
	    					socket.close();}
	    					socket=null;
	    					socket=new DatagramSocket(3000);
	    				}
	    				Log.i("aaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaa");
	    				Log.i("inside aaya", "aaya");
	    				byte [] Rdata=new byte[1000];
	    				Rpacket=new DatagramPacket(Rdata,Rdata.length);
	    				socket.receive(Rpacket);
	    				ClientMsg=new String(Rpacket.getData()).trim();
	    				InetAddress ipAddress = Rpacket.getAddress();
	    				ip1=ipAddress.toString();
	    				ip1=ip1.substring(1,ip1.length());
	    				Log.i("IP",ip1);
	    								
	    				Message m=new Message();
	    				h.sendMessage(m);
	    				
	    				if(ClientMsg.charAt(0)=='|') //Quiz symbol
	    				{
	    					Log.i("response collected", "responese");
	    					String Msg1=ClientMsg.trim().substring(1);
	    					String [] Ids=Msg1.split("@");
	    					if(!Ids[0].equalsIgnoreCase(""))
	    					{
	    			        int STUD_ID=d.getStudentIdByIP(ip1);//TODO change IP
	    			        
	    			        d.insertResponse(STUD_ID, Integer.parseInt(Ids[2]), Integer.parseInt(Ids[1]),Integer.parseInt(Ids[0]));	
	    				}}
	    				if(ClientMsg.charAt(0)=='%')
	    					
	    				{  Log.i("raise", "raise");
	    				Message m1=new Message();
	    				h1.sendMessage(m1);
	    				raise_name=d.getStudent_raised(ip1);
	    				Log.i("asasda",d.getStudent_raised(ip1));
	    					
	    				}
	    				
	    				
	    			} catch (Exception e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    			
	    			}
	    		}
	    	}).start();
	        
		
		mycheck=true;
	}
	
	}
	
	else{
		if(info.groupFormed){
			Toast.makeText(this, "Group formed me not owner", Toast.LENGTH_SHORT).show();
		}
	}
	
}

// informs that wifi direct is up and running (for debugging)
public void myself(WifiP2pDevice dev) {
	// TODO Auto-generated method stub
	Toast.makeText(this, "my device "+dev.deviceName+" status "+dev.status, Toast.LENGTH_SHORT).show();
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
				{Log.d("closing", "socket1");
				socket1.close();}
				socket1=null;
				socket1=new DatagramSocket();
			}
			socket1.send(packet);
			socket1.close();
		}
		
		catch(SocketException e){
			Log.d("error","Socket1 conn error");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("error","Socket1 sending error");
			e.printStackTrace();
		}
		
	}
	
}



		
}
	





package com.iitb.clicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;







import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class ChatClientActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
    /** Called when the activity is first created. */
	 DatagramSocket socket;
	    DatagramPacket Rpacket;
	    DatagramPacket Spacket;
    String str,ServerIp;
    TextView textsec,textmin,questiontext,type,time_separate;
	 CheckBox option1,option2,option3,option4,option5,option6;
	 TableLayout table;
	 XmlReader reader;
	 long timeBlinkInMilliseconds;    
	 long time,sec,min,id;
	 Button next,previous;
	 TabHost tabHost;
	 int question_count;
	 StringBuilder[] answer;
	 String quizid,quiz_time;
	 int tabid,previoustab;
	 String previous_question;
	int [] tempid=new int[10];
	Boolean blink=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         tabHost=(TabHost) findViewById(R.id.tabhost);
        questiontext=(TextView) findViewById(R.id.question);
        type=(TextView) findViewById(R.id.type);
        textsec=(TextView) findViewById(R.id.textsec);
        textmin=(TextView) findViewById(R.id.textmin);
        time_separate=(TextView) findViewById(R.id.text_);
        table=(TableLayout) findViewById(R.id.table);
        option1=(CheckBox) findViewById(R.id.option1);
        option2=(CheckBox) findViewById(R.id.option2);
        option3=(CheckBox) findViewById(R.id.option3);
        option4=(CheckBox) findViewById(R.id.option4);
        option5=(CheckBox) findViewById(R.id.option5);
        option6=(CheckBox) findViewById(R.id.option6);
        next=(Button) findViewById(R.id.buttonnext);
        previous=(Button) findViewById(R.id.buttonprevious);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        reader=new XmlReader();
        option1.setOnCheckedChangeListener(this);
        option2.setOnCheckedChangeListener(this);
        option3.setOnCheckedChangeListener(this);
        option4.setOnCheckedChangeListener(this);
        option5.setOnCheckedChangeListener(this);
        option6.setOnCheckedChangeListener(this);
        
       /*Don't do networking on Main Thread
        * (Shifted it to separate thread)
        * - Anand
        *  try {
    		socket=new DatagramSocket();
    	} catch (SocketException e) {
    		// TODO Auto-generated catch block
    		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    	}
       */
        
       
        Bundle bundle=this.getIntent().getExtras();
        str=bundle.getString("str");
   	 question_count=bundle.getInt("question_count");
   	quizid=bundle.getString("quizid");
   	quiz_time=bundle.getString("time");
   	Log.i("ssssss", new Integer(question_count).toString());
        String temp_time[]=quiz_time.split(":");
        //packet received
        //timer call        
        sec=Integer.parseInt(temp_time[1]);
	    min=Integer.parseInt(temp_time[0]);
	    time=(sec+(min*60))*1000;
	    timeBlinkInMilliseconds = 60 * 1000;            // blink starts at 1 minutes = 60 seconds 
	    textmin.setTextAppearance(getApplicationContext(),R.style.normalText);
	    textsec.setTextAppearance(getApplicationContext(),R.style.normalText);
	    time_separate.setTextAppearance(getApplicationContext(),R.style.normalText);
		final  mycount count=new mycount(time,1000);
		 count.start();
       
		 //end of timer call
        
        
    
        tabHost.setup();
        
       /* mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB 1").setContent(R.id.textview1));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("TAB 2").setContent(R.id.textview2));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("TAB 3").setContent(R.id.textview3));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test4").setIndicator("TAB 4").setContent(R.id.textview4));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test5").setIndicator("TAB 5").setContent(R.id.textview5));
        mTabHost.setCurrentTab(0);*/

        
      for(int i=1;i<=question_count;i++)
        {  
        	 tabHost.addTab(tabHost.newTabSpec(new Integer(i).toString())
                     .setIndicator(createIndicatorView(tabHost, "QUESTION"+i,null))
                     .setContent(android.R.id.tabcontent));
        }
        
        answer=new StringBuilder[question_count+1];
       
        
   /*   tabHost.addTab(tabHost.newTabSpec("0")
                .setIndicator(createIndicatorView(tabHost, "QUESTION1",null))
                .setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("1")
                .setIndicator(createIndicatorView(tabHost, "QUESTION2",null))
                .setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("2")
                .setIndicator(createIndicatorView(tabHost, "QUESTION3",null))
                .setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("3")
                .setIndicator(createIndicatorView(tabHost, "QUESTION4", null))
                .setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("4")
                .setIndicator(createIndicatorView(tabHost, "QUESTION5", null))
                .setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("5")
                .setIndicator(createIndicatorView(tabHost, "QUESTION6", null))
                .setContent(android.R.id.tabcontent));       
        tabHost.addTab(tabHost.newTabSpec("6")
                .setIndicator(createIndicatorView(tabHost, "QUESTION7", null))
                .setContent(android.R.id.tabcontent));       
        tabHost.addTab(tabHost.newTabSpec("7")
                .setIndicator(createIndicatorView(tabHost, "QUESTION8", null))
                .setContent(android.R.id.tabcontent));       
        tabHost.addTab(tabHost.newTabSpec("8")
                .setIndicator(createIndicatorView(tabHost, "QUESTION9", null))
                .setContent(android.R.id.tabcontent));       
        tabHost.addTab(tabHost.newTabSpec("9")
                .setIndicator(createIndicatorView(tabHost, "QUESTION10", null))
                .setContent(android.R.id.tabcontent));   */ 
        
        if(question_count==1)
        {
        	 tabHost.setCurrentTab(0);
        reader.xmlToData(str,0);
        tempid[0]=1;
        next.setText("End");
        }
        else
        { 
        	tabHost.setCurrentTab(1);
        	reader.xmlToData(str,1);
        	tempid[0]=2;
        	next.setText("next");
        }
        tabid=1;
        
        	
		Log.i("sdsds", ""+reader.qid);
		questiontext.setText(reader.question);
		
		if(reader.Type.contentEquals("s"))
			type.setText("  single  ");
		if(reader.Type.contentEquals("m"))
			type.setText(" multiple ");
		if(reader.Type.contentEquals("t"))
			type.setText("true/false");
		
        Log.i("inserted", "sddddd");
		previous_question=reader.qid;
        
        int i=1;
		for(String op:reader.options)
		{
				if(i==1)
				{
					option1.setVisibility(View.VISIBLE);
    				 option1.setChecked(false);
    				option1.setText(op);
				}
				if(i==2)
				{
					option2.setVisibility(View.VISIBLE);
    				 option2.setChecked(false);
    				option2.setText(op);
				}
				if(i==3)
				{
					option3.setVisibility(View.VISIBLE);
    				 option3.setChecked(false);
    				option3.setText(op);
				}
				if(i==4)
				{
					option4.setVisibility(View.VISIBLE);
    				 option4.setChecked(false);
    				option4.setText(op);
				}
				if(i==5)
				{
					option5.setVisibility(View.VISIBLE);
    				 option5.setChecked(false);
    				option5.setText(op);
				}
				if(i==6)
				{
					option6.setVisibility(View.VISIBLE);
    				 option6.setChecked(false);
    				option6.setText(op);
				}
				
				
				i++;
		}
			
         for(int j=i;j<7;j++)
         {
        	 if(j==1)
				{
        		 option1.setVisibility(View.INVISIBLE);
				}
				if(j==2)
				{
					option2.setVisibility(View.INVISIBLE);					
				}		            
				if(j==3)
				{
					option3.setVisibility(View.INVISIBLE);
				}
				if(j==4)
				{
					option4.setVisibility(View.INVISIBLE);
				}
				if(j==5)
				{
					option5.setVisibility(View.INVISIBLE);
				}
				if(j==6)
				{
					option6.setVisibility(View.INVISIBLE);
				}
				
         }
         if((tabid)==question_count)
			{
				Log.i("fffffffffffffffffffff", "sdsdsd");
				next.setText("End");
			}
         
         
       
         
        	
        
        
        /**  
		 * Create TextView Arrays to add the retrieved data to.
		 **/
		
		
	/*	
			SAXParserFactory saxPF = SAXParserFactory.newInstance();
			SAXParser saxP = saxPF.newSAXParser();
			XMLReader xmlR = saxP.getXMLReader();

			
			
			XMLHandler myXMLHandler = new XMLHandler();
			xmlR.setContentHandler(myXMLHandler);
			xmlR.parse(new InputSource(myInput));
			
		} catch (Exception e) {
			System.out.println(e);
		}

		data = XMLHandler.data;
		
		
		Log.i("asdasa", ""+data.getquestion().size());
		
	*/
		
		
	//	reader.xmlToData(new String(Rpacket.getData()), 2);
		//questiontext.setText(reader.question);

			
	
        
        
        
        
        
	tabHost.setOnTabChangedListener(new OnTabChangeListener() {
		
	        
			
			public void onTabChanged(String tabId) {
				tabid=Integer.parseInt(tabId);
				tempid[1]=tabid;
				previoustab=tempid[0];
				tempid[0]=tempid[1];
				
						
				
			           
				
				StringBuilder ptr = new StringBuilder();
				Log.i("previous tab in tab on change:", ""+previoustab);
				if(option1.isChecked())
					ptr.append("1");
				if(option2.isChecked())
					ptr.append("2");
				if(option3.isChecked())
					ptr.append("3");
				if(option4.isChecked())
					ptr.append("4");
				if(option5.isChecked())
					ptr.append("5");
				if(option6.isChecked())
					ptr.append("6");
				
				
				
				
				
				
		
				
				Log.i("minav new tab:", tabid+"");
				
				
				
				
				
				//get values end
				next.setText("next");
				previous.setEnabled(true);
				if(((Integer.parseInt(tabId)))==question_count)
				{
					Log.i("fffffffffffffffffffff", "sdsdsd");
					next.setText("End");
				}
				if(Integer.parseInt(tabId)==0)
				{
					previous.setEnabled(false);
				}
				
			
				reader.xmlToData(str,Integer.parseInt(tabId)-1);	
				Log.i("sdsds", reader.qid);
				questiontext.setText(reader.question);
				
				if(reader.Type.contentEquals("s"))
					type.setText("  single  ");
				if(reader.Type.contentEquals("m"))
					type.setText(" multiple ");
				if(reader.Type.contentEquals("t"))
					type.setText("true/false");
				
				answer[previoustab]=ptr.append("@").append(previous_question).append("@").append(quizid);
				Log.i("answers checked", answer[previoustab].toString());
				  
				   StringBuilder stuff=new StringBuilder();
				try{
					byte [] Sdata=stuff.append("|").append(ptr).toString().getBytes();
					//byte [] ip=InetAddress.getByName("10.0.2.2").getAddress();
					
					Spacket=new DatagramPacket(Sdata,Sdata.length,InetAddress.getByName(ServerIp),3000);
					//socket.send(Spacket);
					new Thread(new packetSender(Spacket)).start();
					// Use PACKET SENDER  wherever packets are being sent                  -Anand    
					Log.i("Message", " starting packet sender");
					
				}
				catch(Exception e)
				{
					
				}
				
				 option1.setChecked(false);
				 option2.setChecked(false);
				 option3.setChecked(false);
				 option4.setChecked(false);
				 option5.setChecked(false);
				 option6.setChecked(false);
				
				int i=1;
				for(String op:reader.options)
				{
						if(i==1)
						{
							option1.setVisibility(View.VISIBLE);
		    				 option1.setChecked(false);
		    				option1.setText(op);
						}
						if(i==2)
						{
							option2.setVisibility(View.VISIBLE);
		    				 option2.setChecked(false);
		    				option2.setText(op);
						}
						if(i==3)
						{
							option3.setVisibility(View.VISIBLE);
		    				 option3.setChecked(false);
		    				option3.setText(op);
						}
						if(i==4)
						{
							option4.setVisibility(View.VISIBLE);
		    				 option4.setChecked(false);
		    				option4.setText(op);
						}
						if(i==5)
						{
							option5.setVisibility(View.VISIBLE);
		    				 option5.setChecked(false);
		    				option5.setText(op);
						}
						if(i==6)
						{
							option6.setVisibility(View.VISIBLE);
		    				 option6.setChecked(false);
		    				option6.setText(op);
						}
						
						i++;
				}
					
		         for(int j=i;j<7;j++)
		         {
		        	 if(j==1)
						{
		        		 option1.setVisibility(View.INVISIBLE);
						}
						if(j==2)
						{
							option2.setVisibility(View.INVISIBLE);					
						}		            
						if(j==3)
						{
							option3.setVisibility(View.INVISIBLE);
						}
						if(j==4)
						{
							option4.setVisibility(View.INVISIBLE);
						}
						if(j==5)
						{
							option5.setVisibility(View.INVISIBLE);
						}
						if(j==6)
						{
							option6.setVisibility(View.INVISIBLE);
						}
						
		         }
				
		       Log.i("ssss", "wwwwwwwwwwww"+reader.qid);
		         //retrieve values
		      
		       String[] temp=new String[20];
				if(answer[tabid]!=null)
				{
				temp=answer[tabid].toString().split("@");
				Log.i(temp[0],new Integer(tabid).toString());
				for(int k=0;k<temp[0].length();k++)
				{Log.i(Character.toString(temp[0].charAt(k)), "char");
					if(temp[0].charAt(k)=='1')
					{Log.i("yes inside", "yes inside");
					    option1.setChecked(true);}
					if(temp[0].charAt(k)=='2')
					    option2.setChecked(true);
					if(temp[0].charAt(k)=='3')
					    option3.setChecked(true);
					if(temp[0].charAt(k)=='4')
					    option4.setChecked(true);
					if(temp[0].charAt(k)=='5')
					    option5.setChecked(true);		
					if(temp[0].charAt(k)=='6')
					    option6.setChecked(true);		
					
				}
				}
		         
		         
		         
		         //retrieve values
				
		 		    	/*	if(!data.getoption1().get(Integer.parseInt(tabId)).contentEquals("null"))
		 		    		{
		 		    			
		 		    				option1.setVisibility(View.VISIBLE);
		 		    				 option1.setChecked(false);
		 		    				option1.setText(data.getoption1().get(Integer.parseInt(tabId)));}
		 		    		else
		 		    			option1.setVisibility(View.INVISIBLE);
		 		    		if(!data.getoption2().get(Integer.parseInt(tabId)).contentEquals("null"))
		 		    		{
		 		    			
		 		    				option2.setVisibility(View.VISIBLE);
		 		    				 option2.setChecked(false);
		 		    				option2.setText(data.getoption2().get(Integer.parseInt(tabId)));} 
		 		    		else
		 		    			option2.setVisibility(View.INVISIBLE);
		 		    		if(!data.getoption3().get(Integer.parseInt(tabId)).contentEquals("null"))
		 		    		{
		 		    			
		 		    				option3.setVisibility(View.VISIBLE);
		 		    				 option3.setChecked(false);
		 		    				option3.setText(data.getoption3().get(Integer.parseInt(tabId)));} 
		 		    		else
		 		    			option3.setVisibility(View.INVISIBLE);
		 		    		if(!data.getoption4().get(Integer.parseInt(tabId)).contentEquals("null"))
		 		    		{
		 		    			
		 		    				option4.setVisibility(View.VISIBLE);
		 		    				 option4.setChecked(false);
		 		    				option4.setText(data.getoption4().get(Integer.parseInt(tabId)));} 
		 		    		else
		 		    			option4.setVisibility(View.INVISIBLE);
		 		    		if(!data.getoption5().get(Integer.parseInt(tabId)).contentEquals("null"))
		 		    		{
		 		    			Log.i("enter", "ss");
		 		    				option5.setVisibility(View.VISIBLE);
		 		    				 option5.setChecked(false);
		 		    				option5.setText(data.getoption5().get(Integer.parseInt(tabId)));} 
		 		    		else
		 		    			option5.setVisibility(View.INVISIBLE);
		 		    		*/
		 		  
				previous_question=reader.qid;
			}
		});

        
    }
   
  
    private View createIndicatorView(TabHost tabHost, CharSequence label, Drawable icon) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tabIndicator = inflater.inflate(R.layout.tab_indicator,tabHost.getTabWidget(), // tab widget is the parent
                false); // no inflate params
        final TextView tv = (TextView) tabIndicator.findViewById(R.id.title);
        tv.setText(label);
        
        
       
 
        return tabIndicator;
    }
    class mycount extends CountDownTimer
   	{
   	  
   		
   		     public mycount(long millisInFuture, long countDownInterval) {
   			super(millisInFuture, countDownInterval);
   			// TODO Auto-generated constructor stub
   		}

   			public void onTick(long millisUntilFinished) {
   				
   				sec=millisUntilFinished/1000;
   				
   				//sec=sec%3600;
   				min=sec/60;
   				sec=sec%60;
   				
   				
   				  textsec.setText(Long.toString(sec));
   			         textmin.setText(Long.toString(min));
   			      if (millisUntilFinished < timeBlinkInMilliseconds ) 
   			      {
   			      textmin.setTextAppearance(getApplicationContext(), R.style.blinkText);
   			   textsec.setTextAppearance(getApplicationContext(), R.style.blinkText);
   			time_separate.setTextAppearance(getApplicationContext(),R.style.normalText);
   			if ( blink ) {
                textmin.setVisibility(View.VISIBLE); 
                textsec.setVisibility(View.VISIBLE); 
                time_separate.setVisibility(View.VISIBLE); 
                // if blink is true, textview will be visible
            } else {
                textmin.setVisibility(View.INVISIBLE);
                textsec.setVisibility(View.INVISIBLE);
                time_separate.setVisibility(View.INVISIBLE); 
            }
            
            blink = !blink;         
   			   
   			   
   			   
   			      }
   		     }

   		     public void onFinish() {
   		    
   		    	int current_tab=tabHost.getCurrentTab();
   		    	StringBuilder ptr = new StringBuilder();
				Log.i("previous tab in tab on change:", ""+previoustab);
				if(option1.isChecked())
					ptr.append("1");
				if(option2.isChecked())
					ptr.append("2");
				if(option3.isChecked())
					ptr.append("3");
				if(option4.isChecked())
					ptr.append("4");
				if(option5.isChecked())
					ptr.append("5");
				if(option6.isChecked())
					ptr.append("6");
				
				
				answer[current_tab]=ptr.append("@").append(reader.qid).append("@").append(quizid);
				Log.i("answers checked", answer[current_tab].toString());
				  StringBuilder stuff=new StringBuilder();
					try{
						byte [] Sdata=stuff.append("|").append(ptr).toString().getBytes();
						//byte [] ip=InetAddress.getByName("").getAddress();     //TODO add serverip here
						
						Spacket=new DatagramPacket(Sdata,Sdata.length,InetAddress.getByName(ServerIp),3000);
						//socket.send(Spacket);
						// Use PACKET SENDER  wherever packets are being sent                  -Anand         
						
						
						new Thread(new packetSender(Spacket)).start();
						
						///PACKET SENDER
						Log.i("last packet", "last packet sent to teacher");
						  finish();
						
					}
					catch(Exception e)
					{
						
					}
					
   		    	 
   		     }
   		  }
	public void onClick(View v) {
		
		if(v.getId()==R.id.buttonnext)
		{   
		/*	Log.i("minav previous on click:", tabid+"");
			//retrieving values
			String[] temp=new String[10];
			if(answer[tabid+1]!=null)
			{
			temp=answer[tabid+1].toString().split("@");
			for(int i=0;i<temp[0].length();i++)
			{
				if(temp[0].charAt(i)==1)
				    option1.setChecked(true);
				if(temp[0].charAt(i)==2)
				    option1.setChecked(true);
				if(temp[0].charAt(i)==3)
				    option1.setChecked(true);
				if(temp[0].charAt(i)==4)
				    option1.setChecked(true);
				if(temp[0].charAt(i)==5)
				    option1.setChecked(true);				
				
			}
			}
			
			
			
			
			//retrieving values end
			//getting values on next clicked
			StringBuilder str = new StringBuilder();
			Log.i("current tab",tabid+"");
			if(option1.isChecked())
				str.append("1");
			if(option2.isChecked())
				str.append("2");
			if(option3.isChecked())
				str.append("3");
			if(option4.isChecked())
				str.append("4");
			if(option5.isChecked())
				str.append("5");
			
			
			answer[tabid]=str.append("@").append(tabid+1);
			Log.i("answers checked", answer[tabid].toString());
			
			//getting values end
			
			*/
			if(next.getText().toString().contentEquals("next"))
		tabHost.setCurrentTab(tabHost.getCurrentTab()+1);
			else
			{Log.i("alert", "msg");
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure you want to end test?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id)
				           {
				              finish();
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
			
		}
		if(v.getId()==R.id.buttonprevious)
		{
			
			//retrieving values
			String[] temp;
			if(answer[tabid-1]!=null)
			{
			temp=answer[tabid-1].toString().split("@");
			for(int i=0;i<temp[0].length();i++)
			{
				if(temp[0].charAt(i)==1)
				    option1.setChecked(true);
				if(temp[0].charAt(i)==2)
				    option2.setChecked(true);
				if(temp[0].charAt(i)==3)
				    option3.setChecked(true);
				if(temp[0].charAt(i)==4)
				    option4.setChecked(true);
				if(temp[0].charAt(i)==5)
				    option5.setChecked(true);	
				if(temp[0].charAt(i)==6)
				    option6.setChecked(true);	
				
			}
			
			}
			
			
			
			//retrieving values end
		tabHost.setCurrentTab(tabHost.getCurrentTab()-1);
		}
		
		// TODO Auto-generated method stub
		
	}


	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		
		if(type.getText().toString().contentEquals("  single  ")||type.getText().toString().contentEquals("true/false"))
		{
			if(arg1)
			{
			  
				   if(arg0.getId()!=R.id.option1)
					   option1.setChecked(false);
				   if(arg0.getId()!=R.id.option2)
					   option2.setChecked(false);
				   if(arg0.getId()!=R.id.option3)
					   option3.setChecked(false);
				   if(arg0.getId()!=R.id.option4)
					   option4.setChecked(false);
				   if(arg0.getId()!=R.id.option5)
					   option5.setChecked(false);
				   if(arg0.getId()!=R.id.option6)
					   option6.setChecked(false);
				   
			  
			}
		}
		
		
		
	}
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
				if((socket==null)||(!socket.isConnected())||(socket.isClosed())){
					if((!(socket==null))&&(!(socket.isClosed())))
					{Log.d("closing", "socket");
					socket.close();}
					
					socket=null;
					socket=new DatagramSocket();
				}
				socket.send(packet);
				Log.i("client", " packet sent to teacher");
				socket.close();
			}
			
			catch(SocketException e){
				Log.d("error","Socket conn error");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("error","Socket sending error");
				e.printStackTrace();
			}
			
		}
		
	}


	
	
    ///
    
    
    
    }


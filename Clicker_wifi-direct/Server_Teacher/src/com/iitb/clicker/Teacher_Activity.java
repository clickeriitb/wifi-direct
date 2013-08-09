package com.iitb.clicker;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.GroupInfoListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Teacher_Activity extends Activity implements OnClickListener
{
	TextView login_error;
	EditText pasword,clas1,section1;
	String password;
	String clas,section;
	Database d;
	// WifiP2pManager manager;
     //Channel channel;
	  final IntentFilter intentFilter = new IntentFilter();
	//  BroadcastReceiver receiver = null;
	 private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        /*
         * storing mac of teacher in preferences so that teacher can view it in next screen
         *                                                                    -Anand 
         */
        WifiManager man=(WifiManager)getSystemService(Context.WIFI_SERVICE);
		man.setWifiEnabled(true);
		for(int i=0;i<1000;i++);// delay loop
		String Mymac=man.getConnectionInfo().getMacAddress();
		man.setWifiEnabled(false);
		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor ed=pref.edit();
		ed.putString("mmac", Mymac);
		ed.commit();

////
        
        try{
            InputStream myInput = this.getAssets().open("clicker_db");
            
        	// Path to the just created empty db
        	String outFileName = "/data/data/com.iitb.clicker/databases/clicker_db";
     
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
        
        
        
        
        
        
        
        
        
        d=new Database(this);
		Button btnforgot = (Button)findViewById(R.id.btnForgotPassword);
		btnforgot.setOnClickListener(this);
		Button btnchange = (Button)findViewById(R.id.btnChangePassword);
		btnchange.setOnClickListener(this);
        final Button btnLink = (Button)findViewById(R.id.btnLink);
		btnLink.setOnClickListener(this);
		Button btnexit = (Button)findViewById(R.id.btnExit);
		btnexit.setOnClickListener(this);
		login_error = (TextView) findViewById(R.id.login_error);
		pasword = (EditText) findViewById(R.id.password);
		clas1 = (EditText) findViewById(R.id.clas);
		section1 = (EditText) findViewById(R.id.section);
		btnLink.setText("Proceed");
		btnLink.setOnClickListener(new View.OnClickListener() 
		{			
			public void onClick(View view) 
			{
				if (checkEmpty(clas1) && checkEmpty(section1))
				{
			try{					
			Log.i("in","Login");
			password=pasword.getText().toString();
			Log.i("password in teacher",password);			
			clas=clas1.getText().toString();
			section=section1.getText().toString();
			
			if(d.check_password(password)) // checking if password is stored in database
				{
					try 
					{					 
					 Intent i = new Intent(getApplicationContext(),HomePage.class);
					 Bundle bundle = new Bundle();   
			         bundle.putString( "class",clas);
			         bundle.putString("section",section);
			         i.putExtras(bundle);   
					startActivity(i);
					finish();					
					} catch (Exception e) 
						{					
							Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
						}				
				}
			else
				{
				login_error.setText("Wrong Password Re-Enter");
				}			
			//d.close();		
			}
		catch(Exception e)
			{
		Toast.makeText(Teacher_Activity.this,e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
				else
				{
					Toast.makeText(getApplicationContext(), "Value Empty Please-Enter", Toast.LENGTH_LONG).show();
				}
		}
	  });
	}
   

    public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btnForgotPassword:
			Intent i1 = new Intent(this,Forgot_Password.class);
			startActivity(i1);
			break;
		case R.id.btnChangePassword:
			Intent i2 = new Intent(this,Change_Password.class);
			startActivity(i2);
			break;
		case R.id.btnExit:
			Intent intent1 = new Intent(Intent.ACTION_MAIN); 
	        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	        intent1.addCategory(Intent.CATEGORY_HOME);
	        startActivity(intent1);
		break;
		}
		
	}
    private boolean checkEmpty(EditText etText)
	{
	 if(etText.getText().toString().length() > 0)
	    return true;
	 else
	   return false;
	}
    @Override
    protected void onDestroy() 
    {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	d.close();
    }


}
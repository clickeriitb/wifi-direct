package com.iitb.clicker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity 
{
	Button btnRegister;
	EditText inputFullName;
	EditText inputRoll;
	EditText inputClass;
	EditText inputSection;
	EditText macAddress;
	TextView registerErrorMsg;
	String macAddr;
	DatagramSocket socket;
    DatagramPacket Spacket;
    String ServerIp;
    private String array[];
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		Bundle bundle = getIntent().getExtras();
		ServerIp = bundle.getString("ip");
		macAddr=bundle.getString("mac");
		array = new String[5];

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputRoll = (EditText) findViewById(R.id.registerRoll);
		inputClass = (EditText) findViewById(R.id.registerClass);
		inputSection = (EditText) findViewById(R.id.registerSection);
		macAddress = (EditText) findViewById(R.id.macAddress);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		
        macAddress.setText(macAddr);
        try 
        {
			socket=new DatagramSocket(2000);
		} 
        catch (SocketException e) 
		{
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
		
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() 
		{			
			public void onClick(View view) 
			{
				if (checkEmpty(inputFullName) && checkEmpty(inputRoll) && checkEmpty(inputClass) && checkEmpty(inputSection))
				{
					array[0] = inputFullName.getText().toString();					
				    array[1] = inputRoll.getText().toString();
				    array[2] = inputClass.getText().toString();
				    array[3] = inputSection.getText().toString();
				    array[4] = macAddr;
					
					String send=")"+array[0]+"|"+array[1]+"|"+array[2]+"|"+array[3]+"|"+array[4]+"|";
					
					// send registration data
					try 
					{						
						byte [] Sdata=send.getBytes();
						byte [] ip=InetAddress.getByName(ServerIp).getAddress();
						//byte [] ip={(byte)10,(byte)0,(byte)2,(byte)2};
						Spacket=new DatagramPacket(Sdata,Sdata.length,InetAddress.getByAddress(ip),3000);
						
						// Use PACKET SENDER  wherever packets are being sent                  -Anand    
						new Thread(new packetSender(Spacket)).start();
						Log.d("Sending packets","From different thread");
					
						// Close Registration Screen
						Intent dashboard = new Intent(getApplicationContext(),HomePage.class);
						// Close all views before launching Login Screen
						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						// Launch Login Screen
						startActivity(dashboard);
						finish();
						
							
					} 
					catch (Exception e) 
					{						
						Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
					}
				}
				else
				{					
					Toast.makeText(getApplicationContext(), "Value Empty Re-Enter", Toast.LENGTH_LONG).show();
				}		
									
			}	
		});
	}
	
	private boolean checkEmpty(EditText etText)
	{
	 if(etText.getText().toString().length() > 0)
	    return true;
	 else
	   return false;
	}
	
	//my addn for packets - you cant do networking on main thread
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
					socket=new DatagramSocket(2000);
				}
				socket.send(packet);
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
	

	

}
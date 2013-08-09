package com.iitb.clicker;

import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,ChannelListener,ConnectionInfoListener,PeerListListener
{ static volatile Boolean universe=true;  
	DatagramSocket socket;
    DatagramPacket Rpacket;
    DatagramPacket Spacket;
    WifiP2pInfo info;
     boolean check=false;
     boolean retryChannel=false;
     WifiP2pManager manager;
     Channel channel;
    ProgressDialog progressDialog = null;
	String macAddr;
	String ServerIp;
	String Servermac=" ";
	String ServerMsg="";
	Object o1;
//	Handler h;
	LoginActivity sd;
	  final IntentFilter intentFilter = new IntentFilter();
	 BroadcastReceiver receiver = null;
	
	 private List peers = new ArrayList();
	 @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);	
		o1=new Object();
		Button btnip = (Button)findViewById(R.id.btnip);
		btnip.setOnClickListener(this);
		Button btnConnect = (Button)findViewById(R.id.btnConnect);
		btnConnect.setOnClickListener(this);
		Button btnper = (Button)findViewById(R.id.btn_peer);
		btnper.setOnClickListener(this);
		Button btnExit = (Button)findViewById(R.id.btnExit);
		btnExit.setOnClickListener(this);
		sd=this;
		WifiManager man=(WifiManager)getSystemService(Context.WIFI_SERVICE);
		man.setWifiEnabled(true);
		macAddr=man.getConnectionInfo().getMacAddress();
		man.setWifiEnabled(false);
		
		Toast.makeText(sd, "mac found"+macAddr, Toast.LENGTH_SHORT).show();
		
		// removing the last mac address entered ( remove it if previously entered mac is to be retained)
		
		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		pref.edit().remove("tmac").commit();
		 
		/*
		 * starting Wi-Fi P2p service (i.e Wifi direct )
		 * also defining the channel using which the devices shall
		 * communicate 
		 *                                          -Anand
		 */
		manager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
	    channel = manager.initialize(getApplicationContext(),
	            getMainLooper(), null);

	    
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
		
		
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
	        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
	        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
	        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

		
	        
	        
	     
	        
	        
	     
  		
  	   
  		
       			
	


			
			
			
	}

	Handler h=new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		TextView loginConnectMsg = (TextView) findViewById(R.id.login_connect);
    		//Log.d("Message from server", ServerMsg);
    		    		
    		if(ServerMsg.equals("Registered")) // checking if macaddress is stored in database
			{
    			//Log.i("inside Registered","true");
				Toast.makeText(LoginActivity.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
				loginConnectMsg.setText("Successfully Logged In");
						universe=false;
						socket.close();
					//	Bundle bundle = new Bundle();
						// bundle.putString( "ip",ServerIp);
						Intent i = new Intent(getApplicationContext(),HomePage.class);	
					//	i.putExtras(bundle);   
						startActivity(i);
						finish();
					
			}
			else if (ServerMsg.equals("UnRegistrd"))
			{
				//Log.i("inside Not Registered","true");
				Toast.makeText(LoginActivity.this,"You are not Registered", Toast.LENGTH_LONG).show();
				loginConnectMsg.setText("You are not Registered");
				universe=false;
				socket.close();
				Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
				 Bundle bundle = new Bundle();   
		         bundle.putString( "ip",ServerIp);
		         bundle.putString("mac",macAddr);
		        
					
		         
				
				try {
					 socket.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
					}
				i.putExtras(bundle);   
				startActivity(i);
					finish();
				
			}
    		
    	}
    };
	 
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	     
		 Servermac=pref.getString("tmac","null");
		 
	        TextView loginErrorMsg = (TextView) findViewById(R.id.login_error);	
	        loginErrorMsg.setText("Mac Address Entered is "+Servermac);
	        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
	        registerReceiver(receiver, intentFilter);
	        
	}
	public void onPause(){
		super.onPause();
		unregisterReceiver(receiver);
	}
	

	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btnip:
			  
			
			Intent i = new Intent(this, IpPreference.class);
			startActivityForResult(i,1);
			break;
		case R.id.btn_peer:
			 manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
					
					public void onSuccess() {
						// TODO Auto-generated method stub
						
					}
					
					public void onFailure(int reason) {
						// TODO Auto-generated method stub
						
					}
				});
			   
			   break;
		case R.id.btnConnect:
			try {
				
/*
 * Forming group with the peer with this device acting as client
 * 				
 */
				WifiP2pConfig config = new WifiP2pConfig();
		        WifiP2pDevice device=(WifiP2pDevice)peers.get(0);// not required but used to inform user that he must press discover peers first
				config.deviceAddress = Servermac;
				
				config.wps.setup = WpsInfo.PBC;
				config.groupOwnerIntent=1;
				
				
				if (progressDialog != null && progressDialog.isShowing()) {
		             progressDialog.dismiss();
		         }
		         progressDialog = ProgressDialog.show(sd, "Press back to cancel",
		                 "Connecting to :" + Servermac, true, true
//		                 new DialogInterface.OnCancelListener() {
		 //
//		                     @Override
//		                     public void onCancel(DialogInterface dialog) {
//		                         ((DeviceActionListener) getActivity()).cancelDisconnect();
//		                     }
//		                 }
		                 );
		         manager.connect(channel, config, new ActionListener() {
						
						public void onSuccess() {
							// TODO Auto-generated method stub
							Log.d("LOGINACTIVITY","Connection success");
						}
						
						public void onFailure(int reason) {
							// TODO Auto-generated method stub
							Toast.makeText(sd, "failed", Toast.LENGTH_SHORT).show();
							Log.d("LOGINACTIVITY","Connection failed");
						}
					});
					
			       
				
				
			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "First press discover peers and then try to connect", Toast.LENGTH_LONG).show();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.btnExit:
			android.os.Process.killProcess(android.os.Process.myPid());
		break;
		}
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		try {
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			}
		
	}

/*
 * Making user aware if ant sort of exception occurs
 * (non-Javadoc)
 * @see android.net.wifi.p2p.WifiP2pManager.ChannelListener#onChannelDisconnected()
 * 
 *                                                            -Anand
 */
	
	
	public void onChannelDisconnected() {
		// TODO Auto-generated method stub
		   if (manager != null && !retryChannel) {
	            Toast.makeText(this, "Channel lost. Trying again", Toast.LENGTH_LONG).show();
	          
	            retryChannel = true;
	            manager.initialize(this, getMainLooper(), this);
	        } else {
	            Toast.makeText(this,
	                    "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",
	                    Toast.LENGTH_LONG).show();
	        }
	}

	/*
	 * This is the callback fuction when any sort of change in connection 
	 * (i.e new group being formed or any data being sent to this device , 
	 * because of multiple calls to this boolean check is used to ensure that 
	 * the thread runs only once when the group is first formed and not every time
	 * a packet is received
	 *                                                              -Anand
	 * (non-Javadoc)
	 * @see android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener#onConnectionInfoAvailable(android.net.wifi.p2p.WifiP2pInfo)
	 */
	public void onConnectionInfoAvailable(WifiP2pInfo info) {
		
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		this.info = info;
		if(info.groupFormed){
			Log.d("LOGINACTIVITY","Group formed");
		if(check==false){
			check=true;                                  // to run only once- the first connection
		ServerIp=info.groupOwnerAddress.getHostAddress();
		Toast.makeText(this,ServerIp,Toast.LENGTH_SHORT);
		Log.d("checking ip",ServerIp);
		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor ed=pref.edit();
		ed.putString("ip", ServerIp);
		ed.commit();

		
		
		 if(macAddr==null)
 		 {
      		macAddr="00:00:00:00:00:00";	//setting macAddress if no value is obtained
 		 }

		
		
  		new Thread(new Runnable() {
    		public void run() {
    			Log.d("Delay","Started");
    			double dd=5;
                for(int im=0;im<30000;im++){
                	//time delay waiting for network to establish  (necessary for udp transmissions to work)
                	// as ips are not assigned instantly after group formation there is a slight delay
                	dd=(dd*55)%20;
                }
                Log.d("Delay","end");
    			try 
    		     {
    				socket=new DatagramSocket(2000);
    				
    			 }
    		     catch (SocketException e) 
    		     {
    					// TODO Auto-generated catch block
    					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    			 }
    			
    			try{		String Address="("+macAddr; //for identifying as macaddress ( as identifier
    			
    			byte [] Sdata=Address.getBytes();
    			byte [] ip=InetAddress.getByName(ServerIp).getAddress();
    			//byte [] ip={(byte)10,(byte)0,(byte)2,(byte)2};
    			Spacket=new DatagramPacket(Sdata,Sdata.length,InetAddress.getByAddress(ip),3000);
    			socket.send(Spacket);
    			// allowed to send without using packetSender as it is in a different thread than main thread
    			Log.i("mac-send",macAddr);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    		}
    			while(universe)
    			{
    			try {	
    				if((socket==null)||(!socket.isConnected())||(socket.isClosed())){
    					if((!(socket==null))&&(!(socket.isClosed())))
    					{Log.d("closing", "socket");
    					socket.close();}
    					socket=null;
    					socket=new DatagramSocket(2000);
    					Log.d("restarting", "socket");
    				}
    				byte [] Rdata=new byte[1000];
    				Rpacket=new DatagramPacket(Rdata,Rdata.length);
    				socket.receive(Rpacket);
    				ServerMsg= new String(Rpacket.getData());
    				ServerMsg=ServerMsg.substring(0, 10);
    				Log.d("Message from server", ServerMsg);
    				Message m=new Message();
    				h.sendMessage(m);
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			}
    		}
    	}).start();
		}
		}
	}
	// informs that group is formed
	public void myself(WifiP2pDevice dev) {
		
		Toast.makeText(this, "my device "+dev.deviceName+" status "+dev.status, Toast.LENGTH_LONG).show();
	}
	
	// list of all available peers is returned here 
	// peers list can be utilised if required by activity
	public void onPeersAvailable(WifiP2pDeviceList peerList) {
			Log.d("FIRSTACTIVITY","Peers found");
		peers.clear();
        peers.addAll(peerList.getDeviceList());
        
        
		
		 
			
        

	}
}


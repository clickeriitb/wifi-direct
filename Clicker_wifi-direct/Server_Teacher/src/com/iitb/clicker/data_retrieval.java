package com.iitb.clicker;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

 
public class data_retrieval extends Activity implements OnClickListener{
	
	 DatagramSocket socket;
	 DatagramPacket Rpacket; 
	 DatagramPacket Spacket;
	 Cursor cursor,cursor1,cursor2,cursor3;
	 StringBuilder str=new StringBuilder();
	 String sql,ClientIp="10.105.14.68";
	 Integer quiz_id=2;
	 String time,title;
	 EditText e1,e2,e3,e4;
	 Button b;
	 SQLiteDatabase db;
	 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.send_quiz);
	        e1=(EditText)findViewById(R.id.editText1);
	        e2=(EditText)findViewById(R.id.editText2);
	        e3=(EditText)findViewById(R.id.editText3);
	        e4=(EditText)findViewById(R.id.editText4);
	        b=(Button)findViewById(R.id.button1);
	        b.setOnClickListener(this);
	        /*try {
				socket=new DatagramSocket();
			} catch (SocketException e1) {
				e1.printStackTrace();
				
			}*/
			Bundle extras=getIntent().getExtras();
	        quiz_id=Integer.parseInt(extras.getString("quiz_id"));
	        
			db=openOrCreateDatabase("clicker_db", SQLiteDatabase.OPEN_READONLY, null);
		    Cursor cursor = db.query("QUIZ", null,"QUIZ_ID=?", new String[]{quiz_id.toString()}, null, null, null);
			startManagingCursor(cursor);
			cursor.moveToFirst();
			String title=cursor.getString(1);
			e1.setText(title);
			Date date=new Date();	
			e2.setText(date.toLocaleString());

		
	 }
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		try{
        	int count=0;
        db=openOrCreateDatabase("clicker_db", SQLiteDatabase.OPEN_READONLY, null);
		sql =  "UPDATE QUIZ SET TITLE='"+e1.getText().toString().toUpperCase()+"' ,DATE='"+e2.getText().toString()+"' WHERE QUIZ_ID='"+quiz_id+"';";              
		db.execSQL(sql);
		//Toast.makeText(getApplicationContext(), "inserted!", 1000).show();
		Cursor cursor = db.query("QQ_MAP", null,"QUIZ_ID=?", new String[]{quiz_id.toString()}, null, null, null);
		
	
		startManagingCursor(cursor);
		time=e3.getText().toString()+":"+e4.getText().toString();
		//cursor.moveToFirst();
		
		//Integer qid = cursor.getInt(cursor.getColumnIndex("Q_ID"));
		//Toast.makeText(getApplicationContext(), "time="+time, 1000).show();
		str.append(XmlWriter.InitialiseData(quiz_id, time));
		//Toast.makeText(getApplicationContext(), "rows="+cursor.getCount(), 1000).show();

		while (cursor.moveToNext()) {
			Integer qid = cursor.getInt(cursor.getColumnIndex("Q_ID"));
			//Toast.makeText(getApplicationContext(), "qid="+qid, 1000).show();
			cursor1=db.query("QUESTION",null,"Q_ID=?",new String[]{qid.toString()},null,null,"Q_ID");
			startManagingCursor(cursor1);
			cursor1.moveToFirst();
			
			String qtn=cursor1.getString(3);
			String type=cursor1.getString(4);
			cursor2=db.query("ANSWER",null,"Q_ID=?",new String[]{qid.toString()},null,null,null);	
			startManagingCursor(cursor2);
			String []arr= new String[cursor2.getCount()];
			int i=0;
			while(cursor2.moveToNext())
			{
				String ans=cursor2.getString(cursor2.getColumnIndex("ANSWER"));
				arr[i]=ans;
				i++;
			}
			str.append(XmlWriter.DataToXml(qid,type.charAt(0),qtn,arr));
			count++;
			
		}
		str.append(XmlWriter.FinalData());
		e1.setText(str);
		byte [] Sdata=str.toString().getBytes();
		cursor3=db.query("STUDENT",null,"IP_ADDR!=0",null,null,null,null);
		startManagingCursor(cursor3);
		//Toast.makeText(getApplicationContext(), "no of rows="+cursor3.getCount(), Toast.LENGTH_LONG).show();
		while(cursor3.moveToNext())
		{
		
			String ip_addr=cursor3.getString(6);
			//Toast.makeText(getApplicationContext(), "ipaddr:"+ip_addr, Toast.LENGTH_LONG).show();
			
			Spacket=new DatagramPacket(Sdata,Sdata.length,InetAddress.getByName(ip_addr),2000);
			Log.i("sss", new String(Sdata));
			
			new Thread(new packetSender(Spacket)).start();
			// use packet sender for sending packets
			
			Log.i("azzz", "inside puri");
			
		}
		Toast.makeText(getApplicationContext(), "sent!", Toast.LENGTH_LONG).show();
		db.close();
		//socket.close();
        }
        catch(Exception e){
        	e1.setText(e.toString());
        	Toast.makeText(getApplicationContext(), "ERROR "+e.toString(), Toast.LENGTH_LONG).show();
        }
		
		Bundle bundle=new Bundle();
		bundle.putString("Quiz_min", e3.getText().toString());
		bundle.putString("Quiz_sec", e4.getText().toString());
		bundle.putInt("Quiz_id", quiz_id);
		if((!(HomePage.socket==null))||(!(HomePage.socket.isClosed()))||(HomePage.socket.isBound()))
		{HomePage.socket.close();}
		HomePage.universe=false;
		Intent i=new Intent(data_retrieval.this,HomePage.class);
          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtras(bundle);
        startActivity(i);
        finish();
	}
	//PacketSender same as used elsewhere  -Anand
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
					socket=new DatagramSocket();//port binding not required as not listening here
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
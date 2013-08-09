package com.iitb.clicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Forgot_Password extends Activity implements OnClickListener, OnItemSelectedListener
{
	
	EditText old,newp,confirm,answer1;
	Button btnrecover,btnback;
	String oldPass,newPass,confirmPass,ans;
	private Spinner spin;
	int iCurrentSelection;
	String[] ques = {"What is your's mothers maiden name?","Which is your native city","Enter your Date of Birth in (DDMMYY)"};
	Database d;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
        d=new Database(this);
        newp = (EditText)findViewById(R.id.registerPassword);
        confirm = (EditText)findViewById(R.id.confirmPassword);
        answer1= (EditText)findViewById(R.id.answer);
		btnrecover = (Button)findViewById(R.id.btnRecover);
		btnrecover.setOnClickListener(this);
		btnback = (Button)findViewById(R.id.btnBack);
		btnback.setOnClickListener(this);
        spin = (Spinner)findViewById(R.id.spinner1);
		spin.setOnItemSelectedListener(this);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ques);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa);
		iCurrentSelection = spin.getSelectedItemPosition();
		
		btnrecover.setOnClickListener(new View.OnClickListener() 
		{			
			public void onClick(View view) 
			{
				if (checkEmpty(answer1) && checkEmpty(newp) && checkEmpty(confirm))
				{
		    	try{			
					ans=answer1.getText().toString();
					if(!d.check_security_answer(iCurrentSelection,ans)) // checking if Security answer is stored in database
					{
						AlertDialog.Builder alert_box=new AlertDialog.Builder(Forgot_Password.this);
						alert_box.setTitle("ALERT");
						alert_box.setMessage("Security Answer Do Not Match");
						alert_box.setPositiveButton("ReEnter",new DialogInterface.OnClickListener() {
						   
						   public void onClick(DialogInterface dialog, int which) 
						   {
							  // inputPassword.setSelection(0);
						   }
						  });
						alert_box.setNegativeButton("Back", new DialogInterface.OnClickListener() {
						   
						   
						   public void onClick(DialogInterface dialog, int which) {
						    // TODO Auto-generated method stub
							   Intent i = new Intent(getApplicationContext(),Teacher_Activity.class);
								// Close all views before launching Dashboard
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(i);
						   }
						  });
						alert_box.show();
							
					}
					else
					{						
						newPass=newp.getText().toString();
						confirmPass=confirm.getText().toString();
						if(!newPass.equals(confirmPass))
						{
							AlertDialog.Builder alert_box=new AlertDialog.Builder(Forgot_Password.this);
							alert_box.setTitle("ALERT");
							alert_box.setMessage("Passwords Do not Match");
							alert_box.setPositiveButton("ReEnter",new DialogInterface.OnClickListener() 
							{							   
							   public void onClick(DialogInterface dialog, int which) 
							   {
								  
							   }
							  });
							alert_box.setNegativeButton("Back", new DialogInterface.OnClickListener() 
							{  
							   public void onClick(DialogInterface dialog, int which) 
							   {
							    // TODO Auto-generated method stub
								   Intent i = new Intent(getApplicationContext(),Teacher_Activity.class);
									// Close all views before launching Dashboard
									i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(i);
							   }
							  });
							alert_box.show();
						}
						else
						{
							d.addPassword(newPass);
							Toast.makeText(Forgot_Password.this,"Password Updated Successfully", Toast.LENGTH_LONG).show();
							d.close();
							Intent dashboard = new Intent(getApplicationContext(),Teacher_Activity.class);
							// Close all views before launching Login Screen
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);
							// Close Registration Screen
							finish();
						}
					}
					//dbUser.close();					
		    	}
		    	catch(Exception e)
				{
		    		Toast.makeText(Forgot_Password.this,e.getMessage(), Toast.LENGTH_LONG).show();
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
		case R.id.btnBack:
			//dbUser.close();
			Intent dashboard = new Intent(getApplicationContext(),Teacher_Activity.class);
			// Close all views before launching Login Screen
			dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(dashboard);
			// Close Registration Screen
			finish();
		break;
		}
		
	}
	 public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
	    {
	    	if (iCurrentSelection != position)
	    	{
	    		Log.i("item selected position inside",Integer.toString(iCurrentSelection));
	    	}
	    	iCurrentSelection = position;
	    	Log.i("item selected position outside",Integer.toString(iCurrentSelection));
	    }

	    public void onNothingSelected(AdapterView<?> parentView) 
	    {
	        // your code here
	    }
	    private boolean checkEmpty(EditText etText)
		{
		 if(etText.getText().toString().length() > 0)
		    return true;
		 else
		   return false;
		}
}

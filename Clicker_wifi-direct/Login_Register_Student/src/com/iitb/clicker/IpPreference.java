package com.iitb.clicker;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class IpPreference extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.ip);
	}
	

}

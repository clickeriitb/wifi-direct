package com.iitb.clicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainFileActivity extends Activity implements OnClickListener {
	String currentDBPath;
	String backupDBPath;
	private static final int REQUEST_PICK_FILE = 1;
	private TextView mFilePathTextView;
	private Button mStartActivityButton;
	DBHelper d = new DBHelper(this);
	String data;
	File f;
	FileWriter writer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.impexplay);
		mFilePathTextView = (TextView) findViewById(R.id.file_path_text_view);
		mStartActivityButton = (Button) findViewById(R.id.start_file_picker_button);
		mStartActivityButton.setOnClickListener(this);
		data = Environment.getExternalStorageDirectory().toString();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_file_picker_button:
			Intent intent = new Intent(this, FilePickerActivity.class);
			startActivityForResult(intent, REQUEST_PICK_FILE);
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_PICK_FILE:
				if (data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
					f = new File(
							data.getStringExtra(FilePickerActivity.EXTRA_FILE_PATH));
					mFilePathTextView.setText(f.getPath());
				}
			}
		}
	}

	public void exportDb(View view) {
		confirmDialog(R.string.export_warning, 0, true);
	}

	public void importDb(View view) {
		confirmDialog(R.string.import_warning, 1, true);
	}

	public void backup(int mode) {
		try {
			File sd = Environment.getExternalStorageDirectory();
			if (sd.canWrite()) {
				switch (mode) {
				case 0:
					
					  InputStream myInput;
					  
					  try {
					 
					  myInput = new FileInputStream(
					  "/data/data/com.iitb.clicker/databases/clicker_db");
					  File directory = new File("/sdcard/Export"); if
					  (!directory.exists()) { directory.mkdirs(); }
					  OutputStream myOutput = new FileOutputStream(
					  directory.getPath() + "/clicker_db.backup"); byte[] buffer =
					  new byte[1024]; int length; while ((length =
					  myInput.read(buffer)) > 0) { myOutput.write(buffer, 0,
					  length); } myOutput.flush(); myOutput.close();
					  myInput.close(); } catch (IOException e) {
					  Toast.makeText(
					  MainFileActivity.this,"Backup Unsuccesfull!",
					  Toast.LENGTH_LONG).show(); e.printStackTrace(); }
					  Toast.makeText
					  (MainFileActivity.this,"Backup Done Succesfully!",
					  Toast.LENGTH_LONG).show();
					 

				case 1:
					OutputStream myOutput;
					try {
						myOutput = new FileOutputStream(
								"/data/data/com.iitb.clicker/databases/clicker_db");
						InputStream myInputs = new FileInputStream(f.getPath());
						byte[] buffer = new byte[1024];
						int length;
						while ((length = myInputs.read(buffer)) > 0) {
							myOutput.write(buffer, 0, length);
						}
						myOutput.flush();
						myOutput.close();
						myInputs.close();

					} catch (FileNotFoundException e) {
						Toast.makeText(MainFileActivity.this,
								"Import Unsuccesfull!", Toast.LENGTH_LONG)
								.show();
						e.printStackTrace();
					} catch (IOException e) {
						Toast.makeText(MainFileActivity.this,
								"Import Unsuccesfull!", Toast.LENGTH_LONG)
								.show();
						e.printStackTrace();
					}
					Toast.makeText(MainFileActivity.this,
							"Import Done Succesfully!", Toast.LENGTH_LONG)
							.show();
				}

			} else {
				Toast.makeText(
						this,
						"There is something wrong with your's SD card. Maybe you haven't inserted it?",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	public void confirmDialog(int warningMessage, final int mode,
			final boolean doBackup) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(this.getString(warningMessage))
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (doBackup) {
									backup(mode);
								} else {
									d.close();
								}
								Toast.makeText(MainFileActivity.this, "Done.",
										Toast.LENGTH_SHORT).show();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.create();
		builder.show();
	}

}
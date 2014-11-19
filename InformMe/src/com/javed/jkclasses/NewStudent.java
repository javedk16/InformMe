package com.javed.jkclasses;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class NewStudent extends Activity{
	EditText Name,Phone;
	DBController controller = new DBController(this);
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_student);
	        Name = (EditText) findViewById(R.id.Name);
	        Phone=(EditText)findViewById(R.id.Phone);
	    }
	public void addSKU(View view) {
		HashMap<String, String> queryValues =  new  HashMap<String, String>();
		queryValues.put("Name", Name.getText().toString());
		queryValues.put("Phone", Phone.getText().toString());
		controller.insertStudent(queryValues);
		this.callHomeActivity(view);
	}
	public void callHomeActivity(View view) {
		Intent objIntent = new Intent(getApplicationContext(), JKMainActivity.class);
		startActivity(objIntent);
		this.finish();
	}	
}

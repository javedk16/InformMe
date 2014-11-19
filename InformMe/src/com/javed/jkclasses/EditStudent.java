package com.javed.jkclasses;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditStudent extends Activity{
	EditText studentName,phone;
	DBController controller = new DBController(this);
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_edit_student);
			studentName = (EditText) findViewById(R.id.studentName);
			phone = (EditText)findViewById(R.id.Phone);
			Intent objIntent = getIntent();
			String studentId = objIntent.getStringExtra("StudId");
			Log.d("Reading: ", "Reading all contacts..");
			HashMap<String, String> studentList = controller.getStudentInfo(studentId);
			Log.d("studentName",studentList.get("Name"));
			if(studentList.size()!=0) {
			studentName.setText(studentList.get("Name"));
			phone.setText(studentList.get("Phone"));
			}
	    }
	public void editStudent(View view) {
		HashMap<String, String> queryValues =  new  HashMap<String, String>();
		studentName = (EditText) findViewById(R.id.studentName);
		Intent objIntent = getIntent();
		String studentId = objIntent.getStringExtra("StudId");
		queryValues.put("StudId", studentId);
		queryValues.put("Name", studentName.getText().toString());
		queryValues.put("Phone", phone.getText().toString());
		controller.updateStudent(queryValues);
		this.callHomeActivity(view);
		this.finish();
		
	}
	public void removeStudent(View view) {
		Intent objIntent = getIntent();
		String studentId = objIntent.getStringExtra("StudId");
		controller.deleteStudent(studentId);
		this.callHomeActivity(view);
		this.finish();
		
	}
	public void callHomeActivity(View view) {
		Intent objIntent = new Intent(getApplicationContext(), JKMainActivity.class);
		startActivity(objIntent);
		this.finish();
	}
}


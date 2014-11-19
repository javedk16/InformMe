package com.javed.jkclasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class JKMainActivity extends ListActivity {
	
	Intent intent;
	TextView StudentId;
	EditText inputSearch;
	ListAdapter adapter;
	ListView lv;
	String tp[]={"a","b","c","d"};
	ArrayList<HashMap<String, String>> studentList;
	String numbers;
	CheckBox cb;
	DBController controller = new DBController(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//inputSearch = (EditText) findViewById(R.id.inputSearch);
		studentList =  controller.getAllStudents();
		if(studentList.size()!=0) {
		lv = getListView();
		
		/*lv.setOnItemClickListener(new OnItemClickListener() {
			  @Override 
			  public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				  String valStudentId = studentList.get(position).get("StudId");					  
				  Intent  objIndent = new Intent(getApplicationContext(),EditStudent.class);
				  objIndent.putExtra("StudId", valStudentId); 
				  startActivity(objIndent); 
			  }
		}); */
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
                // TODO Auto-generated method stub
            	  String valStudentId = studentList.get(pos).get("StudId");					  
				  Intent  objIndent = new Intent(getApplicationContext(),EditStudent.class);
				  objIndent.putExtra("StudId", valStudentId); 
				  startActivity(objIndent); 
				  finish();
            	
            	Log.v("StudentID","studId " + studentList.get(pos).get("StudId"));
                Log.v("long clicked","pos: " + pos);
                return true;
            }
        }); 
			
			adapter = new SimpleAdapter( JKMainActivity.this,studentList, R.layout.view_student_entry, new String[] { "Name"}, new int[] {R.id.Name});
			setListAdapter(adapter);
				
		}
		
		/*inputSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				((SimpleAdapter) JKMainActivity.this.adapter).getFilter().filter(cs);	
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub							
			}
		});*/
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id)
    		{
    		//---toggle the check displayed next to the item---
    	    numbers="";
    		int len =parent.getCount();
    		SparseBooleanArray checked=parent.getCheckedItemPositions();
    		for (int i = 0; i < len; i++)
    			 if (checked.get(i)) {
    			  //String item = lv.getItemAtPosition(i).toString();
    			  numbers = numbers+";"+studentList.get(i).get("Phone");
    			  //s=studentList.get(i).get("Phone");
    			  /* do whatever you want with the checked item */
    			 }
    		//Toast.makeText(this,"Selected Items- " + numbers,Toast.LENGTH_LONG).show();
    		}
	
	
	public void showAddForm(View view) {
		
		Intent objIntent = new Intent(getApplicationContext(), NewStudent.class);
		startActivity(objIntent);
		this.finish();
	}
	
	public void sendSMS(View view) {

		Intent smsIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+numbers));
		String message="JK Classes Parental Alert Service!\n"+
						"This is to inform you that your ward has not been attending the classes regularly"+
						" and was absent on "+
						java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime())+".";
		smsIntent.putExtra("sms_body", message);
		startActivity(smsIntent);
	}
		
}

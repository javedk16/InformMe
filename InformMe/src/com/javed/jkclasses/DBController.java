package com.javed.jkclasses;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController  extends SQLiteOpenHelper {
	private static final String LOGCAT = null;

	public DBController(Context applicationcontext) {
        super(applicationcontext, "sqlitejaved.db", null, 1);
        Log.d(LOGCAT,"Created");
    }
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE Studtable ( StudId INTEGER PRIMARY KEY,Name TEXT,Phone TEXT)";
        database.execSQL(query);
        Log.d(LOGCAT,"Studtable Created");
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS Studtable";
		database.execSQL(query);
        onCreate(database);
	}
	
	public void insertStudent(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Name", queryValues.get("Name"));
		values.put("Phone", queryValues.get("Phone"));
		//values.put("QTY", queryValues.get("QTY"));
		database.insert("Studtable", null, values);
		database.close();
	}
	
	public int updateStudent(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();	 
	    ContentValues values = new ContentValues();
	    values.put("Name", queryValues.get("Name"));
	    values.put("Phone", queryValues.get("Phone"));
	    return database.update("Studtable", values, "StudId" + " = ?", new String[] { queryValues.get("StudId") });
	    //String updateQuery = "Update  words set txtWord='"+word+"' where txtWord='"+ oldWord +"'";
	    //Log.d(LOGCAT,updateQuery);
	    //database.rawQuery(updateQuery, null);
	    //return database.update("words", values, "txtWord  = ?", new String[] { word });
	}
	
	public void deleteStudent(String id) {
		Log.d(LOGCAT,"delete");
		SQLiteDatabase database = this.getWritableDatabase();	 
		String deleteQuery = "DELETE FROM  Studtable where StudId='"+ id +"'";
		Log.d("query",deleteQuery);		
		database.execSQL(deleteQuery);
	}
	
	public ArrayList<HashMap<String, String>> getAllStudents() {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM Studtable";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("StudId", cursor.getString(0));
	        	map.put("Name", cursor.getString(1));
	        	map.put("Phone", cursor.getString(2));
	        	//map.put("QTY", cursor.getString(3));
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return wordList;
	}
	
	public HashMap<String, String> getStudentInfo(String id) {
		HashMap<String, String> wordList = new HashMap<String, String>();
		SQLiteDatabase database = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM Studtable where StudId='"+id+"'";
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
	        do {
					//HashMap<String, String> map = new HashMap<String, String>();
	        	wordList.put("Name", cursor.getString(1));
	        	wordList.put("Phone", cursor.getString(2));
				   //wordList.add(map);
	        } while (cursor.moveToNext());
	    }				    
	return wordList;
	}	
}

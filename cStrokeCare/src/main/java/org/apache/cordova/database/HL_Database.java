package org.apache.cordova.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;





public class HL_Database extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "Dummy_DB";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME_RECORD = "SoccerPlayer";
	
	private static final String TABLE_CREATE_RECORD=
			"CREATE TABLE " + TABLE_NAME_RECORD + " ( " +
					" id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					" Adate TEXT NOT NULL, " +
					" Adata TEXT NOT NULL); ";
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(TABLE_CREATE_RECORD);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
			int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RECORD);
		onCreate(db);
	}
	public HL_Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	private static final String Record_Adate = "Adate";
	private static final String Record_Adata = "Adata";
	/*public long synchronous_network(String[] synchronous_array,ArrayList<BP_DataSet> sites)
	{
		int count=Integer.parseInt(synchronous_array[0]);
		if(count!=0)
		{
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			
			
			
		}
				return 0;
	}*/
	public void create_RECORD()
	{
		SQLiteDatabase db = getReadableDatabase();
		db.execSQL(TABLE_CREATE_RECORD);
	}
	
	public void drop_RECORD()
	{
		SQLiteDatabase db = getReadableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_RECORD);
	}
	
	public long insertDB_RECORD(Record_DataSet set){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Record_Adate, set.getAdate());
		values.put(Record_Adata, set.getAdata());
		long rowId = db.insert(TABLE_CREATE_RECORD, null, values);
		db.close();
		return rowId;
	}
	
	public ArrayList<Record_DataSet> getAllSites_Record(){
		SQLiteDatabase db = getReadableDatabase();
		String[] columns_Record = {Record_Adate,Record_Adata};
		Cursor cursor = db.query(TABLE_NAME_RECORD, columns_Record, null, null, null, null, null);
		ArrayList<Record_DataSet> sites = new ArrayList<Record_DataSet>();
		while(cursor.moveToNext()){
			String Adate = cursor.getString(0);
			String Adata=cursor.getString(1);
						
			
			Record_DataSet site = new Record_DataSet(Adate, Adata);
			sites.add(site);
		}
		cursor.close();
		db.close();
		return sites;		
	}
	

	
	
	public int searchData_Record(String Adate){
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_NAME_RECORD + 
				" WHERE ADate LIKE ?";
		String[] args = {"%" + Adate + "%"};
		Cursor cursor = db.rawQuery(sql, args);
		int dataNum = cursor.getCount();
		return dataNum;
	}

	
	
	
}
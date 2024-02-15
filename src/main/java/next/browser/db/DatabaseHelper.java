package next.browser.db;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;

public class DatabaseHelper extends SQLiteOpenHelper
{
	SQLiteDatabase db; 

	private static final String DATABASE_NAME="history.db"; 
	private static final int DATABASE_VERSION=1; 

	private static final String TABLE_WEBPAGE="_webpage"; 

	private static final  String KEY_ID="id"; 
	private static final  String KEY_NAME="name"; 
	private static final  String KEY_URL="url"; 
	private static final  String KEY_TITLE="title"; 
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION); 
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String Query_Table=" CREATE TABLE " + TABLE_WEBPAGE + "("  
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  
			+ KEY_NAME + " TEXT, " + KEY_URL + " TEXT, " + KEY_TITLE + " TEXT);"; 
		db.execSQL(Query_Table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_WEBPAGE); 
	    onCreate(db); 
	}
	public String getData()
	{ 
		db = this.getReadableDatabase(); 
		String[] columns=new String[] {KEY_ID,KEY_NAME,KEY_URL,KEY_TITLE}; 
		Cursor cursor=db.query(TABLE_WEBPAGE, columns, null, null, null, null, null); 

		int iId= cursor.getColumnIndex(KEY_ID); 
		int iName= cursor.getColumnIndex(KEY_NAME); 
		int iEmail= cursor.getColumnIndex(KEY_URL); 
		int iMobile= cursor.getColumnIndex(KEY_TITLE); 
		String result=""; 

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{ 
			result = result + 
				"Id: " + cursor.getString(iId) + "\n" + 
				"Name: " + cursor.getString(iName) + "\n" + 
				"url:  " + cursor.getString(iEmail) + "\n" + 
				"title: " + cursor.getString(iMobile) + "\n\n"; 
		} 
		db.close(); 
		return result; 
	} 
	public long insertNew(String name, String url, String title)
	{ 
		db = this.getWritableDatabase(); 
		ContentValues values=new ContentValues(); 
		values.put(KEY_NAME, name); 
		values.put(KEY_URL, url); 
		values.put(KEY_TITLE, title); 
		return db.insert(TABLE_WEBPAGE, null, values); 
	} 
	public String getEmail(long l1)
	{ 
		db = this.getReadableDatabase(); 
		String[] columns=new String[]{KEY_ID,KEY_NAME,KEY_URL,KEY_TITLE}; 
		Cursor cursor=db.query(TABLE_WEBPAGE, columns, KEY_ID + "=" + l1, null, null, null, null); 
		if (cursor != null)
		{ 
			cursor.moveToFirst(); 
			String name=cursor.getString(2); 
			return name; 
		} 
		return null; 
	} 
}

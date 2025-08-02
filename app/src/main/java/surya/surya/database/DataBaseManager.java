package surya.surya.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

//  this class class use the save the data base
public class DataBaseManager {
    public static final int readMode = 1;
    public static final int writeMode = 2;
    static SQLiteDatabase database;
    static DBHelper helper;
    private Context context;
    private HashMap<String, String> recordHashMap = null;

    public DataBaseManager(Context context, int Mode) {
        this.context = context;
        helper = new DBHelper(context);
        dbMode(Mode);
    }

    public void dbMode(int mode) {
        if (mode == 1) {
            database = helper.getReadableDatabase();
        } else {
            database = helper.getWritableDatabase();
        }
    }

    public HashMap<String, String> getRecord() {
        recordHashMap = new HashMap<String, String>();
        Cursor cursor = database.rawQuery("SELECT * FROM  demo_details ;", null);
        Log.e("Database", "Total Records = " + cursor.getCount());
        if (!cursor.equals(null)) {
            cursor.moveToLast();
            recordHashMap.put("id", cursor.getString(0));
            recordHashMap.put("json", cursor.getString(1));
        }
        cursor.close();

        return recordHashMap;
    }

    public Long addRow(String Json, String Date, String Time, String imei) {   // Add the row of data
        Long l = null;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("Json", Json);
            contentValues.put("Date", Date);
            contentValues.put("Time", Time);
            contentValues.put("IMEI", imei);
            l = database.insert("demo_details", null, contentValues);
            //Toast.makeText(context, "Data saved in database.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return l;
    }

    public void close() {  /// close the database
        if (database != null) {
            database.close();
            helper.close();
        }

    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "demo.db", null, 1);
            Log.i("DataBase Created", "xxxxxxxx");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {   // create the data base
            System.out.print("In oncreate");
            db.execSQL("create table demo_details(ID INTEGER PRIMARY KEY   AUTOINCREMENT ,Json text,Date text,Time text,IMEI text);");
            Log.e("Table Created", "  Successfully");
            System.out.print("Table Created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }


    }

}

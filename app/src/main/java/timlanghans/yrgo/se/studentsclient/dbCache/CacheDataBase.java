package timlanghans.yrgo.se.studentsclient.dbCache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CacheDataBase extends SQLiteOpenHelper {

    private static final String LOG_TAG = CacheDataBase.class.getName();

    private static final String DATABASE_NAME = "dbStudents";
    private static final int DATABASE_VERSION = 1;

    protected static final String TABLE_Students = "tblStudents";
    protected static final String STUDENT_NAME_COLUMN = "name";
    protected static final String STUDENT_ID_COLUMN = "_id"; // keys shall have a _i

    private static final String DATABASE_CREATE =
            "CREATE TABLE " +
            TABLE_Students + "(" +
            STUDENT_NAME_COLUMN + " TEXT NOT NULL , " +
            STUDENT_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT" + ")";

    // CONSTRUCTOR
    public CacheDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Students);
        db.execSQL(DATABASE_CREATE);
        Log.d(LOG_TAG, "dbStudents dropped and recreated...");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        Log.d(LOG_TAG, "onUpgrade() called");
    }
}

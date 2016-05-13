package timlanghans.yrgo.se.studentsclient.dbCache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import timlanghans.yrgo.se.studentsclient.application.DBCacheException;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import static timlanghans.yrgo.se.studentsclient.dbCache.CacheDataBase.*;

public class DbHandler implements IDbHandler {

    private static DbHandler handler;
    private static final String LOG_TAG = DbHandler.class.getName();
    private CacheDataBase     cdb;
    private SQLiteDatabase database;

    private String[] columns = {
            CacheDataBase.STUDENT_ID_COLUMN ,
            CacheDataBase.STUDENT_NAME_COLUMN};

    public static DbHandler getInstance(Context c) {
        if (handler == null) { handler = new DbHandler(c); }
        return handler;
    }

    private DbHandler(Context c) {
        cdb = new CacheDataBase(c);
        database = cdb.getWritableDatabase();
    }

    public List<Student> getAllStudentsFromCache() {
        List<Student> students = new ArrayList<>();
        Cursor cursor = database.query(CacheDataBase.TABLE_Students,
                    columns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Student student = new Student(
                    cursor.getInt(cursor.getColumnIndex(STUDENT_ID_COLUMN)) ,
                    cursor.getString(cursor.getColumnIndex(STUDENT_NAME_COLUMN)));
            students.add(student);
            cursor.moveToNext();
        }
        cursor.close();
        return students;
    }

    @Override
    public void         cacheAllStudents(List<Student> students)  {

            cdb.onUpgrade(database, database.getVersion(), database.getVersion() + 1);
            ContentValues values = new ContentValues();
            for (Student s : students) {
                values.put(CacheDataBase.STUDENT_ID_COLUMN, s.getId());
                values.put(CacheDataBase.STUDENT_NAME_COLUMN, s.getName());
                long insertId = database.insert(
                        CacheDataBase.TABLE_Students,
                        null,
                        values);
            }
    }

}

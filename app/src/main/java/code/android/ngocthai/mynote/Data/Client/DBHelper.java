package code.android.ngocthai.mynote.Data.Client;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import code.android.ngocthai.mynote.Common.Utils.Constraint;

/**
 * Created by Thaihn on 06/10/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Constraint.DATABASE_NAME, null, Constraint.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //---all necessary of table you like to create will create here---
        String CREATE_TABLE_NOTE = "CREATE TABLE " + Constraint.TABLE_NAME_NOTE + " ("
                + Constraint.COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Constraint.COLUMN_NOTE_HEADER + " TEXT, "
                + Constraint.COLUMN_NOTE_TITLE + " TEXT, "
                + Constraint.COLUMN_NOTE_SECRET + " TEXT"
                + " )";

        String CREATE_TABLE_WORK = "CREATE TABLE " + Constraint.TABLE_NAME_WORK + " ("
                + Constraint.COLUMN_WORK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constraint.COLUMN_WORK_HEADER + " TEXT, "
                + Constraint.COLUMN_WORK_TITLE + " TEXT, "
                + Constraint.COLUMN_WORK_TIME_START + " TEXT, "
                + Constraint.COLUMN_WORK_TIME_END + " TEXT, "
                + Constraint.COLUMN_WORK_DATE + " TEXT, "
                + Constraint.COLUMN_WORK_IMPORTANT + " TEXT, "
                + Constraint.COLUMN_WORK_STATUS + " TEXT "
                + " )";

        db.execSQL(CREATE_TABLE_NOTE);
        db.execSQL(CREATE_TABLE_WORK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + Constraint.TABLE_NAME_NOTE);
        db.execSQL("DROP TABLE IF EXIST " + Constraint.TABLE_NAME_WORK);
        onCreate(db);
    }
}

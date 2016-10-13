package code.android.ngocthai.mynote.Data.Client;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import code.android.ngocthai.mynote.Common.Object.Note;
import code.android.ngocthai.mynote.Common.Object.Work;
import code.android.ngocthai.mynote.Common.Utils.Constraint;

/**
 * Created by Thaihn on 06/10/2016.
 */
public class DBController {

    private DBHelper dbHelper;

    public DBController(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * INSERT NEW NOTE TO DATABASE
     *
     * @param note
     * @return
     */
    public boolean InsertNote(Note note) {
        boolean insert = false;
        //---open connection to write data---
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues vl = new ContentValues();
            vl.put(Constraint.COLUMN_NOTE_HEADER, note.getHEADER());
            vl.put(Constraint.COLUMN_NOTE_TITLE, note.getTITLE());
            vl.put(Constraint.COLUMN_NOTE_SECRET, note.getSECRET());
            long kq = db.insert(Constraint.TABLE_NAME_NOTE, null, vl);
            if (kq > 0) {
                //---insert success---
                insert = true;
            } else {
                insert = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return insert;
    }

    public boolean InsertWork(Work work) {
        boolean insert = false;
        //---open connection to write data---
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues vl = new ContentValues();
            vl.put(Constraint.COLUMN_NOTE_HEADER, work.getHEADER());
            vl.put(Constraint.COLUMN_NOTE_TITLE, work.getTITLE());
            vl.put(Constraint.COLUMN_WORK_TIME_END, work.getTIMEEND());
            vl.put(Constraint.COLUMN_WORK_TIME_START, work.getTIMESTART());
            vl.put(Constraint.COLUMN_WORK_DATE, work.getDATE());
            vl.put(Constraint.COLUMN_WORK_IMPORTANT, work.getIMPORTANT());
            long kq = db.insert(Constraint.TABLE_NAME_WORK, null, vl);
            if (kq > 0) {
                //---insert success---
                insert = true;
            } else {
                insert = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return insert;
    }

    /**
     * Update note
     *
     * @param note
     * @return
     */
    public boolean EditNote(Note note) {
        boolean update = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues vl = new ContentValues();
            vl.put(Constraint.COLUMN_NOTE_HEADER, note.getHEADER());
            vl.put(Constraint.COLUMN_NOTE_TITLE, note.getTITLE());
            vl.put(Constraint.COLUMN_NOTE_SECRET, note.getSECRET());

            long kq = db.update(Constraint.TABLE_NAME_NOTE, vl, " ID  =  " + note.getID(), null);

            if (kq > -1) {
                update = true;
            } else {
                update = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return update;
    }

    /**
     * Delete note with id of note
     *
     * @param id id of note
     * @return
     */
    public boolean DeleteNote(int id) {
        boolean delete = false;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            long kq = db.delete(Constraint.TABLE_NAME_NOTE, "ID  = " + id, null);
            if (kq > -1) {
                delete = true;
            } else {
                delete = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return delete;
    }

    /**
     * Get all note private or public
     *
     * @param status
     * @return
     */
    public ArrayList<Note> getAllNote(String status) {
        ArrayList<Note> ls = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String sql = "SELECT * FROM " + Constraint.TABLE_NAME_NOTE + " WHERE " + Constraint.COLUMN_NOTE_SECRET + " = " + status;
            Cursor cursor = db.rawQuery(sql, null);
            Note note;
            while (cursor.moveToNext()) {
                note = new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                ls.add(note);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return ls;
    }

    public ArrayList<Work> getAllWork() {
        ArrayList<Work> ls = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String sql = "SELECT * FROM " + Constraint.TABLE_NAME_WORK;
            Cursor cursor = db.rawQuery(sql, null);
            Work work;
            while (cursor.moveToNext()) {
                work = new Work(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                ls.add(work);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return ls;
    }
}

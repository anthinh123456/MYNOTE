package code.android.ngocthai.mynote.Common.Utils;

/**
 * Created by Thaihn on 06/10/2016.
 */
public class Constraint {

    public static final String DATABASE_NAME = "NOTE.db";

    //---Column of table note---
    public static final String COLUMN_NOTE_ID = "ID";
    public static final String COLUMN_NOTE_HEADER = "HEADER";
    public static final String COLUMN_NOTE_TITLE = "TITLE";
    public static final String COLUMN_NOTE_SECRET = "SECRET";

    //---column of table work---
    public static final String COLUMN_WORK_ID = "ID";
    public static final String COLUMN_WORK_HEADER = "HEADER";
    public static final String COLUMN_WORK_TITLE = "TITLE";
    public static final String COLUMN_WORK_TIME_START = "TIMESTART";
    public static final String COLUMN_WORK_TIME_END = "TIMEEND";
    public static final String COLUMN_WORK_DATE = "DATE";
    public static final String COLUMN_WORK_STATUS = "STATUS";
    public static final String COLUMN_WORK_IMPORTANT = "IMPORTANT";


    public static final String TABLE_NAME_NOTE = "MYNOTE";
    public static final String TABLE_NAME_WORK = "WORK";

    public static final String VALUES_SECRET_SELECT_NOTE_TRUE = "'TRUE'";
    public static final String VALUES_SECRET_SELECT_NOTE_FALSE = "'FALSE'";
    public static final String STATUS_SECRET_TRUE = "TRUE";
    public static final String STATUS_SECRET_FALSE = "FALSE";

    public static final int REQUEST_CODE_ADD_NOTE_PRIVATE = 2;
    public static final int REQUEST_CODE_EDIT_NOTE_PRIVATE = 1;

    public static final int RESULT_CODE_ADD_NOTE = 3;
    public static final int RESULT_CODE_EDIT_NOTE = 4;


    public static final String KEY_SEND_STATUS_SECRET_TO_ADD = "STATUS_SECRET_ADD";
    public static final String KEY_SEND_STATUS_SECRET_TO_EDIT = "STATUS_SECRET_EDIT";

    public static final String KEY_SEND_OBJECT_NOTE = "SEND_NOTE";

    public static final String KEY_RESULT_ADD_NOTE = "ADD_DONE";
    public static final String KEY_RESULT_EDIT_NOTE = "ADD_DONE";

    public static final String KEY_SEND_STATUS_EDIT_NOTE_PRIVATE = "SENDNOTE";


    public static final String key_send_obj_note_private = "EDIT_PRIVATE";

    public static final int DATABASE_VERSION = 1;

}

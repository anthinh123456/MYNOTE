package code.android.ngocthai.mynote.Common.Object;

import java.io.Serializable;

/**
 * Created by Thaihn on 10/10/2016.
 */
public class Work implements Serializable {

    private int ID;
    private String HEADER, TITLE, TIMESTART, TIMEEND, DATE, IMPORTANT, STATUS;

    public Work(int ID, String HEADER, String TITLE, String TIMESTART, String TIMEEND, String DATE, String IMPORTANT, String STATUS) {
        this.ID = ID;
        this.HEADER = HEADER;
        this.TITLE = TITLE;
        this.TIMESTART = TIMESTART;
        this.TIMEEND = TIMEEND;
        this.DATE = DATE;
        this.IMPORTANT = IMPORTANT;
        this.STATUS = STATUS;
    }

    public Work(int ID, String HEADER, String TITLE, String TIMESTART, String TIMEEND, String DATE, String IMPORTANT) {
        this.ID = ID;
        this.HEADER = HEADER;
        this.TITLE = TITLE;
        this.TIMESTART = TIMESTART;
        this.TIMEEND = TIMEEND;
        this.DATE = DATE;
        this.IMPORTANT = IMPORTANT;
    }

    public String getIMPORTANT() {
        return IMPORTANT;
    }

    public void setIMPORTANT(String IMPORTANT) {
        this.IMPORTANT = IMPORTANT;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHEADER() {
        return HEADER;
    }

    public void setHEADER(String HEADER) {
        this.HEADER = HEADER;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getTIMESTART() {
        return TIMESTART;
    }

    public void setTIMESTART(String TIMESTART) {
        this.TIMESTART = TIMESTART;
    }

    public String getTIMEEND() {
        return TIMEEND;
    }

    public void setTIMEEND(String TIMEEND) {
        this.TIMEEND = TIMEEND;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }
}

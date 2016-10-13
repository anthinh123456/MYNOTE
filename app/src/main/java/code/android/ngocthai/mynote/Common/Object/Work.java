package code.android.ngocthai.mynote.Common.Object;

import java.io.Serializable;

/**
 * Created by Thaihn on 10/10/2016.
 */
public class Work implements Serializable {

    private int ID;
    private String HEADER, TITLE, IMPORTANT,TIMESTART, TIMEEND, STATUS, DATE;

    public Work(int ID, String HEADER, String TITLE, String IMPORTANT, String TIMESTART, String TIMEEND, String STATUS, String DATE) {
        this.ID = ID;
        this.HEADER = HEADER;
        this.TITLE = TITLE;
        this.IMPORTANT = IMPORTANT;
        this.TIMESTART = TIMESTART;
        this.TIMEEND = TIMEEND;
        this.STATUS = STATUS;
        this.DATE = DATE;
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

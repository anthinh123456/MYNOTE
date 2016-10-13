package code.android.ngocthai.mynote.Common.Object;

import java.io.Serializable;

/**
 * Created by Thaihn on 06/10/2016.
 */
public class Note implements Serializable {

    private int ID;
    private String HEADER, TITLE, SECRET;

    /**
     * Constructor default to add values to this function
     *
     * @param ID     id of note
     * @param HEADER header of note your create
     * @param TITLE  title of note your create
     * @param SECRET status of note public or private
     */
    public Note(int ID, String HEADER, String TITLE, String SECRET) {
        this.ID = ID;
        this.HEADER = HEADER;
        this.TITLE = TITLE;
        this.SECRET = SECRET;
    }

    public Note(String HEADER, String TITLE, String SECRET) {
        this.HEADER = HEADER;
        this.TITLE = TITLE;
        this.SECRET = SECRET;
    }

    /**
     * Getter and Setter
     */

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

    public String getSECRET() {
        return SECRET;
    }

    public void setSECRET(String SECRET) {
        this.SECRET = SECRET;
    }
}

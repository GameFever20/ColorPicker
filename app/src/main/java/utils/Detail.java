package utils;

import java.io.Serializable;

/**
 * Created by Aisha on 7/23/2017.
 */

public class Detail implements Serializable {


    private long id;
    private String primaryHexCode;
    private String primaryDarkHexCode;
    private String accentHexCode;

    public String getPrimaryHexCode() {
        return primaryHexCode;
    }

    public void setPrimaryHexCode(String primaryHexCode) {
        this.primaryHexCode = primaryHexCode;
    }

    public String getAccentHexCode() {
        return accentHexCode;
    }

    public void setAccentHexCode(String accentHexCode) {
        this.accentHexCode = accentHexCode;
    }

    public String getPrimaryDarkHexCode() {
        return primaryDarkHexCode;
    }

    public void setPrimaryDarkHexCode(String primaryDarkHexCode) {
        this.primaryDarkHexCode = primaryDarkHexCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public Detail() {

    }




}

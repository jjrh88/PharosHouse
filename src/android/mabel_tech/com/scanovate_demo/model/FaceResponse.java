package mabel_tech.com.scanovate_demo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceResponse {

    @SerializedName("Uid")
    @Expose
    public String uid;


    @SerializedName("IdNumber")
    @Expose
    public String IdNumber;


    public String getUid() {
        return uid;
    }
    public String getIdNumber() {
        return IdNumber;
    }
}

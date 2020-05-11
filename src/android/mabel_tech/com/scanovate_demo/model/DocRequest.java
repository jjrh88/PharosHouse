package mabel_tech.com.scanovate_demo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocRequest {

    @SerializedName("Image")
    @Expose
    public String image;
    @SerializedName("Uid")
    @Expose
    public String uid;

    /**
     * No args constructor for use in serialization
     *
     */
    public DocRequest() {
    }

    /**
     *
     * @param uid
     * @param image
     */
    public DocRequest(String image, String uid) {
        super();
        this.image = image;
        this.uid = uid;
    }
}

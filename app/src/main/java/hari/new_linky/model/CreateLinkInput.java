package hari.new_linky.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ramesh on 9/19/16.
 */
public class CreateLinkInput {

    public CreateLinkInput(Link link) {
        this.link = link;
    }

    @SerializedName("link")
    @Expose
    private Link link;

    /**
     *
     * @return
     * The link
     */
    public Link getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(Link link) {
        this.link = link;
    }

}

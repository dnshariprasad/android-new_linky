package hari.new_linky.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ramesh on 9/16/16.
 */
public class SignUpInput {
    public SignUpInput(User user) {
        this.user = user;
    }

    @SerializedName("user")
    @Expose
    private User user;

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

}

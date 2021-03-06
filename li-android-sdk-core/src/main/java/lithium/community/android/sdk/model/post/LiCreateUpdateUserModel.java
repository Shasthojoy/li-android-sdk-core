package lithium.community.android.sdk.model.post;

import com.google.gson.annotations.SerializedName;

import lithium.community.android.sdk.model.helpers.LiAvatar;

/**
 * Created by shoureya.kant on 3/29/17.
 */

public class LiCreateUpdateUserModel extends LiBasePostModel {

    private String type;
    private LiAvatar avatar;
    private String biography;
    @SerializedName("cover_image")
    private String coverImage;
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String login;
    private String password;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LiAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(LiAvatar avatar) {
        this.avatar = avatar;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

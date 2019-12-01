package com.example.newser.Models;

public class UserAccountSettings {

    private String description;
    private String display_name;
    private String profile_photo;

    public UserAccountSettings(String description, String display_name, String profile_photo) {
        this.description = description;
        this.display_name = display_name;
        this.profile_photo = profile_photo;
    }

    public UserAccountSettings() {
    }

    public String getDescription() {
        return description;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }
}

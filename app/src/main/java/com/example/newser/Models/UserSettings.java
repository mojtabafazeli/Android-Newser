package com.example.newser.Models;

public class UserSettings {

    private User user;
    private UserAccountSettings settings;

    public UserSettings(User user, UserAccountSettings settings) {
        this.user = user;
        this.settings = settings;
    }

    public UserSettings() {
    }

    public User getUser() {
        return user;
    }

    public UserAccountSettings getSettings() {
        return settings;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSettings(UserAccountSettings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + user +
                ", settings=" + settings +
                '}';
    }
}

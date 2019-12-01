package com.example.newser.Models;

public class User {
    private String user_id;
    private String email;
    private String username;

    public User(String user_id, String email, String username){
        this.user_id = user_id;
        this.username = username;
        this.email = email;
    }
     public User() {
     }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

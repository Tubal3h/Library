package it.model;

public class User {
    private int user_id;
    private String user_name;
    private String user_surname;
    private String user_email;
    private String user_password;
    private String user_role;

    public User (String user_name, String user_surname, String user_email, String user_password, String user_role) {
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_role = user_role;
    }
    public int getUser_id() {
        return user_id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_surname() {
        return user_surname;
    }
    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }
    public String getUser_email() {
        return user_email;
    }
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public String getUser_role() {
        return user_role;
    }
    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", user_name=" + user_name + ", user_surname=" + user_surname
                + ", user_email=" + user_email + ", user_password=" + user_password + ", user_role=" + user_role
                + "]";
    }
}

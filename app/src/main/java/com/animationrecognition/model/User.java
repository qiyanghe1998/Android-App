package com.animationrecognition.model;

/**
 * User class includes all of the information and methods for a user.
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    /**
     * getId() is to get the user's id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setID() is to set the user's id.
     *
     * @param id assign the value of id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getName() is to get the user's name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setName() is to set the user's name.
     *
     * @param name assign the value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getemail() is to get the user's email.
     *
     * @return email
     */
    public String getemail() {
        return email;
    }

    /**
     * setemail() is to set the user's email.
     *
     * @param email assign the value of email
     */
    public void setemail(String email) {
        this.email = email;
    }

    /**
     * getpassword() is to get the user's password.
     *
     * @return password
     */
    public String getpassword() {
        return password;
    }

    /**
     * setpassword() is to set the user's password.
     *
     * @param password assign the value of password
     */
    public void setpassword(String password) {
        this.password = password;
    }
}

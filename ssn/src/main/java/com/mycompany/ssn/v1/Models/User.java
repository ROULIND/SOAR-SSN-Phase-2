/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssn.v1.Models;

import java.util.ArrayList;

/**
 * The User class represents a user in the system.
 * Each user has a unique identifier, a username,
 * a first name, a last name, an email address, a password,
 * a list of followers, and a list of users they are following.
 * @author dimitriroulin
 */
public class User {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int password;
    private String profilePicture;
    private ArrayList<User> followers; // List of users who follow this user


    private ArrayList<User> following; // List of users that this user is following
    
/**
* Constructor for the User class.
* Initializes the user's properties when created.
*
* @param userId    Unique identifier for the user.
* @param username  User's username.
* @param firstName User's first name.
* @param lastName  User's last name.
* @param email     User's email address.
* @param password  User's password.
*/

    public User(int userId, String username, String firstName, String lastName, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password.hashCode(); // Hashing the password for security reasons
        this.followers = new ArrayList<>(); // Initializing the list of followers
        this.following = new ArrayList<>(); // Initializing the list of users being followed
        this.profilePicture = "Default-PP.jpeg";
    }
    
    public User(int userId, String username, String firstName, String lastName, String email, String password, String profilePicture) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password.hashCode(); // Hashing the password for security reasons
        this.followers = new ArrayList<>(); // Initializing the list of followers
        this.following = new ArrayList<>(); // Initializing the list of users being followed
        this.profilePicture = profilePicture;
    }
    
/**
    * Gets the list of users who follow the current user.
    *
    * @return ArrayList<User> The list of followers.
    */
    
    public ArrayList<User> getFollowers() {
        return followers;
    }
    
/** 
    * Gets the list of users whom the current user is following.
    * 
    * @return ArrayList<User> The list of users being followed.
    */
    
    public ArrayList<User> getFollowing() {
        return following;
    }
    
/** 
    * Adds a user to the list of followers.
    * 
    * @param user The user to be added as a follower.
    */
    
    public void addFollower(User user) {
        this.followers.add(user);
    }

/**
     * Adds a user to the list of users being followed.
     *
     * @param user The user to be followed.
     */
    public void addFollowing(User user) {
        this.following.add(user);
    }
/**
     * Gets the unique identifier of the user.
     *
     * @return int The user's ID.
     */
    
    public int getId () {
        return userId;
    }
/**
     * Gets the username of the user.
     *
     * @return String The user's username.
     */
    
    public String getUsername() {
        return username;
    }
/**
     * Gets the first name of the user.
     *
     * @return String The user's first name.
     */
    
    public String getFirstName() {
        return firstName;
    }
/**
     * Gets the last name of the user.
     *
     * @return String The user's last name.
     */
    
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return String The user's email address.
     */
    public String getEmail() {
        return email;
    }
    
    // Getter and setter for profilePicture
    public String getProfilePicture() {
        return profilePicture;
    }

    
    
    /**
     * Sets a new username for the user.
     *
     * @param username The new username to be set.
     */
    
    public void setUsername(String username) {
        this.username = username;
    }
    
/**
     * Sets a new first name for the user.
     *
     * @param firstName The new first name to be set.
     */
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
/**
     * Sets a new last name for the user.
     *
     * @param lastName The new last name to be set.
     */
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
/**
     * Sets a new email address for the user.
     *
     * @param email The new email address to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
/**
     * Sets a new password for the user (stored as a hash for security).
     *
     * @param password The new password to be set.
     */
    
    public void setPassword(String password) {
        this.password = password.hashCode();
    }
    
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    
    
/**
     * Checks if the provided password matches the user's password.
     *
     * @param password The password to be checked.
     * @return boolean True if the password is correct, otherwise false.
     */
    
    public boolean isPasswordCorrect(String password) {
        return password.hashCode() == this.password;
    }
/**
     * Overrides the default equals method to compare users based on their username.
     *
     * @param obj Object to compare with the current user.
     * @return boolean True if usernames are equal, otherwise false.
     */
    
    @Override
    public boolean equals(Object obj) {
        return ((User) obj).getUsername().equals(this.username);
    }
/**
     * Overrides the default toString method to provide a textual representation of the user.
     *
     * @return String A formatted string with the user's key information.
     */
    
    @Override
    public String toString() {
        return "Username: " + this.username
                + "\nFirst name: " + this.firstName
                + "\nLast name: " + this.lastName
                + "\nEmail: " + this.email
                + "\nID: " + this.userId;
    }
}

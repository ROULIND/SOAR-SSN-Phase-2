/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssn.v1.Models;

/**
 *
 * @author dimitriroulin
 */

/**
 * The Like class represents a like given by a user to a post in the system.
 * Each like has a unique identifier, the ID of the liked post,
 * and the ID of the user who liked the post.
 */
public class Like {
    private int id; // Unique identifier for the Like
    private int postId; // The post that is liked
    private int userId; // The user who liked the post

/**
     * Constructor for the Like class.
     * Initializes the like's properties when created.
     *
     * @param id      Unique identifier for the like.
     * @param postId  ID of the post that is liked.
     * @param userId  ID of the user who liked the post.
     */
    
    public Like(int id, int postId, int userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }

// Getters for like properties

/**
     * Gets the unique identifier of the like.
     *
     * @return int The like's ID.
     */
    
    public int getId() {
        return id;
    }
/**
     * Gets the ID of the post that is liked.
     *
     * @return int The ID of the liked post.
     */
    
    public int getPostId() {
        return postId;
    }
/**
     * Gets the ID of the user who liked the post.
     *
     * @return int The ID of the user who liked the post.
     */
    
    public int getUserId() {
        return userId;
    }

// Setters for like properties

    /**
     * Sets a new identifier for the like.
     *
     * @param id The new identifier to be set.
     */
    
    public void setId(int id) {
        this.id = id;
    }
/**
     * Sets a new ID for the post that is liked.
     *
     * @param postId The new ID of the liked post to be set.
     */
    
    public void setPostId(int postId) {
        this.postId = postId;
    }
/**
     * Sets a new ID for the user who liked the post.
     *
     * @param userId The new ID of the user who liked the post to be set.
     */
    
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Overriding the toString method for easy printing of Like object details

/**
     * Overrides the default toString method to provide a string representation of the Like object.
     *
     * @return String A formatted string with key information about the like.
     */
    
    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                '}';
    }
}

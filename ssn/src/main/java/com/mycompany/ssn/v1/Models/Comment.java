/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssn.v1.Models;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static com.mycompany.ssn.v1.controllers.PostController.generateUniqueIdForComment;

/**
 *
 * @author dimitriroulin
 */

/**
 * The Comment class represents a comment made by a user on a post in the system.
 * Each comment has a unique identifier, the ID of the commented post,
 * the ID of the user who made the comment, the comment text,
 * and the date the comment was published.
 */

public class Comment {
    private int id;
    private int postId;
    private int userId;
    private String text;
    private Date datePublished;
/**
     * Constructor for the Comment class.
     * Initializes the comment's properties when created.
     *
     * @param postId The ID of the post that is commented.
     * @param userId The ID of the user who made the comment.
     * @param text   The text content of the comment.
     */
    public Comment(int postId, int userId, String text) {
        this.id = ThreadLocalRandom.current().nextInt(1, 9999 + 1);
        // TODO: generate true unique id but will use DB later
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.datePublished = new Date();
    }

    // Getters for comment properties

    /**
     * Gets the unique identifier of the comment.
     *
     * @return int The comment's ID.
     */
    public int getId() {
        return id;
    }
/**
     * Gets the ID of the post that is commented.
     *
     * @return int The ID of the commented post.
     */
    
    public int getPostId() {
        return postId;
    }
/**
     * Gets the ID of the user who made the comment.
     *
     * @return int The ID of the user who made the comment.
     */
    
    public int getUserId() {
        return userId;
    }
/**
     * Gets the text content of the comment.
     *
     * @return String The text content of the comment.
     */
    
    public String getText() {
        return text;
    }
/**
     * Gets the date when the comment was published.
     *
     * @return Date The date when the comment was published.
     */
    
    public Date getDatePublished() {
        return datePublished;
    }

    // Setters for comment properties

/**
     * Sets a new identifier for the comment.
     *
     * @param id The new identifier to be set.
     */
    
    public void setId(int id) {
        this.id = id;
    }
/**
     * Sets a new ID for the post that is commented.
     *
     * @param postId The new ID of the commented post to be set.
     */
    
    public void setPostId(int postId) {
        this.postId = postId;
    }
/**
     * Sets a new ID for the user who made the comment.
     *
     * @param userId The new ID of the user who made the comment to be set.
     */
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
/**
     * Sets new text content for the comment.
     *
     * @param text The new text content to be set.
     */
    
    public void setText(String text) {
        this.text = text;
    }
/**
     * Sets a new date for when the comment was published.
     *
     * @param datePublished The new date to be set.
     */
    
    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    // Method to display comment information

    /**
     * Overrides the default toString method to provide a string representation of the Comment object.
     *
     * @return String A formatted string with key information about the comment.
     */
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", datePublished=" + datePublished +
                '}';
    }
}

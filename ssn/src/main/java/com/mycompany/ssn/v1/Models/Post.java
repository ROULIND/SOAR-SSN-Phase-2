/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssn.v1.Models;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author dimitriroulin
 */

/**
 * The Post class represents a post made by a user in the system.
 * Each post has a unique identifier, the user ID of the author,
 * the post text, the date it was published, a list of comments,
 * and a list of user IDs who have liked the post.
 */

public class Post {
    private int id;
    private int userId;
    private String text;
    private Date datePublished;
    private List<Comment> comments;
    private List<Integer> likes; // Liste des ID des utilisateurs qui ont aimé le post

/**
     * Constructor for the Post class.
     * Initializes the post's properties when created.
     *
     * @param id             Unique identifier for the post.
     * @param userId         User ID of the post author.
     * @param text           Text content of the post.
     * @param datePublished  Date when the post was published.
     */
    
    public Post(int id, int userId, String text, Date datePublished) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.datePublished = datePublished;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    // Getters et Setters
    
/**
     * Gets the unique identifier of the post.
     *
     * @return int The post's ID.
     */
    
    public int getId() {
        return id;
    }
/**
     * Sets a new identifier for the post.
     *
     * @param id The new identifier to be set.
     */
    
    public void setId(int id) {
        this.id = id;
    }
/**
     * Gets the user ID of the post author.
     *
     * @return int The user ID of the post author.
     */
    
    public int getUserId() {
        return userId;
    }
/**
     * Sets a new user ID for the post author.
     *
     * @param userId The new user ID to be set.
     */
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
/**
     * Gets the text content of the post.
     *
     * @return String The text content of the post.
     */
    
    public String getText() {
        return text;
    }
/**
     * Sets new text content for the post.
     *
     * @param text The new text content to be set.
     */
    
    public void setText(String text) {
        this.text = text;
    }
/**
     * Gets the date when the post was published.
     *
     * @return Date The date when the post was published.
     */
    
    public Date getDatePublished() {
        return datePublished;
    }
/**
     * Sets a new date for when the post was published.
     *
     * @param datePublished The new date to be set.
     */
    
    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }
/**
     * Gets the list of comments on the post.
     *
     * @return List<Comment> The list of comments on the post.
     */
    
    public List<Comment> getComments() {
        return comments;
    }
/**
     * Sets a new list of comments for the post.
     *
     * @param comments The new list of comments to be set.
     */
    
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
/**
     * Gets the list of user IDs who liked the post.
     *
     * @return List<Integer> The list of user IDs who liked the post.
     */
    
    public List<Integer> getLikes() {
        return likes;
    }
/**
     * Sets a new list of user IDs who liked the post.
     *
     * @param likes The new list of user IDs to be set.
     */
    
    public void setLikes(List<Integer> likes) {
        this.likes = likes;
    }

/**
     * Adds a like to the post.
     *
     * @param userId The user ID of the user who liked the post.
     */
    
    public void addLike(int userId) {
        if (!likes.contains(userId)) {
            likes.add(userId);
        }
    }
/**
     * Removes a like from the post.
     *
     * @param userId The user ID of the user whose like should be removed.
     */
    
    public void removeLike(int userId) {
        likes.remove(Integer.valueOf(userId));
    }

// Methods to manage comments

    /**
     * Adds a comment to the post.
     *
     * @param comment The comment to be added.
     */
    
    public void addComment(Comment comment) {
        comments.add(comment);
    }
/**
     * Removes a comment from the post.
     *
     * @param comment The comment to be removed.
     */
    
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

// Example of a toString method tailored to the specific format required

    /**
     * Overrides the default toString method to provide a formatted string representation of the post.
     *
     * @return String A formatted string with key information about the post.
     */
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return String.format(
                "------------------------------------\n" +
                "|[%d]\n" +
                "|Author : %s\n" +
                "|Date : %s\n" +
                "|\n" +
                "|%s\n" +
                "|\n" +
                "|Likes : %d\n" +
                "|Comments : %d\n" +
                "------------------------------------\n",
                this.id, this.userId, sdf.format(this.datePublished), this.text, this.likes.size(), this.comments.size()
        );
    }
}
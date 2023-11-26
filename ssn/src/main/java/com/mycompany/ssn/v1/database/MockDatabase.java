/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssn.v1.database;

import java.util.ArrayList;
import com.mycompany.ssn.v1.Models.User;
import com.mycompany.ssn.v1.Models.Post;
import com.mycompany.ssn.v1.Models.Comment;
import com.mycompany.ssn.v1.controllers.UserController;

import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author dimitriroulin
 */
public class MockDatabase {
    
    private static ArrayList<User> users = new ArrayList<User>() {
        {
            add(new User(1,"amart", "Alice", "Martin", "alice.martin@email.com", "1234"));
            add(new User(2,"bsmit", "Bob", "Smith", "bob.smith@email.com", "1234"));
            add(new User(3,"cbrow", "Charlie", "Brown", "charlie.brown@email.com", "1234"));
            add(new User(4,"djohn", "David", "Johnson", "david.johnson@email.com", "1234", "Sierro-PP.jpeg"));
        }
    };
    
    private static ArrayList<Post> posts = new ArrayList<Post>() {
        {
            add(new Post(1, 1, "Hello world! This is my first post on SSN.", subtractDays(new Date(), 10)));
            add(new Post(2, 2, "Just joined SSN! Looking forward to connecting with everyone.", subtractDays(new Date(), 7)));
            add(new Post(3, 3, "Does anyone have book recommendations?", subtractDays(new Date(), 5)));
            add(new Post(4, 4, "Happy to be here!", subtractDays(new Date(), 2)));
        }
    };

    private static ArrayList<Comment> comments = new ArrayList<Comment>() {
        {
            add(new Comment(1, 1, "Welcome to SSN, Alice!"));
            add(new Comment(2, 2,  "Welcome, Bob!"));
            add(new Comment(3, 3,  "I recommend 'The Great Gatsby'."));
            add(new Comment(4, 3, "How about 'To Kill a Mockingbird'?"));
            add(new Comment(5, 4, "My looove for the rose"));
        }
    };
    
     // Helper method to subtract days from the current date
    private static Date subtractDays(Date date, int days) {
        long millis = date.getTime();
        long subtractedMillis = TimeUnit.DAYS.toMillis(days);
        return new Date(millis - subtractedMillis);
    }
    
    
    /* Add a user to the list of existing users in the mock database */
    public static void addAUser(User user) {
        users.add(user);
    }
    
    /* Return the list of existing users in the mock database */
    public static ArrayList<User> getUsers() {
        return users;
    }
    
    public static void addAPost(Post post) {
        posts.add(post);
    }
    
    public static ArrayList<Post> getPosts() {
        return posts;
    }
    
    public static ArrayList<Comment> getComments(){
        return comments;
    }
    
    public static void addAComment(Comment comment) {
        comments.add(comment);
    }
   
    
}

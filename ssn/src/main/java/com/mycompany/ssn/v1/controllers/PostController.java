/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssn.v1.controllers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import com.mycompany.ssn.v1.exceptions.UnauthorizedActionException;
import com.mycompany.ssn.v1.exceptions.InvalidContentException;
import com.mycompany.ssn.v1.exceptions.UserNotLoggedInException;
import com.mycompany.ssn.v1.exceptions.DoesNotExistException;
import com.mycompany.ssn.v1.controllers.UserController;
import com.mycompany.ssn.v1.Models.Post;
import com.mycompany.ssn.v1.Models.User;
import com.mycompany.ssn.v1.Models.Comment;
import com.mycompany.ssn.v1.database.MockDatabase;
/**
 *
 * @author dimitriroulin
 */
public class PostController {
    // Liste pour stocker les publications
    private static List<Post> posts = new ArrayList<>(MockDatabase.getPosts());

    public static Post getPost(int id) {
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    public static ArrayList<Post> getPostsByUser(User user) {
        ArrayList<Post> postsByUser = new ArrayList<Post>();
        for (Post post : posts) {
            if (post.getUserId() == user.getId()) {
                postsByUser.add(post);
            }
        }
        return postsByUser;
    }

    // Méthode pour créer une nouvelle publication
    public static void createPost(User user, String text) throws UserNotLoggedInException, UnauthorizedActionException, InvalidContentException {
        // Vérifier si l'utilisateur est connecté
        if (user == null) {
            throw new UserNotLoggedInException("User must be logged in to create a post.");
        }

        // Vérifier la validité du contenu de la publication
        if (text == null || text.isEmpty() || text.length() > 256) {
            throw new InvalidContentException("Invalid post content. Post must be 1-256 characters long.");
        }

        // Créer une nouvelle publication et l'ajouter à la liste des publications
        Post post = new Post(generateUniqueId(), user.getId(), text, new Date());
        posts.add(post);
    }

    // Méthode pour obtenir la liste des publications
    public static List<Post> getPosts() {
        // TODO: reverse for GUI
        return posts;
    }

    public static Post showPost(int postId) {
        for (Post post : posts) {
            if (post.getId() == postId) {
                return post;
            }
        }
        return null;
    }
    
    public static String displayPosts() {
        String strDisplay = "";
        for(Post post : getPosts()){
            strDisplay = strDisplay + post.toString();
        }
        return strDisplay;
    }
    
    
    
    /* Generate an unique Id for new post based on the existing list of posts */
    public static int generateUniqueId() {
        List<Integer> listOfExistingId = new ArrayList<Integer>();
        // Assuming User has a method getId() that returns an int
        for (Post post : MockDatabase.getPosts()) {
            listOfExistingId.add(post.getId());
        }
        // If there are no IDs, start with 1 (or any other starting point you prefer)
        if (listOfExistingId.isEmpty()) {
            return 1;
        }
        // Find the maximum ID in the list
        int maxId = listOfExistingId.stream().max(Integer::compare).get();
        // Return the next ID which is 1 more than the maximum ID found
        return maxId + 1;
    }
    
    /* Generate an unique Id for new post based on the existing list of posts */
    public static int generateUniqueIdForComment() {
        List<Integer> listOfExistingId = new ArrayList<Integer>();
        // Assuming User has a method getId() that returns an int
        for (Comment comment : MockDatabase.getComments()) {
            listOfExistingId.add(comment.getId());
        }
        // If there are no IDs, start with 1 (or any other starting point you prefer)
        if (listOfExistingId.isEmpty()) {
            return 1;
        }
        // Find the maximum ID in the list
        int maxId = listOfExistingId.stream().max(Integer::compare).get();
        // Return the next ID which is 1 more than the maximum ID found
        return maxId + 1;
    }
    
}

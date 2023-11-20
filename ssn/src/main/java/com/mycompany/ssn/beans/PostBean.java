package com.mycompany.ssn.beans;
import com.mycompany.ssn.v1.Models.User;

import com.mycompany.ssn.v1.Models.Comment;
import com.mycompany.ssn.v1.Models.Post;
import com.mycompany.ssn.beans.LoginBean;
import com.mycompany.ssn.beans.UserBean;
import com.mycompany.ssn.v1.database.MockDatabase;
import com.mycompany.ssn.v1.exceptions.DoesNotExistException;
import com.mycompany.ssn.v1.exceptions.InvalidContentException;
import com.mycompany.ssn.v1.exceptions.UnauthorizedActionException;
import com.mycompany.ssn.v1.exceptions.UserNotLoggedInException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jonathanstefanov
 */
@Named(value = "postBean")
@SessionScoped
public class PostBean implements Serializable {
    private String currentPostText = "";

    private Post selectedPost;
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

    public void setCurrentPostText(String currentPostText) {
        this.currentPostText = currentPostText;
    }

    public String getCurrentPostText() {
        return currentPostText;
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
    public String createPost(User user) throws UserNotLoggedInException, UnauthorizedActionException, InvalidContentException {
        String text = this.currentPostText;
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

        return "/UserPage/UserMainPage.xhtml?faces-redirect=true";
    }

    // Méthode pour obtenir la liste des publications
    public static List<Post> getPosts() {
        // TODO: reverse for GUI
        return posts;
    }

    public void addLike(User user, Post post) throws DoesNotExistException {
        if (post == null) {
            throw new DoesNotExistException("Post does not exist.");
        }
        if (user == null) {
            throw new DoesNotExistException("User does not exist.");
        }
        post.addLike(user.getId());
    }

    public boolean postIsLikedByUser(User user, Post post) throws DoesNotExistException {
        if (post == null) {
            throw new DoesNotExistException("Post does not exist.");
        }
        if (user == null) {
            throw new DoesNotExistException("User does not exist.");
        }

        boolean isLiked = false;
        for (int userId : post.getLikes()) {
            if (userId == user.getId()) {
                isLiked = true;
            }
        }

        return isLiked;
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

    public Post getSelectedPost() {
        return selectedPost;
    }

    public String setSelectedPost(int postId) {
        Post selectedPost = showPost(postId);
        this.selectedPost = selectedPost;
        return "/PostPage/PostPage.xhtml?faces-redirect=true";

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

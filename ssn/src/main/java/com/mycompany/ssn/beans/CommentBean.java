package com.mycompany.ssn.beans;

import com.mycompany.ssn.v1.Models.Comment;
import com.mycompany.ssn.v1.Models.Post;
import com.mycompany.ssn.v1.Models.User;
import com.mycompany.ssn.beans.LoginBean;
import com.mycompany.ssn.beans.UserBean;
import com.mycompany.ssn.v1.exceptions.DoesNotExistException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author jonathanstefanov
 */
@Named(value = "commentBean")
@SessionScoped
public class CommentBean implements Serializable {
    private String currentCommentText = "";


    public void setCurrentCommentText(String currentCommentText) {
        this.currentCommentText = currentCommentText;
        //Line break after 180 characters
        this.currentCommentText = currentCommentText.replaceAll("(.{180})", "$1\n");
    }

    public String getCurrentCommentText() {
        return currentCommentText;
    }


    public void makeComment(User user, Post post) throws IllegalArgumentException {
        //TODO: could just use loginBean to get the connected user?
        // Verify if the user exists
        if (user == null) {
            throw new IllegalArgumentException("Invalid user.");
        }

        // Verify if the post exists
        if (post == null) {
            throw new IllegalArgumentException("Invalid post.");
        }

        // Validate the comment text
        if (this.currentCommentText == null) {
            throw new IllegalArgumentException("Comment text cannot be empty.");
        }
        //Verify the lengh of the comment text
        if (this.currentCommentText == null || this.currentCommentText.length() > 256 || this.currentCommentText.length() == 0) {
            // Faces launch an error message
            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Comment must be between 1 and 256 characters long", null));
        }
        else{
            Comment comment = new Comment(post.getId(), user.getId(), this.currentCommentText);
            post.addComment(comment);
            this.currentCommentText = ""; // reset the comment handler
        }

    }


}
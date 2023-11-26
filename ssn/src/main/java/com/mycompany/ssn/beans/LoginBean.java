package com.mycompany.ssn.beans;

import com.mycompany.ssn.v1.Models.User;
import com.mycompany.ssn.v1.exceptions.DoesNotExistException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author dimitriroulin
 *
 * The LoginBean class serves as a managed bean for handling user login and logout functionality.
 * It is session-scoped and is responsible for managing user authentication and the current user session.
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username = "";
    private String password = "";
    private User currentUser;
    private String messageError = "";
/**
     * Attempts to log in the user based on the provided username and password.
     *
     * @return String The navigation outcome based on the login success or failure.
     */    
    public String userLogsIn() {
        try {
            // Find the user by the provided username
            var user = UserBean.findByUsername(username);
            // Check if the user exists and the provided password is correct
            if (user != null && user.isPasswordCorrect(password)) {
                currentUser = user;
                return "/UserPage/UserMainPage.xhtml?faces-redirect=true";
            }
        } catch (DoesNotExistException ex) {
            System.out.println(ex.getMessage());
        }
        // Set an error message and redirect to the main page in case of login failure
        messageError = "Username or Passeword incorrect";
        return "/MainPage/MainPage.xhtml?faces-redirect=true";
    }
/**
     * Logs out the current user.
     *
     * @return String The navigation outcome to the main page after logout.
     */
    public String userLogsout() {
        currentUser = null;
        return "/MainPage/MainPage.xhtml?faces-redirect=true";
    }
/**
     * Gets the user currently logged in.
     *
     * @return User The user currently logged in.
     */
    public User getUserLoggedIn() {
        return currentUser;
    }
/**
     * Gets the current user's password.
     *
     * @return String The current user's password.
     */
    public String getPassword() {
        return password;
    }
/**
     * Gets the current user's username.
     *
     * @return String The current user's username.
     */
    public String getUsername() {
        return username;
    }
/**
     * Sets the current user.
     *
     * @param currentUser The user to set as the current user.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
/**
     * Gets the current user.
     *
     * @return User The current user.
     */
    public User getCurrentUser() {
        return currentUser; //dd
    }
/**
     * Sets the current user's password.
     *
     * @param password The new password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
/**
     * Sets the current user's username.
     *
     * @param username The new username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
/**
     * Gets the error message related to login failures.
     *
     * @return String The error message.
     */
    public String getMessageError() {
        return messageError;
    }
/**
     * Sets a new error message.
     *
     * @param newMessageError The new error message to be set.
     */
    public void setMessageError(String newMessageError) {
    this.messageError = newMessageError;
}
}
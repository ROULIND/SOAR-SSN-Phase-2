package com.mycompany.ssn.beans;

import com.mycompany.ssn.v1.Models.User;
import com.mycompany.ssn.v1.exceptions.DoesNotExistException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author dimitriroulin
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username = "";
    private String password = "";
    private User currentUser;

    public String userLogsIn() {
        try {
            var user = UserBean.findByUsername(username);
            if (user != null && user.isPasswordCorrect(password)) {
                currentUser = user;
                return "/UserPage/UserMainPage.xhtml?faces-redirect=true";
            }
        } catch (DoesNotExistException ex) {
            System.out.println(ex.getMessage());
        }
        return "/MainPage/MainPage.xhtml?faces-redirect=true";
    }

    public String userLogsout() {
        currentUser = null;

        return "/MainPage/MainPage.xhtml?faces-redirect=true";
    }

    public User getUserLoggedIn() {
        return currentUser;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser; //dd
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

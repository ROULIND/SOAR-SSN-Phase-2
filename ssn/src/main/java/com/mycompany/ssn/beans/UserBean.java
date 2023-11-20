package com.mycompany.ssn.beans;

import com.mycompany.ssn.v1.Models.User;
import com.mycompany.ssn.v1.controllers.UserController;
import com.mycompany.ssn.v1.database.MockDatabase;
import com.mycompany.ssn.v1.exceptions.AlreadyExistsException;
import com.mycompany.ssn.v1.exceptions.DoesNotExistException;
import com.mycompany.ssn.v1.exceptions.UnauthorizedActionException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dimitriroulin
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {
    private String username = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String password = "";

    private User selectedUser;


    
    public void setSelectedUser(User user) {
        this.selectedUser = user;
    }
    public User getSelectedUser() {
        return selectedUser;
    }

    public String goToProfilePage(User user) {
        this.selectedUser = user;
        return "/ProfilePage/ProfilePage.xhtml?faces-redirect=true";
    }
    public void createAUser() {
        try {
            if (!emailExists() && !usernameExists()) {
                MockDatabase.addAUser(new User(generateUniqueId(),username, firstName, lastName, email, password));
            }
        } catch (AlreadyExistsException | DoesNotExistException ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected static User findByUsername(String username) throws DoesNotExistException {
        for (User user : MockDatabase.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new DoesNotExistException("The user " + username + " does not exist.");
    }

    protected boolean emailExists() throws AlreadyExistsException {
        for (User user : MockDatabase.getUsers()) {
            if (user.getEmail().equals(email)) {
                throw new AlreadyExistsException("The email " + email + " already in use.");
            }
        }
        return false;
    }

    protected boolean usernameExists() throws DoesNotExistException {
        for (User user : MockDatabase.getUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUserFromId(int id) throws DoesNotExistException {
        for (User user : MockDatabase.getUsers()) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new DoesNotExistException("The user with id " + id + " does not exist.");
    }
    
    public int generateUniqueId() {
        List<Integer> listOfExistingId = new ArrayList<Integer>();
        // Assuming User has a method getId() that returns an int
        for (User user : MockDatabase.getUsers()) {
            listOfExistingId.add(user.getId());
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


    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void followUser(User user, User userToFollow) throws UnauthorizedActionException {
        if (user.getFollowing().contains(userToFollow) || user.getFollowers().contains(userToFollow)) {
            return;
        }

        userToFollow.addFollower(user);
        user.addFollowing(userToFollow);


    }

    public static void removeUserFromFollowersList(User user, User userToUnfollow) {
        // Make sure userToUnfollow is following user
        if (!userToUnfollow.getFollowing().contains(user)) {
            return;
        }
        user.getFollowers().remove(userToUnfollow);
    }

    public static void removeUserFromFollowedList(User user, User userToUnfollow) {
        // Make sure userToUnfollow is following user
        if (!userToUnfollow.getFollowers().contains(user)) {
            return;
        }
        user.getFollowing().remove(userToUnfollow);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package com.mycompany.ssn.beans;

import com.mycompany.ssn.v1.Models.Post;
import com.mycompany.ssn.v1.Models.User;
import com.mycompany.ssn.v1.controllers.UserController;
import com.mycompany.ssn.v1.database.MockDatabase;
import com.mycompany.ssn.v1.exceptions.AlreadyExistsException;
import com.mycompany.ssn.v1.exceptions.DoesNotExistException;
import com.mycompany.ssn.v1.exceptions.UnauthorizedActionException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.shaded.commons.io.FilenameUtils;

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
    private Part uploadedFile; // Represents the uploaded file
    private String profilePicture = "Default-PP.jpeg";
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
    
    public String goToInfoPage(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.profilePicture = user.getProfilePicture();
        return "/UserPage/UserInfoPage.xhtml?faces-redirect=true";
    }
    
    
    public void createAUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            if (!emailExists() && !usernameAlreadyExists()) {
                MockDatabase.addAUser(new User(generateUniqueId(), username, firstName, lastName, email, password));
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User created successfully!", null));
            }
        } catch (AlreadyExistsException ex) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            System.out.println(ex.getMessage());
        }
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
    }
    
    public String deleteAUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            User userToDelete = findByUsername(this.username);
            if (userToDelete != null) {
                
                // Remove the user's posts
                MockDatabase.getPosts().removeIf(post -> post.getUserId() == userToDelete.getId());
                
                
                // Remove the user from the users list
                MockDatabase.getUsers().remove(userToDelete);

                // Remove the user from other users' followers and following lists
                for (User user : MockDatabase.getUsers()) {
                    user.getFollowers().remove(userToDelete);
                    user.getFollowing().remove(userToDelete);
                }

                // Iterate through all posts and remove likes associated with the user
                for (Post post : MockDatabase.getPosts()) {
                    // Remove the user's ID from the post's likes
                    post.getLikes().removeIf(likeUserId -> likeUserId == userToDelete.getId());
                    post.getComments().removeIf(comment -> comment.getUserId() == userToDelete.getId());
                }

                
                // Remove the user's posts and comments
                MockDatabase.getPosts().removeIf(post -> post.getUserId() == userToDelete.getId());
                MockDatabase.getComments().removeIf(comment -> comment.getUserId() == userToDelete.getId());
                
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account successfully deleted.", null));
                // Additional cleanup (e.g., session invalidation) goes here
                
                return "/MainPage/MainPage.xhtml?faces-redirect=true";
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found.", null));
            }
        } catch (DoesNotExistException e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
        return null;
    }

    
    public void modifyAUser(User targettedUser) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // Check if the username or email is already taken by another user
        try {
            for (User user : MockDatabase.getUsers()) {
                if (user.getId() != targettedUser.getId()) {
                    if (user.getUsername().equals(this.username)) {
                        throw new IllegalStateException("Username already used.");
                    }
                    if (user.getEmail().equals(this.email)) {
                        throw new IllegalStateException("Email already used.");
                    }
                }
            }
        } catch (IllegalStateException ex) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            System.out.println(ex.getMessage());
            return;
        }
        
        
        for (User user : MockDatabase.getUsers()) {
            if (user.getId() == targettedUser.getId()) {
               
                user.setUsername(this.username);
                user.setFirstName(this.firstName);
                user.setLastName(this.lastName);
                user.setEmail(this.email);
                // Do not update the password here for security reasons
                // If you need to update the password, ensure it's done securely
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informations sucessfully modified !", null));
                break;
            }
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
    
    protected boolean usernameAlreadyExists() throws AlreadyExistsException {
        for (User user : MockDatabase.getUsers()) {
            if (user.getUsername().equals(username)) {
                throw new AlreadyExistsException("This username (" + username + ") is already taken.");
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
    
    public String getProfilePicture() {
        return profilePicture;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
    
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Getters and setters for uploadedFile
    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    public void followUser(User user, User userToFollow) throws UnauthorizedActionException {
        if (user.getFollowing().contains(userToFollow)) {
            return;
        }

        userToFollow.addFollower(user);
        user.addFollowing(userToFollow);


    }
    
    public void unfollowUser(User user, User userToUnfollow){
        if ((!userToUnfollow.getFollowers().contains(user)) && !(user.getFollowing().contains(userToUnfollow))) {
            return;
        }
        userToUnfollow.getFollowers().remove(user);
        user.getFollowing().remove(userToUnfollow);
        
    }
    
    
    public void toggleFollow(User user, User userTargetted) throws UnauthorizedActionException{
        if ((userTargetted.getFollowers().contains(user)) && (user.getFollowing().contains(userTargetted))) {
            unfollowUser(user, userTargetted);
        } else {
            followUser(user, userTargetted);
        }
        
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
    
    public boolean checkIfFollowing(User user, User userToCheck) {
        if ((userToCheck.getFollowers().contains(user)) && (user.getFollowing().contains(userToCheck))) {
            return true;
        } else {
            return false;
        }
    }
    
    

    public void uploadProfilePicture(User currentUser) throws MessagingException {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("uploadProfilePicture called");
        if (uploadedFile == null) {
            System.out.println("uploadedFile is null");
        }
        
        if (uploadedFile != null) {
            try {
                String filename = currentUser + "-PP";

                // Define the path where the file will be saved
                String uploadsDir = "/uploads/";
                String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(uploadsDir);
                
                // Create the uploads directory if it doesn't exist
                File file = new File(realPath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                // Save the file
                InputStream input = uploadedFile.getInputStream();
                String filePath = realPath + filename;
                Files.copy(input, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);

                currentUser.setProfilePicture(uploadsDir + filename);
                // Update the currentUser in database or session

                context.addMessage(null, new FacesMessage("Profile picture uploaded successfully!"));
            } catch (IOException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload failed", null));
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No file selected", null));
        }
    }

    



    
}

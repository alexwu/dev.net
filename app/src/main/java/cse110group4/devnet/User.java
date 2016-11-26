package cse110group4.devnet;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexwu on 11/6/16.
 */

public class User {

    private String email;
    private String password;
    private String username;
    private String githubUrl;
    private boolean isDeveloper;
    private boolean isClient;
    private List<Project> projects = new ArrayList<>();
    private Map<String, Post> posts = new HashMap<>();
    private Map<String, Object> favorites = new HashMap<>();

    public User() {}

    public User(String email, String password, String username, boolean isDev, boolean isClient) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.isDeveloper = isDev;
        this.isClient = isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }

    public void setDeveloper(boolean developer) {
        isDeveloper = developer;
    }

    public boolean isClient() {
        return isClient;
    }

    public boolean isDeveloper() {
        return isDeveloper;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public Map<String, Post> getPosts() {
        return posts;
    }

    public void setPosts(Map<String, Post> posts) {
        this.posts = posts;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, Object> getFavorites() {
        return favorites;
    }
    public void setFavorites(Map<String, Object> favorites) {
        this.favorites = favorites;
    }
    public boolean isFavorite(String postId) {
        return favorites.containsKey(postId);
    }
    public void deleteFavorite(String postId) {
        if (favorites.containsKey(postId)) {
            favorites.remove(postId);
        }
        else {
            System.out.println("Post not found!");
        }
    }
    public void addPost(String title, String skills, String body, String key) {
        posts.put(key, new Post(title, skills, body, FirebaseAuth.getInstance().getCurrentUser().getUid(), key));
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }
}

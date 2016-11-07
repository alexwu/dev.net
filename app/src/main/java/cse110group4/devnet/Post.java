package cse110group4.devnet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamesbombeelu on 11/6/16.
 */

public class Post {
    private String title;
    private String body;
    private String userId;
    private int starCount;
    public Map<String, Boolean> stars = new HashMap<>();
    private static int postId = 0;

    public Post() {}

    public Post(String title, String body, String userId) {

        this.title = title;
        this.body = body;
        this.userId = userId;
        this.postId = postId + 1;
    }

    public static int getPostId() {
        return postId;
    }

    public String getBody() {
        return body;
    }

    public int getStarCount() {
        return starCount;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public void setStars(Map<String, Boolean> stars) {
        this.stars = stars;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

}

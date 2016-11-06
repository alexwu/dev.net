package cse110group4.devnet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamesbombeelu on 11/6/16.
 */

public class Post {
    private String title;
    private String body;
    private int id;
    private User author;
    private int starCount;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {}

    public Post(String title, String body, User author, int id) {

        this.title = title;
        this.body = body;
        this.author = author;
        this.id = id;
    }
}

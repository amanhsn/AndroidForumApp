package com.example.socialmediaapp;

public class Post {
    int Picture;
    String Username;
    String Content;
    String Title;
    String Tags;
    String Date;

    public Post(int picture, String username, String content, String title, String tags, String date) {
        Picture = picture;
        Username = username;
        Content = content;
        Title = title;
        Tags = tags;
        Date = date;
    }

    public int getPicture() {
        return Picture;
    }

    public String getUsername() {
        return Username;
    }

    public String getContent() {
        return Content;
    }

    public String getTitle() {
        return Title;
    }

    public String getTags() {
        return Tags;
    }

    public String getDate() {
        return Date;
    }
}

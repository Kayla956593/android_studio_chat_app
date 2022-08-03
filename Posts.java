package com.koddev.chatapp;


public class Posts {
    private String id;
    private String poster_name;
    private String post_type;
    private String post_title;
    private String post_text;
    private String post_photo;

    public Posts(String id, String poster_name, String post_type, String post_title, String post_text, String post_photo) {
        this.id = id;
        this.poster_name = poster_name;
        this.post_type = post_type;
        this.post_title = post_title;
        this.post_text = post_text;
        this.post_photo = post_photo;
    }

    public Posts() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster_name() {
        return poster_name;
    }

    public void setPoster_name(String poster_name) {
        this.poster_name = poster_name;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getPost_photo() {
        return post_photo;
    }

    public void setPost_photo(String post_photo) {
        this.post_photo = post_photo;
    }
}

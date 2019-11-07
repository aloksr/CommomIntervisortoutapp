package com.interviewsortout.cakephpapp.model;

/**
 * Created by alok on 17/6/19.
 */

public class PostListModel {
    private String id;
    private String title;
    private String image;
    private String url;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

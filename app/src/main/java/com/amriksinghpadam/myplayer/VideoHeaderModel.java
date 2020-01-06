package com.amriksinghpadam.myplayer;

public class VideoHeaderModel {
    private int image;
    private String title;
    private String description;

    public VideoHeaderModel(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.example.food;

public class uploadimage {
    private String ImageUrl;

    public uploadimage(){


    }

    public uploadimage(String ImageUrl){
        this.ImageUrl=ImageUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}

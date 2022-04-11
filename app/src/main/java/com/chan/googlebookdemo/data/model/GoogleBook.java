package com.chan.googlebookdemo.data.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GoogleBook implements Serializable {

    public String id;

    public String title;

    public String desc;

    public String book_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public String getBook_image() {
        return book_image;
    }

    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void getDataFromJson(JsonObject jsonObject) {
        if(jsonObject.has("id")){
            String id = jsonObject.get("id").getAsString();
            setId(id);
        }
        if(jsonObject.has("volumeInfo")) {
            JsonObject volumeInfo = jsonObject.get("volumeInfo").getAsJsonObject();
            if(volumeInfo.has("title")){
                String title = volumeInfo.get("title").getAsString();
                setTitle(title);
            }
            if(volumeInfo.has("description")){
                String desc = volumeInfo.get("description").getAsString();
                setDesc(desc);
            }
            if(volumeInfo.has("imageLinks")) {
                JsonObject imageLinks = jsonObject.get("imageLinks").getAsJsonObject();
                if(imageLinks.has("thumbnail")){
                    String thumbnail = imageLinks.get("thumbnail").getAsString();
                    setBook_image(thumbnail);
                }
            }
        }
    }
}

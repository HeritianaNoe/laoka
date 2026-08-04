package com.lakabe.laoka.gs.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabFood")
public class Food {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String desc;
    private String photo;
    private boolean heart;
    private int rating;
    private int categoryId;

    public Food(String name, String desc, String photo, boolean heart, int rating, int categoryId){
        this.name = name;
        this.desc = desc;
        this.photo = photo;
        this.heart = heart;
        this.rating = rating;
        this.categoryId = categoryId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public String getPhoto(){
        return photo;
    }

    public boolean getHeart(){
        return heart;
    }

    public int getRating(){
        return rating;
    }

    public int getCategoryId(){
        return categoryId;
    }
}

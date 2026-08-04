package com.lakabe.laoka.gs.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabCategory")
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String photo;

    public Category(String name, String photo){
        this.name = name;
        this.photo = photo;
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

    public String getPhoto(){
        return photo;
    }
}

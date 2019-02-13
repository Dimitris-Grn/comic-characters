package com.example.jimaras.comiccharatacters.features.characters.model;

public class Results {
    private String name;
    private String description;
    private int id;
    private CharacterDomain thumbnail;

    public Results(String name, String description, int id, CharacterDomain thumbnail) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CharacterDomain getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(CharacterDomain thumbnail) {
        this.thumbnail = thumbnail;
    }
}

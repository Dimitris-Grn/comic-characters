package com.example.jimaras.comiccharatacters.features.characters.model;

public class Thumbnail {

    private final String SIZE_IMAGE = "/portrait_incredible.";
    private String path;
    private String extension;

    public Thumbnail(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getImageUrl(){
        String url = path +  SIZE_IMAGE + extension;
        return url.replaceFirst("http", "https");
    }
}

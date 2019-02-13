package com.example.jimaras.comiccharatacters.features.characters.model;

import java.util.List;



public class CharacterDomain {
    public String attributionText;
    public Data data;


    public class Data {
        public int offset;
        public int total; // TOTAL CHARACTERS 1491
        public List<Results> results;

    }

    public class Results {
        public String name;
        public String description;
        public int id;
        public  Thumbnail thumbnail;
    }

    public class Thumbnail{
        private final String SIZE_IMAGE = "/portrait_incredible.";
        public String path;
        public String extension;

        public String getImageUrl(){
            String url = path +  SIZE_IMAGE + extension;
            return url.replaceFirst("http", "https");
        }

    }
}
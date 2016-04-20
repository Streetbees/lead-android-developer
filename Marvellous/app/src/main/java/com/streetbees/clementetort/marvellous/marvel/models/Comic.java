package com.streetbees.clementetort.marvellous.marvel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by clemente.tort on 20/04/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comic {
    public long id;
    public String title;
    public String description;

    public Image thumbnail;
    public List<Image> images;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {
        String path;
        String extension;

        public String getPath() {
            return path + "." + extension;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Creators {
        public int available;
        public List<Author> items;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {
        public String name;
        public String role;
    }
}

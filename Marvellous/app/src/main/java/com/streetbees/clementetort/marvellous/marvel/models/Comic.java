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
        public String path;
        public String extension;

        public String getFullPath() {
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

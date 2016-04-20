package com.streetbees.clementetort.marvellous.marvel.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by clemente.tort on 20/04/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarvelResponse<T> {
    public int code;
    public String status;
    public Data<T> data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data<T> {
        public int offset;
        public int limit;
        public int total;
        public int count;
        public List<T> results;
    }
}

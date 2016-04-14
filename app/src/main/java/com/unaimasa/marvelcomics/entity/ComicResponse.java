package com.unaimasa.marvelcomics.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by UnaiMasa on 13/04/2016.
 */
public final class ComicResponse {
    @SerializedName("code")
    public final String code;
    @SerializedName("status")
    public final String status;
    @SerializedName("copyright")
    public final String copyright;
    @SerializedName("attributionText")
    public final String attributionText;
    @SerializedName("attributionHTML")
    public final String attributionHTML;
    @SerializedName("data")
    public final Data data;
    @SerializedName("etag")
    public final String etag;

    public ComicResponse( String code, String status, String copyright, String attributionText, String attributionHTML, Data data, String etag){
        this.code = code;
        this.status = status;
        this.copyright = copyright;
        this.attributionText = attributionText;
        this.attributionHTML = attributionHTML;
        this.data = data;
        this.etag = etag;
    }

    public static final class Data {
        @SerializedName("offset")
        public final String offset;
        @SerializedName("limit")
        public final String limit;
        @SerializedName("total")
        public final String total;
        @SerializedName("count")
        public final String count;
        @SerializedName("results")
        public final Result results[];

        public Data(String offset, String limit, String total, String count, Result[] results){
            this.offset = offset;
            this.limit = limit;
            this.total = total;
            this.count = count;
            this.results = results;
        }

        public static final class Result {
            @SerializedName("id")
            public final String id;
            @SerializedName("digitalId")
            public final String digitalId;
            @SerializedName("title")
            public final String title;
            @SerializedName("issueNumber")
            public final String issueNumber;
            @SerializedName("variantDescription")
            public final String variantDescription;
            @SerializedName("description")
            public final String description;
            @SerializedName("modified")
            public final String modified;
            @SerializedName("isbn")
            public final String isbn;
            @SerializedName("upc")
            public final String upc;
            @SerializedName("diamondCode")
            public final String diamondCode;
            @SerializedName("ean")
            public final String ean;
            @SerializedName("issn")
            public final String issn;
            @SerializedName("format")
            public final String format;
            @SerializedName("pageCount")
            public final String pageCount;
            @SerializedName("textObjects")
            public final TextObject textObjects[];
            @SerializedName("resourceURI")
            public final String resourceURI;
            @SerializedName("urls")
            public final Url urls[];
            @SerializedName("series")
            public final Series series;
            @SerializedName("variants")
            public final Variant variants[];
            @SerializedName("collections")
            public final Collection collections[];
            @SerializedName("collectedIssues")
            public final CollectedIssue collectedIssues[];
            @SerializedName("dates")
            public final Date dates[];
            @SerializedName("prices")
            public final Price prices[];
            @SerializedName("thumbnail")
            public final Thumbnail thumbnail;
            @SerializedName("images")
            public final Image images[];
            @SerializedName("creators")
            public final Creators creators;
            @SerializedName("characters")
            public final Characters characters;
            @SerializedName("stories")
            public final Stories stories;
            @SerializedName("events")
            public final Events events;

            public Result(String id, String digitalId, String title, String issueNumber, String variantDescription, String description, String modified, String isbn, String upc, String diamondCode,String ean, String issn, String format, String pageCount, TextObject[] textObjects, String resourceURI, Url[] urls, Series series, Variant[] variants, Collection[] collections, CollectedIssue[] collectedIssues, Date[] dates, Price[] prices, Thumbnail thumbnail, Image[] images, Creators creators, Characters characters, Stories stories, Events events){
                this.id = id;
                this.digitalId = digitalId;
                this.title = title;
                this.issueNumber = issueNumber;
                this.variantDescription = variantDescription;
                this.description = description;
                this.modified = modified;
                this.isbn = isbn;
                this.upc = upc;
                this.diamondCode = diamondCode;
                this.ean = ean;
                this.issn = issn;
                this.format = format;
                this.pageCount = pageCount;
                this.textObjects = textObjects;
                this.resourceURI = resourceURI;
                this.urls = urls;
                this.series = series;
                this.variants = variants;
                this.collections = collections;
                this.collectedIssues = collectedIssues;
                this.dates = dates;
                this.prices = prices;
                this.thumbnail = thumbnail;
                this.images = images;
                this.creators = creators;
                this.characters = characters;
                this.stories = stories;
                this.events = events;
            }

            public static final class TextObject {
                @SerializedName("type")
                public final String type;
                @SerializedName("language")
                public final String language;
                @SerializedName("text")
                public final String text;

                public TextObject(String type, String language, String text){
                    this.type = type;
                    this.language = language;
                    this.text = text;
                }
            }

            public static final class Url {
                @SerializedName("type")
                public final String type;
                @SerializedName("url")
                public final String url;

                public Url(String type, String url){
                    this.type = type;
                    this.url = url;
                }
            }

            public static final class Series {
                @SerializedName("resourceURI")
                public final String resourceURI;
                @SerializedName("name")
                public final String name;

                public Series(String resourceURI, String name){
                    this.resourceURI = resourceURI;
                    this.name = name;
                }
            }

            public static final class Variant {
                @SerializedName("resourceURI")
                public final String resourceURI;
                @SerializedName("name")
                public final String name;

                public Variant(String resourceURI, String name){
                    this.resourceURI = resourceURI;
                    this.name = name;
                }
            }

            public static final class Collection {
                @SerializedName("resourceURI")
                public final String resourceURI;
                @SerializedName("name")
                public final String name;

                public Collection(String resourceURI, String name){
                    this.resourceURI = resourceURI;
                    this.name = name;
                }
            }

            public static final class CollectedIssue {
                @SerializedName("resourceURI")
                public final String resourceURI;
                @SerializedName("name")
                public final String name;

                public CollectedIssue(String resourceURI, String name){
                    this.resourceURI = resourceURI;
                    this.name = name;
                }
            }

            public static final class Date {
                @SerializedName("type")
                public final String type;
                @SerializedName("date")
                public final String date;

                public Date(String type, String date){
                    this.type = type;
                    this.date = date;
                }
            }

            public static final class Price {
                @SerializedName("type")
                public final String type;
                @SerializedName("price")
                public final String price;

                public Price(String type, String price){
                    this.type = type;
                    this.price = price;
                }
            }

            public static final class Thumbnail {
                @SerializedName("path")
                public final String path;
                @SerializedName("extension")
                public final String extension;

                public Thumbnail(String path, String extension){
                    this.path = path;
                    this.extension = extension;
                }
            }

            public static final class Image {
                @SerializedName("path")
                public final String path;
                @SerializedName("extension")
                public final String extension;

                public Image(String path, String extension){
                    this.path = path;
                    this.extension = extension;
                }
            }

            public static final class Creators {
                @SerializedName("available")
                public final String available;
                @SerializedName("returned")
                public final String returned;
                @SerializedName("collectionURI")
                public final String collectionURI;
                @SerializedName("items")
                public final Item items[];

                public Creators(String available, String returned, String collectionURI, Item[] items){
                    this.available = available;
                    this.returned = returned;
                    this.collectionURI = collectionURI;
                    this.items = items;
                }

                public static final class Item {
                    @SerializedName("resourceURI")
                    public final String resourceURI;
                    @SerializedName("name")
                    public final String name;
                    @SerializedName("role")
                    public final String role;

                    public Item(String resourceURI, String name, String role){
                        this.resourceURI = resourceURI;
                        this.name = name;
                        this.role = role;
                    }
                }
            }

            public static final class Characters {
                @SerializedName("available")
                public final String available;
                @SerializedName("returned")
                public final String returned;
                @SerializedName("collectionURI")
                public final String collectionURI;
                @SerializedName("items")
                public final Item items[];

                public Characters(String available, String returned, String collectionURI, Item[] items){
                    this.available = available;
                    this.returned = returned;
                    this.collectionURI = collectionURI;
                    this.items = items;
                }

                public static final class Item {
                    @SerializedName("resourceURI")
                    public final String resourceURI;
                    @SerializedName("name")
                    public final String name;
                    @SerializedName("role")
                    public final String role;

                    public Item(String resourceURI, String name, String role){
                        this.resourceURI = resourceURI;
                        this.name = name;
                        this.role = role;
                    }
                }
            }

            public static final class Stories {
                @SerializedName("available")
                public final String available;
                @SerializedName("returned")
                public final String returned;
                @SerializedName("collectionURI")
                public final String collectionURI;
                @SerializedName("items")
                public final Item items[];

                public Stories(String available, String returned, String collectionURI, Item[] items){
                    this.available = available;
                    this.returned = returned;
                    this.collectionURI = collectionURI;
                    this.items = items;
                }

                public static final class Item {
                    @SerializedName("resourceURI")
                    public final String resourceURI;
                    @SerializedName("name")
                    public final String name;
                    @SerializedName("type")
                    public final String type;

                    public Item(String resourceURI, String name, String type){
                        this.resourceURI = resourceURI;
                        this.name = name;
                        this.type = type;
                    }
                }
            }

            public static final class Events {
                @SerializedName("available")
                public final String available;
                @SerializedName("returned")
                public final String returned;
                @SerializedName("evecollectionURInts")
                public final String collectionURI;
                @SerializedName("items")
                public final Item items[];

                public Events(String available, String returned, String collectionURI, Item[] items){
                    this.available = available;
                    this.returned = returned;
                    this.collectionURI = collectionURI;
                    this.items = items;
                }

                public static final class Item {
                    @SerializedName("resourceURI")
                    public final String resourceURI;
                    @SerializedName("name")
                    public final String name;

                    public Item(String resourceURI, String name){
                        this.resourceURI = resourceURI;
                        this.name = name;
                    }
                }
            }
        }
    }
}

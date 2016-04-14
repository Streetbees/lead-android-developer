package com.unaimasa.marvelcomics;

/**
 * Created by unai.masa on 13/04/2016.
 */
public class Constants {

    public static final String EMPTY_STRING = "";
    public static final String HTTP_PATH_SEPARATOR = "/";
    public static final String ENDPOINT_PARAM_TEMPLATE = "(.*)";

    public interface Config {
        boolean DEVELOPER_MODE = true;
    }

    public interface Errors {
        // Incorrect Sintax
        int SERVER_400_EXCEPTION = 400;
        // Not Authorized
        int SERVER_401_EXCEPTION = 401;
        // Payment Required
        int SERVER_402_EXCEPTION = 402;
        // Not Allowed
        int SERVER_403_EXCEPTION = 403;
        // Not Found
        int SERVER_404_EXCEPTION = 404;
        // Method Not Allowed
        int SERVER_405_EXCEPTION = 405;
        // Conflict
        int SERVER_409_EXCEPTION = 409;
        // Internal Error
        int SERVER_500_EXCEPTION = 500;
    }

    public interface SharedKeys {
        // User Information
        String COMIC_ID = "marvel_comic_id";
    }

    public interface Keys {
        // URL
        String MARVEL_API_URL = "http://gateway.marvel.com/";
        // public key
        String MARVEL_API_PUBLIC_KEY = "0990cd31700f807b8b54609750af08ad";
        // private key
        String MARVEL_API_PRIVATE_KEY = "b640c9b82256fbacf06d5e177c21887206d6727c";
    }

}

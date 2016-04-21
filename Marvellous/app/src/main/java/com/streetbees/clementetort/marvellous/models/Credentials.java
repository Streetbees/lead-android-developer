package com.streetbees.clementetort.marvellous.models;

import io.realm.RealmObject;

/**
 * Created by clemente.tort on 20/04/16.
 */
public class Credentials extends RealmObject {
    private String dropboxToken;

    public String getDropboxToken() {
        return dropboxToken;
    }

    public void setDropboxToken(String dropboxToken) {
        this.dropboxToken = dropboxToken;
    }
}

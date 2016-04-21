package com.streetbees.clementetort.marvellous.dropbox;

import io.realm.RealmObject;

/**
 * Created by clemente.tort on 21/04/16.
 */
public class DropboxFile extends RealmObject {
    public static final String NAME = "name";
    public static final String REV = "rev";

    private String name;
    private String rev;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }
}

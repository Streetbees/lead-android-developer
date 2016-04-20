package com.streetbees.clementetort.marvellous.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

/**
 * Created by clemente.tort on 20/04/16.
 */
public class RealmActivity extends AppCompatActivity{
    protected Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

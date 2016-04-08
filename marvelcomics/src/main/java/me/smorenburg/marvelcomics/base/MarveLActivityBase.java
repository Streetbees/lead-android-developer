package me.smorenburg.marvelcomics.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.karumi.marvelapiclient.MarvelApiConfig;

import me.smorenburg.marvelcomics.BuildConfig;

/**
 * Created by Ivor Smorenburg Aguado on 08/04/2016.
 */
public class MarvelActivityBase extends AppCompatActivity implements MarvelAppFunctionality {

    private MarvelApiConfig marvelApiConfig;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    public MarvelApiConfig getMarvelApiConfig() {
        if (marvelApiConfig != null)
            return marvelApiConfig;
        if (BuildConfig.DEBUG) {
            return (marvelApiConfig = new MarvelApiConfig.Builder(BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY).debug().build());
        } else {
            return (marvelApiConfig = new MarvelApiConfig.Builder(BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY).build());
        }
    }


    protected void toastThis(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}

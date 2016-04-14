package com.unaimasa.marvelcomics.section.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unaimasa.marvelcomics.Constants;
import com.unaimasa.marvelcomics.MarvelComicsApp;
import com.unaimasa.marvelcomics.R;
import com.unaimasa.marvelcomics.entity.ComicResponse;
import com.unaimasa.marvelcomics.rest.IWebApi;
import com.unaimasa.marvelcomics.section.adapter.RVAdapter;
import com.unaimasa.marvelcomics.util.CommonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final Logger sLogger = LoggerFactory.getLogger(MainActivity.class);

    private Retrofit mRetrofit;
    private IWebApi mIWebApi;
    private Call<ComicResponse> mResponse;
    private String mHash;
    private String mTimestamp;
    private RecyclerView rv;
    private Activity mActivity;
    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.Keys.MARVEL_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mIWebApi = mRetrofit.create(IWebApi.class);
        mTimestamp = getCurrentTimestamp();
        mHash = getMd5(mTimestamp + Constants.Keys.MARVEL_API_PRIVATE_KEY + Constants.Keys.MARVEL_API_PUBLIC_KEY);

        mResponse = mIWebApi.requestRegister("comics", "-onsaleDate", "50", Constants.Keys.MARVEL_API_PUBLIC_KEY, mHash, mTimestamp);
        mResponse.enqueue(new Callback<ComicResponse>() {
                    @Override
                    public void onResponse(Call<ComicResponse> call, Response<ComicResponse> response) {
                        int statusCode = response.code();
                        ComicResponse resp = response.body();
                        sLogger.info("Call Success - {}", resp);
                        switch(statusCode){
                            case 200:
                                RVAdapter adapter = new RVAdapter(mActivity, resp);
                                rv.setAdapter(adapter);
                                break;
                            default:
                                CommonUtils.showToast(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ComicResponse> call, Throwable t) {
                        // Log error here since request failed
                        sLogger.info("Call Failure - {}", call);
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    public String getCurrentTimestamp(){
        Date date= new java.util.Date();
        return new Timestamp(date.getTime()).toString();
    }

    public String getMd5(String code){
        MessageDigest m= null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(code.getBytes(), 0, code.length());

        return new BigInteger(1,m.digest()).toString(16);
    }
}

package lt.setkus.interviewtest.data.rest;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import lt.setkus.interviewtest.data.BuildConfig;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class MarvelApiConfiguration {

    private final long timeStamp;

    private MarvelApiConfiguration() {
        timeStamp = new Date().getTime();
    }

    private String hashMd5(String source) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(source.getBytes());

        byte[] a = messageDigest.digest();
        int len = a.length;
        StringBuilder sb = new StringBuilder(len << 1);
        for (int i = 0; i < len; i++) {
            sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(a[i] & 0x0f, 16));
        }

        return sb.toString();
    }

    public static MarvelApiConfiguration buildConfiguration() {
        return new MarvelApiConfiguration();
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getPublicKey() {
        return BuildConfig.PUBLIC_KEY;
    }

    public String getHash() {
        try {
            return hashMd5(timeStamp + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY);
        } catch (NoSuchAlgorithmException e) {
            Log.e(MarvelApiConfiguration.class.getSimpleName(), e.getMessage());
        }

        return "";
    }

}

package lt.setkus.interviewtest.util;

import android.util.Log;

import java.net.UnknownHostException;

import lt.setkus.interviewtest.app.R;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 * @since 2016.01.29
 */
public final class ExceptionsUtil {

    public static int getMessageByException(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            return R.string.no_internet_access_exception;
        }

        Log.e("UNKNOWN EXCEPTION", throwable.getMessage());
        throwable.printStackTrace();

        return R.string.unknown_exception;
    }
}

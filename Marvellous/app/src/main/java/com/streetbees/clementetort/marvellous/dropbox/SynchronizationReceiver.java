package com.streetbees.clementetort.marvellous.dropbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by clemente.tort on 21/04/16.
 */
public class SynchronizationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {
           SynchronizationService.synchronize(context);
        }
    }
}


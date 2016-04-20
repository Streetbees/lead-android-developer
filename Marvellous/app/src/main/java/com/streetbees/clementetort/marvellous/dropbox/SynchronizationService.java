package com.streetbees.clementetort.marvellous.dropbox;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.streetbees.clementetort.marvellous.BuildConfig;
import com.streetbees.clementetort.marvellous.models.Credentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.realm.Realm;

/**
 * Created by clemente.tort on 20/04/16.
 */
public class SynchronizationService extends Service {
    SyncThread thread;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (thread == null) {
            thread = new SyncThread(startId);
            thread.start();
        } else
            stopSelf(startId);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Commodity method to call the service
    public static void synchronize(Context context) {
        Intent intent = new Intent(context, SynchronizationService.class);
        context.startService(intent);
    }

    private class SyncThread extends Thread {
        private final int startId;
        private Realm realm;
        private DropboxAPI<AndroidAuthSession> mDBApi;

        public SyncThread(int startId) {
            this.startId = startId;
        }

        @Override
        public void run() {
            super.run();

            // Initialize dropbox interface
            realm = Realm.getInstance(SynchronizationService.this);

            Credentials credentials = realm.where(Credentials.class).findFirst();

            if (credentials != null) {
                String accessToken = credentials.getDropboxToken();

                AppKeyPair appKeys = new AppKeyPair(BuildConfig.DROPBOX_APP_ID, BuildConfig.DROPBOX_APP_SECRET);
                AndroidAuthSession session = new AndroidAuthSession(appKeys, accessToken);

                if (session.isLinked()) {
                    mDBApi = new DropboxAPI<>(session);
                    startUploadingFiles();
                }
            }

            stopSelf(startId);
        }

        private void startUploadingFiles() {
            java.io.File imagePath = new java.io.File(getFilesDir(), "images");
            java.io.File[] files = imagePath.listFiles();

            for (java.io.File file : files) {
                if (fileHasChanged(file)) {
                    try {
                        uploadFile(file);
                    } catch (FileNotFoundException | DropboxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        private boolean fileHasChanged(File file) {
            String filename = Uri.fromFile(file).getLastPathSegment();
            DropboxFile internalFile = realm.where(DropboxFile.class).equalTo(DropboxFile.NAME, filename).findFirst();

            try {
                DropboxAPI.Entry dropboxFile = mDBApi.metadata(java.io.File.separator + filename, 1, null, false, null);
                // we upload if we have not start tracking the file, or if it is different
                return internalFile == null || !internalFile.getRev().equals(dropboxFile.rev);
            } catch (DropboxException e) {
                // Dropbox file does not exists
                if (internalFile != null) {
                    // Internal file exists, we will remove to synchronize
                    file.delete();
                    internalFile.removeFromRealm();
                    return false;
                }
                return true;
            }
        }

        private void uploadFile(File file) throws FileNotFoundException, DropboxException {
            String rev = null;
            String filename = Uri.fromFile(file).getLastPathSegment();
            DropboxFile internalFile = realm.where(DropboxFile.class).equalTo(DropboxFile.NAME, filename).findFirst();

            // We keep track of rev to rewrite the file
            if (internalFile != null)
                rev = internalFile.getRev();
            else {
                realm.beginTransaction();
                internalFile = realm.createObject(DropboxFile.class);
                realm.commitTransaction();
            }

            FileInputStream inputStream = new FileInputStream(file);
            DropboxAPI.Entry response = mDBApi.putFile(java.io.File.separator + Uri.fromFile(file).getLastPathSegment(), inputStream, file.length(), rev, null);

            // Update the changes
            realm.beginTransaction();

            internalFile.setName(filename);
            internalFile.setRev(response.rev);

            realm.commitTransaction();

            Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
        }
    }
}

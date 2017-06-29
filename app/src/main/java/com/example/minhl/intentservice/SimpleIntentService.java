package com.example.minhl.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class SimpleIntentService extends IntentService {
    public static final String ACTION_1 = "JUST DO IT";

    public SimpleIntentService() {
        super("SimpleIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_1);
        for (int i = 0; i <= 100; i++) {
            broadcastIntent.putExtra("percel", i); //gửi đi i là % đó
            sendBroadcast(broadcastIntent);
            SystemClock.sleep(200); //Ngủ 0.1s
        }
    }
}

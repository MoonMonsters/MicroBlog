package com.newer.newerblogging.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ClearIntentService extends IntentService {

    public ClearIntentService(String name) {
        super(name);
    }

    public ClearIntentService() {
        this("ClearIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}

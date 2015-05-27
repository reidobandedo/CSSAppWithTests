package edu.uw.tacoma.mmuppa.cssappwithtests.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 *
 */
public class CourseService extends IntentService {

    private static final String TAG = "CourseService";

    public CourseService() {
        super("CourseService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Handling Service");
    }
}

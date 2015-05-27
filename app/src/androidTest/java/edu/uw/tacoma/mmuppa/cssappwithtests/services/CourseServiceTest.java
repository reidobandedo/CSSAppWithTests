package edu.uw.tacoma.mmuppa.cssappwithtests.services;

import android.content.Intent;
import android.test.ServiceTestCase;

/**
 * Created by mmuppa on 5/26/15.
 */
public class CourseServiceTest extends ServiceTestCase<CourseService> {

    public CourseServiceTest() {
        super(CourseService.class);
    }

    public void testNotificationService() {
        Intent intent = new Intent();
        intent.setClass(getContext(), CourseService.class);
        startService(intent);
    }
}

package edu.uw.tacoma.mmuppa.cssappwithtests.controllers;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.robotium.solo.SystemUtils;

/**
 * Created by mmuppa on 5/25/15.
 */
public class    CourseListActivityTestDownNetwork extends ActivityInstrumentationTestCase2<CourseListActivity> {

    private Solo solo;

    public CourseListActivityTestDownNetwork() {
        super(CourseListActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testNetworkDown() {
        SystemUtils systemUtils = new SystemUtils(getInstrumentation());
        systemUtils.setWiFiData(false);
        solo = new Solo(getInstrumentation(), getActivity());
        boolean textFound = solo.searchText("Unable to download the list of courses");
        assertTrue("Network is down, message displayed", textFound);
        systemUtils.setWiFiData(true);
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }
}

package edu.uw.tacoma.mmuppa.cssappwithtests.controllers;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by mmuppa on 5/3/15.
 */
public class CourseListActivityTest extends ActivityInstrumentationTestCase2<CourseListActivity>  {

    private Solo solo;

    public CourseListActivityTest() {
        super(CourseListActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testDataShowsUp() {

        solo.unlockScreen();
        boolean textFound = solo.searchText("TCSS450");
        assertTrue("Course List Data retrieved", textFound);

    }


    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }
}

package edu.uw.tacoma.mmuppa.cssappwithtests.controllers;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;


/**
 * Created by mmuppa on 4/30/15.
 */
public class CourseActivityTest extends ActivityInstrumentationTestCase2<CourseActivity> {
    private Solo solo;

    public CourseActivityTest() {
        super(CourseActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testRequiredFields() {
        solo.unlockScreen();

        solo.enterText(0, "");
        solo.clickOnButton("Add Course");
        boolean textFound = solo.searchText("Please enter all fields except for prereqs");
        assertTrue("Required fields validation failed", textFound);
    }

    public void testOrientation() {
        solo.enterText(0, "TCSS305");
        solo.enterText(1, "I am a short description");
        solo.enterText(2, "I am a long description");
        solo.enterText(3, "I am a prereq");

        solo.setActivityOrientation(Solo.LANDSCAPE);
        boolean textFound = solo.searchText("TCSS305");
        assertTrue("Orientation change failed", textFound);

        solo.setActivityOrientation(Solo.PORTRAIT);
        textFound = solo.searchText("TCSS305");
        assertTrue("Orientation change failed", textFound);
    }

    public void testCourseAddButton() {
        solo.enterText(0, "TCSS360");
        solo.enterText(1, "I am a short description");
        solo.enterText(2, "I am a long description");
        solo.enterText(3, "I am a prereq");
        solo.clickOnButton("Add Course");
        boolean textFound = solo.searchText("Course added successfully!");
        assertTrue("Course add failed", textFound);
    }

}

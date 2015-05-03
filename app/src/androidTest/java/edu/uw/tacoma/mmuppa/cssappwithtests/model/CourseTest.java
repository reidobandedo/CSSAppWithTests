package edu.uw.tacoma.mmuppa.cssappwithtests.model;

import junit.framework.TestCase;

/**
 * Created by mmuppa on 4/30/15.
 */
public class CourseTest extends TestCase {

    private Course mCourse;

    public void setUp() {
        mCourse = new Course("CSS450", "description", "long description", "prereqs");
    }

    public void testConstructor() {
        Course course = new Course("CSS450", "description", "long description", "prereqs");
        assertNotNull(course);
    }

    public void testSetCourseId() {
        mCourse.setCourseId("CSS305");
        assertEquals("CSS305", mCourse.getCourseId());
    }

    public void testSetNullCourseId() {
        try {
            mCourse.setCourseId(null);
            fail("Course Id can be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    public void testSetLengthCourseId() {
        try {
            mCourse.setCourseId("15");
            fail("Course Id can be set to less than 5 characters long");
        }
        catch (IllegalArgumentException e) {

        }
    }

    public void testGetCourseId() {
        assertEquals("CSS450", mCourse.getCourseId());
    }

}

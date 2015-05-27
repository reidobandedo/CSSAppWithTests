package edu.uw.tacoma.mmuppa.cssappwithtests.model;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by mmuppa on 4/30/15.
 */
public class CourseTest extends TestCase {

    private Course mCourse;

    public void setUp() {
         mCourse = new Course("CSS360", "description", "long description", "prereqs");
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
        assertEquals("CSS360", mCourse.getCourseId());
    }

    public void testParseCourseJSONWithInvalidString() {
            String message = (String) Course.parseCourseJSON("Invalid JSON", null);
            boolean failed = message.startsWith("Unable to parse data, Reason");
            assertTrue("JSON With Invalid String", failed);
    }

    public void testParseCourseJSONWithValidString() {

            String courseJSON = "[{\"id\":\"TCSS450\",\"shortDesc\":\"Mobile App Programming\",\"longDesc\":\"Covers mobile principles\",\"prereqs\":\"TCSS360\"},{\"id\":\"TCSS445\",\"shortDesc\":\"Database Systems Design\",\"longDesc\":\"Covers database principles\",\"prereqs\":\"TCSS342\"}]";
            String message =  Course.parseCourseJSON(courseJSON
                    , new ArrayList<Course>());
            assertTrue("JSON With Valid String", message == null);
    }

}

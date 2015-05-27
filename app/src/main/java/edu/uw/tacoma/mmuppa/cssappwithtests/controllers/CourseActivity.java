package edu.uw.tacoma.mmuppa.cssappwithtests.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uw.tacoma.mmuppa.cssappwithtests.R;
import edu.uw.tacoma.mmuppa.cssappwithtests.data.CourseDB;
import edu.uw.tacoma.mmuppa.cssappwithtests.model.Course;


public class CourseActivity extends Activity {

    private Course mCourse;

    private EditText mCourseIdEditText;
    private EditText mCourseShortDescriptionEditText;
    private EditText mCourseLongDescriptionEditText;
    private EditText mCoursePrereqsEditText;
    private Button mAddButton;

    private SharedPreferences mSharedPreferences;

    // Keys for the values in Shared Preferences
    private final static String COURSE_ID = "courseId";
    private final static String SHORT_DESC = "shortDesc";
    private final static String LONG_DESC = "longDesc";
    private final static String PRE_REQS = "prereqs";
    private final static String UNKNOWN = "";

    private CourseDB mCourseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mCourseDB = new CourseDB(this);

        mCourseIdEditText = (EditText) findViewById(R.id.course_id_edit_text);
        mCourseShortDescriptionEditText = (EditText) findViewById(R.id.course_short_desc_edit_text);
        mCourseLongDescriptionEditText = (EditText) findViewById(R.id.course_long_desc_edit_text);
        mCoursePrereqsEditText = (EditText) findViewById(R.id.course_prereqs_edit_text);
        mAddButton = (Button) findViewById(R.id.course_add_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseId = mCourseIdEditText.getText().toString();
                String shortDesc = mCourseShortDescriptionEditText.getText().toString();
                String longDesc = mCourseLongDescriptionEditText.getText().toString();
                String prereqs = mCoursePrereqsEditText.getText().toString();

                if (courseId.length() == 0 || shortDesc.length() == 0
                        || longDesc.length() == 0) {
                    Toast.makeText(v.getContext(), "Please enter all fields except for prereqs"
                            , Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (prereqs.length() == 0) prereqs = null;
                mCourse = new Course(courseId, shortDesc, longDesc, prereqs);

                try {
                    mCourseDB.insert(courseId, shortDesc, longDesc, prereqs);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG)
                            .show();

                    return;
                }
                Toast.makeText(v.getContext(), "Course added successfully!", Toast.LENGTH_SHORT)
                        .show();

                // Clearing the texts after completion of the add.
                mCourseIdEditText.setText("");
                mCourseShortDescriptionEditText.setText("");
                mCourseLongDescriptionEditText.setText("");
                mCoursePrereqsEditText.setText("");
            }
        });
    }

    /**
     * Save the state when the back button or home button is pressed.
     */
    @Override
    protected void onPause() {
        super.onPause();

        String courseId = mCourseIdEditText.getText().toString();
        String shortDesc = mCourseShortDescriptionEditText.getText().toString();
        String longDesc = mCourseLongDescriptionEditText.getText().toString();
        String prereqs = mCoursePrereqsEditText.getText().toString();

        // Save the state
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        Editor editor = mSharedPreferences.edit();
        editor.putString(COURSE_ID, courseId);
        editor.putString(SHORT_DESC, shortDesc);
        editor.putString(LONG_DESC, longDesc);
        editor.putString(PRE_REQS, prereqs);
        editor.commit();

    }

    /**
     * Restore the state when the app is in the foreground
     */
    @Override
    protected void onStart() {
        super.onStart();


        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);

        if (mSharedPreferences != null) {
            mCourseIdEditText.setText(mSharedPreferences.getString(COURSE_ID, UNKNOWN));
            mCourseShortDescriptionEditText.setText(mSharedPreferences.getString(SHORT_DESC, UNKNOWN));
            mCourseLongDescriptionEditText.setText(mSharedPreferences.getString(LONG_DESC, UNKNOWN));
            mCoursePrereqsEditText.setText(mSharedPreferences.getString(PRE_REQS, UNKNOWN));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCourseDB != null)
            mCourseDB.close();
    }
}

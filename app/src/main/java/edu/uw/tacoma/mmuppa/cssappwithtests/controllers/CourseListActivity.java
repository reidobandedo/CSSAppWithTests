package edu.uw.tacoma.mmuppa.cssappwithtests.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import edu.uw.tacoma.mmuppa.cssappwithtests.R;
import edu.uw.tacoma.mmuppa.cssappwithtests.data.CourseDB;
import edu.uw.tacoma.mmuppa.cssappwithtests.model.Course;

public class CourseListActivity extends ActionBarActivity {

    private static final String TAG = "CourseListActivity";
    private ListView mCoursesListView;
    private CourseAdapter mAdapter;
    private CourseDB mCourseDB;
    private TextView mTextView;
    private ArrayList<Course> mCourseList;
    private static final String COURSE_URL
            = "http://cssgate.insttech.washington.edu/~mmuppa/Android/test.php?cmd=courses";

    private String mCourses;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        mTextView = (TextView) findViewById(R.id.text_view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{COURSE_URL});

        mCoursesListView = (ListView) findViewById(R.id.course_list_view);

        mCourseList = new ArrayList<>();
        mAdapter = new CourseAdapter(this, mCourseList);

      /*
        mCourseDB = new CourseDB(this);
        mCourseList = mCourseDB.selectAll();
        mCourseDB.close();
      */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Running the loading of the JSON in a separate thread.
     * Code adapted from http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html
     */
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(CourseListActivity.this, "Wait", "Downloading...");
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. If there was an exception, it is displayed in red using the text
         * view widget. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            mProgressDialog.dismiss();
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                mTextView.setText(result);
                mTextView.setTextColor(Color.RED);
                return;
            }

            mCourses = result;
            result = Course.parseCourseJSON(mCourses, mCourseList);
            // Something wrong with the JSON returned.
            if (result != null) {
                mTextView.setTextColor(Color.RED);
                mTextView.setText(result);
                return;
            }

            // Everything is good, show the list of courses.
            if (!mCourseList.isEmpty()) {
                    mCoursesListView.setAdapter(mAdapter);
            }
        }
    }
}


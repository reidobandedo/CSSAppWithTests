package edu.uw.tacoma.mmuppa.cssappwithtests.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.uw.tacoma.mmuppa.cssappwithtests.R;
import edu.uw.tacoma.mmuppa.cssappwithtests.data.CourseDB;
import edu.uw.tacoma.mmuppa.cssappwithtests.model.Course;

public class CourseListActivity extends ActionBarActivity {

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
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }


        @Override
        protected void onPostExecute(String result) {
            mProgressDialog.dismiss();
            mCourses = result;
            if (mCourses != null) {
                try {
                    JSONArray arr = new JSONArray(mCourses);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Course course = new Course(obj.getString(Course.ID), obj.getString(Course.SHORT_DESC)
                                , obj.getString(Course.LONG_DESC), obj.getString(Course.PRE_REQS));
                        mCourseList.add(course);
                    }
                } catch (JSONException e) {
                    System.out.println("JSON Exception");
                }

            }

            if (!mCourseList.isEmpty()) {
                mCoursesListView.setAdapter(mAdapter);
            }
        }
    }
}


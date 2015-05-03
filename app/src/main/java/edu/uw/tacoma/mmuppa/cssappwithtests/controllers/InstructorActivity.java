package edu.uw.tacoma.mmuppa.cssappwithtests.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.uw.tacoma.mmuppa.cssappwithtests.R;
import edu.uw.tacoma.mmuppa.cssappwithtests.model.Instructor;


public class InstructorActivity extends ActionBarActivity {

    private Spinner mTitleSpinner;
    private EditText mName, mEmail, mPhone, mOffice, mBio;
    private Button mAddButton;

    // To demonstrate saving to file
    private final static String FILE_NAME = "instructor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        // Get a reference to the Spinner
        mTitleSpinner = (Spinner) findViewById(R.id.instructor_title);

        // Create an Adapter that holds a list of titles
        // titles is defined in strings.xml
        // simple_spinner_item and simple_spinner_dropdown_item are predefined
        // Android layout elements.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.titles, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the Adapter for the spinner
        mTitleSpinner.setAdapter(adapter);


        mAddButton = (Button) findViewById(R.id.instructor_add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = (EditText) findViewById(R.id.instructor_name_edit_text);
                String name = mName.getText().toString();
                String title = (String) mTitleSpinner.getSelectedItem();
                mEmail = (EditText) findViewById(R.id.instructor_email_edit_text);
                String email = mEmail.getText().toString();
                mPhone = (EditText) findViewById(R.id.instructor_phone_edit_text);
                String phone = mPhone.getText().toString();
                mOffice = (EditText) findViewById(R.id.instructor_office_edit_text);
                String office = mOffice.getText().toString();
                mBio = (EditText) findViewById(R.id.instructor_bio_edit_text);
                String bio = mBio.getText().toString();

                if (name.length() == 0 || email.length() == 0
                        || phone.length() == 0 || office.length() == 0
                        || bio.length() == 0) {
                    Toast.makeText(v.getContext(), "Please enter all fields"
                            , Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(v.getContext(), "Invalid Email Address"
                            , Toast.LENGTH_SHORT)
                            .show();
                    mEmail.requestFocus();
                    return;
                }
                if (!Patterns.PHONE.matcher(phone).matches()) {
                    Toast.makeText(v.getContext()
                            , "Invalid Phone Number, format is 253-692-5555"
                            , Toast.LENGTH_SHORT)
                            .show();
                    mPhone.requestFocus();
                    return;
                }

                Instructor instructor = new Instructor(name, title, email, phone, office, bio);
                Toast.makeText(v.getContext(), "edu.uw.tacoma.mmuppa.cssappwithwebservices.model.Instructor added Successfully!", Toast.LENGTH_SHORT)
                        .show();

                writeToFile(instructor); //Save to file

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Instructor instructor = readFromFile(); //Read from file
        if (instructor != null) {
            Toast.makeText(this, instructor.toString(), Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private void writeToFile(Instructor instructor) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(instructor);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Instructor readFromFile() {

        Instructor instructor = null;
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        try {
            fileInputStream = openFileInput(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            instructor = (Instructor) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instructor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instructor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

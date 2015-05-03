package edu.uw.tacoma.mmuppa.cssappwithtests.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by mmuppa on 4/14/15.
 */
public class Instructor implements Serializable {
    private String mFullName;
    private String mTitle;
    private String mEmail;
    private String mPhone;
    private String mOffice;
    private String mBio;
    private Set<String> mCourses;

    public Instructor(String fullName, String title, String email, String phone, String office, String bio) {
        mFullName = fullName;
        mTitle = title;
        mEmail = email;
        mPhone = phone;
        mOffice = office;
        mBio = bio;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getOffice() {
        return mOffice;
    }

    public void setOffice(String office) {
        mOffice = office;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    @Override
    public String toString() {
        return "edu.uw.tacoma.mmuppa.cssappwithwebservices.model.Instructor{" +
                "mBio='" + mBio + '\'' +
                ", mFullName='" + mFullName + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mOffice='" + mOffice + '\'' +
                '}';
    }
}

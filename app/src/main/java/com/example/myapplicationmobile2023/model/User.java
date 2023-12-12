package com.example.myapplicationmobile2023.model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    // Constructor
    public User(String email, String first_name, String last_name,String avatar) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    // Parcelable implementation
    protected User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(avatar);
    }


}

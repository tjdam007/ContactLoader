package com.dev4solutions.contactloader;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by MaNoJ SiNgH RaWaL on 09-Jun-16.
 */
public class Contact implements Parcelable {

    private String id;
    private String name;
    private String photoUri;
    private String phoneNumber;
    private String initials;
    private boolean selected;

    private String extraHashCode;

    public Contact(String phone) {
        this.phoneNumber = phone;
    }

    public Contact() {
    }

    public Contact(Parcel parcel) {
        this.id = parcel.readString();
        this.phoneNumber = parcel.readString();
        this.name = parcel.readString();
        this.initials = parcel.readString();
        this.photoUri = parcel.readString();
        this.extraHashCode = parcel.readString();
        boolean[] b = new boolean[1];
        parcel.readBooleanArray(b);
        this.selected = b[0];
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int hashCode() {
        if (TextUtils.isEmpty(extraHashCode))
            return phoneNumber.hashCode();
        else
            return extraHashCode.hashCode() + phoneNumber.hashCode();

    }

    public boolean equals(Object obj) {
        if (obj instanceof Contact) {
            Contact contact = (Contact) obj;
            return (contact.phoneNumber.equals(this.phoneNumber));
        } else {
            return false;
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }


    public void setHashCode(String extraHashCode) {
        this.extraHashCode = extraHashCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(phoneNumber);
        parcel.writeString(name);
        parcel.writeString(initials);
        parcel.writeString(photoUri);
        parcel.writeString(extraHashCode);
        parcel.writeBooleanArray(new boolean[]{selected});
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel parcel) {
            return new Contact(parcel);
        }

        @Override
        public Contact[] newArray(int i) {
            return new Contact[i];
        }
    };
}

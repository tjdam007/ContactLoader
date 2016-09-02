package com.dev4solutions.contactloader;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Created by MaNoJ SiNgH RaWaL on 10-Jun-16.
 */
public class LazyListContact extends LazyList<Contact> {
    public LazyListContact(Context context, Cursor cursor) {
        super(cursor);
    }


    /**
     * Return a Contact object on index but it can be null if Contact obj is duplicate.
     */
    @Override
    public Contact get(int index) {
        int size = size();
        if (index < size) {
            // find item in the collection

            Contact item = super.get(index);

            if (item == null) {
                item = create(getCursor(), index);

                set(index, item);

            }
            return item;
        } else {
            // we have to grow the collection
            for (int i = size; i < index; i++) {
                add(null);
            }
            // create last object, add and return
            Contact item = create(getCursor(), index);
            add(item);
            return item;
        }
    }

    @Override
    public Contact create(Cursor cursor, int index) {
        Contact contact = new Contact();
        cursor.moveToPosition(index);

        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        String uri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));

        contact.setId(id);
        contact.setName(name);
        contact.setPhoneNumber(phone);
        contact.setPhotoUri(uri);

        return contact;
    }

}

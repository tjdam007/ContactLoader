package com.dev4solutions.contactloader;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by MaNoJ SiNgH RaWaL on 09-Jun-16.
 */
public class ContactDAO {


    public LazyListContact selectAllLazy(Context context, Cursor cursor) {
        LazyListContact contacts = new LazyListContact(context, cursor);
        return contacts;
    }
}

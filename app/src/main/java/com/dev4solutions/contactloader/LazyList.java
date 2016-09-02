package com.dev4solutions.contactloader;

import android.database.Cursor;

import java.util.ArrayList;

public abstract class LazyList<T> extends ArrayList<T> {

    private Cursor mCursor;

    public LazyList(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public T get(int index) {
        int size = super.size();
        if (index < size) {
            // find item in the collection
            T item = super.get(index);
            if (item == null) {
                item = create(mCursor, index);
                set(index, item);
            }
            return item;
        } else {
            // we have to grow the collection
            for (int i = size; i < index; i++) {
                add(null);
            }
            // create last object, add and return
            T item = create(mCursor, index);
            add(item);
            return item;
        }
    }

    @Override
    public int size() {
        if (mCursor != null && !mCursor.isClosed())
            return mCursor.getCount();
        else return 0;
    }

    public void closeCursor() {
        mCursor.close();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public abstract T create(Cursor cursor, int index);
}
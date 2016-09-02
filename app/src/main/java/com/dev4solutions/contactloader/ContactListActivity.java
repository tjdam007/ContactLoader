package com.dev4solutions.contactloader;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class ContactListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ContactCallback {

    private static final String TAG = "ContactListActivity";
    private static final int LOADER_ID = 1523;
    private RecyclerView recyclerView;
    private ContactDAO contactDAO;
    private LazyListContact list;
    private ContactAdapter contactAdapter;
    private RippleView doneRippleView;

    ArrayList<Contact> selectedContactHashSet;
    private HashSet<String> dbContactHashSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doneRippleView = (RippleView) findViewById(R.id.doneRippleView);
        doneRippleView.setVisibility(View.GONE);
        doneRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                //TODO: CODE YOURSELF... AFTER CLICKING DONE
                Toast.makeText(ContactListActivity.this, "You have selected :" + selectedContactHashSet.size(), Toast.LENGTH_SHORT).show();
            }
        });

        contactDAO = new ContactDAO();
        selectedContactHashSet = new ArrayList();

     /*IF HAVE PREVIOUS CONTACT SELECTED ADD THEM IN dbContactHashSet*/
        dbContactHashSet = new HashSet<>();
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
        };
        CursorLoader cursorLoader = new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        try {
            list = contactDAO.selectAllLazy(this, data);
            contactAdapter = new ContactAdapter(this, list);
            recyclerView.setAdapter(contactAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onContactChecked(ContactViewHolder holder, Contact contact) {
       /* if (!selectedContactHashSet.contains(contact)) {

        }*/
        selectedContactHashSet.add(contact);

        if (selectedContactHashSet.size() > 0) {
            animateShowDone();
        }

    }

    @Override
    public void onContactUnchecked(ContactViewHolder holder, Contact contact) {
        if (selectedContactHashSet.contains(contact)) {
            selectedContactHashSet.remove(contact);
        }

        if (selectedContactHashSet.size() == 0) {
            animateHideDone();
        }
    }

    private void animateHideDone() {
        if (doneRippleView.getVisibility() == doneRippleView.GONE) {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                doneRippleView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        doneRippleView.startAnimation(animation);
    }

    private void animateShowDone() {
        if (doneRippleView.getVisibility() == doneRippleView.VISIBLE) {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                doneRippleView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        doneRippleView.startAnimation(animation);
    }

    @Override
    public void onViewState(ContactViewHolder holder, Contact contact) {
        if (selectedContactHashSet.contains(contact)) {
            contact.setSelected(true);
            holder.setSelected(true);
        } else {
            holder.setSelected(false);
            contact.setSelected(false);
        }


        if (dbContactHashSet.contains(contact.getPhoneNumber())) {
            holder.setCheckVisible(false);
            holder.setClickable(false);
        }
    }
}

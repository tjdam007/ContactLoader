package com.dev4solutions.contactloader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MaNoJ SiNgH RaWaL on 09-Jun-16.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private LazyListContact list;
    private ContactListActivity contactActivity;


    public ContactAdapter(ContactListActivity activity, LazyListContact list) {
        this.list = list;
        this.contactActivity = activity;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contactActivity).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(contactActivity, view);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position) {
        final Contact contact = list.get(position);

        if (contact != null) {
            holder.setName(contact.getName());
            holder.setNumber(contact.getPhoneNumber());

            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contact.isSelected()) {
                        contact.setSelected(false);
                        holder.setSelected(false);
                        contactActivity.onContactUnchecked(holder, contact);

                    } else {
                        contact.setSelected(true);
                        holder.setSelected(true);
                        contactActivity.onContactChecked(holder, contact);

                    }
                }
            });

            contactActivity.onViewState(holder, contact);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

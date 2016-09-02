package com.dev4solutions.contactloader;


/**
 * Created by MaNoJ SiNgH RaWaL on 11-Jun-16.
 */
public interface ContactCallback {
    void onContactChecked(ContactViewHolder holder, Contact contact);
    void onContactUnchecked(ContactViewHolder holder, Contact contact);
    void onViewState(ContactViewHolder holder, Contact contact);
}

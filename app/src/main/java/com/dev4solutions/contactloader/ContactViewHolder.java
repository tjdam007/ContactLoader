package com.dev4solutions.contactloader;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by MaNoJ SiNgH RaWaL on 09-Jun-16.
 */
public class ContactViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTextView;
    private final TextView numberTextView;
    private final AppCompatImageView checkImageView;

    private final Context mContext;
    private View view;
    private boolean selected;
    private boolean checkVisible;
    private boolean clickable;

    public ContactViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        this.view = itemView;
        nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        numberTextView = (TextView) itemView.findViewById(R.id.numberTextView);
        checkImageView = (AppCompatImageView) itemView.findViewById(R.id.checkImage);
    }

    public void setName(String name) {
        nameTextView.setText(name);
    }

    public void setNumber(String number) {
        numberTextView.setText(number);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        view.setOnClickListener(onClickListener);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected)
            checkImageView.setImageResource(R.drawable.round_tick_selected);
        else
            checkImageView.setImageResource(R.drawable.round_tick_dselected);
    }


    public void setCheckVisible(boolean checkVisible) {
        this.checkVisible = checkVisible;
        if (checkVisible)
            checkImageView.setImageResource(R.drawable.round_tick_dselected);
        else {
            checkImageView.setImageResource(R.drawable.round_tick_already_selected);
        }
    }


    public void setClickable(boolean clickable) {
        if (clickable)
            view.setClickable(true);
        else {
            view.setClickable(false);
            view.setOnClickListener(null);
        }
    }
}

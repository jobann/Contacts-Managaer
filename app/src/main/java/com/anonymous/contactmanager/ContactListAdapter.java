package com.anonymous.contactmanager;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactListAdapter extends ArrayAdapter<String> {

    JSONObject response;
    public ContactListAdapter(Context context, JSONObject response) {
        super(context, R.layout.custom_contact_card);

        this.response = response;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.custom_contact_card, null, true);

        TextView nameTV = rowView.findViewById(R.id.nameTV);
        TextView phoneTV = rowView.findViewById(R.id.phoneTV);
        TextView emailTV = rowView.findViewById(R.id.emailTV);

        try {
            String name = response.get("fName").toString()+ response.get("lName").toString();
            String phone = response.get("phone").toString();
            String email = response.getString("email");
            nameTV.setText(name);
            phoneTV.setText(phone);
            emailTV.setText(email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return rowView;

    }

    @Override
    public int getCount() {
        return 2;
    }
}
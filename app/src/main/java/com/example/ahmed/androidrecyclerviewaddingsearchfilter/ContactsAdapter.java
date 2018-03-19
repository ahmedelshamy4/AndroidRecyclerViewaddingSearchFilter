package com.example.ahmed.androidrecyclerviewaddingsearchfilter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Created by Ahmed on 3/18/2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.myViewHolder>
        implements Filterable {
    Context context;
    List<Contact> contactList;
    List<Contact> contactListFiltered;
    ContactsAdapterListener listener;

    public ContactsAdapter(Context context, List<Contact> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Contact contact = contactListFiltered.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());
        Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }


    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Contact> list = new ArrayList<>();
                    for (Contact row : contactList) {
                        if (row.getName().toLowerCase().contains(charString.trim().toLowerCase())
                                || row.getPhone().contains(charString)) {
                            list.add(row);
                        }
                    }
                    contactListFiltered = list;
                }
                FilterResults results = new FilterResults();
                results.values = contactListFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactListFiltered = (List<Contact>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;
        ImageView thumbnail;

        public myViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            thumbnail = view.findViewById(R.id.thumbnail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Contact contact);
    }

}

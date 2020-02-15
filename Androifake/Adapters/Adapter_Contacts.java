package com.example.android.greetup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.greetup.Contacts.ContactList;
import com.example.android.greetup.Contacts.contact;
import com.example.android.greetup.Interfaces.ItemClickListener;
import com.example.android.greetup.MainActivity;
import com.example.android.greetup.Model_Video;
import com.example.android.greetup.R;

import java.util.List;

/**
 * Created by raj garg on 13-02-2020.
 */

public class Adapter_Contacts extends RecyclerView.Adapter<Adapter_Contacts.MyVideoHolder>{
    private Context mctx;
    private List<contact> MyVideoList;
    ContactList homePage;
    public Adapter_Contacts(ContactList homePage,Context mctx, List<contact> myVideoList) {
        this.homePage=homePage;
        this.mctx = mctx;
        MyVideoList = myVideoList;
    }

    @Override
    public Adapter_Contacts.MyVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.adapter_contact, parent,false);
        Adapter_Contacts.MyVideoHolder holder = new Adapter_Contacts.MyVideoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Adapter_Contacts.MyVideoHolder holder, int position) {
        contact myContact = MyVideoList.get(position);
        holder.name.setText(myContact.getName());
        holder.phoneNo.setText(myContact.getPhoneNo());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return MyVideoList.size();
    }
    class MyVideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        TextView name,phoneNo;
        private ItemClickListener itemClickListener;
        public MyVideoHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phoneNo = itemView.findViewById(R.id.phone);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),true);
            return true;
        }
    }
}

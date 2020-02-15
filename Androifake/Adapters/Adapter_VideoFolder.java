package com.example.android.greetup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.greetup.Contacts.ContactList;
import com.example.android.greetup.GetText;
import com.example.android.greetup.Interfaces.ItemClickListener;
import com.example.android.greetup.MainActivity;
import com.example.android.greetup.Model_Video;
import com.example.android.greetup.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by raj garg on 13-02-2020.
 */

public class Adapter_VideoFolder extends RecyclerView.Adapter<Adapter_VideoFolder.MyVideoHolder> {
    private Context mctx;
    private List<HashMap<String,String>> MyVideoList;
    MainActivity homePage;
    public Adapter_VideoFolder(MainActivity homePage,Context mctx, List<HashMap<String,String>> myVideoList) {
        this.homePage=homePage;
        this.mctx = mctx;
        MyVideoList = myVideoList;
    }

    @Override
    public MyVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.adapter_videos, parent,false);
        MyVideoHolder holder = new MyVideoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Adapter_VideoFolder.MyVideoHolder holder, int position) {
        String myAudio = MyVideoList.get(position).get("file_name");
        String myPath = MyVideoList.get(position).get("file_path");
        holder.title.setText(myAudio);
        holder.path.setText(myPath);
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

        TextView title,path,time_publishing,likes,views;
        ImageView image;
        private ItemClickListener itemClickListener;
        public MyVideoHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            path = itemView.findViewById(R.id.path);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String xxx=path.getText().toString();
                    Intent intent = new Intent(homePage, GetText.class);
                    intent.putExtra("path",xxx);
                    intent.putExtra("name",title.getText().toString());
                    homePage.startActivity(intent);
                }
            });
            title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    audioPlayer(path.getText().toString(),title.getText().toString());
                    return true;
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
        public void audioPlayer(String path, String fileName){
            //set up MediaPlayer
            MediaPlayer mp = new MediaPlayer();

            try {
                mp.setDataSource(path);
                mp.prepare();
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


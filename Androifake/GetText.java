package com.example.android.greetup;

import android.animation.TypeConverter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.media.AudioFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.greetup.Contacts.ContactList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class GetText extends AppCompatActivity {
    boolean flag = false;
    boolean flag2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_text);
        Button b1 = findViewById(R.id.replace);
        Button b2 = findViewById(R.id.create);
        Button b3 = findViewById(R.id.download);
        Bundle bundle = getIntent().getExtras();
        final String value = bundle.getString("path");
        final String name = bundle.getString("name");
        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                EditText e1 = findViewById(R.id.text1);
                EditText e2 = findViewById(R.id.text2);
                File ffile = new File(value);
                Uri file = Uri.fromFile(ffile);

                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://greetup-6a2a3.appspot.com").child(name);
                storageRef.putFile(file).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("----------------Failed to upload file to cloud storage" + exception);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(GetText.this,
                                "File has been uploaded to cloud storage",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root, "input.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append(name.split("\\.")[0]+"\n");
                    writer.append(e1.getText().toString() + '\n');
                    writer.append(e2.getText().toString() + '\n');
                    writer.flush();
                    writer.close();
                    storageRef = firebaseStorage.getReferenceFromUrl("gs://greetup-6a2a3.appspot.com").child("input.txt");
                    storageRef.putFile(Uri.fromFile(gpxfile)).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(@NonNull Exception exception) {
                            System.out.println("----------------Failed to upload file to cloud storage" + exception);
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(GetText.this,
                                    "File has been uploaded to cloud storage",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                downloadFile();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1 = findViewById(R.id.text1);
                Uri file = Uri.fromFile(new File(value));

                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://greetup-6a2a3.appspot.com").child("output.wav");

                storageRef.putFile(file).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("----------------Failed to upload file to cloud storage" + exception);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(GetText.this,
                                "File has been uploaded to cloud storage",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // downloadFile();
            }
        });
    }

    private class DownLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... strings) {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://greetup-6a2a3.appspot.com");
            File fileNameOnDevice = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + "test2.mp3");
            try {
                fileNameOnDevice.createNewFile();
            } catch (IOException e) {
                System.out.println("------------------------NO");
                e.printStackTrace();
            }
            flag = true;
            flag2 = false;
            while (true) {
                flag = true;
                if (fileNameOnDevice.exists()) {
                    storageRef.child("output.wav").getFile(fileNameOnDevice).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("------------------Downloaded successfully");
                            flag = false;
                            flag2 = true;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            flag = false;
                            System.out.println("----------------Failed to upload file to cloud storage" + exception);
                        }
                    });

                    while (flag == true) try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (flag2 == true)
                        break;
                } else
                    System.out.println("------------File is not available");
            }
            return null;
        }
    }

    private void downloadFile() {
        DownLoad downLoad = new DownLoad();
        downLoad.execute();
    }
    public static File convertMP3toWAV(File mp3){
        File temp=null;
        try {
            temp = File.createTempFile(mp3.getName().split("\\.")[0],".wav");
        //    temp.deleteOnExit();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return temp;
    }
}

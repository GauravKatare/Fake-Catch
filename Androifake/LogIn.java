package com.example.android.greetup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        final EditText user = findViewById(R.id.username);
        final EditText pass = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        Button btnLogIn = findViewById(R.id.login);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=user.getText().toString();
                String password=pass.getText().toString();
                if (!(email.isEmpty() && password.isEmpty())) {firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogIn.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogIn.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(LogIn.this, MainActivity.class));
                        }
                    }
                });
                }
                else
                    System.out.println("---------------Plz put some value");
            }
        });
    }
}

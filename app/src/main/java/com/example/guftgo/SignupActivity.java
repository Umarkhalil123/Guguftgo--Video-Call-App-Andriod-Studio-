package com.example.guftgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailbox,passwordbox,namebox;
    Button loginbtn,signupbtn,accountbtn;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth= FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        emailbox= findViewById(R.id.emailbox);
        namebox= findViewById(R.id.namebox);
        passwordbox= findViewById(R.id.passwordbox);


        loginbtn= findViewById(R.id.loginbtn);
        accountbtn= findViewById(R.id.accountbtn);
        signupbtn= findViewById(R.id.createpbtn);
        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActvity.class));
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name ,pass ,email;
                email=emailbox.getText().toString();
                pass=passwordbox.getText().toString();
                name=namebox.getText().toString();

                com.example.guftgo.User user=new com.example.guftgo.User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name) ;



                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(SignupActivity.this,LoginActvity.class));

                                }
                            });
 //                           Toast.makeText(SignupActivity.this,"Account is Created", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
}
package com.example.logindemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegistrationActivity2 extends AppCompatActivity {

    private EditText userName2, userPassword2, userEmail2,userAge2;
    private Button regButton2;
    private TextView userLogin2;
    private FirebaseAuth firebaseAuth2;
    private ImageView userProfilePic2;
    String email2, name2, age2, password2;
    private FirebaseStorage firebaseStorage2;
    private static int PICK_IMAGE =123;
    Uri imagePath;
    private StorageReference storageReference2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfilePic2.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        setupUIViews();
        firebaseAuth2 = FirebaseAuth.getInstance();
        firebaseStorage2 = FirebaseStorage.getInstance();

        storageReference2 = firebaseStorage2.getReference();

        userProfilePic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*"); //application/pdf or application/doc or application/* for audio audio/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"),PICK_IMAGE);
            }
        });



        regButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    //upload data
                    String user_email2 = userEmail2.getText().toString().trim();
                    String user_password2 = userPassword2.getText().toString().trim();
                    firebaseAuth2.createUserWithEmailAndPassword(user_email2, user_password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //sendEmailVerification();
                                sendUserData();
                                Toast.makeText(RegistrationActivity2.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                firebaseAuth2.signOut();
                                finish();
                                startActivity(new Intent(RegistrationActivity2.this, MainActivity.class));
                            }else{
                                Toast.makeText(RegistrationActivity2.this, "Registration Fail",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        userLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity2.this, MainActivity.class));
            }
        });
    }
    private void setupUIViews(){
        userName2 = (EditText)findViewById(R.id.etUserName2);
        userPassword2 = (EditText)findViewById(R.id.etUserPassword2);
        userEmail2 = (EditText)findViewById(R.id.etUserEmail2);
        regButton2 = (Button) findViewById(R.id.btnRegister2);
        userLogin2 = (TextView)findViewById(R.id.tvUserLogin2);
        userAge2 = (EditText)findViewById(R.id.etAge2);
        userProfilePic2 = (ImageView)findViewById(R.id.ivProfile2);
    }
    private Boolean validate(){
        Boolean result = false;
        name2 = userName2.getText().toString();
        password2= userPassword2.getText().toString();
        email2 = userEmail2.getText().toString();
        age2= userAge2.getText().toString();

        if (name2.isEmpty() || password2.isEmpty() || email2.isEmpty() || age2.isEmpty() || imagePath == null){
            Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();

        }else {
            result = true;
        }
        return result;
    }
    /*private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth2.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(RegistrationActivity2.this,"Successfully Registered, Verification mail sent!",Toast.LENGTH_SHORT).show();
                        firebaseAuth2.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity2.this, MainActivity.class));
                    }else {
                        Toast.makeText(RegistrationActivity2.this, "Verification mail hasn't sent",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth2.getUid());
        StorageReference imageReference = storageReference2.child(firebaseAuth2.getUid()).child("Images").child("Profile Pic");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity2.this,"Upload failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity2.this,"Upload Successful",Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile2 userProfile2 =new UserProfile2(age2, email2, name2);
        myRef.setValue(userProfile2);
    }
}

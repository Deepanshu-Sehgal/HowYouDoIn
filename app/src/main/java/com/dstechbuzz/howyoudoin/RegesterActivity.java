package com.dstechbuzz.howyoudoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegesterActivity extends AppCompatActivity {

    TextInputLayout Name,Email,Password;
    Button SignUp;

    FirebaseAuth mAuth;
    DatabaseReference refrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        Toolbar toolbar=findViewById(R.id.toolBar_reg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        

        Name=findViewById(R.id.UserName_reg);
        Email=findViewById(R.id.email_reg);
        Password=findViewById(R.id.password_reg);
        SignUp=findViewById(R.id.signup_reg);

        mAuth=FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = Name.getEditText().getText().toString();
                String txt_email = Email.getEditText().getText().toString();
                String txt_password = Password.getEditText().getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) ||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegesterActivity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6){
                    Toast.makeText(RegesterActivity.this, "Password Must Be At least 6 characters", Toast.LENGTH_SHORT).show();
                }else {
                    regester(txt_username,txt_email,txt_password);
                }
            }
        });


    }
    private void regester(final String Name, String Email, String Password){
        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser firebaseUser=mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid=firebaseUser.getUid();

                    refrence= FirebaseDatabase.getInstance().getReference("Users").child(userid);


                    HashMap<String,String>hashmap=new HashMap<>();
                    hashmap.put("id",userid);
                    hashmap.put("Name",Name);
                    hashmap.put("imageUrl","default");

                    refrence.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent=new Intent(RegesterActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else {
                    Toast.makeText(RegesterActivity.this, "You can't regester woth this email or password", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
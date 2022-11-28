package com.dllihaart.dillihaart;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> bigTextList = new ArrayList<>();
    private ArrayList<String> detailList = new ArrayList<>();
    private ArrayList<String> imageList = new ArrayList<>();
    private ArrayList<Integer> keyList = new ArrayList<>();

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore firebase;
    ImageView rImage, closeBtn;
    TextView  textViewShowBig, textViewShowSmall;
    String keyOfrecordToFetch, loginEmail,loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        closeBtn = findViewById(R.id.imageView1o1);

        //sign_in_user(loginEmail, loginPassword);

        if (1==1) {
            for (int record =1; record <4; record++) {
                if (record == 1) {
                    keyOfrecordToFetch = "" + record;
                    rImage = findViewById(R.id.imageView1o1);
                    textViewShowBig = findViewById(R.id.textView1o1);
                    textViewShowSmall = findViewById(R.id.textView2o1);
                } else if (record == 2) {
                    keyOfrecordToFetch = "" + record;
                    rImage = findViewById(R.id.imageView1o2);
                    textViewShowBig = findViewById(R.id.textView1o2);
                    textViewShowSmall = findViewById(R.id.textView2o2);
                } else if (record == 3) {
                    keyOfrecordToFetch = "" + record;
                    rImage = findViewById(R.id.imageView1o3);
                    textViewShowBig = findViewById(R.id.textView1o3);
                    textViewShowSmall = findViewById(R.id.textView2o3);
                }
                populateDataCard(keyOfrecordToFetch, rImage, textViewShowBig, textViewShowSmall);
            }
        }else{
            finish();
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });
    }

    private void populateDataCard(String recKey, ImageView imageViewload, TextView textBigShow, TextView textSmallShow) {
        DocumentReference docRef = firebase.collection("DH-AD").document(recKey);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                        textBigShow.setText((document.get("bigText")).toString());
                                        textSmallShow.setText((document.get("smallText")).toString());
                                        String link = (document.get("imageUrl")).toString();
                                        Picasso.get().load(link).into(imageViewload);

                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
    }

    private void sign_in_user(String sign_in_email, String sign_in_password){
            String email = sign_in_email.toString().trim();
            String password = sign_in_password.toString().trim();;
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // sign in success
                            if (task.isSuccessful()) {
                                  mUser = mAuth.getCurrentUser();
                            } else {
                                // if signin fails
                                Toast.makeText(MainActivity.this, "Fatal Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();
                        }
                    });
        mUser = mAuth.getCurrentUser();
    }


}
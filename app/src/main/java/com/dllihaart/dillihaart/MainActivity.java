package com.dllihaart.dillihaart;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> bigTextList = new ArrayList<>();
    private ArrayList<String> smallTextList = new ArrayList<>();
    private ArrayList<String> imageURLList = new ArrayList<>();
    private ArrayList<Integer> keyList = new ArrayList<>();

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore firebase;
    CollectionReference collRef;
    ImageView closeBtn, firstScrImage, picChgImageView;
    TextView  textViewShowBig, textViewShowSmall;
    String keyOfrecordToFetch, loginEmail,loginPassword;
    Button showCuponsBtn;
    ImageButton firstImageBtn;
    final int min = 1;
    final int max = 5;
    int numberOfRecordsToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firstImageBtn = findViewById(R.id.imageButtonFirst);
        showCuponsBtn = findViewById(R.id.buttonShowCupons);
        firstScrImage = findViewById(R.id.imageViewFirst);
        picChgImageView = (ImageView) findViewById(R.id.imageViewFirst);

        // Decide on the picture to show on first screen
        final int random = new Random().nextInt((max - min) + 1) + min;
        switch (random) {
            case 1 : picChgImageView.setImageResource(R.drawable.f1);  break;
            case 2 : picChgImageView.setImageResource(R.drawable.f2);  break;
            case 3 : picChgImageView.setImageResource(R.drawable.f3);  break;
            case 4 : picChgImageView.setImageResource(R.drawable.f4);  break;
            case 5 : picChgImageView.setImageResource(R.drawable.f5);  break;
            default:picChgImageView.setImageResource(R.drawable.dhlogonobkgrnd);
        }

        pouplateDataArrayV2();
        firstImageBtn.setOnClickListener(v -> viewCuponData());
        showCuponsBtn.setOnClickListener(v -> viewCuponData());
        firstScrImage.setOnClickListener(v -> viewCuponData());
    }

       private void viewCuponData()
       {
           //Toast.makeText(MainActivity.this, "Before data Fetch="+ bigTextList.size(),Toast.LENGTH_SHORT).show();
           // Collect Arguments to Share
           Bundle args = new Bundle();
           args.putStringArrayList("BIGTEXT", bigTextList);
           args.putStringArrayList("SMALLTEXT", smallTextList);
           args.putStringArrayList("IMAGEURLLIST",imageURLList);
           // Go to a view and display
           Intent intent = new Intent(MainActivity.this, ViewActivity.class);
           intent.putExtra("BUNDLE", args);
           startActivity(intent);
       }
        private void pouplateDataArrayV1( ){
            bigTextList.clear();
            smallTextList.clear();
            imageURLList.clear();
            int recordToFetch =99;
            for (int record = 1; record <= recordToFetch; record++) {
                keyOfrecordToFetch = "" + record;
                DocumentReference docRef = firebase.collection("DH-AD").document(keyOfrecordToFetch);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
//                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                String link = (document.get("imageUrl")).toString();
                                bigTextList.add(document.get("bigText").toString()) ;
                                smallTextList.add(document.get("smallText").toString()) ;
                                imageURLList.add(link);
//                                Toast.makeText(MainActivity.this, "Inside Array size="+ bigTextList.size(),Toast.LENGTH_SHORT).show();

                            } else {
                                Log.d(TAG, "No such document");

                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

            }
        }

        private void pouplateDataArrayV2( ) {
            bigTextList.clear();
            smallTextList.clear();
            imageURLList.clear();
                firebase.collection("DH-AD")
                        .whereEqualTo("show", true)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String link = (document.get("imageUrl")).toString();
                                        bigTextList.add(document.get("bigText").toString());
                                        smallTextList.add(document.get("smallText").toString());
                                        imageURLList.add(link);
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }

}
package com.dllihaart.dillihaart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private ArrayList<String> bigViewTextList = new ArrayList<>();
    private ArrayList<String> smallViewTextList = new ArrayList<>();
    private ArrayList<String> imageViewURLList = new ArrayList<>();
    private ArrayList<Integer> keyViewList = new ArrayList<>();

    Button showData;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        bigViewTextList = args.getStringArrayList("BIGTEXT");
        smallViewTextList = args.getStringArrayList("SMALLTEXT");
        imageViewURLList =args.getStringArrayList("IMAGEURLLIST");

        recyclerView = findViewById(R.id.dhRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewActivity.this));

        adapter = new RecyclerAdapter(bigViewTextList, smallViewTextList,imageViewURLList, ViewActivity.this  );
        recyclerView.setAdapter(adapter);
    }
}
package com.dllihaart.dillihaart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.dhcardViewHolder> {

    private ArrayList<String> firstTextList;
    private ArrayList<String> secondTextList;
    private ArrayList<String> imagePathList;
    private ArrayList<Integer> KEY;
    private Context context;
    private String imagePath;

    public RecyclerAdapter(ArrayList<String> inputFirstTextList, ArrayList<String> inputSecondTextList, ArrayList<String> imageList, Context context) {
        this.firstTextList = inputFirstTextList;
        this.secondTextList = inputSecondTextList;
        this.imagePathList = imageList;
        this.context = context;
        //this.KEY = inKey;
    }

    @NonNull
    @Override
    public dhcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design,parent,false);
        return new dhcardViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull dhcardViewHolder holder, int position) {
        int clickposition= holder.getAdapterPosition();
        holder.textBig.setText(firstTextList.get(clickposition));
        holder.textSmall.setText(secondTextList.get(clickposition));
        String link = imagePathList.get(clickposition);
        Picasso.get().load(link).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return firstTextList.size();
    }

    public class dhcardViewHolder extends RecyclerView.ViewHolder {
        private TextView textBig, textSmall;
        private ImageView imageView;
        private CardView cardView;

        public dhcardViewHolder(@NonNull View itemView) {
            super(itemView);

            textBig = itemView.findViewById(R.id.textViewB);
            textSmall = itemView.findViewById(R.id.textViewS);
            imageView = itemView.findViewById(R.id.imageViewAd);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     // int pid = android.os.Process.myPid();
                    // android.os.Process.killProcess(pid);
                    Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dillihaart.com/"));
                    context.startActivity(Getintent);

                }
            });


        }
    }
}

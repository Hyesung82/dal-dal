package com.example.dalyeodalyeok.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import com.example.dalyeodalyeok.R;

public class BackgroundFragment extends Fragment {

    private final int GET_GALLERY_IMAGE = 200;
    private final int RESULT_OK = -1;
    private ImageView imageview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_background, contatiner, false);

        imageview = (ImageView)root.findViewById(R.id.imageView);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

        Uri selectedImageUri = data.getData();
        imageview.setImageURI(selectedImageUri);

    }

}
}

package com.test.simerrefress.imageCompration;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.simerrefress.R;

import java.io.File;

public class ImageComprassActivity extends AppCompatActivity {

    String selectedImagePath;
    ImageView ivImage;
    private static final String TAG=ImageComprassActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_comprass);

        ivImage = findViewById(R.id.iv_img);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 12) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);
                try {

                    if (selectedImagePath != null) {

                        File image = new File(selectedImagePath);
                        Log.e(TAG, "onActivityResult: image size "+image.length()/1024);
                        if ((image.length()/1024) > 500) {
                            String newImg = CommonUtils.compressImage(selectedImagePath);
                            Toast.makeText(this, "path : " + newImg, Toast.LENGTH_SHORT).show();
                            onShowImage(newImg);
                        } else {
                            Toast.makeText(this, "pellathij compress che", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "path : namadyo", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onShowImage(String path) {
        File imgFile = new File(path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivImage.setImageBitmap(myBitmap);

        }
    }

    public void onSelectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12);
    }
}

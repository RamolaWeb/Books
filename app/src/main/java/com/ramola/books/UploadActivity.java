package com.ramola.books;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class UploadActivity extends AppCompatActivity {
ImageView close,photo,location,send;
    private static final int PICK_IMAGE_REQUEST =1 ;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        close= (ImageView) findViewById(R.id.close_image);
        photo= (ImageView) findViewById(R.id.image_button_upload);
        location= (ImageView) findViewById(R.id.location_button_upload);
        send= (ImageView) findViewById(R.id.send_button_upload);
        editText= (EditText) findViewById(R.id.description_edittext_upload);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createchooser();
            }
        });
    }
    private void createchooser(){
        Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "CHOOSE PHOTO"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(filePath, filePathColumn, null, null, null);
            c.moveToFirst();
            String imgDecodableString = c.getString(c.getColumnIndex(filePathColumn[0]));
            c.close();
            Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
            Drawable image=new BitmapDrawable(getResources(),bitmap);
            image.setBounds(0,0,400,400);
            editText.setCompoundDrawables(null,null,null,image);
        }
    }
}

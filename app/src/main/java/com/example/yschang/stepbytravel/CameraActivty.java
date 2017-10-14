package com.example.yschang.stepbytravel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CameraActivty extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    static final int REQUEST_IMAGE_CROP = 3;

    Button btn_takePicture, btn_getAlbum;
    ImageView iv_capture;
    String mCurrentPhotoPath;
    Uri photoURI, albumURI = null;
    Boolean album = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        iv_capture = (ImageView) findViewById(R.id.iv_capture);
        btn_takePicture = (Button) findViewById(R.id.btn_takePicture);
        btn_getAlbum = (Button) findViewById(R.id.btn_getAlbum);
        btn_takePicture.setOnClickListener((View.OnClickListener) this);
        btn_getAlbum.setOnClickListener((View.OnClickListener) this);



    }

    // 사진찍기
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(); // 사진찍은 후 저장할 임시 파일
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "createImageFile Failed", Toast.LENGTH_LONG).show();
            }

            if (photoFile != null) {
                photoURI = Uri.fromFile(photoFile); // 임시 파일의 위치,경로 가져옴
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI); // 임시 파일 위치에 저장
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // 저장할 폴더 생성
    private File createImageFile() throws IOException {
        /* Create an image file name, 폴더명 지정 방법 (문제 : DIRECTORY_DCIM , DIRECTORY_PICTURE 경로가 없는 폰 존재)
        String imageFileName = "tmp_" + String.valueOf(System.currentTimeMillis());
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "MYAPP/");
        File file = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = file.getAbsolutePath();
        return file;
        */

        // 특정 경로와 폴더를 지정하지 않고, 메모리 최상 위치에 저장 방법
        String imageFileName = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory(), imageFileName);
        mCurrentPhotoPath = storageDir.getAbsolutePath();
        return storageDir;
    }

    private void doTakeAlbumAction()
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void cropImage() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(photoURI, "image/*");
        cropIntent.putExtra("outputX", 240); // crop한 이미지의 x축 크기 240
        cropIntent.putExtra("outputY", 320); // crop한 이미지의 y축 크기 320
        //cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율
        //cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);

        if(album == false) {
            cropIntent.putExtra("output", photoURI); // 크랍된 이미지를 해당 경로에 저장
        } else if(album == true){
            cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
        }

        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    // ActivityResult = 가져온 사진 뿌리기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            Toast.makeText(getApplicationContext(), "onActivityResult : RESULT_NOT_OK", Toast.LENGTH_LONG).show();
        } else {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: // 앨범 이미지 가져오기
                    album = true;
                    File albumFile = null;
                    try {
                        albumFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(albumFile != null){
                        albumURI = Uri.fromFile(albumFile); // 앨범 이미지 Crop한 결과는 새로운 위치 저장
                    }

                    photoURI = data.getData(); // 앨범 이미지의 경로

                    /* iv_capture 에 띄우기
                    Bitmap image_bitmap 	= null;
                    try {
                        image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_capture.setImageBitmap(image_bitmap);
                    */

                    // break; REQUEST_IMAGE_CAPTURE로 전달하여 Crop
                case REQUEST_IMAGE_CAPTURE:
                    cropImage();

                    break;
                case REQUEST_IMAGE_CROP:

                    Bitmap photo = BitmapFactory.decodeFile(photoURI.getPath());
                    iv_capture.setImageBitmap(photo);

                    Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE ); // 동기화
                    if(album == false) {
                        mediaScanIntent.setData(photoURI); // 동기화
                    } else if(album == true){
                        album = false;
                        mediaScanIntent.setData(albumURI); // 동기화
                    }
                    this.sendBroadcast(mediaScanIntent); // 동기화

                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.btn_takePicture):
                dispatchTakePictureIntent();
                break;
            case (R.id.btn_getAlbum):
                doTakeAlbumAction();
                break;
        }
    }




}

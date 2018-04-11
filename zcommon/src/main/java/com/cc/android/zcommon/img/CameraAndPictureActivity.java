package com.cc.android.zcommon.img;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cc.android.zcommon.utils.android.BitmapUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Camera and picture activity
 *
 *
 * @Author:LiuLiWei
 */
public class CameraAndPictureActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = CameraAndPictureActivity.class.getSimpleName();

    private static final String PHOTO_SUFFIX = ".png";


    private static final int PERMISSIONS_REQUEST_PHOTO = 0x01;
    private static final int PERMISSIONS_REQUEST_FILE = 0x02;
    private static final int REQUEST_CODE_TAKING_PHOTO = 0x03;
    private static final int REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL = 0x04;
    private static final int REQUEST_CODE_CUT_PHOTO = 0x05;

    private static final int TARGET_HEAD_SIZE = 1000;
    private static final String IMAGE_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/rainbow/photo";
    private  String IMAGE_SAVE_PATH = IMAGE_SAVE_DIR + "/photo" + PHOTO_SUFFIX;
    public static final int TYPE_CHOOSING_IMG = 0x06;
    public static final int TYPE_CHOOSING_IMG_AND_CROP = 0x07;
    public static final int TYPE_TAKING_PHOTO = 0x08;
    public static final int TYPE_TAKING_PHOTO_AND_CROP = 0x09;

    private String mPath;
    private boolean isTakePhoto = false;
    private boolean isGetPic = false;
    private boolean isCrop = false;
    private Uri mUri;

    /**
     *
     * @param activity
     * @param type
     * @return
     */
    public static Intent newIntent(Activity activity,int type) {
        return newIntent(activity, type, "");
    }

    /**
     *
     * @param activity
     * @param type
     * @param photoName
     * @return
     */
    public static Intent newIntent(Activity activity,int type, String photoName) {
        Intent intent = new Intent(activity, CameraAndPictureActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("photoName", photoName);
        return intent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Path", mPath);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FrameLayout view = new FrameLayout(this);
        setContentView(view);
        view.setOnClickListener(v -> finish());
        init();
        if (savedInstanceState != null) mPath = savedInstanceState.getString("Path");
    }

    private void init() {
        final String photoName = getIntent().getStringExtra("photoName");
        if(!TextUtils.isEmpty(photoName)) {
            IMAGE_SAVE_PATH = IMAGE_SAVE_DIR + "/" + photoName + PHOTO_SUFFIX;
        }
        final int type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case TYPE_CHOOSING_IMG:
                operChoosePic();
                break;
            case TYPE_CHOOSING_IMG_AND_CROP:
                isCrop = true;
                operChoosePic();
                break;
            case TYPE_TAKING_PHOTO:
                operTakePhoto();
                break;
            case TYPE_TAKING_PHOTO_AND_CROP:
                isCrop = true;
                operTakePhoto();
                break;
            default:
                Log.d(TAG, "operation type " + type + "is not valid.");
                finish();
                break;
        }

        File file = new File(IMAGE_SAVE_DIR);
        if (!file.exists()) file.mkdirs();
    }

    /**
     * Take photo.
     */
    private void operTakePhoto() {
        isTakePhoto = true;
        isGetPic = false;
        if (ContextCompat.checkSelfPermission(CameraAndPictureActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CameraAndPictureActivity.this, Manifest.permission.CAMERA)){
                showPhotoPerDialog();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(CameraAndPictureActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showPhotoPerDialog();
            } else {
                ActivityCompat.requestPermissions(CameraAndPictureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO);
            }
        } else {
            takePhoto();
        }
    }

    /**
     * Choose pic.
     */
    private void operChoosePic() {
        isTakePhoto = false;
        isGetPic = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                showFilePerDialog();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_FILE);
        } else getPictureFromLocal();
    }

    /**
     * Show camera permission.
     */
    private void showPhotoPerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("系统需要获取您的相机访问权限和文件读写权限，以确保您可以正常拍照。")
                .setPositiveButton("确定", (dialog, which) -> ActivityCompat.requestPermissions(CameraAndPictureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHOTO)).create().show();
    }

    /**
     * Show file permission.
     */
    private void showFilePerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("系统需要获取存储文件权限，以确保可以正常保存拍摄或选取图片。")
                .setPositiveButton("确定", (dialog, which) -> ActivityCompat.requestPermissions(CameraAndPictureActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, PERMISSIONS_REQUEST_FILE)).create().show();
    }

    /**
     * Take photo.
     */
    private void takePhoto() {

        String mUUID = UUID.randomUUID().toString();
        Intent intent = new Intent();

        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        mPath = Environment.getExternalStorageDirectory().getPath() + "/ccAndroid" + mUUID;
        File file = new File(mPath + PHOTO_SUFFIX);

        //Android N.
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,  getPackageName() + ".provider", file)); //Uri.fromFile(tempFile)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }

        startActivityForResult(intent, REQUEST_CODE_TAKING_PHOTO);
    }

    /**
     * Get picture from local.
     */
    private void getPictureFromLocal() {
        Intent innerIntent = new Intent(Intent.ACTION_PICK);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL);
    }

    /**
     * Picture crop.
     * @param uri
     * @param size
     */
    private void startPhotoZoom(Uri uri, int size) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);

            // outputX,outputY 是剪裁图片的宽高
            intent.putExtra("outputX", size);
            intent.putExtra("outputY", size);
            //   intent.putExtra("return-data", true);
            mUri = Uri.parse("file:///" + IMAGE_SAVE_PATH);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, REQUEST_CODE_CUT_PHOTO);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * content:// uri
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * Deal take photo without zoom.
     */
    private void dealTakePhotoWithoutZoom() {
        String finalPath = mPath + PHOTO_SUFFIX;
        Bitmap bitmap = BitmapFactory.decodeFile(finalPath);
        if (bitmap != null) {
            boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
            if (b) {
                Intent intent = new Intent();
                intent.putExtra("path", IMAGE_SAVE_PATH);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    /**
     * Deal take photo then zoom.
     */
    private void dealTakePhotoThenZoom() {
        File file = new File(mPath + PHOTO_SUFFIX);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            startPhotoZoom(getImageContentUri(file), TARGET_HEAD_SIZE);
        } else {
            startPhotoZoom(Uri.fromFile(file), TARGET_HEAD_SIZE);
        }
    }

    /**
     * Deal choose photo without zoom.
     * @param data
     */
    private void dealChoosePhotoWithoutZoom(Intent data) {
        Uri uri = data.getData();
        if (uri != null) {
            Bitmap bitmap = BitmapUtils.uriToBitmap(this, uri);
            if (bitmap != null) {
                boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                if (b) {
                    Intent intent = new Intent();
                    intent.putExtra("path", IMAGE_SAVE_PATH);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    /**
     * Deal choose photo then zoom.
     * @param data
     */
    private void dealChoosePhotoThenZoom(Intent data) {
        Uri uri = data.getData();
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
            }
            File file = new File(IMAGE_SAVE_PATH);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                startPhotoZoom(getImageContentUri(file), TARGET_HEAD_SIZE);
            } else {
                startPhotoZoom(Uri.fromFile(file), TARGET_HEAD_SIZE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deal photo zooming result.
     */
    private void dealZoomPhoto() {
        try {
            if (mUri != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUri));
                if (bitmap != null) {
                    boolean b = BitmapUtils.compressBitmap2file(bitmap, IMAGE_SAVE_PATH);
                    if (b) {
                        Intent intent = new Intent();
                        intent.putExtra("path", IMAGE_SAVE_PATH);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_PHOTO: {
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (isTakePhoto) takePhoto();
                    if (isGetPic) getPictureFromLocal();
                } else {
                    finish();
                }
            }
            case PERMISSIONS_REQUEST_FILE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    dealZoomPhoto();
                else
                    finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult resultCode:" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKING_PHOTO:
                    if (isCrop) dealTakePhotoThenZoom();
                    else dealTakePhotoWithoutZoom();
                    break;
                case REQUEST_CODE_SELECT_PHOTO_FROM_LOCAL:
                    if (isCrop) dealChoosePhotoThenZoom(data);
                    else dealChoosePhotoWithoutZoom(data);
                    break;
                case REQUEST_CODE_CUT_PHOTO:
                    dealZoomPhoto();
                    break;
            }
        } else {
            finish();
        }
    }
}

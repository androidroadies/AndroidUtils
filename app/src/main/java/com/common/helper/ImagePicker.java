package com.common.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;


import com.common.utils.BuildConfig;
import com.common.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class ImagePicker {

    private static final int DEFAULT_MIN_WIDTH_QUALITY = 400;        // min pixels
    private static final String TAG = "ImagePicker";
    private static final String TEMP_IMAGE_NAME = "tempImage";
    public static final String EXTRA_FILE_URI = "EXTRA_FILE_URI";
    public static final String EXTRA_FILE = "EXTRA_FILE";

    public static final String EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI";
    public static final String EXTRA_CAPTURE_URI = "EXTRA_CAPTURE_URI";
    public static final String EXTRA_VIDEO_URI = "EXTRA_VIDEO_URI";

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Deals";

    private static int minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY;

    public static  Intent getImageIntent(Context context){
        Uri fileURI = null;
        try {
            fileURI = Uri.fromFile(createImageFile(context, MEDIA_TYPE_IMAGE));
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                    fileURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setType("image/*");
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static  Intent captureIntent(Context context){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileURI = null;
        try {
            fileURI = getOutputMediaFileUri(context, MEDIA_TYPE_IMAGE);
            //Uri fileURI = Uri.fromFile(createImageFile(context, MEDIA_TYPE_IMAGE));
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileURI);
                takePictureIntent.putExtra(EXTRA_FILE_URI, String.valueOf(fileURI));
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            return takePictureIntent;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static  Intent getCaptureIntent(Context context){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            File photoFile = null;
            try {
                photoFile = createImageFile(context, MEDIA_TYPE_IMAGE);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }

            if (photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(context,
                        context.getPackageName() + ".provider",
                        photoFile);
                takePictureIntent.putExtra(EXTRA_FILE_URI, photoURI);
                takePictureIntent.putExtra(EXTRA_FILE, photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
        }
        return takePictureIntent;
    }

    public static  Intent getVideoIntent(Context context){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            File photoFile = null;
            try {
                photoFile = createImageFile(context, MEDIA_TYPE_VIDEO);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }

            if (photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(context,
                        context.getPackageName() + ".provider",
                        photoFile);
                // set video quality
                // 1 : for high quality video
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                intent.putExtra(EXTRA_FILE_URI, photoURI);
                intent.putExtra(EXTRA_FILE, photoFile);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 36d);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
        }
        return intent;
    }

    /*public static  Intent getVideoIntent(Context context) throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Uri fileURI = Uri.fromFile(createImageFile(context, MEDIA_TYPE_VIDEO));

        // set video quality
        // 1 : for high quality video
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, String.valueOf(fileURI));
        intent.putExtra(EXTRA_FILE_URI, String.valueOf(fileURI));
        return intent;
    }*/

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
            Log.d(TAG, "Intent: " + intent.getAction() + " package: " + packageName);
        }
        return list;
    }


    /*public static Bitmap getImageFromResult(Context context, Intent returnIntent, Uri fileURI) {
        Bitmap bm = null;

        File imageFile = new File(fileURI.getPath());

        Uri selectedImage;

        boolean isCamera = (returnIntent == null ||
                returnIntent.getData() == null  ||
                returnIntent.getData().toString().contains(imageFile.toString()));

        if (isCamera) {     *//** CAMERA **//*
            selectedImage = fileURI;
        } else {            *//** GALLERY **//*
            selectedImage = returnIntent.getData();
        }
        Log.d(TAG, "selectedImage: " + selectedImage);

        try {
            bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //bm = getImageResized(context, selectedImage);
        //int rotation = getRotation(context, selectedImage, isCamera);
        //bm = rotate(bm, rotation);
        return bm;
    }*/

    public static File getImageFromResult(Context context, Intent returnIntent, Uri fileURI) {
        Bitmap bm = null;

        File selectedImage;

        boolean isCamera = (returnIntent == null ||
                returnIntent.getData() == null);

        if (isCamera) {     /** CAMERA **/
            selectedImage = FileUtils.getFile(context, fileURI);
        } else {            /** GALLERY **/
            selectedImage = FileUtils.getFile(context, returnIntent.getData());
        }
        Log.d(TAG, "selectedImage: " + selectedImage);

        /*try {
            bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //bm = getImageResized(context, selectedImage);
        //int rotation = getRotation(context, selectedImage, isCamera);
        //bm = rotate(bm, rotation);
        return selectedImage;
    }


    /*private static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), TEMP_IMAGE_NAME);
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }*/

    /**
     * Creating file uri to store image/video
     */
    public static Uri getOutputMediaFileUri(Context context, int type) throws IOException {
        Uri fileURI;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            // Nougat or higher
            fileURI = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    createImageFile(context, type));
        } else{
            // do something for phones running an SDK before Marshmallow
            fileURI = Uri.fromFile(createImageFile(context, type));
        }
        return fileURI;
    }

    public static Uri getFileProviderUri(Context context, File file){
        Uri fileURI;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            // Nougat or higher
            fileURI = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
        } else{
            // do something for phones running an SDK before Marshmallow
            fileURI = Uri.fromFile(file);
        }
        return fileURI;
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(Context context, int type) {

        // External sdcard location
//        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//                IMAGE_DIRECTORY_NAME);

        File mediaStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("ERROR", "Oops! Failed create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private static File createImageFile(Context context, int type) throws IOException {
        String FILE_EXT = type == MEDIA_TYPE_IMAGE ? ".jpeg" : ".mp4";
        String FILE_PREFIX = type == MEDIA_TYPE_IMAGE ? "IMAGE_" : "VIDEO_";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = FILE_PREFIX + timeStamp;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //File.createTempFile()
        return File.createTempFile(
                imageFileName,   //prefix
                FILE_EXT,          //suffix
                storageDir       //directory
        );
    }

    private static Bitmap decodeBitmap(Context context, Uri theUri, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(theUri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(), null, options);

        Log.d(TAG, options.inSampleSize + " sample method bitmap ... " +
                actuallyUsableBitmap.getWidth() + " " + actuallyUsableBitmap.getHeight());

        return actuallyUsableBitmap;
    }

    /**
     * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
     **/
    public static Bitmap getImageResized(Context context, Uri selectedImage) {
        Bitmap bm = null;
        int[] sampleSizes = new int[]{5, 3, 2, 1};
        int i = 0;
        do {
            bm = decodeBitmap(context, selectedImage, sampleSizes[i]);
            Log.d(TAG, "resizer: new bitmap width = " + bm.getWidth());
            i++;
        } while (bm.getWidth() < minWidthQuality && i < sampleSizes.length);
        return bm;
    }


    private static int getRotation(Context context, Uri imageUri, boolean isCamera) {
        int rotation;
        if (isCamera) {
            rotation = getRotationFromCamera(context, imageUri);
        } else {
            rotation = getRotationFromGallery(context, imageUri);
        }
        Log.d(TAG, "Image rotation: " + rotation);
        return rotation;
    }

    private static int getRotationFromCamera(Context context, Uri imageFile) {
        int rotate = 0;
        try {

            context.getContentResolver().notifyChange(imageFile, null);
            ExifInterface exif = new ExifInterface(imageFile.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static int getRotationFromGallery(Context context, Uri imageUri) {
        int result = 0;
        String[] columns = {MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
                result = cursor.getInt(orientationColumnIndex);
            }
        } catch (Exception e) {
            //Do nothing
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }//End of try-catch block
        return result;
    }


    private static Bitmap rotate(Bitmap bm, int rotation) {
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap bmOut = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            return bmOut;
        }
        return bm;
    }
}
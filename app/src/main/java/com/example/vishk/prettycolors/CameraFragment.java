package com.example.vishk.prettycolors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */

public class CameraFragment extends Fragment implements View.OnTouchListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mCameraImage;
    private TextView mHexResult;
    private View mColorView1, mColorView2, mColorView3;
    private Bitmap mImageBitmap;
    private Button saveButton;
    private float[] hsv1, hsv2, hsv3;
    public PaletteItem palette;

    public CameraFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        mCameraImage = v.findViewById(R.id.cameraImage);

        mHexResult = v.findViewById(R.id.hexResult);
        mColorView1 = v.findViewById(R.id.colorView1);
        mColorView2 = v.findViewById(R.id.colorView2);
        mColorView3 = v.findViewById(R.id.colorView3);
        saveButton = v.findViewById(R.id.saveColors);

        mCameraImage.setDrawingCacheEnabled(true);
        mCameraImage.buildDrawingCache(true);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                palette = new PaletteItem(hsv1, hsv2, hsv3, "Untitled");
//                palette = new PaletteItem("camera dummy", "Vish");
                ColorsFragment.addPalette(palette);
            }
        });

        mCameraImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    mImageBitmap = mCameraImage.getDrawingCache();

                    int pixel = mImageBitmap.getPixel((int) event.getX(), (int) event.getY());

                    //getting RGB values
                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    hsv1 = new float[3];
                    hsv2 = new float[3];
                    hsv3 = new float[3];
                    Color.RGBToHSV(r, g, b, hsv1);
                    Color.RGBToHSV(r, g, b, hsv2);
                    Color.RGBToHSV(r, g, b, hsv3);

                    hsv2[0] = (hsv2[0] + 120) % 360;
                    hsv3[0] = (hsv3[0] + 240) % 360;

                    //getting HEX value
                    String hex = "\nHEX: #" + Integer.toHexString(pixel);

                    mHexResult.setText("RGB: " + r + ", " + g + ", " + b + hex);
                    mColorView1.setBackgroundColor(Color.HSVToColor(hsv1));
                    mColorView2.setBackgroundColor(Color.HSVToColor(hsv2));
                    mColorView3.setBackgroundColor(Color.HSVToColor(hsv3));

                }
                return true;
            }
        });

        dispatchTakePictureIntent();
        return v;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            /*Uri imageUri = data.getData();
            String path = imageUri.getPath(); */
            Bundle extras = data.getExtras();
            mImageBitmap = (Bitmap) extras.get("data");
            /*ExifInterface ei = null;
            try {
                ei = new ExifInterface(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);*/

/*            Bitmap rotatedBitmap;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(imageBitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(imageBitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(imageBitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = imageBitmap;
            }*/
            Bitmap rotatedBitmap = rotateImage(mImageBitmap, 90);
            mCameraImage.setImageBitmap(rotatedBitmap);
            int height = rotatedBitmap.getHeight();
            int width = rotatedBitmap.getWidth();
            int centerPixel = rotatedBitmap.getPixel(width / 2, height / 2);
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}

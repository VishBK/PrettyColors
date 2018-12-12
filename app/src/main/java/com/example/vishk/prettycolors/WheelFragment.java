package com.example.vishk.prettycolors;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WheelFragment extends Fragment implements View.OnTouchListener {

    private ImageView mImageView;
    private TextView mHexResult;
    private View mColorView1, mColorView2, mColorView3;
    private Bitmap bitmap;
    private Button saveButton;
    private float[] hsv1, hsv2, hsv3;
    public PaletteItem paletteItem;

    public WheelFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wheel, container, false);

        mImageView = v.findViewById(R.id.imageView);
        mHexResult = v.findViewById(R.id.mHexResult);
        mColorView1 = v.findViewById(R.id.colorView1);
        mColorView2 = v.findViewById(R.id.colorView2);
        mColorView3 = v.findViewById(R.id.colorView3);
        saveButton = v.findViewById(R.id.saveColors);

        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache(true);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                paletteItem = new PaletteItem("Hi", "bye");
                ColorsFragment.addPalette(paletteItem);
            }
        });

        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    bitmap = mImageView.getDrawingCache();

                    int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

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

        return v;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}

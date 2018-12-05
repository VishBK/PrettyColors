package com.example.vishk.prettycolors;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
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

    private final SurfaceHolder mHolder;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public WheelFragment() {
        // Required empty public constructor
        surfaceHolder = getHolder();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
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

        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache(true);

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

                    float[] hsv = new float[3];
                    Color.RGBToHSV(r, g, b, hsv);

                    //getting HEX value
                    String hex = "\nHEX: #" + Integer.toHexString(pixel);

                    mHexResult.setText("RGB: " + r + ", " + g + ", " + b + hex);
                    mColorView1.setBackgroundColor(Color.rgb(r, g, b));
                    hsv[0] = (hsv[0] + 120) % 360;
                    mColorView2.setBackgroundColor(Color.HSVToColor(hsv));
                    hsv[0] = (hsv[0] + 120) % 360;
                    mColorView3.setBackgroundColor(Color.HSVToColor(hsv));\

                    Canvas canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.BLACK);
                    canvas.drawCircle(event.getX(), event.getY(), 50, paint);
                    surfaceHolder.unlockCanvasAndPost(canvas);
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

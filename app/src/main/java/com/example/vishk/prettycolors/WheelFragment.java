package com.example.vishk.prettycolors;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;



/**
 * A simple {@link Fragment} subclass.
 */
public class WheelFragment extends Fragment implements View.OnTouchListener {

    private ImageView mImageView;
    private TextView colorName, mHexValue;
    private EditText mEditR, mEditG, mEditB;
    private View mColorView1, mColorView2, mColorView3;
    private Bitmap bitmap;
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
        mHexValue = v.findViewById(R.id.hexVal);
        colorName = v.findViewById(R.id.colorName);
        mEditR = v.findViewById(R.id.editR);
        mEditG = v.findViewById(R.id.editG);
        mEditB = v.findViewById(R.id.editB);
        mColorView1 = v.findViewById(R.id.colorView1);
        mColorView2 = v.findViewById(R.id.colorView2);
        mColorView3 = v.findViewById(R.id.colorView3);
        Button saveButton = v.findViewById(R.id.saveColors);

        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache(true);
        mHexValue.setTextIsSelectable(true);

        //Hitting save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int r = Integer.parseInt(mEditR.getText().toString());
                    int g = Integer.parseInt(mEditG.getText().toString());
                    int b = Integer.parseInt(mEditB.getText().toString());

                    if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0) {
                        Toast.makeText(getActivity(), "Invalid value", Toast.LENGTH_SHORT).show();
                    } else {
                        String hex = setColors(r, g, b);

                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        String url = "https://api.color.pizza/v1/" + hex;

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        colorName.setText(parse(response.toString()));

                                        paletteItem = new PaletteItem(hsv1, hsv2, hsv3, colorName.getText().toString());
                                        ColorsFragment.addPalette(paletteItem);
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String errorText = "Name not found";
                                        colorName.setText(errorText);
                                    }
                                });

                        // Access the RequestQueue through your singleton class.
                        queue.add(jsonObjectRequest);

                        colorName.setTextColor(Color.HSVToColor(hsv1));
                    }
                } catch (NumberFormatException e) {
                    Log.e("Num", "Not a valid number");
                }
            }
        });

        //Touching color wheel
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    bitmap = mImageView.getDrawingCache();
                    try {
                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());
                        setColors(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
                    } catch (IllegalArgumentException e) {
                        Log.e("Bounds", "Touched outside of color wheel");
                    }
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

    /**
     * Calculates palette colors and sets red, green, and blue colors to editTexts and colorViews
     * @param r Red value
     * @param g Green value
     * @param b Green value
     * @return Hex value
     */
    private String setColors(int r, int g, int b) {
        hsv1 = new float[3];
        hsv2 = new float[3];
        hsv3 = new float[3];
        Color.RGBToHSV(r, g, b, hsv1);
        Color.RGBToHSV(r, g, b, hsv2);
        Color.RGBToHSV(r, g, b, hsv3);

        hsv2[0] = (hsv2[0] + 120) % 360;
        hsv3[0] = (hsv3[0] + 240) % 360;

        //Getting the hex value
        String hex = String.format("%02x%02x%02x", r, g, b);
        String displayHex = "HEX: #" + hex;

        mHexValue.setText(displayHex);
        mEditR.setText("" + r);
        mEditG.setText("" + g);
        mEditB.setText("" + b);

        colorName.setTextColor(Color.HSVToColor(hsv1));
        mColorView1.setBackgroundColor(Color.HSVToColor(hsv1));
        mColorView2.setBackgroundColor(Color.HSVToColor(hsv2));
        mColorView3.setBackgroundColor(Color.HSVToColor(hsv3));

        return hex;
    }

    public String parse(String jsonLine) {
        JsonElement jelement = new JsonParser().parse(jsonLine);
        JsonObject  jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray("colors");
        jobject = jarray.get(0).getAsJsonObject();
        String result = jobject.get("name").getAsString();
        return result;
    }
}

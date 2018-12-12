package com.example.vishk.prettycolors;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private Exception exception;
    private ProgressBar progressBar;

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }

    protected void onPreExecute() {
        //progressBar.setVisibility(View.VISIBLE);
        //responseView.setText("");
    }

    protected String doInBackground(String hex, Void... urls) {
        //String email = emailText.getText().toString();
        // Do some validation here

        try {
            URL url = new URL("https://api.color.pizza/v1/" + hex);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally {
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERRORPLEASE", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);
        //responseView.setText(response);
    }
}

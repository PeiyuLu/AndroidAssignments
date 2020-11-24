package com.code.wlu.peiyu.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class WeatherForecast extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "WeatherForecast";
    ProgressBar progressBar;
    ImageView imageView;
    TextView current_temp;
    TextView min_temp;
    TextView max_temp;
    TextView cityName;
    List<String> cityList;
//    ProgressBar progressBar;
//    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        current_temp = (TextView)findViewById(R.id.current_temp);
        min_temp = (TextView)findViewById(R.id.min_temp);
        max_temp = (TextView)findViewById(R.id.max_temp);
        imageView = findViewById(R.id.imageView);
        cityName = findViewById(R.id.cityName);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        get_a_city();
    }

    public void get_a_city() {
        // Get the list of cities.
        cityList = Arrays.asList(getResources().getStringArray(R.array.cities));
        // Make a handler for the city list.
        final Spinner citySpinner = findViewById(R.id.citySpinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  android.R.layout.simple_spinner_item
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView,
                                       View view, int i, long l) {
                ForecastQuery f = new ForecastQuery(cityList.get(i));//.execute();
                f.execute();
                cityName.setText(cityList.get(i)+" weather");
            }
            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {
            }
        });
    }



    //@SuppressLint("StaticFieldLeak")
    @SuppressLint("StaticFieldLeak")
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        //min, max, and current temperature

        private String min, max, current;
        private Bitmap picture;

        protected String city;
        @SuppressWarnings("deprecation")
        ForecastQuery(String city) {
            this.city = city;
        }



        //ForecastQuery fcastQuery = new ForecastQuery(); fcastQuery.execute();
        @Override
        protected void onPreExecute() {

            //Log.d("sn", "00000");
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }
        @Override
        protected String doInBackground(String... strings) {
            //URL url;
            System.out.println("Enter or not?");
            try {
                //url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                //url = new URL ("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=ef63f21ec460bc15add9521acd165a4a&mode=xml&units=metrichttp://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=ef63f21ec460bc15add9521acd165a4a&mode=xml&units=metric");
                //url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric");
//                //System.out.println(url);
                InputStream in = downloadUrl("https://api.openweathermap.org/" + "data/2.5/weather?q=" + this.city + ",ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric");

                try{
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(
                            XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);
                    //parser.nextTag();
                    //return readFeed(parser);
                    //int type;
                    //While you're not at the end of the document:
                    while ((parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("temperature")) {
                                Log.i(ACTIVITY_NAME,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                current = parser.getAttributeValue(null, "value");
                                Log.i(ACTIVITY_NAME,"11111111111111111111111");
                                publishProgress(25);
                                min = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                max = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";
                                Log.i(ACTIVITY_NAME, "Looking for file: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME, "Found the file locally");
                                    picture = BitmapFactory.decodeStream(fis);
                                } else {
                                    String iconUrl =
                                            "https://openweathermap.org/img/w/" + fileName;
                                    picture = getImage(new URL(iconUrl));
                                    FileOutputStream outputStream =
                                            openFileOutput(fileName, Context.MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG, 80,
                                            outputStream);
                                    Log.i(ACTIVITY_NAME,
                                            "Downloaded from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                        }
                        // Go to the next XML event
                        parser.next();
                    }
                } finally {
                    //conn.disconnect();
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return "";
        }
        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            return conn.getInputStream();
        }
        public void parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
//                parser.nextTag();
//                return readFeed(parser);
            } finally {
                in.close();
            }
            //return ;
        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        //@SuppressLint("SetTextI18n")
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String a) {
            super.onPostExecute(a);
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(picture);
            current_temp.setText(current + "\u00b0C");
            min_temp.setText(min + "\u00b0C");
            max_temp.setText(max + "\u00b0C");
        }

        protected void onProgressUpdate(Integer ...current){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(current[0]);
        }
    }
}

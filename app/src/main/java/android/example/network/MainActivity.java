package android.example.network;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   RecyclerView recyclerView;
   RecyclerView.Adapter adapter;
   List<item> list;
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String final_Url = "https://codeforces.com/api/contest.list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EarthquakeAsync earth = new EarthquakeAsync();
        earth.execute();

    }
    private void updateUI(ArrayList<item> list) {
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        adapter = new customAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }


    private class EarthquakeAsync extends AsyncTask<String, Void, ArrayList<item>> {
        @Override
        protected ArrayList<item> doInBackground(String... urls) {

            URL url = createURL(final_Url);
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            ArrayList<item> l = new ArrayList<>();

            l = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return l;

        }

        @Override
        protected void onPostExecute(ArrayList<item> words) {
            if(words!=null)
                updateUI(words);
            else
                return;
        }

        private ArrayList<item> extractFeatureFromJson(String jsonResponse) {
            ArrayList<item> earthquakes = new ArrayList<>();

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {

                JSONObject root = new JSONObject(jsonResponse);
                JSONArray jsonArray = root.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String staus = object.getString("phase");
                    if(staus.equals("FINISHED"))
                         break;

                    String naam = object.getString("name");
                    if(naam.length()>=30)
                        naam = naam.substring(0,28)+"...";
                    long Stime = Long.parseLong(object.getString("startTimeSeconds"));
                    long Etime = Long.parseLong(object.getString("durationSeconds"));
                    java.util.Date time1=new java.util.Date((long)Stime*1000);
                    Etime = Etime/3600;

                    Log.i(" time1  "," "+time1);
                    String display1 = String.valueOf(time1);
                    String f1 = display1.substring(4,7);
                    String f2 = display1.substring(4,7);
                    String d1 = display1.substring(8,10);
                    String d2 = display1.substring(8,10);
                    String exT1 = display1.substring(11,16);
                    String exT2 = String.valueOf(Long.parseLong(display1.substring(11,13))+Etime)+display1.substring(13,16);;

                    item it = new item(staus,d1,f1,d2,f2,exT1,exT2,naam);
                    earthquakes.add(it);

                }


            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            return earthquakes;
        }

    }

    private URL createURL(String final_url) {
        URL url = null;
        try {
            url = new URL(final_url);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }


    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}

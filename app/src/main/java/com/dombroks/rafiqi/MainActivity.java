package com.dombroks.rafiqi;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.dombroks.rafiqi.Model.PrayerTimes;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private TextView TextView;
    private PrayerTimes Times;

    private TextView salat;
    private TextView salatTime;
    private TextView remainingTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView = findViewById(R.id.datahi);

        salat = findViewById(R.id.prayerName);
        salatTime = findViewById(R.id.adhanTime);
        remainingTime = findViewById(R.id.remainingTime);

        Times = getPrayerTimes("Algeria", "Algiers");


    }

    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        return time;

    }

    public PrayerTimes getPrayerTimes(String country, String city) {

        final String END_POINT = "http://api.aladhan.com/v1/timingsByCity?" +
                "city=" + city + "&country=" + country;

        final PrayerTimes prayerTimes = new PrayerTimes();


        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(END_POINT)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Something went wrong, please check your internet..", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final List<String> timings = extractDataFromJson(response);
                //Assign work to new thread to dispatch the work
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        prayerTimes.setFajr(timings.get(0));
                        prayerTimes.setSunrise(timings.get(1));
                        prayerTimes.setDhuhr(timings.get(2));
                        prayerTimes.setAsr(timings.get(3));
                        prayerTimes.setSunset(timings.get(4));
                        prayerTimes.setMaghrib(timings.get(5));
                        prayerTimes.setIsha(timings.get(6));
                        prayerTimes.setImsak(timings.get(7));
                        prayerTimes.setMidnight(timings.get(8));


                    }
                }).start();


            }

        });

        return prayerTimes;

    }

    public List<String> extractDataFromJson(Response response) {
        String[] prayers = {"Fajr", "Sunrise", "Dhuhr", "Asr", "Sunset", "Maghrib", "Isha", "Imsak", "Midnight"};
        List<String> prayertimes = new ArrayList<>();
        String data = null;
        String Prayer;
        try {
            data = response.body().string();
            JSONObject object = new JSONObject(data);
            for (int i = 0; i < prayers.length; i++) {
                Prayer = object.getJSONObject("data")
                        .getJSONObject("timings")
                        .getString(prayers[i]);

                prayertimes.add(Prayer);
            }


        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }


        return prayertimes;

    }


}





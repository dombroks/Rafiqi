package com.dombroks.rafiqi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dombroks.rafiqi.Model.PrayerTimes;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Savepoint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    public PrayerTimes Times;
    private Intent otherActivity;
    private TextView salat, salatTime, remainingTime, place;
    private ImageView nextSalat, previousSalat;
    private String currentSalatTime, currentSalatName;
    private String city, country, willaya;
    private ImageView qubla, sallat, quran, dua, assmaa, calendar, tassbih, chahadaa;
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Intent intent = getIntent();
        city = intent.getStringExtra("city");
        willaya = intent.getStringExtra("wilaya");
        country = intent.getStringExtra("country");

        if (city != null && willaya != null && country != null) {
            storeData(city, country, willaya);
        }


        place = findViewById(R.id.place);
        salat = findViewById(R.id.prayerName);
        salatTime = findViewById(R.id.adhanTime);
        remainingTime = findViewById(R.id.remainingTime);
        nextSalat = findViewById(R.id.switchToRight);
        previousSalat = findViewById(R.id.switchToLeft);
        qubla = findViewById(R.id.qubla);
        quran = findViewById(R.id.quran);
        tassbih = findViewById(R.id.tassbih);
        assmaa = findViewById(R.id.asmaa);
        sallat = findViewById(R.id.salat);
        calendar = findViewById(R.id.calendar);
        dua = findViewById(R.id.dua);
        chahadaa = findViewById(R.id.shahada);


        place.setText(getData("city") + ", " + getData("willaya") + ", " + getData("country"));

        Times = getPrayerTimes(getData("country"), getData("city"));

        tassbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherActivity = new Intent(MainActivity.this, tassbih.class);
                startActivity(otherActivity);
            }
        });
        chahadaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherActivity = new Intent(MainActivity.this, chahada.class);
                startActivity(otherActivity);
            }
        });
        quran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherActivity = new Intent(MainActivity.this, Quran.class);
                startActivity(otherActivity);
            }
        });

    }

    public String getLocalTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat.format(new Date());
        return time;

    }

    public PrayerTimes getPrayerTimes(String country, String city) {

        final int[] i = {0, 1, 2, 3, 4};
        final String[] prayers = {"Fajr", "Dhuhr", "Asr", "Maghrib", "Isha"};

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
                    @Override
                    public void run() {
                        //set data.
                        prayerTimes.setFajr(timings.get(0));
                        prayerTimes.setSunrise(timings.get(1));
                        prayerTimes.setDhuhr(timings.get(2));
                        prayerTimes.setAsr(timings.get(3));
                        prayerTimes.setSunset(timings.get(4));
                        prayerTimes.setMaghrib(timings.get(5));
                        prayerTimes.setIsha(timings.get(6));
                        prayerTimes.setImsak(timings.get(7));
                        prayerTimes.setMidnight(timings.get(8));

                        //fix current salat.
                        currentSalatTime = prayerTimes.getFajr();
                        currentSalatName = prayers[i[0]];

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                salat.setText(currentSalatName);
                                salatTime.setText(prayerTimes.getFajr());
                                remainingTime.setText(getRemainingTime(currentSalatTime, getLocalTime()));
                            }
                        });

                        nextSalat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                salatTime.setText(nextSalatTime(currentSalatTime, prayerTimes));
                                currentSalatTime = nextSalatTime(currentSalatTime, prayerTimes);
                                salat.setText(nextSalatName(currentSalatName));
                                currentSalatName = nextSalatName(currentSalatName);
                                remainingTime.setText(getRemainingTime(currentSalatTime, getLocalTime()));


                            }
                        });
                        previousSalat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                salatTime.setText(previousSalatTime(currentSalatTime, prayerTimes));
                                currentSalatTime = previousSalatTime(currentSalatTime, prayerTimes);
                                salat.setText(previousSalatName(currentSalatName));
                                currentSalatName = previousSalatName(currentSalatName);
                                remainingTime.setText(getRemainingTime(currentSalatTime, getLocalTime()));
                            }
                        });
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

    public String nextSalatTime(String currentSalat, PrayerTimes times) {
        String next = "";
        if (currentSalat == times.getAsr()) {
            next = times.getMaghrib();
        } else if (currentSalat == times.getMaghrib()) {
            next = times.getIsha();
        } else if (currentSalat == times.getIsha()) {
            next = times.getFajr();
        } else if (currentSalat == times.getFajr()) {
            next = times.getDhuhr();
        } else if (currentSalat == times.getDhuhr()) {
            next = times.getAsr();
        }

        return next;

    }

    public final String nextSalatName(String currentSalatName) {
        String nextName = "";

        switch (currentSalatName) {
            case "Fajr":
                nextName = "Dhuhr";
                break;
            case "Dhuhr":
                nextName = "Asr";
                break;
            case "Asr":
                nextName = "Maghrib";
                break;
            case "Maghrib":
                nextName = "Isha";
                break;
            case "Isha":
                nextName = "Fajr";
                break;
        }

        return nextName;
    }

    public final String previousSalatName(String currentSalatName) {
        String previousName = "";

        switch (currentSalatName) {
            case "Fajr":
                previousName = "Isha";
                break;
            case "Dhuhr":
                previousName = "Fajr";
                break;
            case "Asr":
                previousName = "Dhuhr";
                break;
            case "Maghrib":
                previousName = "Asr";
                break;
            case "Isha":
                previousName = "Maghrib";
                break;
        }

        return previousName;
    }

    public String previousSalatTime(String currentSalat, PrayerTimes times) {
        String previous = "";
        if (currentSalat == times.getAsr()) {
            previous = times.getDhuhr();
        } else if (currentSalat == times.getMaghrib()) {
            previous = times.getAsr();
        } else if (currentSalat == times.getIsha()) {
            previous = times.getMaghrib();
        } else if (currentSalat == times.getFajr()) {
            previous = times.getIsha();
        } else if (currentSalat == times.getDhuhr()) {
            previous = times.getFajr();
        }

        return previous;

    }

    public String getRemainingTime(String currentSalatTime, String currentTime) {
        long remaining = 0;
        currentSalatTime.replace(":", "");
        currentTime.replace(":", "");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date now = null;
        Date current = null;

        try {
            current = format.parse(currentSalatTime);
            now = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (String.valueOf(current.getTime()) != null && String.valueOf(now.getTime()) != null) {
            remaining = current.getTime() - now.getTime();
        } else
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

        long inMin = (remaining / 1000) / 60;
        return String.valueOf((convert_to_hours_minutes(inMin)));
    }

    public String convert_to_hours_minutes(long t) {
        String H = "";
        String M = "";
        long hours = 0;
        long minutes = 0;
        boolean SalatPassed = false;
        if (t < 0) {
            SalatPassed = true;
        }

        hours += t / 60;
        minutes = t % 60;

        if (minutes < 0) {
            minutes += 60;
        }

        if (minutes < 10) {
            M = "0" + minutes;
        } else
            M = String.valueOf(minutes);

        if (SalatPassed) {
            hours = hours * (-1) + 1;
            hours = 24 - hours;
        }
        if (hours < 10) {
            H = "0" + hours;
        } else
            H = "" + hours;

        return String.valueOf(H + "H " + M + " M");

    }

    public void setLoadingDialog() {
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        }, 5000);

    }

    public void storeData(String City, String Country, String Willay) {
        SharedPreferences sharedPreferences = getSharedPreferences("mFile", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("city", City);
        editor.putString("willaya", Willay);
        editor.putString("country", Country);
        editor.apply();


    }

    public String getData(String Key) {
        SharedPreferences preferences = getSharedPreferences("mFile", Context.MODE_PRIVATE);
        String value = preferences.getString(Key, "");
        return value;
    }


}





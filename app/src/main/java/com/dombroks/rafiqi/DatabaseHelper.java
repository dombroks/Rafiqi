package com.dombroks.rafiqi;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 5;
    private static String DB_NAME = "data.db";
    private static String DB_PATH;
    private final Context mContext;
    private SQLiteDatabase mDataBase;


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
        this.DB_PATH = "/data/data/" + context.getApplicationContext().getPackageName() + "/databases/";

        this.mContext = context;


    }

    private boolean checkDataBase() {
        try {


            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.getMessage();
            return false;
        }
    }


    private void copyDB() throws IOException {

        try {
            InputStream mInput = mContext.getAssets().open(DB_NAME);
            OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0)
                mOutput.write(mBuffer, 0, mLength);
            mOutput.flush();
            mOutput.close();
            mInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDB() throws IOException {
        boolean mDatabaseExist = checkDataBase();
        if (!mDatabaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDB();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            } finally {
                this.close();
            }
        }
    }


    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public String laodQuery() {
        try {
            createDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = "";
        SQLiteOpenHelper dbHelper;
        SQLiteDatabase db = this.getWritableDatabase();
        int SuraIndex = 1;

        Cursor c = db.rawQuery("select text from quran_text where sura ='5'", null);

        while (c.moveToNext()) {
            String AyaEnd = "﴿" + SuraIndex + "﴾";
            result += c.getString(0) + AyaEnd;
            SuraIndex++;
        }
        c.close();
        db.close();
        return result;

    }

}
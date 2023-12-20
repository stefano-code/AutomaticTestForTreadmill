package com.android.inverter.test.testsuite.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


import com.android.inverter.test.testsuite.db.InverterTestSuiteDB;
import com.android.inverter.test.testsuite.db.InverterTestSuiteDBHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class InverterTestSuiteCP extends ContentProvider
{
    private static final String TAG = "InverterTestSuiteCP";

    private InverterTestSuiteDBHelper dbHelper;

    @Override
    public boolean onCreate()
    {
        File f = new File(InverterTestSuiteDB.getAbsolutePathName(getContext()));
        if (!f.exists())
        {
            Log.d(TAG, "WARNING: " + f.getAbsolutePath() + " doesn't exists!!!");

            Log.e("TEST_1","D1");
            copyFromAsset(getContext(), InverterTestSuiteDB.DB_NAME);
        }
        else if (oldVersionDB())
        {
            Log.e("TEST_1","D10");
            Log.d(TAG, "oldVersionDB ");
            copyFromAsset(getContext(), InverterTestSuiteDB.DB_NAME);
        }
        else
        {
            Log.e("TEST_1","D20");
            Log.d(TAG, "found database, path=" + f.getAbsolutePath() + " length=" + f.length());
        }

        Log.e("TEST_1","D30");
        dbHelper = new InverterTestSuiteDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder)
    {
        String table = getTableName(uri);
        if (table == null)
        {
            Log.e(TAG, "table null URI " + uri.toString());
            return null;
        }

        SQLiteDatabase db = null;
        Cursor cursor = null;

        db = dbHelper.getReadableDatabase();
        cursor = db.query(table, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        String table = getTableName(uri);
        if (table == null)
            return null;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newId = db.insert(table, null, values);

        Uri newUri = Uri.withAppendedPath(uri, "/" + newId);
        getContext().getContentResolver().notifyChange(uri, null);

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        String table = getTableName(uri);
        if (table == null)
            return 0;

        SQLiteDatabase db = null;
        int numDeleted = 0;

        db = dbHelper.getWritableDatabase();
        numDeleted = db.delete(table, selection, selectionArgs);

        if (numDeleted > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        String table = getTableName(uri);
        if (table == null)
            return 0;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numUpdated = db.update(table, values, selection, selectionArgs);

        if (numUpdated > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

    private String getTableName(Uri uri)
    {
        if (uri == null)
            return null;
        if (uri.toString().contentEquals(InverterTestSuiteMetaData.URI_UNIT_TEST_TABLE.toString()))
            return InverterTestSuiteMetaData.UNIT_TEST_TABLE;

        return null;
    }

    private boolean oldVersionDB()
    {
        Log.d(TAG, "oldVersionDB ");
        SQLiteDatabase db = null;

        boolean res = false;

        try
        {
            String myPath = InverterTestSuiteDB.getAbsolutePathName(getContext());
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            int version = db.getVersion();
            int newVersion = InverterTestSuiteDB.DB_VERSION;
            Log.d(TAG, "UPGRADE version " + version + " newVersion " + newVersion);
            if (newVersion > version)
                res = true;
        } catch (Exception e)
        {
            res = true;
            e.printStackTrace();
        } finally
        {

            if (db != null)
                db.close();

            return res;
        }
    }

    private void copyFromAsset(Context context, String filename)
    {
        Log.e("TEST_1","D2 " + filename);
        String destinationPath = InverterTestSuiteDB.getAbsolutePathName(context);    //SSCrossPlatformUtil.getTgPath() + "/";

        Log.e("TEST_1","D4 " + destinationPath);
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        FileOutputStream out = null;
        try
        {
            Log.e("TEST_1","D6");
            in = assetManager.open(filename);
            //SS File outFile = new File(destinationPath);
            Log.e("TEST_1","D7");
            //SS out = new FileOutputStream(outFile);
            out = new FileOutputStream(destinationPath);
            Log.d(TAG, "inizio copia:" + destinationPath);
            byte[] buffer = new byte[50*1024];
            int read;
            int i = 0;
            Log.e("TEST_1","D8");
            while ((read = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, read);
                i++;
            }
            Log.d(TAG, "fine copia " + i);
            in.close();
            in = null;
            out.flush();
            out.getFD().sync();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }
    }

}

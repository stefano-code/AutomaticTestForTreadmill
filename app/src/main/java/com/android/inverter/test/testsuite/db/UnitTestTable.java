package com.android.inverter.test.testsuite.db;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.android.inverter.test.testsuite.cp.InverterTestSuiteMetaData;

import java.util.ArrayList;

public class UnitTestTable
{
    private ContentResolver cr;

    public UnitTestTable(Context ctx)
    {
        cr = ctx.getContentResolver();
    }

    public int clear(String name, String type)
    {
        String selection = InverterTestSuiteMetaData.NAME + " = ? and " + InverterTestSuiteMetaData.TYPE + " = ?";
        String[] selectionArgs = { name, type };;
        return cr.delete(InverterTestSuiteMetaData.URI_UNIT_TEST_TABLE, selection, selectionArgs);
    }

    public void update(Record record)
    {
        ContentValues cv =new ContentValues();
        cv.put(InverterTestSuiteMetaData.NAME, record.name);
        cv.put(InverterTestSuiteMetaData.TYPE, record.type);
        cv.put(InverterTestSuiteMetaData.SCRIPT_STARTUP, record.script_startup);
        cv.put(InverterTestSuiteMetaData.SCRIPT_BODY, record.script_body);
        cv.put(InverterTestSuiteMetaData.SCRIPT_TEARDOWN, record.script_teardown);
        //SS cr.insert(InverterTestSuiteMetaData.URI_UNIT_TEST_TABLE, cv);

        String selection = InverterTestSuiteMetaData.NAME + " = ? and " + InverterTestSuiteMetaData.TYPE + " = ?";
        String[] selectionArgs = { record.name, record.type };

        int res = cr.update(InverterTestSuiteMetaData.URI_UNIT_TEST_TABLE, cv, selection, selectionArgs);
        if(res <= 0)
            cr.insert(InverterTestSuiteMetaData.URI_UNIT_TEST_TABLE, cv);
    }

    public void add(Record record)
    {
        try
        {
            ContentValues cv =new ContentValues();
            cv.put(InverterTestSuiteMetaData.NAME, record.name);
            cv.put(InverterTestSuiteMetaData.TYPE, record.type);
            cv.put(InverterTestSuiteMetaData.SCRIPT_STARTUP, record.script_startup);
            cv.put(InverterTestSuiteMetaData.SCRIPT_BODY, record.script_body);
            cv.put(InverterTestSuiteMetaData.SCRIPT_TEARDOWN, record.script_teardown);
            cr.insert(InverterTestSuiteMetaData.URI_UNIT_TEST_TABLE, cv);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Record> getAll()
    {
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;

        ArrayList<Record> res = new ArrayList<>();
        Cursor c = cr.query(InverterTestSuiteMetaData.URI_UNIT_TEST_TABLE, projection, selection, selectionArgs, null);
        if(c != null)
        {

            while (c.moveToNext())
            {

                    res.add(new Record(
                            c.getString(c.getColumnIndex(InverterTestSuiteMetaData.NAME)),
                            c.getString(c.getColumnIndex(InverterTestSuiteMetaData.TYPE)),
                            c.getString(c.getColumnIndex(InverterTestSuiteMetaData.SCRIPT_STARTUP)),
                            c.getString(c.getColumnIndex(InverterTestSuiteMetaData.SCRIPT_BODY)),
                            c.getString(c.getColumnIndex(InverterTestSuiteMetaData.SCRIPT_TEARDOWN))));

            }
            c.close();
        }
            return res;
    }
}

package com.android.inverter.test.testsuite.db;

import android.content.Context;
import android.os.Environment;

public class InverterTestSuiteDB
{
    private InverterTestSuiteDB()
    {
    }

    public static final String DB_NAME = "InverterTestSuite.db";
    //SS public static final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath(); //SS"/mnt/sdcard/technogym/";
    //SS private static final String DB_ABSOLUTE_PATH = DB_PATH + "/" + DB_NAME;
    public static final int DB_VERSION = 2;

    public static String getAbsolutePathName(Context ctx)
    {
        return "/data/data/" + ctx.getPackageName() + "/" + DB_NAME;
    }
}

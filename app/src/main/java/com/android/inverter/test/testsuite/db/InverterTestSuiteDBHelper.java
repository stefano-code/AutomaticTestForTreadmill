package com.android.inverter.test.testsuite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class InverterTestSuiteDBHelper extends SQLiteOpenHelper
{
    private static final String TAG = "InverterTestSuiteDBHelper";

    public InverterTestSuiteDBHelper(Context context)
    {
        super(context, InverterTestSuiteDB.getAbsolutePathName(context), null, InverterTestSuiteDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(TAG, "CREATE " + db.getPath() + " version " + db.getVersion());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(TAG, "CREATE " + db.getPath() + " old version " + oldVersion + " newVersion " + newVersion);
    }
}

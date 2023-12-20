package com.android.inverter.test.testsuite.cp;

import android.net.Uri;

public class InverterTestSuiteMetaData
{
    public static final String AUTHORITY = "com.android.inverter.test.testsuite.InverterTestSuite.cp.AUTHORITY";

    public static final Uri URI_UNIT_TEST_TABLE = Uri.parse("content://"+AUTHORITY+"/UnitTestTable");

    public static final String UNIT_TEST_TABLE = "UnitTestTable";

    public static final String NAME = "name";
    public static final String TYPE = "classe";
    public static final String SCRIPT_STARTUP = "script_startup";
    public static final String SCRIPT_BODY = "script_body";
    public static final String SCRIPT_TEARDOWN = "script_teardown";
}

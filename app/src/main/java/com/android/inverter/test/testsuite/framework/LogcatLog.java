package com.android.inverter.test.testsuite.framework;

import android.util.Log;

public class LogcatLog implements EngineLog
{
    @Override
    public void add(String s)
    {
        Log.e( "SCRIPT", s );
    }
}

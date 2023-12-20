package com.android.inverter.test.testsuite.framework;


import android.content.Context;

import com.android.inverter.test.testsuite.framework.stub.Equipment;


public class Treadmill
{
    private final static double EPSILON = 0.1;
    private final Context ctx;
    private final EngineLog log;

    public Treadmill(Context ctx, EngineLog log)
    {
        this.ctx = ctx;
        this.log = log;
    }

    public Treadmill Set(Setting s, double value )
    {
        log.add( "setting " + s.name() + " to " + value);
        s.setValue( value ) ;
        return this;
    }

    public Treadmill Start()
    {
        log.add( "starting motor");

        Equipment.getInstance(ctx).start();

        return this;
    }

    public Treadmill Stop()
    {
        log.add( "stopping motor");

        Equipment.getInstance(ctx).stop();

        return this;
    }

    public Treadmill Wait(int seconds ) throws InterruptedException
    {
        log.add( "waiting " + seconds + " second[s]");

        Thread.sleep( seconds * 1000);
        return this;
    }


    public Treadmill TestEqual(Setting s, double expectedValue ) throws Exception
    {
        log.add( "checking " + s.name() + " equal to " + expectedValue);
        double value = s.getValue();
        if( Math.abs( value - expectedValue ) > EPSILON )
            onError( s.name(), value, expectedValue );
        return this;
    }

    private void onError(String name, double value, double expectedValue) throws Exception
    {
        throw new Exception( "Wrong value for " + name + ": expecting " + expectedValue + " but got " + value);
    }

}

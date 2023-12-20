package com.android.inverter.test.testsuite.framework;

import android.content.Context;

public class SettingMaxSpeed extends Setting
{
    public SettingMaxSpeed(Context ctx)
    {
        super(ctx, "MaxSpeed");
    }

    @Override
    public void setValue(double value)
    {
        eq.set("MaxSpeed", value);
    }

    @Override
    public double getValue()
    {
        return eq.get("MaxSpeed");
    }
}

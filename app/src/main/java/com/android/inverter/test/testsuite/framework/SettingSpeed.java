package com.android.inverter.test.testsuite.framework;

import android.content.Context;

public class SettingSpeed extends Setting
{
    public SettingSpeed(Context ctx)
    {
        super(ctx, "Speed");
    }

    @Override
    public void setValue(double value)
    {
        eq.set("Speed", value);
    }

    @Override
    public double getValue()
    {
        return eq.get("Speed");
    }
}
